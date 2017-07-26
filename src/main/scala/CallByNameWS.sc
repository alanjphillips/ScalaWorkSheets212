val randomGen = scala.util.Random

def random(): Int = {
  println("Print Entered calculate")
  randomGen.nextInt
}

def regularCallByValue(param: Int): Unit = {
  println(s"Print param = $param")
  println(s"Print param again = $param")
}

def callByName(param: => Int): Unit = {
  println(s"Print param = $param")
  println(s"Print param again = $param")
}

def callWithFunc(param: () => Int): Unit = {
  println(s"Print param = ${param()}")
  println(s"Print param again = ${param()}")
}


val callByValueRes = regularCallByValue(random())

val callByNameRes = callByName(random())

val callWithFuncRes = callWithFunc(random _)

class XLazy { lazy val x = { Thread.sleep(10000); 15 } }
val waitXLazy = new XLazy
println(s"XLazy instance = $waitXLazy")
println(s"XLazy.x = ${waitXLazy.x}")

class X { val x = { Thread.sleep(10000); 15 } }
val waitX = new X
println(s"X instance = $waitX")
println(s"X.x = ${waitX.x}")