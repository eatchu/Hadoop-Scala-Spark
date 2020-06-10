package com.spark.ch03_sparkML


import org.apache.spark.sql.SparkSession // Dataframe 생성
import org.apache.spark.ml.classification.{DecisionTreeClassifier} // tree model 생성
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가
import org.apache.spark.mllib.evaluation.MulticlassMetrics // confusion matirx
import org.apache.spark.ml.feature.{StringIndexer,VectorAssembler}
import org.apache.spark.ml.classification.RandomForestClassificationModel
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.Pipeline


object Step08_RandomForest2 {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
     
    // 1. dataset load            
    val df = spark.read
             .format("csv")
             .option("header","true")
             .option("delimiter",",")
             .option("inferSchema","ture")   
             .load("src/main/resources/weather.csv")
               
    df.show()   
    df.printSchema()
    
    // 2. label 생성 : RainTomorrow -> yes/no -> dummy(1/8)
    val sIndexer = new StringIndexer()
              .setInputCol("RainTomorrow")
              .setOutputCol("label")
              
    val sIndexerDF = sIndexer.fit(df).transform(df)
    
    
    // 3. features 생성 
    val assembler = new VectorAssembler()
                    .setInputCols(Array("Sunshine","WindGustSpeed","Humidity","Pressure"))
                    .setOutputCol("features")
    val weather_df = assembler.transform(sIndexerDF)
                   
  
    // 4. train/test split
    val Array(train,test) = weather_df.randomSplit(Array(0.7,0.3))
    
    // 5. model 생성
    val rf = new RandomForestClassifier()
             .setLabelCol("label")
             .setFeaturesCol("features")
             .setNumTrees(10)

    // 6. Pipeline : step01 : label 생성 -> step02 : features 생성 -> step03 : model 생성
    val pipeline = new Pipeline().setStages(Array(sIndexer,assembler,rf))
    val pipelineModel = pipeline.fit(train)
    val pred = pipelineModel.transform(test)
    pred.show()
    
    
    
    
    
    } 
}