// Do an immutable cache accessed using Actor
import scala.collection.Map
import scala.collection.immutable.HashMap
import akka.actor.{Actor, ActorSystem, Props}
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.Future

case class AddToCache[V](key: String, value: V)
case class RemoveFromCache(key: String)
case class GetFromCache(key: String)

trait Cachable
case class StringItem(value: String) extends Cachable
case class IntItem(value: Int) extends Cachable
case object Size

class CacheActor[A <: Cachable] extends Actor {

  override def receive = cache(HashMap[String, A]())

  def cache(cacheMap: Map[String, A]): Receive = {
    case a: AddToCache[A]   => context.become(cache(cacheMap + ((a.key, a.value))))
    case r: RemoveFromCache => context.become(cache(cacheMap - r.key))
    case g: GetFromCache    => sender ! cacheMap.get(g.key)
    case Size               => sender ! cacheMap.size
  }

}

// For Actor to work in worksheet untick
// "Run worksheet in the compiler process" under
// "Preferences > Languages & Frameworks > Scala > Worksheet"

implicit val system = ActorSystem("as")
implicit val t = Timeout(5 seconds)
val cacheActor = system.actorOf(Props(new CacheActor[StringItem]))

cacheActor ! AddToCache("one", StringItem("value_one"))
cacheActor ! AddToCache("two", StringItem("value_two"))
cacheActor ! AddToCache("three", StringItem("value_three"))
cacheActor ! RemoveFromCache("two")

val resultSize = (cacheActor ? Size).mapTo[Int]
val result3 = (cacheActor ? GetFromCache("three")).mapTo[Option[StringItem]]

