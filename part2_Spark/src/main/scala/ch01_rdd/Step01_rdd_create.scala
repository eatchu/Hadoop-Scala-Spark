package com.spark.ch01_rdd

import org.apache.spark.{SparkConf,SparkContext}

object Step01_rdd_create {
  
    def main(args:Array[String]):Unit = {
    
    // SparkConf object 생성
    val conf = new SparkConf()
        .setAppName("SparkTest")
        .setMaster("local") // Spark 환경 객체

    
    // SparkContext 객체 생성 : rdd data 생성 
    val sc = new SparkContext(conf) 
    

    
    // 1. 파티션 지정 RDD 생성 : parallelize(data, 파티션수) 이용 : 2.1.3절 예제 2-6 
    val rdd1 = sc.parallelize(1 to 100, 5) // data : vector 이용 
    // RDD read -> 구분자 기준 원소 출력 
    println(rdd1) // ParallelCollectionRDD[0] at parallelize at Step01_rdd_create.scala:21
    // object.bind() : 5개의 파티션마다 출력
    rdd1.foreach((x:Int)=>print(x+" ")) // 무명함수
    println(rdd1.collect().mkString(","))

    
    // 파티션 미지정
    val rdd2 = sc.parallelize(List("a", "b", "c", "d", "e")) // data : List collection  
    println(rdd2) //ParallelCollectionRDD[1] at parallelize at Step01_rdd_create.scala:30
    rdd2.foreach((x:String)=>print(x+" ")) // a, b, c, d, e

    
    
    
    // 2. 외부 저장 매체(file, HDFS) 데이터 이용 RDD 생성 : spark_home/README.md 
    val rdd3 = sc.textFile("file:/C:/hadoop-2.6.0/NOTICE.txt") 
    println(rdd3)    
    // 텍스트 파일의 한 줄은 한 개의 RDD 구성요소 
    
    // 텍스트 파일에서 1줄 -> RDD 1개 원소
    rdd3.foreach((x:String)=>println(x))
    
  
    
    sc.stop
    
    
    }  // main end
    
 
  
}


