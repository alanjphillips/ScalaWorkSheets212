import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global

val fut1 = Future { "One" }

case class MyResult(name: String)

trait AppError
case class MyError(desc: String) extends AppError

val outcomeA: Either[AppError, MyResult] = Right(MyResult("worked"))
val outcomeB: Either[AppError, MyResult] = Right(MyResult("worked again"))
val outcomeC: Either[AppError, MyResult] = Left(MyError("failed"))

outcomeA.toOption.map(s => s.name)
outcomeC.toOption.map(s => s.name)

val finish = for {
  r1 <- outcomeA
  r2 <- outcomeB
} yield MyResult(s"${r1.name} and ${r2.name}")

val finishAsOption: Option[MyResult] = finish.toOption

val badFinish = for {
  r1 <- outcomeA
  r2 <- outcomeB
  r3 <- outcomeC
} yield MyResult(s"${r1.name} and ${r2.name} and ${r3.name}")

val badFinishAsOption: Option[MyResult] = badFinish.toOption