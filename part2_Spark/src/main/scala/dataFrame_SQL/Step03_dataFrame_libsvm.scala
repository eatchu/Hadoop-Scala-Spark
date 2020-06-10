package com.spark.dataFrame_SQL

import org.apache.spark.sql.SparkSession

object Step03_dataFrame_libsvm {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
    val df = spark.read.format("libsvm")
                  .load("src/main/resources/iris_libsvm.txt")
    
    df.show(false)            
              
                
    // 객체 닫기                
    spark.close()   
    
    
    // linear regression model
    import org.apache.spark.ml.regression.LinearRegression
    val trainset = spark.read.format("libsvm")
                        .load("src/main/resources/sample_libsvm_data.txt")
    trainset.show(false)
    
    // object 생성
    val lr = new LinearRegression()
    // model 생성
    val model = lr.fit(trainset)
    // 객체 닫기
    spark.close()
    
                              
  }
  
}