package Chap01_Variable

/*
 * Scala 기본 자료형
 *  Int : 정수형 (4byte) : default
 *  Long : 정수형 (8byte)
 *  Float : 실수형 (4byte)
 *  Double : 실수형 (8byte) : default
 *  String : 문자형 (n개 문자)
 *  Char : 단일문자형 (1개 문자)
 *  Boolean : 논리형 (true/false)
 */

object Step03_Datatype {
  
  def main(args:Array[String]): Unit={
    val calc : Int = 100*4/3 // 실수 -> 정수
    println("calc="+calc) // 133
    
    val calc_long : Long = 1000000*2
    println("calc_long="+calc_long, calc_long.getClass())
    
    val calc_re : Float = 100*4/3 // 실수 -> 실수
    println("calc_Re="+calc_re)
    
    val calc_re2 = 100*2.5/3 // 실수 -> 실수
    println("clac_re2="+calc_re2) // 83.3333333
    // format 이용, 줄바꿈 없음
    printf("calc_re2=%.5f",calc_re2)
    println() //line skip
    
    println("type="+calc_re2.getClass()) // double
    
    // 실수형 변수 선언
    val x : Double = 2.4
    val a : Float = 0.5f
    val b : Float = 1.0f
    
    val y_pred = x * a + b
    println("y_pred="+y_pred) // 2.2
    
    // 논리형 변수 선언
    var bool_re : Boolean = 2.5f == y_pred // 관계식 or 논리식
    println("bool_re="+bool_re)
    
    // 문자형과 단일문자형
    var strVar : String = "우리나라대한민국"
    var charVar : Char = '우' // 단일 문자형
    println(strVar)
    println(charVar)
   
  }
  
}
















