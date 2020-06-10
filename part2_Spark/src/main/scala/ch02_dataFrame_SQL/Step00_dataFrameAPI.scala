package com.spark.ch02_dataFrame_SQL

// import org.apache.spark.{SparkConf,SparkContext} // RDD 생성
import org.apache.spark.sql.SparkSession // DataFrame 생성


object Step00_dataFrameAPI {
  
  def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
                
                
                
                
                
                
                
                
                
    // 객체 닫기                
    spark.close()            
                              
  }
  
}













