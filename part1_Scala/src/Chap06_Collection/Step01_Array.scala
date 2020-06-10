package Chap06_Collection

/*
 * 컬렉션(collection)
 *  - 데이터의 집합 의미
 *  - 수정 여부, 순서 보장(index), 중복 허용 등으로 분류
 *  
 * Array 컬렉션 특징
 *  - 수정 기능
 *  - 순서 존재 : index 사용
 *  - 중복 허용
 *  - 동일 type 저장 가능
 *  형식) var 변수 : Array[type] = new Array[type]
 */


object Step01_Array {
  
  def main(args:Array[String]):Unit={
    
    // 1. new 명령어 객체 생성
    var arr : Array[Int] = new Array[Int](5) // object
    arr(0) = 10
    arr(1) = 20
    arr(2) = 30
    arr(3) = 40
    arr(4) = 50
    
    // 원소 수정 가능 : 다른 자료형 입력은 error
    arr(4) = 500
    
    for(i<-arr) print(i+" ")
    
    // 2. 객체 생성 + 초기화
    var arr2 = Array(10,20,33,40,50)
    
    println()
    for(i<-arr2 if(i%2==0))print(i+" ")
    
    // 3. Array 생성 축약형
    var arr3 = new Array[Double](50) // object
    
    // index : 0 ~ 49 -> start until stop-1
    var idx = 0 until 50
    for(i<-idx){
      var r = Math.random() // 0-1
      arr3(i) = r 
    }
    
    // 컬렉션 원소 출력
    println("\narr3 size="+arr3.size)
    
    // 가드 추가
    var cnt = 0
    for(a<-arr3 if(a>=0.5 && a<=0.8)) {print(a+" ")
      cnt += 1
    }
    println()
    println("선택된 원소"+cnt)
    
  }
  
}






