// Do an immutable cache accessed using Actor
import scala.collection.Map
import scala.collection.immutable.HashMap
import akka.actor.Actor

case class AddToCache(key: String, value: String)
case class RemoveFromCache(key: String)
case class GetFromCache(key: String)

class CacheActor extends Actor {

  override def receive = cache(HashMap[String, String]())

  def cache(cacheMap: Map[String, String]): Receive = {
    case a: AddToCache      => context.become(cache(cacheMap + ((a.key, a.value))))
    case r: RemoveFromCache => context.become(cache(cacheMap - r.key))
    case g: GetFromCache    => sender ! cacheMap.get(g.key)
  }

}