package funsets

import common._

/**
 * 2. Purely Functional Sets.
 */
object FunSets {
  /**
   * We represent a set by its characteristic function, i.e.
   * its `contains` predicate.
   */
  type Set = Int => Boolean

  /**
   * Indicates whether a set contains a given element.
   */
  def contains(s: Set, elem: Int): Boolean = s(elem)

  /**
   * Returns the set of the one given element.
   */
  def singletonSet(elem: Int): Set = x => (x == elem)

  /**
   * Returns the union of the two given sets,
   * the sets of all elements that are in either `s` or `t`.
   * 
   * Si el elemento se contiene en s o en t -> true, else false
   */
  def union(s: Set, t: Set): Set = x => (contains(s, x) || contains(t, x))

  /**
   * Returns the intersection of the two given sets,
   * the set of all elements that are both in `s` or `t`.
   * 
   * Si el elemento se contiene en s y en t -> true, else false
   */
  def intersect(s: Set, t: Set): Set = x => (contains(s, x) && contains(t, x))

  /**
   * Returns the difference of the two given sets,
   * the set of all elements of `s` that are not in `t`.
   */
  def diff(s: Set, t: Set): Set = x => (contains(s, x) && !contains(t, x))

  /**
   * Returns the subset of `s` for which `p` holds.
   */
  def filter(s: Set, p: Int => Boolean): Set = x => if(p(x)) contains(s, x) else false

  /**
   * The bounds for `forall` and `exists` are +/- 1000.
   */
  val bound = 1000

  /**
   * Returns whether all bounded integers within `s` satisfy `p`.
   */
  def forall(s: Set, p: Int => Boolean): Boolean = {
    def iter(a: Int): Boolean = {
      if (a < -bound || a > bound) true
      else if (contains(s, a) && !p(a)) false
      else iter(a+1)
    }
    iter(-bound)
  }

  /**
   * Returns whether there exists a bounded integer within `s`
   * that satisfies `p`.
   * 
   * exists should be implemented in terms of forall
   * "if not ALL are true, there must be AT LEAST ONE false" or "if not ALL are false there must be AT LEAST ONE true"
   * But for a function fun(p: Int => Boolean) you could give fun(x => !p(x))
   */
  def exists(s: Set, p: Int => Boolean): Boolean = !forall(s, x=> !p(x))
  //def exists(s: Set, p: Int => Boolean): Boolean = if(forall(s, x=> !p(x))) false else true

  /**
   * Returns a set transformed by applying `f` to each element of `s`.
   * b is in the map(S,f) provided that there exists an element, c in S, so that f(c)==b.
   * You need to return a function that takes b as the input and returns true if b is in the map of s through f.
   * x belongs to mapped set if there is a value in the Set s which will be equal to x after applying function f
   */
  def map(s: Set, f: Int => Int): Set = x=> exists(s, c=>f(c)==x)

  /**
   * Displays the contents of a set
   */
  def toString(s: Set): String = {
    val xs = for (i <- -bound to bound if contains(s, i)) yield i
    xs.mkString("{", ",", "}")
  }

  /**
   * Prints the contents of a set on the console.
   */
  def printSet(s: Set) {
    println(toString(s))
  }
}
