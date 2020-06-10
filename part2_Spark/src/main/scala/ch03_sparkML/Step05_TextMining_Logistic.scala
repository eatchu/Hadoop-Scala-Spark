package com.spark.ch03_sparkML

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.VectorAssembler // x,y 변수 선택 
import org.apache.spark.ml.feature.StringIndexer
import org.apache.spark.ml.classification.{LogisticRegression} // model, save/load 
import org.apache.spark.ml.{Pipeline, PipelineModel} // pipeline model, save/loadimport org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가
import org.apache.spark.ml.feature.{RegexTokenizer, StopWordsRemover, IDF, CountVectorizer}


object Step05_TextMining_Logistic {
    
    def main(args:Array[String]) : Unit = {
    
    // SparkSession 객체 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
    // 1. dataset load
    val df = spark.read
         .format("csv")
         .option("header","false")
         .option("delimiter",",")
         .option("inferSchema","true")
         .load("src/main/resources/sms_spam_data.csv")            
     
    df.show(false)     
    
    // 2. StringIndexer : String -> dummy변수
    val idx = new StringIndexer()
              .setInputCol("_c0")
              .setOutputCol("label")
    
    // fit() : model -> transform() : old DF -> new DF
    val sms_data_label = idx.fit(df).transform(df)
    sms_data_label.show()
    
    // 3. Regextokenizer : 정규표현식 이용
    val tokenizer = new RegexTokenizer()
                    .setInputCol("_cl")
                    .setOutputCol("words")
                    .setPattern("\\w+")
    // old -> new
    val tokenizered = tokenizer.transform(sms_data_label)
    tokenizered.show()
    
    
    // 4. StopWordsRemover : 단어에 포함된
    val stopWords = new StopWordsRemover()
                    .setInputCol("words")
                    .setOutputCol("terms")
    
    // old -> new
    val newData = stopWords.transform(tokenizered)
    newData.select("words","terms").show()
    
    
    // 5. CountVectorizer :  word -> 고유번호, 단어 길이 제한
    val countVec = new CountVectorizer()
                   .setInputCol("terms")
                   .setOutputCol("countVec")
                   .setVocabSize(4000)
    // fit() : model -> transform() : old -> new DF
    val newDataCont = countVec.fit(newData).transform(newData)
    newDataCont.show()   
    
    // 6. IDF : 단어 출현빈도수에 대한 가중치 (TFiDF)
    val tfidf = new IDF().setInputCol("countVec")
    .setOutputCol("tfidfVec")  // features
    
    
    val tfidf_data = tfidf.fit(newDataCont)  // model 만들고
    .transform(newDataCont)  // ?
    println("[print created features]")
    tfidf_data.show(false)  // | _c0|    _c1|label|  words|   terms| countVec|  tfidfVec| 가중치의 결과는 비율로 나옴
    
    // label -> y변수, tfidfVec -> x 변수(features)
    tfidf_data.select("label", "tfidfVec").show(false)
    
    
    
    
    // 7. features 생성
    val assemble = new VectorAssembler().setInputCols(Array("tfidfVec")) // x 변수 선택
    .setOutputCol("features")  // x 변수 -> features 지정
    
    // old DF -> new DF
    val data = assemble.transform(tfidf_data)
    println("[show data including label and feature]")
    data.show()
    data.select("label", "features").show()  // libsvm file이 됐음
    
    
    
    
    // 8. train/test split
    val Array(train, test) = data.randomSplit(Array(0.8, 0.2))
    
    
    // 9. model 생성
    
    
    
    
    
    
    spark.close()
    }              
}