package Chap03_Method


/*
 * method vs function
 * method : class of object에서 선언한 함수 (객체 지향언어)
 * function : 단독으로 선언한 함수 (함수 지향언어)
 * 
 * 형식)
 * def 함수명(매개변수: type): ={
 *     실행문 1
 *     실행문 2
 *     [return 반환값]
 * }
 * 
 * 반환값이 없는 경우 : 리턴 타입 = Unit
 */


object Step01_method_basic {
  // max method
  def max(x : Int, y : Int):Int={
      if(x>y) x else y
  }
  
  // adder method
  def adder(x : Float, y : Float):Float={
      var add : Float = (x+y) * 0.5f
      return add // return 값
  }
  
  // 반환값 없는 method
  def adder2(x:Float,y:Float):Unit={
      val add : Float = (x+y)*0.5f  
  }
  
  // PI = 3.14159
  def getPI() : Double={
    val PI = 3.14159 // 상수
    return PI
  }
  
  // return, {} 생략
  def getPI2() : Double = 3.14159
  
  // 인수 없는 함수 : (), return, {} 생략
  def getPI3 : Double = 3.14159
  
  // 시작점 : main method
  def main(args:Array[String]):Unit={
    println("method")
    val x = 20
    val y = 15
    var max_re = max(x,y) // max method 호출
    println("max="+max_re)
    var adder_re = adder(15f,20f) // adder method 호출
    println("add="+adder_re)
    
    // getPI method 호출
    println(getPI())
    println(getPI2())
    println(getPI3)
    
  }
}










