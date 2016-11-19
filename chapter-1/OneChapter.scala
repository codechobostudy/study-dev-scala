import scala.annotation.tailrec

/**
  * Chapter 2-5 Polymorphism Function
  *
  * FP에서의 다형성은 OOP에서의 다형성과 다르다
  * OOP에서의 다형성은 하위형식화 혹은 상속 관계를 나타내지만
  * FP에서는 인터페이스나 하위형식화가 전혀 없다 매개변수적 다형성(parametric polymorphism)이라 한다.
  *
  * @author loustler
  * @since 10/03/2016 15:44
  */
object OneChapter {

  /**
    * 다형성 적용 전
    *
    * @param ss
    * @param key
    * @return
    */
  def findFirst(ss: Array[String], key: String): Int = {
    @annotation.tailrec
    def loop(n: Int): Int = {
      if (n >= ss.length) -1
      else if (ss(n) == key) n
      else loop(n+1)
    }

    loop(0)
  }

  /**
    * 다형성 적용 후 (다형적 함수 중 일반적 함수)
    * A는 타입을 의미하며
    * Java의 Generics 중 T와 비슷한 걸로 보임
    *
    * Parameter 중 p 는 A => Boolean으로 되어 있는데 이것은,
    * 고차원 함수에서 사용하는 함수를 파라미터로 넘기는 것이다
    * 즉, type A에서 Boolean을 return하는 메서드를 parameter로 넘겨야 된다
    *
    * @param as Array
    * @param p Array's Key
    * @tparam A Type
    * @return Int
    */
  def findFirst[A](as: Array[A], p: A => Boolean): Int = {
    @tailrec
    def loop(n: Int): Int =
      if (n >= as.length) -1
      else if (p(as(n)))  n
      else loop(n+1)

    loop(0)
  }

  /**
    * Loop를 돌면서 Index 0부터 체크
    * Type은 parameter 두개가 동일 해야됨
    * ordered는 anonymous function으로 처리
    *
    * @param as
    * @param ordered
    * @tparam A
    * @return
    */
  def isSorted[A](as: Array[A], ordered: (A,A) => Boolean): Boolean = {
    @tailrec
    def loop(n: Int): Boolean =
      if (n >= as.length-1) true
      else if (!ordered(as(n), as(n+1))) false
      else loop(n+1)

    loop(0)
  }

  /**
    * 함수를 리턴
    * 고차함수의 일종으로 부분 적용(partial application)이라 부르는 작업을 수행함
    * 이 고차함수는 이미 parameter a를 인자로 받은 함수에 넣어주고 나머지 한 개의 인자는 별도로 받아서 처리할 수 있게 함
    * 즉, 부분적으로 처리할 수 있는 함수를 일컫음
    *
    * @param a
    * @param f
    * @tparam A
    * @tparam B
    * @tparam C
    * @return
    */
  def partial1[A, B, C](a: A, f: (A,B) => C) : B => C =
  (b: B) => f(a, b)

  /**
    * 다형적 함수의 예
    * @param x
    * @param y
    * @tparam T
    * @return
    */
  def dup[T](x: T, y: Int): List[T] =
  if(y==0)  Nil
  else x::dup(x, y-1)

  /**
    * 연습문제 2-3
    * 위의 this#partial1을 참조하여서 만들어야 됨
    * 반환형식이 A => (B => C) 이므로 B를 받아서 C를 리턴하는 함수를 A를 통해 리턴하게 해야 됨
    *
    * @param f
    * @tparam A
    * @tparam B
    * @tparam C
    * @return A만 인자로 받는 부분적으로 완성된 f가 리턴됨 즉 B를 인자로 넣어주면 되는 함수를 리턴함
    */
  def curry[A, B, C](f: (A, B)=> C): A => (B => C) = {
    //  (a : A) => ((b:B) => f(a,b))
    (a) => ((b) => f(a,b))
  }

  /**
    * 연습문제 2-4
    * 위 2-3을 이해해야 됨
    * 함수를 리턴해서 그것을 실행한다는 개념을 이해해야 됨
    * reverse를 하는 것
    * 리턴이 반대로 (A,B) => C 기 때문에 리턴할 때 (A,B)를 인자로 받아서 바로 C를 출력하는 함수를 리턴해야 됨
    * f:A => B => C는
    * f(A)를 하면 B => C인 함수를 리턴함
    * 그래서 추가로 B => C인 함수를 사용해야 (A,B) => C인 효과를 보일 수 있음
    *
    * @param f
    * @tparam A
    * @tparam B
    * @tparam C
    * @return
    */
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    //    (a: A, b : B) => f(a)(b)
    (a, b) => f(a)(b)
  }

  /**
    * 연습문제 2-5
    * 합성함수
    * 다른 함수의 리턴값을 다른 함수의 파라미터로 넣어줌
    * A => C를 만족하기 위해서 A를 파라미터로 받는 g에 A를 넣은 뒤
    * B를 리턴받아 B를 파라미터로 받는 f에 인자로 넣어줌
    * 함수의 리턴값을 다른 함수의 파라미터로 넣어주는 것을 합성함수라고 함
    * FPIS에서는 다음과 같이 말한다.
    * : 함수 합성에서는 한 함수의 출력을 다른 함수의 입력으로 공급한다.
    *
    * @param f
    * @param g
    * @tparam A
    * @tparam B
    * @tparam C
    * @return
    */
  def compose[A, B, C](f : B => C, g: A => B): A => C = {
    (a) => f(g(a))
  }


  def main(args: Array[String]): Unit = {
    var a = Array[String]("one", "two", "three", "four", "five")
    val b = Array("a", "b", "c", "d")
    val k = "four"

    println(findFirst(a, k))
    println(findFirst[String](a, k.equals))
    println(isSorted(b, (x: String, y: String)=> x < y))
    println(partial1[Int, Int, Int](5, (x, y) => x+y)(6))
    println(curry((x:Int, y: Int)=> x+y)(6)(7))
    println(uncurry((x:Int) => (y:Int) => x+y)(6,7))

    println(dup[Int](3,4))
    println(dup("three",3))
    println(dup("three",1))

    println(compose[Int, Int, Int]( (x : Int) => 5+x, (y: Int) => (y*3))(5)) // 5*3 (second func) + 5 (first func)
  }
}
