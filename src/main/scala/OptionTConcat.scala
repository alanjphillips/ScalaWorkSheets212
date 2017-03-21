import cats.data.OptionT
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import cats.implicits._

object OptionTConcat {

  def concatPrint = {
    val futThing: Future[Option[String]] =  Future.successful(Some("FutureThing"))

    val otherThing: Future[Option[String]] = Future.successful(Some("OtherThing"))

    val someThing: Option[String] = Some("SomeThing")

    val trans1: OptionT[Future, String] = OptionT(futThing)
    val trans2: OptionT[Future, String] = OptionT(otherThing)
    val trans3: OptionT[Future, String] = OptionT.fromOption(someThing)

    val res = for {
      a: String <- trans1
      b: String <- trans2
      c: String <- trans3
    } yield a + b + c

    res.value.map(res =>
      println(res.getOrElse(""))
    )
  }

}
