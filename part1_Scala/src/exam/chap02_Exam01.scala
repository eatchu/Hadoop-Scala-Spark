package exam

/*
 * 문) 두 실수를 인수로 받아서 나눗셈 연산 후 실수값으로 반환하는  div 메서드 정의하기
 *    함수명 : div()
 */

object Exam01 {
  
  // div 메서드 정의
  def div(x:Float, y:Float): Float={
    return x/y
    }
  
  
  def main(args: Array[String]): Unit={
    print("div=%.5f".format(div(30f,7f)))
    }
 
}


 