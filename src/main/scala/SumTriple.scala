import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.stream.scaladsl._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

object SumTriple {
  implicit val system = ActorSystem("as")
  implicit val materializer = ActorMaterializer()

  def calculate = {
    val mySource = Source(1 to 10)
    val myFlow = Flow[Int].fold(0)(_ + _)
    val mySink: Sink[Int, Future[Int]] = Sink.head

    val triple: Int => Future[Int] =
      i => Future {
        i * 3
      }

    val sumGraph: RunnableGraph[Future[Int]] =
      mySource
        .via(myFlow)
        .mapAsync(1)(triple)
        .toMat(mySink)(Keep.right)

    val res: Future[Int] = sumGraph.run()

    res.foreach(c => println(s"$c "))
  }

}
