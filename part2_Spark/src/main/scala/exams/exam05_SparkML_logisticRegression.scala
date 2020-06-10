package com.spark.exams

  /*
   * 문) iris_libsvm.txt 파일을 이용하여 로지스틱 선형회귀모델을 생성하시오. 
   * y변수 : label 
   * x변수 : features
   * train/test split 비율 - 70 : 30 
   * train set : model 생성 
   * test set : model 평가(accuracy, f1 score)   
   */

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.feature.VectorAssembler // x,y 변수 선택 
import org.apache.spark.ml.classification.{LogisticRegression, LogisticRegressionModel} // model, save/load 
import org.apache.spark.ml.{Pipeline, PipelineModel} // pipeline model, save/load
import org.apache.spark.ml.evaluation.MulticlassClassificationEvaluator // model 평가

object exam05_SparkML_logisticRegression {
  def main(args: Array[String]) {

    val spark = SparkSession
      .builder()
      .appName("Logistic_PipelineSample")
      .master("local[*]")
      .getOrCreate()

    // 1. data set Load
    val data = spark.read.format("libsvm")
      .load("src/main/resources/iris_libsvm.txt") 
    data.show() // label, features 확인 
          

    // 2. train/test split  
    val Array(train,test) =  data.randomSplit(Array(0.7,0.3), seed=123)
    
    val assemble_train = new VectorAssembler()
                         .setInputCols(Array("features")) // x변수 선택
                         .setOutputCol("feature") // x변수
    val trainset = assemble_train.transform(train)
    trainset.show()
    

    
    // 3. model 생성 환경 : 로지스틱 회귀 모델 
    val lr = new LogisticRegression()
         .setMaxIter(10)
         .setRegParam(0.01)
         .setLabelCol("label") // y변수
         .setFeaturesCol("feature") // x변수
    val model = lr.fit(trainset) // model 생성
    
    
        
    // 4. model 평가 : 이항/다항 모두 적용 가능 
    val assemble_test = new VectorAssembler()
                     .setInputCols(Array("features")) // x변수 선택
                     .setOutputCol("feature") // x변수
    val testset = assemble_train.transform(test)


    // model 예측치 
    val pred = model.transform(testset)
    pred.show()
    
    pred.select("label","prediction").show(150)
    
    // 이항 or 다항 분류 평가
    val evaluator = new MulticlassClassificationEvaluator()
                        .setLabelCol("label")
                        .setPredictionCol("prediction")
                        
    val acc = evaluator.setMetricName("accuracy").evaluate(pred)
    val f1 = evaluator.setMetricName("f1").evaluate(pred)
    println(s"acc = ${acc}, f1 = ${f1}")
    // acc = 0.98, f1 = 0.97993265993266
    
    spark.stop()
  }
  
}









