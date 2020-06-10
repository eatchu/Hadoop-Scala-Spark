package com.spark.dataFrame_SQL


import org.apache.spark.sql.SparkSession // DataFrame 생성


object Step01_dataFrame_create {
  
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
    df.show()
    println("관측치 수="+df.count()) // 150
    df.show(150)
    
    // 칼럼 type                
    df.printSchema()            
               
    
    // 칼럼 단위 통계구하기 : 칼럼명 .포함 인식 x
    // df.describe("Sepal.Length") //error
         
    
    // 칼럼명 변경하기 : col1 ~ col4, Sepal.Length
    val colNames = Seq("col1","col2","col3","col4","Species")
    // old -> new
    val iris_df = df.toDF(colNames:_*)
    iris_df.show() // |col1|col2|col3|col4|Species|
    
    // 통계구하기 : count, mean, stddev, min, max
    iris_df.describe().show() // describe("col1") - 특정칼럼 검색
    
    // 집단 변수 그룹화
    val df_grp = iris_df.groupBy("Species")
    df_grp.count().show() 
    /*
    +----------+-----+
    |   Species|count|
    +----------+-----+
    | virginica|   50|
    |versicolor|   50|
    |    setosa|   50|
    +----------+-----+
    */
    
    // 그룹단위 통계구하기
    df_grp.max().show()
    df_grp.min().show()
    df_grp.mean().show()
    //df_grp.sum().show()
    
    
    // DataFrame save
    iris_df.write.format("csv")
           .option("header","true")
           .mode("overwrite")
           .save("src/main/resources/output_df")
    
           
           
    // DataFrame save : HDFS 디렉터리 저장
    iris_df.write.format("csv")
           .option("header","true")
           .mode("overwrite")
           .save("hdfs://localhost:9000/output_df")
           
           
           
    println("file save")
    
    // 객체 닫기                
    spark.close()                                   
    }
  
    
    
    
}