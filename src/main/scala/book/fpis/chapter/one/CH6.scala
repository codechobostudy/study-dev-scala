package book.fpis.chapter.one

import book.fpis.chapter.two

object CH6 {

  /**==========================
         - 순수 함수적 상태 -
     ==========================*/
     
  val (v1,r1) = SimpleRNG(1).nextInt              //> v1  : Int = 384748
                                                  //| r1  : ch6.RNG = SimpleRNG(25214903928)
  val (v2,r2) = r1.nextInt                        //> v2  : Int = -1151252339
                                                  //| r2  : ch6.RNG = SimpleRNG(206026503483683)
  
  Int.MinValue                                    //> res0: Int(-2147483648) = -2147483648
  math.abs(Int.MinValue+1)                        //> res1: Int = 2147483647
  math.abs(Int.MinValue)                          //> res2: Int = -2147483648
  math.abs(Int.MaxValue)                          //> res3: Int = 2147483647
  math.abs(Int.MaxValue+1)                        //> res4: Int = -2147483648
  
  RNG.nonNegativeInt(r1)                          //> res5: (Int, ch6.RNG) = (1151252339,SimpleRNG(206026503483683))
  RNG.double(r1)                                  //> res6: (Double, ch6.RNG) = (0.5360936461947858,SimpleRNG(206026503483683))
  RNG.intDouble(r1)                               //> res7: ((Int, Double), ch6.RNG) = ((-1151252339,0.25582678942009807),SimpleRN
                                                  //| G(245470556921330))
  RNG.doubleInt(r1)                               //> res8: ((Double, Int), ch6.RNG) = ((0.5360936461947858,-549383847),SimpleRNG(
                                                  //| 245470556921330))
  RNG.double3(r1)                                 //> res9: ((Double, Double, Double), ch6.RNG) = ((0.5360936461947858,0.255826789
                                                  //| 42009807,0.7510961224325001),SimpleRNG(105707381795861))
  RNG.ints(3)(r1)                                 //> res10: (List[Int], ch6.RNG) = (List(-1151252339, -549383847, 1612966641),Sim
                                                  //| pleRNG(105707381795861))
  r1.nextInt._2.nextInt._2.nextInt                //> res11: (Int, ch6.RNG) = (1612966641,SimpleRNG(105707381795861))
  
  val identity = RNG.unit(1)(r1)                  //> identity  : (Int, ch6.RNG) = (1,SimpleRNG(25214903928))
  RNG.unit(identity._1)(identity._2)              //> res12: (Int, ch6.RNG) = (1,SimpleRNG(25214903928))

  RNG.nonNegativeEven(r1)                         //> res13: (Int, ch6.RNG) = (1151252338,SimpleRNG(206026503483683))
  RNG.nonNegativeEven(RNG.nonNegativeEven(r1)._2) //> res14: (Int, ch6.RNG) = (549383846,SimpleRNG(245470556921330))

  RNG.map(RNG.nonNegativeInt)(_ / (Int.MaxValue.toDouble+1))(r1)
                                                  //> res15: (Double, ch6.RNG) = (0.5360936461947858,SimpleRNG(206026503483683))
  RNG.double2(r1)                                 //> res16: (Double, ch6.RNG) = (0.5360936461947858,SimpleRNG(206026503483683))

  RNG.map2(RNG.int, RNG.double2)((a,b) => (a,b))(r1)
                                                  //> res17: ((Int, Double), ch6.RNG) = ((-1151252339,0.25582678942009807),SimpleR
                                                  //| NG(245470556921330))
  RNG.map2(RNG.double2, RNG.int)((a,b) => (a,b))(r1)
                                                  //> res18: ((Double, Int), ch6.RNG) = ((0.5360936461947858,-549383847),SimpleRNG
                                                  //| (245470556921330))
  RNG.both(RNG.int, RNG.double2)(r1)              //> res19: ((Int, Double), ch6.RNG) = ((-1151252339,0.25582678942009807),SimpleR
                                                  //| NG(245470556921330))
  RNG.both(RNG.double2, RNG.int)(r1)              //> res20: ((Double, Int), ch6.RNG) = ((0.5360936461947858,-549383847),SimpleRNG
                                                  //| (245470556921330))

