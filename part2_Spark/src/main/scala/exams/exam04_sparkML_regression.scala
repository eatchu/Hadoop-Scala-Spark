package com.spark.exams


import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.feature.VectorAssembler // x,y 변수 선택
import org.apache.spark.ml.evaluation.RegressionEvaluator


object exam04_sparkML_regression {
  
  /*
   * score_iq.csv 파일을 대상으로 선형회귀분석을 수행하시오.
   * - y변수 : score
   * - x변수 : iq, academy, game, tv
   * - sid 변수 제거 
   * - train/test split(8:2)
   * - model 생성 : train
   * - model 평가 : test 
   */
  
  
    def main(args:Array[String]) : Unit = {
    
    // SparkSession 객체 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
    // 1. dataset load
    val df = spark.read
         .format("csv")
         .option("header","true")
         .option("delimiter",",")
         .option("inferSchema","true")
         .load("src/main/resources/score_iq.csv")
   
    // 2. 칼럼 제거 및 x,y 변수 설정
    val score_xy = df.drop("sid")
    
    val assemble = new VectorAssembler()
               .setInputCols(Array("iq","academy","game","tv")) // ㅌqustn tjsxor
               .setOutputCol("features")
                  
    val data = assemble.transform(score_xy)
    data.show(false)              
    
    data.select("score","features").show(false)
    
  
    // 3. data split
    val Array(train,test) =  data.randomSplit(Array(0.8,0.2), seed=123)
    
        
    // 4. model 생성
    val lr = new LinearRegression()
             .setMaxIter(10) // hyper parameter
             .setFeaturesCol("features") // x변수
             .setLabelCol("score") // y변수
             
    val model = lr.fit(train)
    
    val model_summary = model.summary
    println(s"Train MSE:${model_summary.meanSquaredError}") // MSE:1.8018270468308004
    println(s"Train r2 score:${model_summary.r2}") // r2 score:0.9532999594435441
      
  
    // 5. model 평가
    val pred = model.transform(test)
    
    val evaluater = new RegressionEvaluator()
    
    evaluater.setLabelCol("score").setPredictionCol("prediction")
    
    val mse = evaluater.setMetricName("mse").evaluate(pred)
    val r2_score = evaluater.setMetricName("r2").evaluate(pred)
    val rmse = evaluater.setMetricName("rmse").evaluate(pred)
    val mae = evaluater.setMetricName("mae").evaluate(pred)
    
    println("Test set 적용 model 평가 결과")
    println(s"rmse:${rmse}, mse:${mse}, mae:${mae}, r2:${r2_score}")
    // rmse:1.416329334515348, mse:2.0059887838086885, mae:1.2436961043958465, r2:0.9641912995429347
    
    spark.close()
    
    }
}






