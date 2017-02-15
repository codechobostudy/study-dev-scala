package book.fpis.chapter.one

import book.fpis.chapter.two

object CH5 {

  /**==========================
         - 엄격성과 나태성 -
     ==========================*/
  
  // 비엄격 함수를 만드는 방법: 인자에 thunk 사용, 스칼라가 제공하는 편의 구문(: => A) 이용, || 과 && 연산자 사용
  
  // 인자를 평가되지 않은 채로 함수 본문에 전달하기 위한 방법으로 '() => A' 형태의 함수를 사용할 수 있다.
  // 이러한 형태를 thunk 라고 하며 함수 본문에서 해당 함수를 호출하여 인자가 평가되도록 할 수 있다.
  def if2[A](cond: Boolean, onTrue: () => A, onFalse: () => A): A =
    if (cond) onTrue() else onFalse()             //> if2: [A](cond: Boolean, onTrue: () => A, onFalse: () => A)A
  
  if2(true, () => 1, () => {print("@");0})        //> res0: Int = 1

  // 어떤 인수의 형식 바로 앞에 '=>' 를 붙이면 해당 인수는 평가되지 않은 채로 함수 본문에 전달된다. ( 함수 시그니쳐의 onTrue: => A )
  // 함수 본문에서 해당 인수를 평가하기 위해 특별한 구문이 필요하지 않으며 '식별자'를 그대로 사용한다. ( 함수 본문의 onTrue )
  def if3[A](cond: Boolean, onTrue: => A, onFalse: => A): A =
    if (cond) onTrue else onFalse                 //> if3: [A](cond: Boolean, onTrue: => A, onFalse: => A)A
  
  if3(true, 1, {print("@");0})                    //> res1: Int = 1
  if3(true, () => 1, () => 0)                     //> res2: () => Int = <function0>
  if3(true, () => 1, () => 0)()                   //> res3: Int = 1
  if3(true, 1, if3(true, 1, 1))                   //> res4: Int = 1
  

  def twice(b: Boolean, i: => Int) = if (b) i+i else 0
                                                  //> twice: (b: Boolean, i: => Int)Int
  // val 선언에 lazy 를 추가하면 우변의 평가가 처음 참조되는 시점까지 지연되며 평가 결과를 캐쉬에 담아두고 이후 참조 시 캐시된 값을 사용한다.
  def twice2(b: Boolean, i: => Int) = {
   lazy val j = i
   if (b) j+j else 0
  }                                               //> twice2: (b: Boolean, i: => Int)Int
  
  twice(true, { print("@"); 1+1 })                //> @@res5: Int = 4
  twice2(true, { print("@"); 1+1 })               //> @res6: Int = 4


  
  val s123 = Stream(1,2,3)                        //> s123  : ch5.Stream[Int] = Cons(<function0>,<function0>)
  Stream(()=>1,()=>2,()=>3)                       //> res7: ch5.Stream[() => Int] = Cons(<function0>,<function0>)
  Stream(()=>1,()=>2,()=>3).toList                //> res8: List[() => Int] = List(<function0>, <function0>, <function0>)
  Stream(()=>1,()=>2,()=>3).toList.head           //> res9: () => Int = <function0>
  Stream(()=>1,()=>2,()=>3).toList.head()         //> res10: Int = 1
  
  s123.toList                                     //> res11: List[Int] = List(1, 2, 3)
  Empty.toList                                    //> res12: List[Nothing] = List()
  Cons(() => 0,() => s123).toList                 //> res13: List[Int] = List(0, 1, 2, 3)
  Stream.cons(0,s123).toList                      //> res14: List[Int] = List(0, 1, 2, 3)
  
  Empty.take(1)                             //> res15: ch5.Stream[Nothing] = Empty
  s123.take(0).toList                       //> res16: List[Int] = List()
  s123.take(1).toList                       //> res17: List[Int] = List(1)
  s123.take(3).toList                       //> res18: List[Int] = List(1, 2, 3)
  s123.take(4).toList                       //> res19: List[Int] = List(1, 2, 3)
  s123.take(3)                              //> res20: ch5.Stream[Int] = Cons(<function0>,<function0>)
  
  Empty.drop(1)                             //> res21: ch5.Stream[Nothing] = Empty
  s123.drop(0).toList                       //> res22: List[Int] = List(1, 2, 3)
  s123.drop(1).toList                       //> res23: List[Int] = List(2, 3)
  s123.drop(3).toList                       //> res24: List[Int] = List()
  s123.drop(4).toList                       //> res25: List[Int] = List()
  s123.drop(1)                              //> res26: ch5.Stream[Int] = Cons(<function0>,<function0>)
  
