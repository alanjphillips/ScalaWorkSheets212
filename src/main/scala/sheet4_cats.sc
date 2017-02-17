import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

case class MyResult(name: String)
case class OtherResult(name: String)
case class FinalResult(name: String)

trait AppError
case class MyError(desc: String) extends AppError

def processA(name: String): Future[Either[AppError, MyResult]] = {
  Future {
    Right(MyResult(name))
  }
}

def processB(nameEither: Either[AppError, MyResult]): Future[Either[AppError, OtherResult]] = {
  Future {
    nameEither.flatMap {
      n => Right(OtherResult(n.name))
    }
  }
}

def processC(nameEither: Either[AppError, OtherResult]): Future[Either[AppError, FinalResult]] = {
  Future {
    nameEither.flatMap {
      n => Right(FinalResult(n.name))
    }
  }
}

def processAll(start: String): Future[Option[FinalResult]] = {
  for {
    res1 <- processA(start)
    res2 <- processB(res1)
    res3 <- processC(res2)
  } yield res3.toOption
}

val processAllFuture = processAll("test_abc")

processAllFuture.onComplete {
  case Success(s) => println(s)
  case Failure(f) => println(f)
}