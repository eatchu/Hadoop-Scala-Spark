package Chap01_Variable

/*
 * Scala 상수 선언
 *  형식) val 변수명 : 자료형 = 값 
 *    - 초기화 이후 수정 불가능
 *    - 참조 가능
 *    - 자료형 생략시 추론기능
 */

object Step02_Val {
  
  def main(args:Array[String]): Unit={
    val numVal : Int = 100
    val numVal2 = 195.41
    println("numVal="+numVal+"  "+"numVal2="+numVal2)
    println("type="+numVal2.getClass()) //type=double
    
    // 변수 수정
    // numVal = 200 -> 오류발생 : 새로운값 할당 불가능 compile 안됨
    
    // 참조 기능 : 연산
    var numVal_calc = numVal * 2
    println("numVal_calc="+numVal_calc)
    
  }
  
}