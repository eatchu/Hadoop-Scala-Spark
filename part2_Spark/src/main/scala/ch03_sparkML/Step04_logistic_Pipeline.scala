package com.spark.ch03_sparkML


import org.apache.spark.sql.SparkSession // df 생성
import org.apache.spark.ml.feature.VectorAssembler // x,y 변수 선택 
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel} // model, save/load 
import org.apache.spark.ml.{Pipeline, PipelineModel} // pipeline model, save/load
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가




object Step04_logistic_Pipeline {
  
      def main(args:Array[String]) : Unit = {
    
    // SparkSession 객체 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
    
    // 1. dataset 생성            
    import spark.implicits._ // scala -> df
    
    // 1) train set : 키, 몸무게, 나이, 성별(y)
    val train = List((171,68.65,29,1),
                     (175,74.5,35,1),
                     (159,58.6,29,0)).toDF("height","weight","age","gender")
    train.show()
                        
    //2) test set : 키, 몸무게, 나이, 성별(y)   
    val test = List((169,65.0,35,1),
                    (161,52.0,29,0),
                    (171,70.5,25,1)).toDF("height","weight","age","gender")
    test.show()
    
    
    // 2. assembler 생성 : features
    val assembler = new VectorAssembler()
                         .setInputCols(Array("height","weight","age")) // x변수 선택
                         .setOutputCol("features") // x변수
    
    
    // 3. model 생성
    val lr = new LogisticRegression()
             .setMaxIter(10)
             .setRegParam(0.01)
             .setLabelCol("gender") // y변수
             .setFeaturesCol("features") // x변수

    // 4. pipeline model : step1 : features -> step2 : Ir_model
    val pipeline = new Pipeline().setStages(Array(assembler,lr))
    
    // model 생성
    val pipelineModel = pipeline.fit(train) //model
    
    // model 평가
    val pred = pipelineModel.transform(test)
    pred.show()
    
    
    // 5. pipeline model save/load
    val path = "C:/hadoop-2.6.0/pipeModel"
    pipelineModel.write.overwrite().save(path)
    println("model save")
    
    // load
    val new_pipeline = PipelineModel.load(path)
    val new_pred = new_pipeline.transform(train)
    new_pred.show()
    
    
    spark.close()
             
      }
}















