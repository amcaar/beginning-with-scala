package funsets

object Main extends App {
  import FunSets._
  println(contains(singletonSet(1), 1))
  println(contains(singletonSet(1), 2))
  
  println(FunSets.toString((union(singletonSet(1), singletonSet(2)))))
  println(FunSets.toString((intersect(singletonSet(1), singletonSet(1)))))
  println(FunSets.toString((diff(singletonSet(1), singletonSet(1)))))
  
  println(FunSets.toString(filter(singletonSet(-1), x=>x<0)))
  
  println("Forall: " + forall(union(singletonSet(-1), singletonSet(1000)), x=>x>0))
  println("Forall: " + forall(x=>false, x=>false))
  println("Forall: " + forall(x=>false, x=>true))
  
  println("Exists: " + exists(union(singletonSet(-1), singletonSet(1000)), x=>x<0))
  
  println(FunSets.toString(map(union(singletonSet(1), singletonSet(2)), x=>x-1)))

}
