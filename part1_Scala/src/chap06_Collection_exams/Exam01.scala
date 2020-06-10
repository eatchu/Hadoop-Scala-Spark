package chap06_Collection_exams

/*
 * 문) Array 컬렉션 객체 생성 
 * 단계1 : 실수 100를 저장할 수 있는 Array 객체 생성
 * 단계2 : 난수 실수 100개를 생성하여 Array 객체에 저장
 * 단계3 : 최댓값/최솟값 출력하기   
 */
object Exam01 {
  
  def main(args: Array[String]): Unit = {
      
      // 단계1 : 실수 100를 저장할 수 있는 Array 객체 생성
      val arr : Array[Double] = new Array[Double](100)
      

      
      // 단계2 : 난수 실수 100개를 생성하여 Array 객체에 저장
      var idx = 0 until 100
      for(i<-idx){
        var r = Math.random() // 0-1
        arr(i) = r 
       }

      // 단계3 : 최댓값/최솟값 출력하기
      var idx2 = 0 until 99
      for(i<-idx2){
        if (arr(i) >= arr(i+1)){
          var min_re = arr(i+1)
          var max_re = arr(i)
        }else{
          var min_re = arr(i)
          var max_re = arr(i+1)
        }
      }


    }
  
}