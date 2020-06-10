package com.spark.exams

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.{Pipeline, PipelineModel}
// label, features 전처리 
import org.apache.spark.ml.feature.{StringIndexer, VectorAssembler} 
// model 생성 & save
import org.apache.spark.ml.classification.RandomForestClassifier
import org.apache.spark.ml.classification.RandomForestClassificationModel
// model 평가 
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가  
import org.apache.spark.mllib.evaluation.MulticlassMetrics // 교차분할표 

/*
 * 문) 다음과 같은 단계별로 Pipeline 모델을 생성하시오.  
 */
object exam06_SparkML_randomForest {
    
  def main(args: Array[String]): Unit = {
    
    val spark = SparkSession
      .builder()
      .appName("exam06_SparkML_randomForest")
      .master("local[*]")
      .getOrCreate()
      
    // 단계1. dataset load   
    val filePath = "src/main/resources"  
    val df = spark.read
      .option("header", "true")
      .option("inferSchema", "true") // raw data type 적용 
      .csv(filePath + "/iris.csv")    
    
    // column 이름 변경 
    val newName = Seq("Sepal_Length","Sepal_Width","Petal_Length","Petal_Width","Species")
    val newDF = df.toDF(newName: _*)
           
    newDF.printSchema()    
    newDF.show()
    
    // 단계2. label 칼럼 생성 : Species 컬럼 이용 
        
   
    // 단계3. features 칼럼  생성 : "Sepal_Length","Sepal_Width","Petal_Length","Petal_Width" 컬럼 이용  
   
        
    // 단계4. Split 70% vs 30% : weather_data -> data 수정 
    
    
    // 단계5. model 생성  : RandomForestClassifier 클래스 이용 
    
    
    // 단계6. pipeline model : step(label -> feature -> model) 
     
        
    // 단계7. pipeline model 평가 : accuracy, confusion matrix
    
  }
}