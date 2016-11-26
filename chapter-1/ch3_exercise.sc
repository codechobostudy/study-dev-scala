object ch3_excercise {

  /**==========================
         - 함수적 자료 구조 -
     ==========================*/

  List.tail( List() )                             //> res0: ch3.List[Nothing] = Nil
  List.tail( List(1) )                            //> res1: ch3.List[Int] = Nil
  List.tail( List(1,2,3) )                        //> res2: ch3.List[Int] = Cons(2,Cons(3,Nil))
  
  List.setHead( List(), 4 )                       //> res3: ch3.List[Int] = Cons(4,Nil)
  List.setHead( List(1), 4 )                      //> res4: ch3.List[Int] = Cons(4,Nil)
  List.setHead( List(1,2,3), 4 )                  //> res5: ch3.List[Int] = Cons(4,Cons(2,Cons(3,Nil)))
  
  List.drop( List(), 1 )                          //> res6: ch3.List[Nothing] = Nil
  List.drop( List(1), 0 )                         //> res7: ch3.List[Int] = Cons(1,Nil)
  List.drop( List(1), 1 )                         //> res8: ch3.List[Int] = Nil
  List.drop( List(1,2,3), 1 )                     //> res9: ch3.List[Int] = Cons(2,Cons(3,Nil))
  List.drop( List(1,2,3), 4 )                     //> res10: ch3.List[Int] = Nil
  
  List.dropWhile( List(), (a:Int) => a<3 )        //> res11: ch3.List[Nothing] = Nil
  List.dropWhile( List(1), (a:Int) => a>3 )       //> res12: ch3.List[Int] = Cons(1,Nil)
  List.dropWhile( List(1,2,3), (a:Int) => a<0 )   //> res13: ch3.List[Int] = Cons(1,Cons(2,Cons(3,Nil)))
  List.dropWhile( List(1,2,3), (a:Int) => a<2 )   //> res14: ch3.List[Int] = Cons(2,Cons(3,Nil))
  List.dropWhile( List(1,2,3), (a:Int) => a<4 )   //> res15: ch3.List[Int] = Nil
  
  List.init( List() )                             //> res16: ch3.List[Nothing] = Nil
  List.init( List(1) )                            //> res17: ch3.List[Int] = Nil
  List.init( List(1,2,3) )                        //> res18: ch3.List[Int] = Cons(1,Cons(2,Nil))
  
  List.length( List() )                           //> res19: Int = 0
  List.length( List(1,2,3) )                      //> res20: Int = 3
  
  List.foldLeft ( List(1,2,3), 0 )(_ + _)         //> res21: Int = 6
  List.sum3( List(1,2,3) )                        //> res22: Int = 6
  List.product3( List(1,2,3) )                    //> res23: Double = 6.0
  List.reverse( List(1,2,3) )                     //> res24: ch3.List[Int] = Cons(3,Cons(2,Cons(1,Nil)))
  List.append( List(1,2,3), List(4,5) )           //> res25: ch3.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Cons(5,Nil)))))
  List.concat( List(List(1,2), List(3,4)) )       //> res26: ch3.List[Int] = Cons(1,Cons(2,Cons(3,Cons(4,Nil))))
  List.foldRight2( List(1,2,3), 0 )(_+_)          //> res27: Int = 6
  List.foldLeft2( List(1,2,3), Nil:List[Int] )((b,a) => Cons(a,b))
                                                  //> res28: ch3.List[Int] = Cons(3,Cons(2,Cons(1,Nil)))
  List.plusOne( List(1,2,3) )                     //> res29: ch3.List[Int] = Cons(2,Cons(3,Cons(4,Nil)))
  List.convert( List(1,2,3) )                     //> res30: ch3.List[String] = Cons(1.0,Cons(2.0,Cons(3.0,Nil)))
  
  List.map ( List(1,2,3) )(_.toString)            //> res31: ch3.List[String] = Cons(1,Cons(2,Cons(3,Nil)))
  List.filter( List(1,2,3) )( _%2 == 1 )          //> res32: ch3.List[Int] = Cons(1,Cons(3,Nil))
  List.flatMap(List(1,2))(a => List(a,a+1))       //> res33: ch3.List[Int] = Cons(1,Cons(2,Cons(2,Cons(3,Nil))))
  List.filter2( List(1,2,3) )( _%2 == 1 )         //> res34: ch3.List[Int] = Cons(1,Cons(3,Nil))
  
  List.addPair( List(1,2,3), List(1,2) )          //> res35: ch3.List[Int] = Cons(2,Cons(4,Nil))
  List.zipWith( List(1,2), List(3,4) )(_+_)       //> res36: ch3.List[Int] = Cons(4,Cons(6,Nil))
  
  List.startWith( List(), List() )                //> res37: Boolean = true
  List.startWith( List(), List(1) )               //> res38: Boolean = false
  List.startWith( List(1,2,3), List() )           //> res39: Boolean = true
  List.startWith( List(1,2,3), List(1,2) )        //> res40: Boolean = true
  List.startWith( List(1,2,3), List(1,3) )        //> res41: Boolean = false
  
  List.hasSubsequence(List(),List())              //> res42: Boolean = true
  List.hasSubsequence(List(),List(1))             //> res43: Boolean = false
  List.hasSubsequence(List(1,2,3),List())         //> res44: Boolean = true
  List.hasSubsequence(List(1,2,3),List(1,2))      //> res45: Boolean = true
  List.hasSubsequence(List(1,2,3),List(1,3))      //> res46: Boolean = false
  List.hasSubsequence(List(1,2,3),List(2))        //> res47: Boolean = true
  List.hasSubsequence(List(1,2,3),List(2,3))      //> res48: Boolean = true
  
  
  val twoDepthTree = Branch(Leaf(1),Branch(Leaf(2),Leaf(3)))
                                                  //> twoDepthTree  : ch3.Branch[Int] = Branch(Leaf(1),Branch(Leaf(2),Leaf(3)))
  Tree.size(twoDepthTree)                         //> res49: Int = 5
  Tree.maximum(Leaf(1))                           //> res50: Int = 1
  Tree.maximum(twoDepthTree)                      //> res51: Int = 3
  Tree.depth( Leaf(1) )                           //> res52: Int = 0
  Tree.depth(twoDepthTree)                        //> res53: Int = 2
  Tree.map( Leaf(1) )(_+1)                        //> res54: ch3.Tree[Int] = Leaf(2)
  Tree.map(twoDepthTree)(_+1)                     //> res55: ch3.Tree[Int] = Branch(Leaf(2),Branch(Leaf(3),Leaf(4)))
  
  Tree.fold(twoDepthTree)(a => 1)(1+_+_)          //> res56: Int = 5
  Tree.fold(twoDepthTree)(a => a)(_ max _)        //> res57: Int = 3
  Tree.fold(twoDepthTree)(a => 0)((a,b) => 1 + (a max b))
                                                  //> res58: Int = 2
  Tree.fold(twoDepthTree)(Leaf(_): Tree[Int])(Branch(_,_))
                                                  //> res59: ch3.Tree[Int] = Branch(Leaf(1),Branch(Leaf(2),Leaf(3)))


  /**=====================================
    List := Nil | Cons(value, List)
  ========================================*/
  sealed trait List[+A] // `List` data type, parameterized on a type, `A`
  case object Nil extends List[Nothing] // A `List` data constructor representing the empty list
  /* Another data constructor, representing nonempty lists. Note that `tail` is another `List[A]`,
  which may be `Nil` or another `Cons`.
   */
  case class Cons[+A](head: A, tail: List[A]) extends List[A]
  
  object List { // `List` companion object. Contains functions for creating and working with lists.
    def sum(ints: List[Int]): Int = ints match { // A function that uses pattern matching to add up a list of integers
      case Nil => 0 // The sum of the empty list is 0.
      case Cons(x,xs) => x + sum(xs) // The sum of a list starting with `x` is `x` plus the sum of the rest of the list.
    }
  
    def product(ds: List[Double]): Double = ds match {
      case Nil => 1.0
      case Cons(0.0, _) => 0.0
      case Cons(x,xs) => x * product(xs)
    }
  
    def apply[A](as: A*): List[A] = // Variadic function syntax
      if (as.isEmpty) Nil
      else Cons(as.head, apply(as.tail: _*))
  
    val x = List(1,2,3,4,5) match {
      case Cons(x, Cons(2, Cons(4, _))) => x
      case Nil => 42
      case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
      case Cons(h, t) => h + sum(t)
      case _ => 101
    }
  
    def append[A](a1: List[A], a2: List[A]): List[A] =
      a1 match {
        case Nil => a2
        case Cons(h,t) => Cons(h, append(t, a2))
      }
    
    def foldRight[A,B](as: List[A], z: B)(f: (A, B) => B): B = // Utility functions
      as match {
        case Nil => z
        case Cons(x, xs) => f(x, foldRight(xs, z)(f))
      }
  
    def sum2(ns: List[Int]) =
      foldRight(ns, 0)((x,y) => x + y)
  
    def product2(ns: List[Double]) =
      foldRight(ns, 1.0)(_ * _) // `_ * _` is more concise notation for `(x,y) => x * y`; see sidebar
  
    // ex 3.2
    def tail[A](l: List[A]): List[A] = l match {
      case Nil => Nil
      case Cons(_,t) => t
    }
  
    // ex 3.3
    def setHead[A](l: List[A], h: A): List[A] = l match {
      case Nil => apply(h)
      case Cons(_,t) => Cons(h,t)
    }
    
    // ex 3.4
    def drop[A](l: List[A], n: Int): List[A] = l match {
        case Nil => Nil
        case Cons(_,t) => if ( n>0 ) drop(t, n-1) else l
    }
  
    // ex 3.5
    def dropWhile[A](l: List[A], f: A => Boolean): List[A] = l match {
      case Nil => Nil
      case Cons(h,t) => if ( f(h) ) dropWhile(t,f) else l
    }
    
    def identity[A](l: List[A]): List[A] = l match {
      case Nil => Nil
      case Cons(h,t) => Cons( h, identity(t) )
    }
  
    // ex 3.6
    def init[A](l: List[A]): List[A] = l match {
      case Nil => Nil
      case Cons(h,Nil) => Nil
      case Cons(h,t) => Cons( h, init(t) )
    }
  
    def last[A](l: List[A]): A = l match {
      case Nil => sys.error("List is empty")
      case Cons(h,Nil) => h
      case Cons(h,t) => last(t)
    }
  
    // ex 3.9
    def length[A](l: List[A]): Int = foldRight(l,0)((a,b) => b+1)
    
    // ex 3.10
    @annotation.tailrec
    def foldLeft[A,B](l: List[A], z: B)(f: (B, A) => B): B = l match {
      case Nil => z
      case Cons(h,t) => foldLeft(t, f(z,h))(f)
    }
    
    // ex 3.11
    def sum3(ints: List[Int]): Int = foldLeft(ints, 0)(_ + _)
    def product3(ds: List[Double]): Double = foldLeft(ds, 1.0)(_ * _)
    
    // ex 3.12
    def reverse[A](l: List[A]): List[A] =
      foldLeft(l, Nil:List[A])((b,a) => Cons(a,b))
    
    // ex 3.13
    def foldRight2[A,B](as: List[A], z: B)(f: (A, B) => B): B =
      foldLeft(reverse(as), z)((b,a) => f(a,b))
    
    def foldLeft2[A,B](as: List[A], z: B)(f: (B, A) => B): B = {
      val reversed = foldRight(as, Nil:List[A])( (a,b) => append(b, Cons(a,Nil)) )
      foldRight(reversed, z)((a,b) => f(b,a))
    }
    
    // ex.3.14
    def append2[A](a1: List[A], a2: List[A]): List[A] =
      foldRight2(a1,a2)(Cons(_,_))
    
    // ex 3.15
    def concat[A](ll: List[List[A]]): List[A] =
      foldRight2(ll, Nil:List[A])(append2(_,_))
    
    // ex 3.16
    def plusOne[A](l: List[Int]): List[Int] =
      foldRight2(l, Nil:List[Int])( (a,b) => Cons(a+1, b) )
    
    // ex 3.17
    def convert[A](l: List[Double]): List[String] =
      foldRight2(l, Nil:List[String])( (a,b) => Cons(a.toString, b) )
    
    // ex 3.18
    def map[A,B](l: List[A])(f: A => B): List[B] =
      foldRight2(l, Nil:List[B])( (a,b) => Cons(f(a), b) )
      
    // ex 3.19
    def filter[A](l: List[A])(f: A => Boolean): List[A] =
      foldRight2(l, Nil: List[A])( (a,b) => if ( f(a) ) Cons(a,b) else b )
  
    // ex 3.20
    def  flatMap[A,B](as: List[A])(f: A => List[B]): List[B] =
      foldRight2(as, Nil: List[B])( (a,b) => append2(f(a), b) )
    
    // ex 3.21
    def filter2[A](l: List[A])(f: A => Boolean): List[A] =
      flatMap(l)( a => if ( f(a) ) List(a) else List() )
  
    // ex 3.22 : Pair도 자료 생성자이므로 패턴 매치에 사용될 수 있다.
     def addPair(as: List[Int], bs: List[Int]): List[Int] = (as,bs) match {
       case ( Cons(a,as), Cons(b,bs) ) => Cons( a+b, addPair(as,bs) )
       case _ => Nil
     }
  
    // ex 3.23
    def zipWith[A,B,C](as: List[A], bs: List[B])(f: (A,B) => C): List[C] = (as,bs) match {
       case ( Cons(a,as), Cons(b,bs) ) => Cons( f(a,b), zipWith(as,bs)(f) )
       case _ => Nil
    }
    
    def startWith[A](sup: List[A], sub:List[A]): Boolean = (sup, sub) match {
      case ( _, Nil ) => true
      case ( Nil, _ ) => false
      case ( Cons(a,as), Cons(b,bs) ) => a == b && startWith(as,bs)
    }
      
    // ex 3.24
    def hasSubsequence[A](sup: List[A], sub:List[A]): Boolean = (sup, sub) match {
      case ( _, Nil ) => true
      case ( Nil, _ ) => false
      case ( Cons(a,as), _ ) => startWith(sup, sub) || hasSubsequence(as,sub)
    }
  }
  
  
  /**===========================================
    Tree := Leaf value | Branch(Tree, Tree)
  ==============================================*/
  sealed trait Tree[+A]
  case class Leaf[A](value: A) extends Tree[A]
  case class Branch[+A](left: Tree[A], right: Tree[A]) extends Tree[A]
  
  
  object Tree {
    
    // ex 3.25
    def size[A](t: Tree[A]):Int = t match {
      case Leaf(v) => 1
      case Branch(l,r) => 1 + size(l) + size(r)
    }
  
    // ex 3.26
    def maximum(t: Tree[Int]):Int = t match {
      case Leaf(v) => v
      case Branch(l,r) => maximum(l) max maximum(r)
    }
    
    // ex 3.27
    def depth[A](t: Tree[A]):Int = t match {
      case Leaf(v) => 0
      case Branch(l,r) => 1 + ( depth(l) max depth(r) )
    }
    
    // ex 3.28
    def map[A,B](t: Tree[A])(f: A => B):Tree[B] = t match {
      case Leaf(v) => Leaf( f(v) )
      case Branch(l,r) => Branch( map(l)(f), map(r)(f) )
    }
    
    // ex 3.29
    def fold[A,B](t: Tree[A])(f:A => B)(g:(B,B) => B):B = t match {
      case Leaf(v) => f(v)
      case Branch(l,r) => g( fold(l)(f)(g), fold(r)(f)(g) )
    }
  }


}