  Empty.takeWhile((p:Int) => p<3)           //> res27: ch5.Stream[Nothing] = Empty
  s123.takeWhile((p:Int) => p<5).toList     //> res28: List[Int] = List(1, 2, 3)
  s123.takeWhile((p:Int) => p<3).toList     //> res29: List[Int] = List(1, 2)
  s123.takeWhile((p:Int) => p<1).toList     //> res30: List[Int] = List()
  s123.takeWhile((p:Int) => p<3)            //> res31: ch5.Stream[Int] = Cons(<function0>,<function0>)

  Empty.forAll((p:Int) => p<3)              //> res32: Boolean = true
  s123.forAll((p:Int) => p<5)               //> res33: Boolean = true
  s123.forAll((p:Int) => p<3)               //> res34: Boolean = false
  s123.forAll((p:Int) => p<1)               //> res35: Boolean = false
  s123.forAll((p:Int) => p<3)               //> res36: Boolean = false
    
  Empty.takeWhile2((p:Int) => p<3)          //> res37: ch5.Stream[Nothing] = Empty
  s123.takeWhile2((p:Int) => p<5).toList    //> res38: List[Int] = List(1, 2, 3)
  s123.takeWhile2((p:Int) => p<3).toList    //> res39: List[Int] = List(1, 2)
  s123.takeWhile2((p:Int) => p<1).toList    //> res40: List[Int] = List()
  s123.takeWhile2((p:Int) => p<3)           //> res41: ch5.Stream[Int] = Cons(<function0>,<function0>)
  
  Empty.headOption                          //> res42: Option[Nothing] = None
  Stream(1).headOption                      //> res43: Option[Int] = Some(1)
  s123.headOption                           //> res44: Option[Int] = Some(1)
  
  Empty.map((a: Int) => a+1)                //> res45: ch5.Stream[Int] = Empty
  s123.map((a: Int) => a+1).toList          //> res46: List[Int] = List(2, 3, 4)
  Empty.filter((p:Int) => p!=2)             //> res47: ch5.Stream[Nothing] = Empty
  s123.filter((p:Int) => p!=2).toList       //> res48: List[Int] = List(1, 3)
  
  Empty.append(Stream.empty).toList         //> res49: List[Nothing] = List()
  Empty.append(s123).toList                 //> res50: List[Int] = List(1, 2, 3)
  s123.append(Stream.empty).toList          //> res51: List[Int] = List(1, 2, 3)
  s123.append(s123).toList                  //> res52: List[Int] = List(1, 2, 3, 1, 2, 3)
  
  Empty.flatMap((_:Int) => s123).toList     //> res53: List[Int] = List()
  s123.flatMap(_ => Stream.empty).toList    //> res54: List[Nothing] = List()
  s123.flatMap(_ => s123).toList            //> res55: List[Int] = List(1, 2, 3, 1, 2, 3, 1, 2, 3)

  Stream.ones.take(2).toList                      //> res56: List[Int] = List(1, 1)
  
  Stream.constant(2).take(2).toList               //> res57: List[Int] = List(2, 2)
  Stream.from(2).take(3).toList                   //> res58: List[Int] = List(2, 3, 4)
  Stream.fibs.take(8).toList                      //> res59: List[Int] = List(0, 1, 1, 2, 3, 5, 8, 13)

  Stream.unfold(1)((a) => Some(a,a+1)).take(5).toList
                                                  //> res60: List[Int] = List(1, 2, 3, 4, 5)
  Stream.unfold(1)((a) => None).take(5).toList    //> res61: List[Nothing] = List()
  
  Stream.fibs2.take(8).toList                     //> res62: List[Int] = List(0, 1, 1, 2, 3, 5, 8, 13)
  Stream.from2(2).take(3).toList                  //> res63: List[Int] = List(2, 3, 4)
  Stream.constant(2).take(2).toList               //> res64: List[Int] = List(2, 2)
  Stream.onse2.take(5).toList                     //> res65: List[Int] = List(1, 1, 1, 1, 1)

  Empty.map2((a: Int) => a+1)               //> res66: ch5.Stream[Int] = Empty
  s123.map2((a: Int) => a+1).toList         //> res67: List[Int] = List(2, 3, 4)

