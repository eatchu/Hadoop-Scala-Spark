package exam

import scala.io.Source

/*
 * 문) iris.csv 데이터셋에서 "setosa"가 포함된 행만 출력하기 
 */

import scala.io.Source // file source read
import java.io.FileNotFoundException // 예외처리

object Exam {
  
  def csv_load(file_name : String) : Unit = {
    try{
      val fileRead = Source.fromFile("C:\\IITT\\5_Hadoop_Spark\\workspace\\part1_Scala\\src\\fileDir\\"+file_name)
      println("load 성공!")
      var row = 0
      var lines = fileRead.getLines().drop(1) // 제목줄 제거, 전체 줄 단위 읽기
      for(line<-lines if(line.contains("setosa"))){
        // 한줄 읽기 -> 콤마 기준 토큰 생성 -> 변수 저장
        var cols = line.split(",").map(_.trim) // line -> col
        row += 1
        
        // 칼럼단위 출력 : s"${칼럼}"
        println(s"${row} : ${cols(0)},${cols(1)},${cols(2)},${cols(3)},${cols(4)}")
      }
      
      fileRead.close

    }catch{
      case ex : FileNotFoundException => println("예외정보"+ex)
    }
    
  } 
  
  def main(args: Array[String]): Unit = {
      csv_load("iris.csv")
  }
  
}

