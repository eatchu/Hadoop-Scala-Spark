package com.spark.ch02_dataFrame_SQL

import org.apache.spark.sql.SparkSession 

object Step06_dataframe_txt {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
    // 1. text file -> DF
    val path = "src/main/resources/"
    val df = spark.read.text(path+"input.txt")
    df.show(false) // 정렬
    
    // 2. column 사용을 위한 표준내장함수
    import org.apache.spark.sql.functions._
    // df.select(column 표준내장함수)
    
    // col() -> split()
    df.select(col("value")).show()
    df.select(split(col("value"), " ").as("words")).show(false)
    
    // col() -> split() -> explode()
    val words = df.select(explode(split(col("value")," ")).as("words"))
    print("단어생성결과")
    words.show()
    
    // word count
    val grp = words.groupBy("words") // df.groupBy()
    val wc = grp.count() // 단어 출현 빈도수
    println(wc)
    
    
    println("성공~!!")
    
                
                
                
                
                
                
                
                
    // 객체 닫기                
    spark.close()            
                              
  }
  
  
}