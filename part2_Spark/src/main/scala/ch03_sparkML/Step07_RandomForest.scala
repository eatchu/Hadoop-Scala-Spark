package com.spark.ch03_sparkML

import org.apache.spark.sql.SparkSession // Dataframe 생성
import org.apache.spark.ml.classification.{DecisionTreeClassifier} // tree model 생성
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가
import org.apache.spark.mllib.evaluation.MulticlassMetrics // confusion matirx
import org.apache.spark.ml.feature.{StringIndexer,VectorAssembler}
import org.apache.spark.ml.classification.RandomForestClassificationModel
import org.apache.spark.ml.classification.RandomForestClassifier


object Step07_RandomForest {
  
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
    sIndexerDF.show()
    
    
    // 3. features 생성 : Sunshine|WindGustSpeed|Humidity|Pressure -> features
    val assembler = new VectorAssembler()
                    .setInputCols(Array("Sunshine","WindGustSpeed","Humidity","Pressure"))
                    .setOutputCol("features")
    val weather_df = assembler.transform(sIndexerDF)
    weather_df.show()
                   
  
    // 4. train/test split
    val Array(train,test) = weather_df.randomSplit(Array(0.7,0.3))
    
    // 5. model 생성
    val rf = new RandomForestClassifier()
             .setLabelCol("label")
             .setFeaturesCol("features")
             .setNumTrees(10)
    val model = rf.fit(train)
    val pred = model.transform(test)
    pred.show()
    pred.select("label","prediction","features").show()
    
             
    // 6. model 평가
    // 1) accuracy
    val evaluator = new MulticlassClassificationEvaluator()
                       .setLabelCol("label")
                       .setPredictionCol("prediction")
                       .setMetricName("accuracy")
    val acc = evaluator.evaluate(pred)
    println(s"accuracy : ${acc}")
    // 2) confusion matrix
    import spark.implicits._
    
    val pred_rdd = pred.select("label","prediction").as[(Double,Double)].rdd
    val con_max = new MulticlassMetrics(pred_rdd)
    println("교차분할표")
    println(con_max.confusionMatrix)
    
    
    // 7. model save/load
    val path = "C:/hadoop-2.6.0/rfModel"
    model.write.overwrite().save(path)
    println("model saved")
    
    // model load
    val rmModel = RandomForestClassificationModel.load(path)
    rmModel.transform(test).show()
  }
}