  RNG.sequence(two.List(RNG.int, RNG.double2))(r1)    //> res21: (List[AnyVal], ch6.RNG) = (List(-1151252339, 0.25582678942009807),Sim
                                                  //| pleRNG(245470556921330))
  RNG.sequence(two.List(RNG.double2, RNG.int))(r1)    //> res22: (List[AnyVal], ch6.RNG) = (List(0.5360936461947858, -549383847),Simpl
                                                  //| eRNG(245470556921330))
  RNG.sequence2(two.List(RNG.int, RNG.double2))(r1)   //> res23: (List[AnyVal], ch6.RNG) = (List(-1151252339, 0.25582678942009807),Si
                                                  //| mpleRNG(245470556921330))
  RNG.sequence2(two.List(RNG.double2, RNG.int))(r1)   //> res24: (List[AnyVal], ch6.RNG) = (List(0.5360936461947858, -549383847),Simp
                                                  //| leRNG(245470556921330))
            
  RNG.ints2(3)(r1)                                //> res25: (List[Int], ch6.RNG) = (List(-1151252339, -549383847, 1612966641),Si
                                                  //| mpleRNG(105707381795861))
  
  RNG.nonNegativeLessThan(123)(r1)                //> res26: (Int, ch6.RNG) = (14,SimpleRNG(206026503483683))

  RNG._map(RNG.nonNegativeInt)(_ / (Int.MaxValue.toDouble+1))(r1)
                                                  //> res27: (Double, ch6.RNG) = (0.5360936461947858,SimpleRNG(206026503483683))
                                                  //| 
  RNG._map2(RNG.int, RNG.double2)((a,b) => (a,b))(r1)
                                                  //> res28: ((Int, Double), ch6.RNG) = ((-1151252339,0.25582678942009807),Simple
                                                  //| RNG(245470556921330))
  RNG._map2(RNG.double2, RNG.int)((a,b) => (a,b))(r1)
                                                  //> res29: ((Double, Int), ch6.RNG) = ((0.5360936461947858,-549383847),SimpleRN
                                                  //| G(245470556921330))
  
  val s1 = State((a:Int) => (a,a+1))              //> s1  : ch6.State[Int,Int] = State(<function1>)
  val s2 = State((b:Int) => (b+3,b))              //> s2  : ch6.State[Int,Int] = State(<function1>)
  s1.run(1)                                       //> res30: (Int, Int) = (1,2)
  s1.map(a => a.toDouble).run(1)                  //> res31: (Double, Int) = (1.0,2)
  s1._map(a => a.toDouble).run(1)                 //> res32: (Double, Int) = (1.0,2)
  s1.flatMap(a => s2).run(1)                      //> res33: (Int, Int) = (5,2)

  s1.map2(s2)((a,b) => a+b).run(1)                //> res34: (Int, Int) = (6,2)
  s1._map2(s2)((a,b) => a+b).run(1)               //> res35: (Int, Int) = (6,2)

  State.sequence(two.List(s1, s2, s1, s2)).run(1)        //> res36: (List[Int], Int) = (List(1, 5, 2, 6),3)
  
