package com.spark.ch01_rdd


import org.apache.spark.{SparkConf,SparkContext}



object Step03_rddTrnas_join {
  
  def zip(sc:SparkContext) : Unit = {
    val rdd1 = sc.parallelize(Seq("a","b","c"))
    val rdd2 = sc.parallelize(List(1,2,3))
    val zip_re = rdd1.zip(rdd2)
    zip_re.foreach(println)
  }
  
  def reduceByKey(sc:SparkContext):Unit={
    val lst = List("data","text","word","data","word") // data
    val rdd = sc.parallelize(lst) // rdd 생성
    
    // Transformation : old rdd -> new rdd
    val new_rdd = rdd.map((_,1)) // ("data",1)...("word",1)
    println(new_rdd.collect.mkString(","))
    // (data,1),(text,1),(word,1),(data,1),(word,1)
    
    
    // Transformation : old rdd -> new rdd
    new_rdd.reduceByKey(_+_).foreach(println) //(text,1)(data,2)(word,2)
    
  }
  
  
  
  def main(args:Array[String]):Unit = {
    
    // SparkConf object 생성
    val conf = new SparkConf()
        .setAppName("SparkTest")
        .setMaster("local") // Spark 환경 객체
   
    // SparkContext 객체 생성 : rdd data 생성 
    val sc = new SparkContext(conf) 
    
    
    
    // 1. join : 동일한 키를 기준으로 원소 모음 : 길이는 서로 다름
    val rdd1 = sc.parallelize(Seq("kim","lee","park","choi")).map((_,1))
    println(rdd1.collect.mkString(" ")) // (kim,1) (lee,1) (park,1) (choi,1)
    
    val rdd2 = sc.parallelize(List("lee","choi")).map((_,2))
    println(rdd2.collect.mkString(" ")) // (lee,2) (choi,2)
    
    val join_re = rdd1.join(rdd2) // 동일키 기준 join
    println(join_re.collect.mkString(",")) // (lee,(1,2)),(choi,(1,2))
      
    
    // 2. zip : 원소의 순서대로 원소 묶음 : 길이는 동일
    zip(sc)
    //(a,1)(b,2)(c,3) 
    
    
    // 3. reduceByKey : 동일한 키를 기준으로 value를 합친다
    reduceByKey(sc)
  
    
  }
}




























