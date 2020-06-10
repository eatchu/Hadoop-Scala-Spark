package com.spark.exams

import org.apache.spark.sql.SparkSession

object exam02_dataFrame_SQL {
  
  /*
   * 문) score_iq.csv 파일을 읽어서 다음과 같이 DataFrame을  생성하시오.
   * 
   *    단계1 : spark DataFrame 객체 생성(file 위치 : "src/main/resources")
   *    
   *    단계2 : academy 변수를 대상으로 그룹화 하고, 집단별 score 평균 구하기 
   *    
   *    단계 3 : DataFrame을 대상으로 가상 테이블 만들기(테이블명 : score_df)
   *    
   *    단계 4 : tv 칼럼이 2이상인 관측치를 대상으로 subset 만들기 
   *    
   *    단계 5 : subset을 csv 형식으로 저장하기 (file 위치 : "src/main/resources/output_df") 
   */
  
    def main(args: Array[String]): Unit = {
    
     // SparkSession 객체 생성 
     val spark = SparkSession.builder
                 .master("local") 
                 .appName("dataFrameAPI")
                 .getOrCreate()
                 
     
     val df = spark.read
              .format("csv")
              .option("header", "true")
              .option("delimiter", ",")
              .option("inferSchema", "true")
              .load("src/main/resources/score_iq.csv")
     
     // 칼럼 구조 보기 
     df.printSchema()
          
     val df_grp = df.groupBy("academy")
     df_grp.count().show()
     
     
     // 그룹 단위 통계 구하기 
     df_grp.mean("score").show()
     
     // 가상 테이블(view) 만들기 
     df.createOrReplaceTempView("score") // 가상 테이블 : iris 
     
     // SQL문 사용하기      
     val subset = spark.sql("select * from score where tv >= 2")
     subset.show()
     
     subset.write.format("csv")
            .option("header", "true")
            .mode("overwrite")
            .save("src/main/resources/output_df")
     
     // 객체 닫기 
     spark.close()
     
  }
  
}