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

val callByValueRes = regularCallByValue(random())

val callByNameRes = callByName(random())

