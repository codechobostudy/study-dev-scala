import scala.annotation.tailrec

/**
  * trait   : 자료 형식을 도입할 때
  *   일종의 추상 인터페이스, 일부 메서드의 구현도 가능
  * sealed  : trait의 모든 구현이 이 파일 안에 선언되어 있어야 함
  *
  * @tparam +A : Covariant Type Parameter
  *
  * Covariant : Generics에서 type의 상속관계를 인정해주는 것
  *   ex: class Animal  class Dog extends Animal 일 경우,
  *       List[Animal]의 subtype으로 List[Dog]를 인정함(Java는 인정 안 함 => Collection과 언어의 한계)
  */
sealed trait List[+A]

/**
  * List의 두 가지 자료 생성자 ( data constructor)
  * List가 가질 수 있는 2가지 형태
  *
  * Nothing은 모든 type의 하위
  */
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


/**
  * 동반객체(companion object)  : 자료형식과 같은 이름의 object로 자료 형식의 값들을 생성하거나 조작하는 여러 편의용 함수를 제공하는 목적으로 쓰임
  *                             관례에 가까움
  *                           =>  여기에서는 sum과 product가 해당됨(List의 동반객체)
  *
  * 패턴 부합(pattern matching) : 표현식의 구조를 따라 내려가면서 구조의 부분 표현식을 추출하는 복잡한 switch와 비슷하게 동작
  *         syntax  : return type = parameter( matching target )  match { case ~ => ~ }
  *
  * @author loustler
  * @since 11/20/2016 13:17
  */
object List {
  /**
    * Sum of Int List
    *
    * @param ints
    * @return
    *         Nil 즉 아무것도 없으면 0
    *         Nil이 아니면 합계
    */
  def sum(ints: List[Int]): Int = ints match {
    case Nil  =>  0
    case Cons(x, xs)  =>  x * sum(xs)
  }

  /**
    * Product of Double List's element
    *
    * @param doubleList
    * @return
    *         Nil이면 1.0
    *         0.0으로 시작하면 0.0
    *         그 외에는 내부 element들 곱셈 결과
    */
  def product(doubleList: List[Double]): Double = doubleList match {
    case Nil  =>  1.0
    case Cons(0.0, _) =>  0.0
    case Cons(x, xs)  =>  x * product(xs)
  }

  /**
    * A* : 0 ~ n개의 A (가변인수)
    *   =>  Java의 ... 와 유사
    *
    * @param as
    * @tparam A
    * @return
    */
  def apply[A](as: A*): List[A] =
    if (as.isEmpty) Nil
    else Cons(as.head,  apply(as.tail : _*))

  val ex1:  List[Double] = Nil
  val ex2:  List[Int] = Cons(1, Nil)
  val ex3:  List[String]  = Cons("a", Cons("b", Nil))

  /**
    * exercise 3-2
    * 첫 요소 제거 하는 함수
    *
    * @param list
    * @tparam A
    * @return
    *         Nil 이면 Nil
    *         tail이 Nil이면 Nil
    *         tail이 있으면 tail
    */
  def tail[A](list: List[A]): List[A] = list match {
    case Nil  =>  Nil
    case Cons(h, Nil) =>  Nil
    case Cons(h, t) =>  t
  }

  /**
    * exercise 3-2
    *
    * @param x
    * @param list
    * @tparam A
    * @return
    *         Nil이면 x를 넣어주고 Nil
    *         tail이 Nil이면 head는 바꾸고 Nil 반환
    *         tail이 있으면 head만 변경하고 tail 반환
    */
  def setHeader[A](x: A, list: List[A]): List[A] = list match {
    case Nil => Cons(x, Nil)
    case Cons(h, Nil) =>  Cons(x, Nil)
    case Cons(h, t) =>  Cons(x, t)

  }

  /**
    * 앞의 요소를 삭제
    *
    * @param n
    * @param l
    * @tparam A
    * @return
    *         Nil 이면 Nil
    *         tail이 Nil이면(head만 있으면) 삭제하고자 하는 개수가 0보다 크면 Nil, 0이면 그대로
    *
    */
  def drop[A](l: List[A], n: Int): List[A] = l match {
    case Nil  =>  Nil
    case Cons(h, Nil) =>  if (n > 0) Nil else l
//    case Cons(h, Cons(hs, Nil)) =>  Cons(hs, Nil) // 넣으면 정상동작 안 함 why? 아래의 case와 겹치기 때문인 듯 ..
    case Cons(h, ts)  =>  if (n-1 >= 0)  drop(ts, n-1) else l
  }


  /**
    * exercise 3-5 advance
    * Remove all match elements in list
    *
    * @param l
    * @param f
    * @tparam A
    * @return
    */
  def dropWhileAll[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil  =>  Nil
    case Cons(h, Nil) => if (f(h))  Nil else  l
    case Cons(h, t) =>  if (f(h)) t else  Cons(h, dropWhileAll(t, f))
  }

  /**
    * exercise 3-5
    * remove element in list
    * if not match break recursive.
    *
    * @param l
    * @param f
    * @tparam A
    * @return
    */
  def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
    case Nil  =>  Nil
    case Cons(h, Nil) => if (f(h))  Nil else  l
    case Cons(h, t) =>  if (f(h)) dropWhile(t, f) else l
  }

  /**
    * remove last element in list
    *
    * @param l
    * @tparam A
    * @return
    */
  def init[A](l: List[A]): List[A] = l match {
    case Nil => l
    case Cons(h, Nil) => Nil
    case Cons(h, t) => Cons(h, init(t))
  }
}

object Main extends App {
  var t : List[Int] = Cons(1, Cons(2, Cons(3, Cons(4, Nil))))
  var ta : List[Int] = List(1,1,1,2,1,1)

  // check status
  println(t)

  // exercise 3-2
  println(List.tail(t))

  // exercise 3-3
  println(List.setHeader(0, t))

  // exercise 3-4
  println(List.drop(t, 2))

  // exercise 3-5
  println(List.dropWhile[Int](ta, (x => x == 1)))

  // advanced 3-5
  println(List.dropWhileAll[Int](t, x  =>  x == 2))

  // exercise 3-6
  println(List.init(t))

}