  val coins = two.List(Coin, Turn, Coin, Turn, Coin, Turn, Coin, Turn)
                                                  //> coins  : List[Product with Serializable with ch6.Input] = List(Coin, Turn, 
                                                  //| Coin, Turn, Coin, Turn, Coin, Turn)
  val machine = Machine(false, 5, 10)             //> machine  : ch6.Machine = Machine(false,5,10)
  State.simulateMachine(coins).run(machine)       //> res37: ((Int, Int), ch6.Machine) = ((14,1),Machine(true,1,14))
  val machine2 = Machine(true, 0, 0)              //> machine2  : ch6.Machine = Machine(true,0,0)
  State.simulateMachine(coins).run(machine2)      //> res38: ((Int, Int), ch6.Machine) = ((0,0),Machine(true,0,0))
  val machine3 = Machine(true, 2, 0)              //> machine3  : ch6.Machine = Machine(true,2,0)
  State.simulateMachine(coins).run(machine3)      //> res39: ((Int, Int), ch6.Machine) = ((2,0),Machine(true,0,2))
  val machine4 = Machine(false, 0, 2)             //> machine4  : ch6.Machine = Machine(false,0,2)
  State.simulateMachine(coins).run(machine4)      //> res40: ((Int, Int), ch6.Machine) = ((2,0),Machine(false,0,2))
  val machine5 = Machine(false, 2, 2)             //> machine5  : ch6.Machine = Machine(false,2,2)
  State.simulateMachine(coins).run(machine5)      //> res41: ((Int, Int), ch6.Machine) = ((4,0),Machine(true,0,4))
  State.simulateMachine(two.List(Coin)).run(machine5) //> res42: ((Int, Int), ch6.Machine) = ((3,2),Machine(false,2,3))
  State.simulateMachine(two.List(Turn)).run(machine5) //> res43: ((Int, Int), ch6.Machine) = ((2,1),Machine(true,1,2))
  val coins2 = two.List(Coin, Coin, Coin, Turn, /*locked*/ Turn, Turn)
                                                  //> coins2  : List[Product with Serializable with ch6.Input] = List(Coin, Coin,
                                                  //|  Coin, Turn, Turn, Turn)
  val machine6 = Machine(false, 2, 2)             //> machine6  : ch6.Machine = Machine(false,2,2)
  State.simulateMachine(coins2).run(machine6)     //> res44: ((Int, Int), ch6.Machine) = ((5,1),Machine(true,1,5))
  

  trait RNG {
    def nextInt: (Int, RNG) // Should generate a random `Int`. We'll later define other functions in terms of `nextInt`.
  }
  
  case class SimpleRNG(seed: Long) extends RNG {
    def nextInt: (Int, RNG) = {
      val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL // `&` is bitwise AND. We use the current seed to generate a new seed.
      val nextRNG = SimpleRNG(newSeed) // The next state, which is an `RNG` instance created from the new seed.
      val n = (newSeed >>> 16).toInt // `>>>` is right binary shift with zero fill. The value `n` is our new pseudo-random integer.
      (n, nextRNG) // The return value is a tuple containing both a pseudo-random integer and the next `RNG` state.
    }
  }
  
  object RNG {
  
    type Rand[+A] = RNG => (A, RNG)
  
    val int: Rand[Int] = _.nextInt
  
    def unit[A](a: A): Rand[A] =
      rng => (a, rng)
  
    def map[A,B](s: Rand[A])(f: A => B): Rand[B] =
      rng => {
        val (a, rng2) = s(rng)
        (f(a), rng2)
      }
  
    // ex 6.1
    def nonNegativeInt(rng: RNG): (Int, RNG) = {
      val (v1,r1) = rng.nextInt
      val positiveVal = if (v1==Int.MinValue) 0 else math.abs(v1)
      (positiveVal, r1)
    }
    
    // ex 6.2
    def double(rng: RNG): (Double, RNG) = {
      val (positiveVal,r1) = nonNegativeInt(rng)
      val doubleVal = positiveVal/(Int.MaxValue.toDouble+1)
      (doubleVal, r1)
    }
  
    // ex 6.3
    def intDouble(rng: RNG): ((Int,Double), RNG) = {
      val (intVal,r1) = rng.nextInt
      val (doubleVal,r2) = double(r1)
      ((intVal,doubleVal), r2)
    }
  
    def doubleInt(rng: RNG): ((Double,Int), RNG) = {
      val (doubleVal,r1) = double(rng)
      val (intVal,r2) = r1.nextInt
      ((doubleVal,intVal), r2)
    }
  
    def double3(rng: RNG): ((Double,Double,Double), RNG) = {
      val (v1, r1) = double(rng)
      val (v2, r2) = double(r1)
      val (v3, r3) = double(r2)
      ((v1,v2,v3),r3)
    }
    
    // ex 6.4
    def ints(count: Int)(rng: RNG): (two.List[Int], RNG) = {
      if (count<=0) (two.List(), rng)
      else {
        val (v,nextRng) = rng.nextInt
        val next = ints(count-1)(nextRng)
        (v :: next._1, next._2)
      }
    }
    
