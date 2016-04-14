package objsets

import common._
import TweetReader._

class Tweet(val user: String, val text: String, val retweets: Int) {

  /*override def toString: String =
    "User: " + user + "\n" +
    "Text: " + text + " [" + retweets + "]"*/
  
    override def toString: String =
    "User: " + user + " " +
    "Text: " + text + " [" + retweets + "]"

}

abstract class TweetSet {

  /** This method takes a predicate and returns a subset of all the elements
   *  in the original set for which the predicate is true.
   */
  def filter(p: Tweet => Boolean): TweetSet = filter0(p, new Empty)
  def filter0(p: Tweet => Boolean, accu: TweetSet): TweetSet

  def union(that: TweetSet): TweetSet

  // Hint: the method "remove" on TweetSet will be very useful.
  def ascendingByRetweet: Trending = {
    ascendingByRetweet0(this, new EmptyTrending)
  }
  
   def ascendingByRetweet0(tweets: TweetSet, acc: Trending): Trending = {
     if(tweets.isEmpty) acc
     else ascendingByRetweet0(tweets.remove(tweets.findMin), acc + tweets.findMin)
  }
   
   /*def ascendingByRetweet0(trending: Trending): Trending = {
    if(isEmpty) trending
    else{
      val minTweet = findMin
      val newTweetSet = remove(minTweet)
      newTweetSet.ascendingByRetweet0(trending + minTweet)
    }
  }*/
   
   //Otra version
  /*def ascendingByRetweet: Trending = {
    this.ascendingByRetweet0(new EmptyTrending)
  }*/
   
   /*def ascendingByRetweet0(trending: Trending): Trending = {
    if(isEmpty) trending
    else remove(minTweet).ascendingByRetweet0(trending + findMin)
  }*/


  // The following methods are provided for you, and do not have to be changed
  // -------------------------------------------------------------------------
  def incl(x: Tweet): TweetSet
  def contains(x: Tweet): Boolean
  def isEmpty: Boolean
  def head: Tweet
  def tail: TweetSet

  /** This method takes a function and applies it to every element in the set.
   */
  def foreach(f: Tweet => Unit): Unit = {
    if (!this.isEmpty) {
      f(this.head)
      this.tail.foreach(f)
    }
  }

  def remove(tw: Tweet): TweetSet

  def findMin0(curr: Tweet): Tweet =
    if (this.isEmpty) curr
    else if (this.head.retweets < curr.retweets) this.tail.findMin0(this.head)
    else this.tail.findMin0(curr)

  def findMin: Tweet =
    this.tail.findMin0(this.head)
  // -------------------------------------------------------------------------
}

class Empty extends TweetSet {

  def filter0(p: Tweet => Boolean, accu: TweetSet): TweetSet = accu//new Empty accu o this
  
  def union(that: TweetSet): TweetSet = that

  // The following methods are provided for you, and do not have to be changed
  // -------------------------------------------------------------------------
  def contains(x: Tweet): Boolean = false
  def incl(x: Tweet): TweetSet = new NonEmpty(x, new Empty, new Empty)
  def isEmpty = true
  def head = throw new Exception("Empty.head")
  def tail = throw new Exception("Empty.tail")
  def remove(tw: Tweet): TweetSet = this
  // -------------------------------------------------------------------------
  override def toString = "."
}

class NonEmpty(elem: Tweet, left: TweetSet, right: TweetSet) extends TweetSet {
  
  def filter0(p: Tweet => Boolean, accu: TweetSet): TweetSet = {
    if (isEmpty) accu
    else if(p(this.head)) this.tail.filter0(p, accu.incl(this.head))
    else this.tail.filter0(p, accu)
  }
  
  def union(that: TweetSet): TweetSet = {
    if (this.isEmpty) that
    else this.tail.union(that.incl(this.head))
  }

  // The following methods are provided for you, and do not have to be changed
  // -------------------------------------------------------------------------
  def contains(x: Tweet): Boolean =
    if (x.text < elem.text) left.contains(x)
    else if (elem.text < x.text) right.contains(x)
    else true

  def incl(x: Tweet): TweetSet = {
    if (x.text < elem.text) new NonEmpty(elem, left.incl(x), right)
    else if (elem.text < x.text) new NonEmpty(elem, left, right.incl(x))
    else this
  }

