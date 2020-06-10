package com.spark.ch01_rdd

import org.apache.spark.{SparkConf,SparkContext}


object Step07_rddFileIO {
  
  def main(args:Array[String]):Unit={
        // SparkConf object 생성
    val conf = new SparkConf()
        .setAppName("SparkTest")
        .setMaster("local") // Spark 환경 객체

    
    // SparkContext 객체 생성 : rdd data 생성 
    val sc = new SparkContext(conf) 
    
    val filepath = "src/main/resources/"
    val rdd = sc. textFile(filepath + "input.txt") // file load
    
    println(rdd.collect.mkString("\n"))
    
    val rdd2 = rdd.flatMap(_.split(" ")) // string split -> word
    val rdd3 = rdd2.filter(_.size>3) 
    
    val wc = rdd3.map((_,1)).reduceByKey(_+_) // wordcount
    wc.foreach(println)
    
    wc.saveAsTextFile(filepath+"output")
    
    print("success")
    
    sc.stop()
  }
  
}