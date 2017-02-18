package not_book

/**
  * Remove duplicate source code using closure.
  *
  * @author loustler
  * @since 02/16/2017 00:34
  */
object Closure {
  val closureVal = "This String is "

  /**
    * The closureVal which in this function is closure value.
    *
    * It was called bound value, cuz use only in this function.
    *
    * @param f parameter type is String , return type is String.
    */
  def printStr(f: String => String) =
    println(f(closureVal))

  var str = "A"

  def printA() =
    printStr(_ + str)


  str = "B"

  def printB() =
    printStr(_ + str)

  def printC() =
    printStr(_ + str)

  def printAny(s: String) =
    printStr(_ + s)

  def main(args: Array[String]): Unit = {
    // Test all functions
    printA

    printB

    printC

    printAny("empty string")
  }
}
