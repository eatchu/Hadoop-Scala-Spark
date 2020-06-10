package com.spark.ch01_rdd

import org.apache.spark.{SparkConf,SparkContext}


object Step02_trans {
  
  def main(args:Array[String]):Unit = {
    
    // SparkConf object 생성
    val conf = new SparkConf()
        .setAppName("SparkTest")
        .setMaster("local") // Spark 환경 객체

    
    // SparkContext 객체 생성 : rdd data 생성 
    val sc = new SparkContext(conf) 
    
    // 1. map(매핑연산자) : rdd 원소를 순서대로 받아서 연산 수행
    val rdd = sc.parallelize(List("a","b","c"))
    val map_re = rdd.map((_,1)) // (a,1),(b,1),(c,1)
    println(map_re) // 객체 정보
    map_re.foreach(println) // 결과값 (a,1),(b,1),(c,1)
    
    
    val rdd2 = sc.parallelize(1 to 10)
    val map_re2 = rdd2.map(_+1) 
    // rdd 원소 추출
    println(map_re2.collect().mkString(" ")) // 2,3,4,5,6,7,8,9,10,11
    
    
    // 2. flatmap(매핑연산자) : rdd 원소를 순서대로 받아서 연산 수행 (1:N)
    val names = sc.parallelize(List("홍길동,강호동","이순신,강감찬,유관순","홍길동,이순신,강감찬"))
    val flatmap_re = names.flatMap(_.split(","))
    println("size:"+flatmap_re.count()) // 8
    println(flatmap_re.collect().mkString(",")) // 홍길동,강호동,이순신,강감찬,유관순,홍길동,이순신,강감찬
    
    
   
    // 3. filter(조건식) : rdd 원소를 순서대로 받아서 조건이 참인 원소 반환
    val filter_re = names.filter(_.size>10)
    println(filter_re.collect().mkString("\t"))
    
  }  // main end
  
}





















