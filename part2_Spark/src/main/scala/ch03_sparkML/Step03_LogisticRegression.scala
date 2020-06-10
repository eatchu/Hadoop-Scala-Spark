package com.spark.ch03_sparkML

/*
 * 이항분류와 다항분류
 */


import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.VectorAssembler 
// model 생성, model save/load
import org.apache.spark.ml.classification.{LogisticRegression,LogisticRegressionModel}
import org.apache.spark.ml.evaluation.RegressionEvaluator
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator


object Step03_LogisticRegression {
  
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
    val assemble_train = new VectorAssembler()
                         .setInputCols(Array("height","weight","age")) // x변수 선택
                         .setOutputCol("features") // x변수
    val trainset = assemble_train.transform(train)
    trainset.show()
    
    
    // 3. model 생성
    val lr = new LogisticRegression()
             .setMaxIter(10)
             .setRegParam(0.01)
             .setLabelCol("gender") // y변수
             .setFeaturesCol("features") // x변수
    val model = lr.fit(trainset) // model 생성
    
    
    // 4. model 평가 : test 
    val assemble_test = new VectorAssembler()
                         .setInputCols(Array("height","weight","age")) // x변수 선택
                         .setOutputCol("features") // x변수
    val testset = assemble_train.transform(test)
    testset.show()
    
    
    // model 예측치 
    val pred = model.transform(testset)
    pred.show()
    
    pred.select("gender","prediction").show()
    
    // 이항 or 다항 분류 평가
    val evaluator = new MulticlassClassificationEvaluator()
                        .setLabelCol("gender")
                        .setPredictionCol("prediction")
                        
    val acc = evaluator.setMetricName("accuracy").evaluate(pred)
    val f1 = evaluator.setMetricName("f1").evaluate(pred)
    println(s"acc = ${acc}, f1 = ${f1}")
    // acc = 0.6666, f1 = 0.6666
    
    
    // 5. model save & load
    val path = "C:/hadoop-2.6.0/IrModel"
    // model.write.overwrite().save(path)
    println("model saved")
    
    
    // model load
    val new_Irmodel = LogisticRegressionModel.load(path)
    val result = new_Irmodel.transform(trainset)
    result.show()
    

    spark.close()
    }
}







