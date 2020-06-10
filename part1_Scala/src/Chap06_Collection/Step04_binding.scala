package Chap06_Collection


/*
 * 바인딩 메서드 (Binding method)
 *  - 컬렉션 객체에서 호출 가능한 함수
 *  형식) object.method1().method2()
 *  - 원소를 순차적으로 method1 넘김 -> method1(처리) -> method2(처리)
 *  - 자체 제너레이터(반복) 기능 포함
 */


object Step04_binding {
  
  def main(args:Array[String]):Unit={
    
    // 1. 컬렉션 객체 생성
    // var nums = 1 to 20 // 숫자 컬렉션 생성
    // println(nums.size)
    
    val nums = List(1,2,3,4,5,1,2,3,7) // List 컬렉션
    
    
    
    
    // 2. 바인딩 메서드 
    
    // 1) object.foreach(func) : object 원소를 순차적으로 받고 func 자료 처리
    println("foreach")
    nums.foreach((x:Int) => print(x*x+" "))
    println()
    
    // 2) object.map(_매핑연산자) : object 원소를 순차적으로 받고(_), 연산수행
    println("map")
    var map_re = nums.map(_*2)
    println(map_re)
    
    // 3) object.filter(_조건식) : object 원소를 순자척으로 받고(_), 조건에 따라서 필터링
    println("filter")
    var filter_re = nums.filter(_%2==0)
    println(filter_re)
    
    // 4) object.filter().map()
    println("filter.map")
    var filter_map_re = nums.filter(_>10).map(_*2)
    println(filter_map_re)
    
  }
  
}





