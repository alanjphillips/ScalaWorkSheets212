case class Thing[A](value: A)  {
  def map[B](f: A => B): Thing[B] = {
    Thing(f(value))
  }
}

val aThing = Thing[Int](6)

val bThing: Thing[String] = aThing.map(a => s"${a.toString}:String")

import cats.Functor

implicit val catsFunctor: Functor[Thing] = new Functor[Thing] {
  override def map[A, B](fa: Thing[A])(f: A => B) = fa map f
}

val catsBThing = Functor[Thing].map(aThing)(a => s"${a.toString}:String")

