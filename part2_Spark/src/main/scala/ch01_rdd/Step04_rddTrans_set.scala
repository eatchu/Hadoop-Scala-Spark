package com.spark.ch01_rdd

import org.apache.spark.{ SparkConf, SparkContext }

/*
 * 1. RDD Transformation [집합 관련 method] : file upload
 * 
 *  - rdd.distinct() : RDD 원소의 중복 제거 
 *  - rdd.cartesian() : 두 개의 RDD 원소의 카데시안곱 반환
 *  - rdd.subtract() : 두 개의 RDD 원소의 차집합  
 *  - rdd.union() : 두 개의 RDD 원소의 합집합 
 *  - rdd.intersection() : 두 개의 RDD 원소의 교집합  
 */

  
object Step04_rddTrans_set {
  
  def distinct(sc: SparkContext) {
    val rdd = sc.parallelize(List(1, 2, 3, 1, 2, 3, 1, 2, 3))
    val result = rdd.distinct()
    println(result.collect.mkString(", ")) // 1, 3, 2 : 중복 제거
  }

  def cartesian(sc: SparkContext) {
    val rdd1 = sc.parallelize(List(1, 2, 3))
    val rdd2 = sc.parallelize(List("a", "b", "c"))
    val result = rdd1.cartesian(rdd2)
    println(result.collect.mkString(", "))    
    // (1,a), (1,b), (1,c), (2,a), (2,b), (2,c), (3,a), (3,b), (3,c) : 카데시안 곱
  }

  def subtract(sc: SparkContext) {
    val rdd1 = sc.parallelize(List("a", "b", "c", "d", "e"))
    val rdd2 = sc.parallelize(List("d", "e"))
    val result = rdd1.subtract(rdd2)
    println(result.collect.mkString(", ")) // a, b, c : 차집합
  }

  def union(sc: SparkContext) {
    val rdd1 = sc.parallelize(List("a", "b", "c"))
    val rdd2 = sc.parallelize(List("d", "e", "f"))
    val result = rdd1.union(rdd2)
    println(result.collect.mkString(", ")) // a, b, c, d, e, f : 합집합
    
  }

  def intersection(sc: SparkContext) {
    val rdd1 = sc.parallelize(List("a", "a", "b", "c"))
    val rdd2 = sc.parallelize(List("a", "a", "c", "c"))
    val result = rdd1.intersection(rdd2)
    println(result.collect.mkString(", "))  // a, c : 교집합
  }
  
  def main(args: Array[String]) {      
     val conf = new SparkConf()
          .setAppName("RDD_SET")
          .setMaster("local")
  
     val sc = new SparkContext(conf)
      
      distinct(sc)
      cartesian(sc)
      subtract(sc)
      union(sc)
      intersection(sc)
    }  
}


