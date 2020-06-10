package com.spark.ch03_sparkML

/*
 * Tree model + confusion matrix
 */

import org.apache.spark.sql.SparkSession // Dataframe 생성
import org.apache.spark.ml.classification.{DecisionTreeClassifier} // tree model 생성
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가
import org.apache.spark.mllib.evaluation.MulticlassMetrics // confusion matirx




object Step06_Classification {
  
  
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
    val df = spark.read.format("libsvm")
            .load("src/main/resources/iris_libsvm.txt")
    
    df.show(false)               
                
                
    // train/test split
    val Array(train,test) =  df.randomSplit(Array(0.7,0.3))
    train.cache()
    test.cache()
    
    
    // Tree model
    val dt = new DecisionTreeClassifier()
             .setLabelCol("label")
             .setFeaturesCol("features")
    val model = dt.fit(train)
    println(s"변수의 중요도 : ${model.featureImportances}")
    

    val pred = model.transform(test)            
    pred.show()    
    
    
    
    
    //  model 평가
    val evaluator = new MulticlassClassificationEvaluator()
    .setLabelCol("label")
    .setPredictionCol("prediction")
    
    val acc = evaluator.setMetricName("accuracy").evaluate(pred)
    val f1 = evaluator.setMetricName("f1").evaluate(pred)
    
    println(s"model accuracy : ${acc}, f1 score : ${f1}")
    // model accuracy : 0.9318181818181818, f1 score : 0.9318940236626554  >> 아이리스라그런지 정확도가 좋음.
    
    // 교차분할표(confusion matrix) :  MulticlassMetrics는 mllib소속이므로, 이 아이가 사용할 수 있는 자료구조는 rdd이다.
    //impoprt spark.implicits._
    
    //val predRdd = pred.select("labbel","prediction").as[(Double,Double)].rdd
    
   // val conmat = new MulticlassMetrics(predRdd)  // RDD를 인수로.
        

                
    // 객체 닫기                
    spark.close()            
                              
  }
  

  
  
}