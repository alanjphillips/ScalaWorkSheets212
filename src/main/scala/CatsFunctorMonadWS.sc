case class Thing[A](value: A)  {

  def map[B](f: A => B): Thing[B] = {
    Thing(f(value))
  }

  def ap[B](f: Thing[A => B]): Thing[B] = {
    Thing(f.value(value))
  }

  def flatMap[B](f: A => Thing[B]): Thing[B] = {
    f(value)
  }

}

val aThing = Thing[Int](6)

val bThing: Thing[String] = aThing.map(a => s"${a.toString}:String")

val cFunc: (Int) => Thing[String] = {
  i => Thing[String](s"${i.toString}:String")
}

val cThing: Thing[String] = aThing.flatMap(cFunc)

val apRes = aThing.ap(Thing[Int => String](a => s"${a.toString}:String"))

import cats.Functor

implicit val catsFunctor: Functor[Thing] = new Functor[Thing] {
  override def map[A, B](fa: Thing[A])(f: A => B) = fa map f
}

val catsFunctorBThing = Functor[Thing].map(aThing)(a => s"${a.toString}:String")

import cats.Monad

implicit val catsMonad: Monad[Thing] = new Monad[Thing] {
  override def pure[A](x: A) = Thing[A](x)

  override def flatMap[A, B](fa: Thing[A])(f: (A) => Thing[B]) = {
    fa flatMap f
  }

  override def tailRecM[A, B](a: A)(f: (A) => Thing[Either[A, B]]) = ???
}

val dThing = Monad[Thing].pure(7)
val catsMonadCThing = Monad[Thing].flatMap(dThing)(cFunc)