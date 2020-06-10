package com.spark.dataFrame_SQL


import org.apache.spark.sql.SparkSession


object Step05_datafrane_scala {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
                
    // tuple vector : eno, pay, bonus, dno
    val v1 = (1001,250,60,10) 
    val v2 = (1002,350,70,20)
    val v3 = (1003,200,55,10)
    
    // scala -> dataframe
    import spark.implicits._ // spark 암묵적 변환 방식 : scala -> df
    val emp = List(v1,v2,v3).toDF("eno","pay","bonus","dno")
    emp.show()
                
    // column 사용을 위한 표준내장함수
    import org.apache.spark.sql.functions._
                
    // 형식) df.select(column 표준내장함수)
    emp.select(max("pay"), sum("bonus"), mean("bonus"), stddev("bonus")).show()
                
                
                
    // 객체 닫기                
    spark.close()            
                              
  } 
}