  Empty.take2(1)                            //> res68: ch5.Stream[Nothing] = Empty
  s123.take2(0).toList                      //> res69: List[Int] = List()
  s123.take2(1).toList                      //> res70: List[Int] = List(1)
  s123.take2(3).toList                      //> res71: List[Int] = List(1, 2, 3)
  s123.take2(4).toList                      //> res72: List[Int] = List(1, 2, 3)
  s123.take2(3)                             //> res73: ch5.Stream[Int] = Cons(<function0>,<function0>)

  Empty.takeWhile3((p:Int) => p<3)          //> res74: ch5.Stream[Nothing] = Empty
  s123.takeWhile3((p:Int) => p<5).toList    //> res75: List[Int] = List(1, 2, 3)
  s123.takeWhile3((p:Int) => p<3).toList    //> res76: List[Int] = List(1, 2)
  s123.takeWhile3((p:Int) => p<1).toList    //> res77: List[Int] = List()
  s123.takeWhile3((p:Int) => p<3)           //> res78: ch5.Stream[Int] = Cons(<function0>,<function0>)

  Empty.zipWith(s123)((a: Int,b) => a+b).toList   //> res79: List[Int] = List()
  s123.zipWith(Empty)((a,b:Int) => a+b).toList    //> res80: List[Int] = List()
  s123.zipWith(s123)((a,b) => a+b).toList         //> res81: List[Int] = List(2, 4, 6)
  s123.zipWith(Stream(1,2))((a,b) => a+b).toList  //> res82: List[Int] = List(2, 4)

  Empty.zipAll(s123).toList                       //> res83: List[(Option[Nothing], Option[Int])] = List((None,Some(1)), (None,So
                                                  //| me(2)), (None,Some(3)))
  s123.zipAll(Empty).toList                       //> res84: List[(Option[Int], Option[Nothing])] = List((Some(1),None), (Some(2)
                                                  //| ,None), (Some(3),None))
  s123.zipAll(s123).toList                        //> res85: List[(Option[Int], Option[Int])] = List((Some(1),Some(1)), (Some(2),
                                                  //| Some(2)), (Some(3),Some(3)))
  s123.zipAll(Stream(1,2)).toList                 //> res86: List[(Option[Int], Option[Int])] = List((Some(1),Some(1)), (Some(2),
                                                  //| Some(2)), (Some(3),None))
  None == None                                    //> res87: Boolean = true
  None == Option(Stream(1,2,3).toList)            //> res88: Boolean = false
  s123.toList == Stream(1,2,3).toList             //> res89: Boolean = true
  Option(s123.toList) == Option(Stream(1,2,3).toList)
                                                  //> res90: Boolean = true
  Empty.startsWith(Empty)                         //> res91: Boolean = true
  Empty.startsWith(s123)                          //> res92: Boolean = false
  s123.startsWith(Empty)                          //> res93: Boolean = true
  s123.startsWith(Stream(1,2))                    //> res94: Boolean = true
  s123.startsWith(Stream(2,3))                    //> res95: Boolean = false
  s123.startsWith(Stream(1,2,3))                  //> res96: Boolean = true
  s123.startsWith(Stream(1,2,3,4))                //> res97: Boolean = false
 
  s123.tails.toList                               //> res98: List[ch5.Stream[Int]] = List(Cons(<function0>,<function0>), Cons(<fu
                                                  //| nction0>,<function0>), Cons(<function0>,<function0>), Empty)
  s123.tails.toList.head.toList                   //> res99: List[Int] = List(1, 2, 3)
  s123.tails.toList.tail.head.toList              //> res100: List[Int] = List(2, 3)
  s123.tails.toList.tail.tail.head.toList         //> res101: List[Int] = List(3)
  s123.tails.toList.last.toList                   //> res102: List[Int] = List()
  s123.tailsList                                  //> res103: List[List[Int]] = List(List(1, 2, 3), List(2, 3), List(3), List())
                                                  //| 
  (Empty:Stream[Int]).scanRight(0)(_+_).toList    //> res104: List[Int] = List(0)
  s123.scanRight(0)(_+_).toList                   //> res105: List[Int] = List(6, 5, 3, 0)
  (Empty:Stream[Int]).scanLeft(0)(_+_).toList     //> res106: List[Int] = List(0)
  s123.scanLeft(0)(_+_).toList                    //> res107: List[Int] = List(0, 1, 3, 6)

  import Stream._
  trait Stream[+A] {

