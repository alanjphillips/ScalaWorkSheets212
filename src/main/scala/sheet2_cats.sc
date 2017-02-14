import cats.Semigroup
import cats.implicits._

val a: Int = Semigroup[Int].combine(1, 2)

val b: List[Int] = Semigroup[List[Int]].combine(List(1,2,3), List(4,5,6))

val c: Option[Int] = Semigroup[Option[Int]].combine(Option(1), Option(2))

val d: Option[Int] = Semigroup[Option[Int]].combine(Option(1), None)

val f1: Int => Int = (p1: Int) => p1 * 1
val f2: Int => Int = (p1: Int) => p1 * 2

val e: Int => Int = Semigroup[Int => Int].combine(f1, f2)
val g: Int = e(2)


import cats.Monoid

val h = Monoid[String].empty
val i = Monoid[Int].empty
val j = Monoid[List[Int]].empty

val l = Monoid[Int].combineAll(List(1, 1))
val m = Monoid[Int].combineAll(List.empty)

val n = Monoid[List[Int]].combineAll(List(List(1), List(4), List(7, 8)))
val o = Monoid[List[Int]].combineAll(List(List.empty))


import cats.Foldable

val p = Foldable[List].fold(List(1, 2, 3))
val q = Foldable[List].foldLeft(List("Hello", "World", "Again"), "")((a, b) => a+a+b)

val r1 = Foldable[Option].toList(Option(5))
val r2 = Foldable[Option].toList(None)

