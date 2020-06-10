package com.spark.ch01_rdd


import org.apache.spark.{ SparkConf, SparkContext }


/*
 * RDD Action : load & save
 *    - rdd.collect() : rdd 원소 추출 -> vector 형식 생성
 *    - rdd.textfile() : 
 */


object Step05_rddAction {
  
  def main(args:Array[String]):Unit = {
    
    // SparkConf object 생성
    val conf = new SparkConf()
        .setAppName("SparkTest")
        .setMaster("local") // Spark 환경 객체
   
    // SparkContext 객체 생성 : rdd data 생성 
    val sc = new SparkContext(conf) 
    
    // 1. rddcollect()
    val rdd = sc.parallelize(1 to 100,5) //data : 정수컬렉션, 파티션 수 : 5
    println(rdd.collect.mkString(" "))
    println(rdd.first) // 첫번째 원소 추출 : 1
    println(rdd.take(10)) // 첫번째 원소 ~ n번째 원소 추출
  
    // 2. rdd = sc.textFile(local file or HDFS)
    val rdd2 =  sc.textFile("file:/C:/hadoop-2.6.0/README.txt")
    println(rdd2.count()) // 31
    println(rdd2.take(20).mkString("\n"))
    
    
    // 3. save 관련 : rdd 객체 -> file 저장
    //rdd.saveAsTextFile("file:/C:/hadoop-2.6.0/output1")
    //rdd2.saveAsTextFile("file:/C:/hadoop-2.6.0/output2")
    
    // 4.HDFS file load & HDFS file save
    // rdd load
    val hdfs_rdd = sc.textFile("hdfs://localhost:9000/test/README.txt",5)
    
    // rdd save
    val hdfs_rdd2 = hdfs_rdd.take(10)
    hdfs_rdd.saveAsTextFile("hdfs://localhost:9000/output")
    
    
    
    println("sucess")
    
    sc.stop
    
        
  }
  
}