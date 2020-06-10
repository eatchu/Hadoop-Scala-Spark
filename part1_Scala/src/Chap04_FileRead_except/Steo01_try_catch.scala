package Chap04_FileRead_except


/*
 * 예외처리 : 실행 시점 오류 처리 과정
 * try{
 *   예외발생 가능한 코드
 * }catch{
 *   예외처리 코드 작성 
 * }
 */

object Steo01_try_catch {
  
  def main(args:Array[String]):Unit={
    
    var lst = List(10,20,30,40,50)
    var size = lst.size //원소갯수 반환
    println("size="+size) // size=5
    println(lst(0)) // 10
    println(lst(size-1)) // lst(4)=50
    
    for(i<-lst)print(i+" ")
    println()
    
    try{ //예외발생
      for(i<-0 until 6) // 0~5
        print(lst(i)+" ")
    }catch{
      case ex : IndexOutOfBoundsException => println("예외정보:"+ex)
    }     
    println("프로그램종료")
    
    
  }
  
}