package com.spark.exams

import org.apache.spark.sql.SparkSession

object exam03_dataFrameWordCount {
  def main(args: Array[String]): Unit = {
    
     // SparkSession 객체 생성 
     val spark = SparkSession.builder
                 .master("local") 
                 .appName("dataFrameAPI")
                 .getOrCreate()
                 
    // 단계1 : HDFS의 /test 디렉터리에서 README.txt 파일 읽기(만약 file 없으면 file 업로드)
    val df = spark.read.text("hdfs://localhost:9000/test/README.txt")
    df.show(false)
                             
    // 단계2 : 줄단위 읽기 -> 공백 기준 단어 분리 
    import org.apache.spark.sql.functions._
    val words = df.select(explode(split(col("value")," ")).as("words"))
    words.show()
    
    // 단계3 : 워드 카운트 구하고, HDFS의 /output_wc 디렉터리에 저장하기
    val grp = words.groupBy("words") // df.groupBy()
    val wc = grp.count() // 단어 출현 빈도수
    wc.show()   
    
    wc.write.format("txt")
            .option("header", "true") 
            .save("hdfs://localhost:9000/output_wc")
    
    print("success")
            
    // 단계4 : HDFS의 /output_wc 디렉터리에 저장된 결과 파일 보기           
            
     // 객체 닫기 
     spark.close()
     
  }
}