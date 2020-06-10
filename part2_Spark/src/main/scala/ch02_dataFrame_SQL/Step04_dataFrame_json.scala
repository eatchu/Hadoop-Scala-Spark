package com.spark.ch02_dataFrame_SQL

/*
 * json file -> DataFrame
 */

import org.apache.spark.sql.SparkSession

object Step04_dataFrame_json {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
                
    // path 변수
    val path = "src/main/resources/usagov_bitly.txt"
    
    // json -> DataFrame
    val df = spark.read.json(path)
                
    df.show()
                
    df.printSchema
    
    df.createOrReplaceTempView("json")
    
    spark.sql("select * from json")
    spark.sql("select count(*) from json")

    val nk_true = spark.sql("select * from json where nk=1")
    nk_true.show()
                
    
                
                
    // 객체 닫기                
    spark.close()            
                              
  }
  
}