    // ex 5.1
    def toList: two.List[A] = this match {
      case Empty => two.List()
      case Cons(h,t) => h() :: t().toList
    }
    
    // 초기값과 f의 두번째 인자가 게으른 방식으로 평가되는 foldRight
    def foldRight[B](z: => B)(f: (A, => B) => B): B = // The arrow `=>` in front of the argument type `B` means that the function `f` takes its second argument by name and may choose not to evaluate it.
      this match {
        case Cons(h,t) => f(h(), t().foldRight(z)(f)) // If `f` doesn't evaluate its second argument, the recursion never occurs.
        case _ => z
      }
  
    def exists(p: A => Boolean): Boolean =
      foldRight(false)((a, b) => p(a) || b) // Here `b` is the unevaluated recursive step that folds the tail of the stream. If `p(a)` returns `true`, `b` will never be evaluated and the computation terminates early.
  
    @annotation.tailrec
    final def find(f: A => Boolean): Option[A] = this match {
      case Empty => None
      case Cons(h, t) => if (f(h())) Some(h()) else t().find(f)
    }
    
    // ex 5.2 : case 구문에 if 조건을 넣을 수 있다. 일반 if 구문과 달리 조건부에 괄호를 넣지 않아도 된다.
    def take(n: Int): Stream[A] = this match {
      case Cons(h,t) if n>0 => cons(h(),t().take(n-1))
      case _ => Empty
    }
    
    def drop(n: Int): Stream[A] = this match {
      case Cons(h,t) => if (n>0) t().drop(n-1) else this
      case _ => Empty
    }
  
    // ex 5.3
    def takeWhile(p: A => Boolean): Stream[A] = this match {
      case Cons(h,t) if p(h()) => cons( h(), t().takeWhile(p) )
      case _ => Empty
    }
    
    // ex 5.4 : p(a)가 true를 반환하면 게으른 평가가 적용된 && 연산자 오른쪽의 b( t().foldRight(z)(f) )는 평가되지 못한다.
    def forAll(p: A => Boolean): Boolean =
      foldRight(true)((a,b) => p(a) && b)
  
    // ex 5.5 : p(a)가 true인 경우에만 b가 평가된다.
    def takeWhile2(p: A => Boolean): Stream[A] =
      foldRight(empty: Stream[A])((a,b) => if (p(a)) cons(a,b) else empty)
    
    // ex 5.6 : foldRight의 정의에 따라
    //            Empty인 경우 - 첫번째 파라미터로 넘긴 None이 그대로 반환된다.
    //            Cons(h,t)인 경우 - Some(h())가 반환되고 b는 평가되지 못한다.
    def headOption: Option[A] =
      foldRight(None: Option[A])((a,b) => Some(a))
      
    // 5.7 map, filter, append, flatmap using foldRight. Part of the exercise is
    // writing your own function signatures.
    
    // ex 5.7
    def map[B](f: A => B): Stream[B] =
      foldRight(empty: Stream[B])((a,b) => cons(f(a),b))
      
    def filter(f: A => Boolean): Stream[A] =
      foldRight(empty: Stream[A])((a,b) => if (f(a)) cons(a,b) else b)
    
    // 타입을 아래와 같이 Stream[A]로 놓으면 Empty에 Cons(h,t)를 append 하려고 하는 경우 타입 오류가 발생한다.
    // def append(o: => Stream[A]): Stream[A] =
    // Stream[+A] 를 A의 공변 형식으로 선언해도 안전하다고 추론하게 하기위해 B >: A 라고 지정해야 한다.
    def append[B>:A](o: => Stream[B]): Stream[B] =
      foldRight(o)((a,b) => cons(a,b))
  
    def flatMap[B](f: A => Stream[B]): Stream[B] =
      foldRight(empty: Stream[B])((a,b) => f(a).append(b))
    
    // ex 5.13
    def map2[B](f: A => B): Stream[B] = unfold(this){
      case Empty => None
      case Cons(h,t) => Some((f(h()),t()))
    }
    
    def take2(n: Int): Stream[A] = unfold((this,n)){
      case (Cons(h,t),n) if n>0 => Some(( h(), (t(), n-1) ))
      case _ => None
    }
    
    def takeWhile3(p: A => Boolean): Stream[A] = unfold((this)){
      case Cons(h,t) if p(h()) => Some(( h(), t() ))
      case _ => None
    }