  def isEmpty = false
  def head = if (left.isEmpty) elem else left.head
  def tail = if (left.isEmpty) right else new NonEmpty(elem, left.tail, right)

  def remove(tw: Tweet): TweetSet =
    if (tw.text < elem.text) new NonEmpty(elem, left.remove(tw), right)
    else if (elem.text < tw.text) new NonEmpty(elem, left, right.remove(tw))
    else left.union(right)
  // -------------------------------------------------------------------------
  override def toString = "{" + left + elem + right + "}"
}


/** This class provides a linear sequence of tweets.
 */
abstract class Trending {
  def + (tw: Tweet): Trending
  def head: Tweet
  def tail: Trending
  def isEmpty: Boolean
  def foreach(f: Tweet => Unit): Unit = {
    if (!this.isEmpty) {
      f(this.head)
      this.tail.foreach(f)
    }
  }
}

class EmptyTrending extends Trending {
  def + (tw: Tweet) = new NonEmptyTrending(tw, new EmptyTrending)
  def head: Tweet = throw new Exception
  def tail: Trending = throw new Exception
  def isEmpty: Boolean = true
  override def toString = "EmptyTrending"
}

class NonEmptyTrending(elem: Tweet, next: Trending) extends Trending {
  /** Appends tw to the end of this sequence.
   */
  def + (tw: Tweet): Trending =
    new NonEmptyTrending(elem, next + tw)
  def head: Tweet = elem
  def tail: Trending = next
  def isEmpty: Boolean = false
  override def toString =
    "NonEmptyTrending(" + elem.retweets + ", " + next + ")"
}

object GoogleVsApple {
  val google = List("android", "Android", "galaxy", "Galaxy", "nexus", "Nexus")
  
  val apple = List("ios", "iOS", "iphone", "iPhone", "ipad", "iPad")

  val googleTweets: TweetSet = TweetReader.allTweets.filter(tweet => google.exists(tweet.text.contains))

  val appleTweets: TweetSet = TweetReader.allTweets.filter(tweet => apple.exists(tweet.text.contains))

  // Q: from both sets, what is the tweet with highest #retweets?
  val trending: Trending = (googleTweets union appleTweets).ascendingByRetweet
}

object Main extends App {
  // Some help printing the results:
  // println("RANKED:")
  // GoogleVsApple.trending foreach println
  /*val tweet = new Tweet("me", "probando", 5);
  val arbol = new NonEmpty(tweet, new Empty, new Empty)
  val arbol2 = arbol.incl(new Tweet("me", "vamos", 10))
  val arbol3 = arbol2.incl(new Tweet("you", "a ver si va esto", 0))
  println(arbol3.toString)
  println("-------------------------------------------------------")*/
  //val tweetsis = arbol3.filter(tweet => tweet.retweets == 5)
  //println("filter: " + tweetsis.toString)
  //println("min: " + arbol3.findMin)
  //println("Orden: " + arbol3.ascendingByRetweet)
  //println(arbol3.filter(tweet => tweet.retweets == 5))
  //println("Head= " + arbol3.head.toString)
  //val tweets = arbol3.tail
  //println(tweets.toString)
  //println("---------------------")
  //println("Head= " + tweets.head.toString)
  //val tweets2 = tweets.tail
  //println(tweets2.toString)
  //val aux = tweets2.filter(tweet => tweet.retweets > 10)
  //println("filter: " + aux.toString)
  //println("---------------------")
  //println("Head= " + tweets2.head.toString)
  //val tweets3 = tweets2.tail
  //println(tweets3.toString)
  //val aux = tweets3.filter(tweet => tweet.retweets == 10)
  //println(aux.toString)
  //println("---------------------")
  /*val otrotweet = new Tweet("yo", "probandoooo", 46);
  val otroarbol = new NonEmpty(otrotweet, new Empty, new Empty)
  println((otroarbol union arbol3).toString)*/
  val allTweets = TweetReader.allTweets
  println(allTweets.toString)
  //val tweetsis = allTweets.filter(tweet => tweet.retweets == 5)
  //println("filter: " + tweetsis.toString)
  
  /*val wordList = List("scala", "akka", "play framework", "sbt", "typesafe")
  val tweet = "This is an example tweet talking about scala and sbt."
  
  println(wordList.exists(tweet.contains))*/
}
