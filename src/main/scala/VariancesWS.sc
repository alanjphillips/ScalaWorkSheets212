/**

Pasted from:
https://blog.codecentric.de/en/2015/04/the-scala-type-system-parameterized-types-and-variances-part-2/#condensed

Subtype Relationships
---------------------
Assume that class Orange extends Fruit holds. If class Box[A] is declared, then A can be prefixed with + or -.

A without annotation is invariant, i.e.:
 - Box[Orange] has no inheritance relationship to Box[Fruit].

+A is covariant, i.e.:
 - Box[Orange] is a subtype of Box[Fruit].
 - var f: Box[Fruit] = new Box[Orange]() is allowed.

-A is contravariant, i.e.:
 - Box[Fruit] is a subtype of Box[Orange].
 - var f: Box[Orange] = new Box[Fruit]() is allowed.


Allowed Variance Positions
--------------------------
Invariant type parameters can be used everywhere:
 - abstract class Box[A] { def foo(a: A): A } is allowed.
 - abstract class Box[A] { var a: A } is allowed.

Covariant type parameters can be used as return types only:
 - abstract class Box[+A] { def foo(): A } is allowed.

Contravariant type parameters can be used as types of method parameters only:
 - abstract class Box[-A] { def foo(a: A) } is allowed.

Workaround to use a covariant type in a method parameter type:
 - abstract class Box[+A] { def foo[B >: A](b: B) } is allowed.

Workaround to use a contravariant type in a return type:
 - abstract class Box[-A] { def foo[B <: A](): B } is allowed.

 */


trait Fruit {
  def name: String
}

class Apple extends Fruit {
  def name = "Apple"
}

class Orange extends Fruit {
  def name = "Orange"
}

/**
Covariant type parameters can be used as return types only:
  - abstract class Box[+A] { def foo(): A } is allowed.
*/
class BagCo[+A <: Fruit](fruits: List[A]) {
  def getHeadFruit: A = fruits.head                        // Covariant type parameters can be used as return types only
  def add[B >: A](fruit: B): List[B] =  fruit :: fruits    // use a covariant type in a method parameter type
}

val appleBagCo = new BagCo[Apple](List(new Apple))
val ap: Apple = appleBagCo.getHeadFruit

val orangeBagCo = new BagCo[Orange](List(new Orange))

val fruitBag: BagCo[Fruit] = new BagCo[Apple](List(new Apple))
val f: Fruit =  fruitBag.getHeadFruit
val a: Apple = f.asInstanceOf[Apple]
/**
Contravariant type parameters can be used as types of method parameters only:
 - abstract class Box[-A] { def foo(a: A) } is allowed.
  */
class BagContra[-A <: Fruit](fruits: List[A]) {
  def add(fruit: A): List[Fruit] = fruit :: fruits                 // Contravariant type parameters can be used as types of method parameters only
  def getHeadFruit[B <: A](): B = fruits.head.asInstanceOf[B]      // use a contravariant type in a return type
}

val appleBagContra: BagContra[Apple] = new BagContra[Fruit](Nil)
val updated = appleBagContra.add(new Apple)