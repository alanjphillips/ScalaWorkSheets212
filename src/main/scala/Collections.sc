import scala.collection.immutable.ListMap

val listTuples1 = 1 to 10 map {i => s"key$i" -> s"value$i" } toList

val listTuples2 = 11 to 20 map {i => s"key$i" -> s"value$i" } toList

val listCombined = listTuples2 ++ listTuples1

// Create a ListMap which preserves the order of tuples in listCombined
val insertionOrderedMap = ListMap(listCombined map {entry => (entry._1, entry._2)} : _*)