    def nonNegativeEven: Rand[Int] =
      map(nonNegativeInt)(a => a - a%2)
    
    // ex 6.5
    def double2: Rand[Double] =
      map(nonNegativeInt)( _ / (Int.MaxValue.toDouble+1) )
    
    // ex 6.6
    def map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
      val (a, rngA) = ra(rng)
      val (b, rngB) = rb(rngA)
      (f(a,b), rngB)
    }
    
    def both[A,B](ra: Rand[A], rb: Rand[B]): Rand[(A,B)] =
      map2(ra,rb)((_,_))

    // ex 6.7
    def sequence[A](fs: two.List[Rand[A]]): Rand[two.List[A]] =
      rng => fs.reverse.foldRight((two.List(): two.List[A],rng))((a, b) => { val aa = a(b._2); (b._1 ++ two.List(aa._1), aa._2) }  )
  
    def concat[A](ra: Rand[A], rb: Rand[two.List[A]]): Rand[two.List[A]] =
      map2(ra,rb)(_::_)

    def sequence2[A](fs: two.List[Rand[A]]): Rand[two.List[A]] =
      fs.foldRight( unit(two.List[A]()) )(concat)
    
    def ints2(count: Int): Rand[two.List[Int]] =
      sequence2(two.List.fill(count)(int) )
    
    // ex 6.8
    def flatMap[A,B](f: Rand[A])(g: A => Rand[B]): Rand[B] = rng => {
      val (a,rngA) = f(rng)
      g(a)(rngA)
    }
    
    def nonNegativeLessThan(n: Int): Rand[Int] = flatMap(nonNegativeInt)(
      a => {
        val mod = a % n
        if (a+(n-1)-mod >= 0) unit(mod) else nonNegativeLessThan(n)
    })
    
    // ex 6.9
    def _map[A,B](s: Rand[A])(f: A => B): Rand[B] =
      flatMap(s)(a => unit(f(a)))
    
    def _map2[A,B,C](ra: Rand[A], rb: Rand[B])(f: (A,B) => C): Rand[C] =
      flatMap(ra)(  a => map(rb)( b => f(a,b) )  )
  }
  
  case class State[S,+A](run: S => (A, S)) {
    
    // ex 6.10a
    def map[B](f: A => B): State[S, B] =
      State( s => { val (a,r) = run(s); (f(a),r)  } )
    
    def map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
      State( s => { val (a,r) = run(s); val (b,r2) = sb.run(r); (f(a,b),r2) } )
    
    def flatMap[B](f: A => State[S, B]): State[S, B] =
      State( s => { val (a,r) = run(s); f(a).run(r)  } )
    
    def _map[B](f: A => B): State[S, B] =
      flatMap(a => State.unit(f(a)) )

    def _map2[B,C](sb: State[S, B])(f: (A, B) => C): State[S, C] =
      flatMap(a => sb.map(b => f(a, b)))
  }
  
  sealed trait Input
  case object Coin extends Input
  case object Turn extends Input
  
  case class Machine(locked: Boolean, candies: Int, coins: Int)
  
  object State {
  
    // ex 6.10b
    def unit[A,S](a: A):State[S,A] = State( s => (a,s) )
    
    def sequence[A,S](fs: two.List[State[S,A]]): State[S,two.List[A]] =
      fs.foldRight( unit(two.List()):State[S,two.List[A]] )((a, b) => a.map2(b)(_ :: _) )
  
    type Rand[A] = State[RNG, A]

    //ex 6.11
    def simulateMachine(inputs: two.List[Input]): State[Machine, (Int, Int)] = {
      def processInput(input: Input, m: Machine): Machine = input match {
        case Coin if m.candies>0 => Machine(false, m.candies, m.coins+1)
        case Turn if !m.locked && m.candies>0 => Machine(true, m.candies-1, m.coins)
        case _ => Machine(m.locked, m.candies, m.coins)
      }
      
      State( m => {
        val m2 = inputs.foldLeft(m)((b,a) => processInput(a,b));
        ( (m2.coins, m2.candies), m2 )
      } )
    }
  }

}