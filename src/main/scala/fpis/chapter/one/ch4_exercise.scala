object ch4 {

  /**====================================
         - 예외를 이용하지 않은 오류 처리 -
     ====================================*/

  val some1 = Some(1)                       //> some1  : ch4.Some[Int] = Some(1)
  val noneInt = (None: Option[Int])         //> noneInt  : ch4.Option[Int] = None
  
  some1.map(a => a+1)                       //> res0: ch4.Option[Int] = Some(2)
  noneInt.map(_+1)                          //> res1: ch4.Option[Int] = None
  
  some1.getOrElse(2)                        //> res2: Int = 1
  None.getOrElse(2)                         //> res3: Int = 2
  
  some1.flatMap(a => Some(a+1))             //> res4: ch4.Option[Int] = Some(2)
  noneInt.flatMap(a => Some(a+1))           //> res5: ch4.Option[Int] = None

  some1.orElse(Some(2))                     //> res6: ch4.Option[Int] = Some(1)
  some1.orElse(None)                        //> res7: ch4.Option[Int] = Some(1)
  noneInt.orElse(Some(2))                   //> res8: ch4.Option[Int] = Some(2)

  some1.filter(a => a==1)                   //> res9: ch4.Option[Int] = Some(1)
  some1.filter(a => a!=1)                   //> res10: ch4.Option[Int] = None
  noneInt.filter(_==1)                      //> res11: ch4.Option[Int] = None
  
  val seq123 = Seq(1.0, 2.0, 3.0)           //> seq123  : Seq[Double] = List(1.0, 2.0, 3.0)
  seq123.sum / seq123.length                //> res12: Double = 2.0
  
  Option.variance(seq123)                   //> res13: ch4.Option[Double] = Some(0.6666666666666666)
  
  
  
  Option.lift((a:Int) => a+1)(some1)        //> res14: ch4.Option[Int] = Some(2)

  Option.Try("a".toInt)                           //> res15: ch4.Option[Int] = None
  Option.Try2( try "a".toInt catch { case e: Exception => 0 } )
                                                  //> res16: ch4.Option[Int] = Some(0)
  Option.Try("123".toInt)                         //> res17: ch4.Option[Int] = Some(123)
  
  Option.map2(some1,some1)(_+_)             //> res18: ch4.Option[Int] = Some(2)
  
  
  
  def calc(a: Int, b: Int): Double = a  * 10.0 / b//> calc: (a: Int, b: Int)Double
  
  def tryToInt(a: String): Option[Int] =
    Option.Try(a.toInt)                           //> tryToInt: (a: String)ch4.Option[Int]
  
  def parseAndCalc(a: String, b: String): Option[Double] =
    Option.map2( tryToInt(a), tryToInt(b) )(calc) //> parseAndCalc: (a: String, b: String)ch4.Option[Double]
    
  calc(10,20)                                     //> res19: Double = 5.0
  parseAndCalc("10", "20")                        //> res20: ch4.Option[Double] = Some(5.0)
  parseAndCalc("a", "20")                         //> res21: ch4.Option[Double] = None
  parseAndCalc("10", "20").getOrElse(0.0)         //> res22: Double = 5.0
  parseAndCalc("a", "20").getOrElse(0.0)          //> res23: Double = 0.0
  
  def square(d: Double) = d * d                   //> square: (d: Double)Double
  Option.lift(square)( parseAndCalc("10", "20") ) //> res24: ch4.Option[Double] = Some(25.0)
  
  Option.sequence(List(Some(1),Some(2)))    //> res25: ch4.Option[List[Int]] = Some(List(1, 2))
  Option.sequence(List(Some(1),None))       //> res26: ch4.Option[List[Int]] = None
  
  Option.traverse(List(1,2,3))(a => Some(a))//> res27: ch4.Option[List[Int]] = Some(List(1, 2, 3))
  Option.traverse(List(1,2,3))(a => if (a==2) None else Some(a))
                                                  //> res28: ch4.Option[List[Int]] = None
  Option.sequence2(List(Some(1),Some(2)))   //> res29: ch4.Option[List[Int]] = Some(List(1, 2))
  Option.sequence2(List(Some(1),None))      //> res30: ch4.Option[List[Int]] = None
  
  Either.Try(10/0)                          //> res31: ch4.Either[Exception,Int] = Left(java.lang.ArithmeticException: / by
                                                  //|  zero)
  def parseAndCalc2(a: String, b: String): Either[Exception, Double] =
    Either.Try(a.toInt).map2( Either.Try(b.toInt) )(calc)
                                                  //> parseAndCalc2: (a: String, b: String)ch4.Either[Exception,Double]
  
  parseAndCalc2("10", "20")                       //> res32: ch4.Either[Exception,Double] = Right(5.0)
  parseAndCalc2("a", "20")                        //> res33: ch4.Either[Exception,Double] = Left(java.lang.NumberFormatException:
                                                  //|  For input string: "a")
  
  val right1 = Right(1)                     //> right1  : ch4.Right[Int] = Right(1)
  val left1 = Left("1"):Either[String,Int]  //> left1  : ch4.Either[String,Int] = Left(1)
  
  right1.map(a => a+1)                      //> res34: ch4.Either[Nothing,Int] = Right(2)
  left1.map(a => a+1)                       //> res35: ch4.Either[String,Int] = Left(1)
  
  right1.flatMap(a => Right(a+1))           //> res36: ch4.Either[Nothing,Int] = Right(2)
  left1.flatMap(a => Right(a+1))            //> res37: ch4.Either[String,Int] = Left(1)
  
  right1.map2(right1)(_+_)                  //> res38: ch4.Either[Nothing,Int] = Right(2)
  
  right1.orElse(Right(2.0))                 //> res39: ch4.Either[Nothing,AnyVal{def getClass(): Class[_ >: Int with Double
                                                  //|  <: AnyVal]}] = Right(1)
  left1.orElse(Right(2.0))                  //> res40: ch4.Either[String,AnyVal{def getClass(): Class[_ >: Int with Double 
                                                  //| <: AnyVal]}] = Right(2.0)
  
  
  Either.traverse(List(1,2))(a => Right(a)) //> res41: ch4.Either[Nothing,List[Int]] = Right(List(1, 2))
  Either.sequence(List(right1,right1))      //> res42: ch4.Either[Nothing,List[Int]] = Right(List(1, 1))
  
  
  import scala.{Option => _, Some => _, Either => _, _} // hide std library `Option`, `Some` and `Either`, since we are writing our own in this chapter
  
  /**===============================
      Option := Some(value) | None
  ==================================*/
  sealed trait Option[+A] {
    
    // ex 4.1
    def map[B](f: A => B): Option[B] = this match {
      case None => None
      case Some(v) => Some(f(v))
    }
  
    def getOrElse[B>:A](default: => B): B = this match {
      case None => default
      case Some(v) => v
    }
  
    def flatMap[B](f: A => Option[B]): Option[B] =
      this.map(a => f(a)).getOrElse(None)
  
    def orElse[B>:A](ob: => Option[B]): Option[B] =
      this.map(a => Some(a)).getOrElse(ob)
  
    def filter(f: A => Boolean): Option[A] =
      this.flatMap(a => if (f(a)) this else None)
  }
  case class Some[+A](get: A) extends Option[A]
  case object None extends Option[Nothing]
  
  object Option {
    def failingFn(i: Int): Int = {
      val y: Int = throw new Exception("fail!") // `val y: Int = ...` declares `y` as having type `Int`, and sets it equal to the right hand side of the `=`.
      try {
        val x = 42 + 5
        x + y
      }
      catch { case e: Exception => 43 } // A `catch` block is just a pattern matching block like the ones we've seen. `case e: Exception` is a pattern that matches any `Exception`, and it binds this value to the identifier `e`. The match returns the value 43.
    }
  
    def failingFn2(i: Int): Int = {
      try {
        val x = 42 + 5
        x + ((throw new Exception("fail!")): Int) // A thrown Exception can be given any type; here we're annotating it with the type `Int`
      }
      catch { case e: Exception => 43 }
    }
  
    // http://www.scala-lang.org/api/current/scala/collection/Seq.html
    def mean(xs: Seq[Double]): Option[Double] =
      if (xs.isEmpty) None
      else Some(xs.sum / xs.length)
  
    /**
     ex 4.2: variance 함수를 flatMap을 이용해 구현하라. 순차열의 평균이 m이라 할 때, 분산은 각 요소 x에 대한 math.pow(x-m, 2)들의 평균이다.
     
        분산(variance) = sum( (변량-평균)^2 ) / 변량개수
        예를들면 값들이 1,3,5 가 있다면 평균 = 3, 변량개수 = 3, 분산 = ( (1-3)^2 + (3-3)^2 + (1-5)^2 ) / 3
    */
    def variance(xs: Seq[Double]): Option[Double] =
      mean(xs).flatMap(m => mean( xs.map(x => math.pow(x-m, 2)) ) )
    
    // http://stackoverflow.com/questions/28375449/meaning-of-underscore-in-lifta-bf-a-b-optiona-optionb-map-f
    def lift[A,B](f: A => B): Option[A] => Option[B] = _ map f //  =  _.map(f)  =  (a) => a.map(f)
    
    // a를 평가하는 도중 예외가 발생하면 None 으로 변환하기 위해 엄격하지 않은 방식으로 A 인수를 받는다.
    // ( 표현식 a를 함수 본문에서 사용되는 시점에 평가되도록 만든다. )
    def Try[A](a: => A): Option[A] =
      try Some(a)
      catch { case e: Exception => None }
    
    def Try2[A](a: A): Option[A] =
      try Some(a)
      catch { case e: Exception => None }
    
    // ex 4.3
    def map2[A,B,C](oa: Option[A], ob: Option[B])(f: (A, B) => C): Option[C] =
      oa.flatMap( a => ob.map(b => f(a,b)) )
  
    // ex 4.4
    // http://www.scala-lang.org/api/current/scala/collection/immutable/List.html
    def sequence[A](a: List[Option[A]]): Option[List[A]] =
      a.foldRight( Some(Nil):Option[List[A]] )( (oa,ob) => ob.flatMap(b => oa.map(a => a::b)) )
  
    // ex 4.5
    def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] =
      a.foldRight( Some(Nil):Option[List[B]] )( (a,ob) => ob.flatMap(b => f(a).map(aa => aa::b)) )
    
    def sequence2[A](a: List[Option[A]]): Option[List[A]] =
      traverse(a)(aa => aa)
  }
  
  
  
  import scala.{Option => _, Either => _, Left => _, Right => _, _} // hide std library `Option` and `Either`, since we are writing our own in this chapter
  
  /**=======================================
      Either := Left(value) | Right(value)
  ==========================================*/
  sealed trait Either[+E,+A] {
  
    //ex 4.6
    def map[B](f: A => B): Either[E, B] = this match {
      case Left(e) => Left(e)
      case Right(a) => Right(f(a))
    }
  
    def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B] = this match {
      case Left(e) => Left(e)
      case Right(a) => f(a)
    }
  
    def orElse[EE >: E, B >: A](b: => Either[EE, B]): Either[EE, B] = this match {
      case Left(e) => b
      case Right(a) => this
    }
    def map2[EE >: E, B, C](eb: Either[EE, B])(f: (A, B) => C): Either[EE, C] =
      this.flatMap( a => eb.map(b => f(a,b)) )
  }
  case class Left[+E](get: E) extends Either[E,Nothing]
  case class Right[+A](get: A) extends Either[Nothing,A]
  
  object Either {
    
    // ex 4.7
    def traverse[E,A,B](es: List[A])(f: A => Either[E, B]): Either[E, List[B]] =
      es.foldRight( Right(Nil):Either[E, List[B]] )( (a,eb) => f(a).flatMap(aa => eb.map(b => aa::b)) )
   
    def sequence[E,A](es: List[Either[E,A]]): Either[E,List[A]] =
      traverse(es)(i => i)
  
    def mean(xs: IndexedSeq[Double]): Either[String, Double] =
      if (xs.isEmpty)
        Left("mean of empty list!")
      else
        Right(xs.sum / xs.length)
  
    def safeDiv(x: Int, y: Int): Either[Exception, Int] =
      try Right(x / y)
      catch { case e: Exception => Left(e) }
  
    def Try[A](a: => A): Either[Exception, A] =
      try Right(a)
      catch { case e: Exception => Left(e) }
  
  }
  
}
