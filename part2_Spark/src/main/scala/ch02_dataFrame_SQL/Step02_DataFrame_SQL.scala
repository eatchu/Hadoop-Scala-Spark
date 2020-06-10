package com.spark.ch02_dataFrame_SQL

import org.apache.spark.sql.SparkSession

object Step02_DataFrame_SQL {
  
    def main(args: Array[String]):Unit = {
    
    // SparkSession 생성
    val spark = SparkSession.builder
                .master("local")
                .appName("dataFrameAPI")
                .getOrCreate()
                
    val df = spark.read
             .format("csv")
             .option("header","true")
             .option("delimiter",",")
             .option("inferSchema","true")
             .load("src/main/resources/iris.csv")
             
    df.printSchema()
             
    // 칼럼명 변경    
    val colNames = Seq("Sepal_Length","Sepal_Width","Petal_Length","Petal_Width","Species")
    val iris_df = df.toDF(colNames:_*)
    // iris_df.show() // |col1|col2|col3|col4|Species|
    
    
    // 가상 테이블 만들기 (view)
    iris_df.createOrReplaceTempView("iris") // 가상테이블 iris
    
    //SQL문 사용하기 
    spark.sql("select Sepal_Length, Petal_Length from iris").show()
    spark.sql("select * from iris where Sepal_Length > 6.0").show()
    spark.sql("select * from iris where Sepal_Length > 6.0 order by Sepal_Length desc").show()
    spark.sql("select Species, mean(Sepal_Length) as mean1, mean(Petal_Length) as mean2 from iris group by Species").show()
    
    
    // 특정 칼럼 제거하기
    val iris_x = iris_df.drop("Species")
    iris_x.show()
    
    // 전체 칼럼 대상 기술 통계량 구하기
    iris_x.describe().show()
    
    // 객체 닫기                
    spark.close()   
    
    }
  
  
  
}