    def zipWith[B,C](b: Stream[B])(f: (A,B) => C): Stream[C] = unfold( (this,b) ) {
      case (Cons(h1,t1), Cons(h2,t2)) => Some(( f(h1(), h2()), (t1(), t2()) ))
      case _ => None
    }
    
    def zipAll[B](s2: Stream[B]): Stream[(Option[A],Option[B])] = unfold( (this,s2) ) {
      case ( Cons(h1,t1), Empty ) => Some (( (Some(h1()), None), (t1(),empty) ))
      case ( Empty, Cons(h2,t2) ) => Some (( (None, Some(h2())), (empty,t2()) ))
      case ( Cons(h1,t1), Cons(h2,t2) ) => Some (( (Some(h1()), Some(h2())), (t1(), t2()) ))
      case ( Empty, Empty ) => None
    }
    
    // ex 5.14 : filter(a => a._1 == a._2) 에서 a._1 과 a._2 둘다 None 인 경우는 없으므로 동등비교만으로 충분하다.
    def startsWith[B](s: Stream[B]): Boolean =
      zipAll(s).filter(a => a._1 == a._2).map(b => b._1.get).toList == s.toList
      
    // ex 5.15
    def tails: Stream[Stream[A]] = (unfold (this) {
      case Cons(h,t) => Some(( cons(h(),t()), t()  ))
      case _ => None
    }).append( Stream(Stream()) )
    
    def tailsList: two.List[two.List[A]] =
      tails.toList.init.foldRight(two.List(two.List()): two.List[two.List[A]])(_.toList :: _)
    
    // ex 5.16
    def scanRight[B](z: B)(f: (A, => B) => B): Stream[B] = foldRight(Stream(z)){
      //case ( a, Cons(h,t) ) => Stream( f(a,h()) ).append( Cons(h,t) )
      case ( a, Cons(h,t) ) => cons( f(a,h()), Cons(h,t) )
      case _ => empty
    }
      
    def scanLeft[B](z: B)(f: (A, => B) => B): Stream[B] = cons(z, unfold((this,z)) {
      case ( Cons(h,t), z ) => { val a = f(h(),z); Some(( a, (t(),a) )) }
      case _ => None
    })
  }
  
  /**========================================
       Stream := Empty | Cons(head, Stream)
  ===========================================*/
  case object Empty extends Stream[Nothing]
  // h와 t는 기술적인 한계로 이름으로 전달되는 인수가 아닌 thunk 이다.
  case class Cons[+A](h: () => A, t: () => Stream[A]) extends Stream[A]
  
  object Stream {
  
    // thunk 를 한번만 평가하기 위한 smart 생성자
    def cons[A](hd: => A, tl: => Stream[A]): Stream[A] = {
      lazy val head = hd
      lazy val tail = tl
      Cons(() => head, () => tail)
    }
  
    def empty[A]: Stream[A] = Empty
  
    def apply[A](as: A*): Stream[A] =
      if (as.isEmpty) empty
      else cons(as.head, apply(as.tail: _*))
    
    val ones: Stream[Int] = Stream.cons(1, ones)
    
    // ex 5.8
    def constant[A](a: A): Stream[A] =
      cons(a, constant(a))
    
    // ex 5.9
    def from(n: Int): Stream[Int] =
      cons(n, from(n+1))
      
    // ex 5.10
    // http://www.scala-lang.org/files/archive/api/current/scala/Tuple2.html
    def fibs: Stream[Int] = {
      def gen(a: (Int,Int)): Stream[Int] =
        cons(a._1, gen(a._2, a._1+a._2))
      gen( (0,1) )
    }
    
    // ex 5.11 : 인자로 '초기상태' 하나와 '(현재 값, 다음 상태) 쌍을 산출하는 함수'를 받는다.
    //           반환값은 현재 값들을 연결한 스트림이다.
    // http://www.scala-lang.org/files/archive/api/current/scala/Option.html
    def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] = {
      val o = f(z)
      if (o == None) empty
      else cons(o.get._1, unfold(o.get._2)(f))
    }
    
    // ex 5.12
    def fibs2: Stream[Int] =
      unfold((0,1))( a => Some(( a._1, (a._2, a._1+a._2) )) )
    
    def from2(n: Int): Stream[Int] =
      unfold(n)( a => Some((a, a+1)) )
          
    def constant2[A](a: A): Stream[A] =
      unfold(a)( b => Some((b,b)) )
      
    def onse2: Stream[Int] =
      unfold(1)( a => Some((a,a)) )
  }

}