package com.spark.ch03_sparkML

import org.apache.spark.sql.SparkSession // DataFrame object
import org.apache.spark.ml.regression.LinearRegression // model 

/*
 * libsvm file + linear regression model
 *   - libsvm file : label(y변수), features(x변수)
 */

object Step01_LinearRegression {
  
  def main(args:Array[String]) : Unit = {
    
    // SparkSession 객체 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
    // 1. data set load
    val dataset = spark.read.format("libsvm")
                  .load("src/main/resources/iris_libsvm.txt")
    
    dataset.show(150,false)
    
    // 2. model 생성
    val lr = new LinearRegression()
    val model = lr.fit(dataset)
    
    // 3. model 결과확인/평가
    print(s"기울기:${model.coefficients}, 절편:${model.intercept}")
    // 기울기:[-0.1097,-0.04424,0.2270,0.60989] 
    // 절편:0.1920
    
    val model_summary = model.summary
    
    // 잔차(오차) : model - predict
    model_summary.residuals.show() 
    
    // mse or r2 score
    println(s"r2 score : ${model_summary.r2}")
    println(s"MSE : ${model_summary.meanSquaredError}")
    // r2 score : 0.9304223675331595
    // MSE : 0.04638508831122697
    
    
    
    spark.close()         
  }
  
}