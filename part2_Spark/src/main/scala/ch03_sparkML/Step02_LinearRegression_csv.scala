package com.spark.ch03_sparkML

import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.regression.LinearRegression
import org.apache.spark.ml.feature.VectorAssembler // x,y 변수 선택
import org.apache.spark.ml.evaluation.RegressionEvaluator

/*
 * csv file + Linear Regression model
 */

object Step02_LinearRegression_csv {
  
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
         .load("src/main/resources/iris.csv")
            
    // 칼럼명 변경    
    val colNames = Seq("Sepal_Length","Sepal_Width","Petal_Length","Petal_Width","Species")
    val iris_df = df.toDF(colNames:_*)
    iris_df.show()
    
    // x,y 변수 생성 : y(Sepal_Length), x(나머지 3개)
    val iris_xy = iris_df.drop("Species") // 칼럼제거
    iris_xy.show()
    
    
    
    // 2. label, features 생성 : VectorAssembler
    val assemble = new VectorAssembler()
                   .setInputCols(Array("Sepal_Width","Petal_Length","Petal_Width")) // ㅌqustn tjsxor
                   .setOutputCol("features") // x변수 -> features 지정
                       
    val data = assemble.transform(iris_xy)
    data.show(false)
    
    data.select("Sepal_Length","features").show(false)
    
    
    
    // 3. train/test split (70% vs 30%)
    val Array(train,test) =  data.randomSplit(Array(0.7,0.3), seed=123)
    
    
    
    // 4. model 생성
    val lr = new LinearRegression()
             .setMaxIter(10) // hyper parameter
             .setFeaturesCol("features") // x변수
             .setLabelCol("Sepal_Length") // y변수
             
    val model = lr.fit(train)
    println(s"기울기:${model.coefficients}, 절편:${model.intercept}")
    
    val model_summary = model.summary
    println(s"Train MSE:${model_summary.meanSquaredError}")
    println(s"Train r2 score:${model_summary.r2}")
             
    
    
    // 5. model 평가
    val pred = model.transform(test)
    pred.show()
    
    // 평가 객체 생성
    val evaluater = new RegressionEvaluator()
    
    // 평가에 사용할 칼럼 선택
    evaluater.setLabelCol("Sepal_Length").setPredictionCol("prediction")
    
    val mse = evaluater.setMetricName("mse").evaluate(pred)
    val r2_score = evaluater.setMetricName("r2").evaluate(pred)
    val rmse = evaluater.setMetricName("rmse").evaluate(pred)
    val mae = evaluater.setMetricName("mae").evaluate(pred)
    
    println("Test set 적용 model 평가 결과")
    println(s"rmse:${rmse}, mse:${mse}, mae:${mae}, r2:${r2_score}")
    // rmse:0.35527888737371266, mse:0.1262230878135032, mae:0.29288148507062084, r2:0.8295839370878721
    
    spark.close()
    }
}






