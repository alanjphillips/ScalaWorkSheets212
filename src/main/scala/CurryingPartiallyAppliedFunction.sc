def addition(a: Int)(b: Int) = a + b

val addRes1 = addition(1)(3)

val addTo1 = addition(1)_

val addRes2 = addTo1(4)

def multiply(a: Int)(b: Int) = a * b

val multBy2 = multiply(2)_
val multRes = multBy2(3)


def subtract(a: Int, b: Int) = a - b

val sub3 = subtract(_ :Int, 3)

val subRes = sub3(7)

val subtractFunction = subtract _

val subRes2 = subtractFunction(7, 2)





