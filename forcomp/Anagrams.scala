package forcomp

import common._

object Anagrams {

  /** A word is simply a `String`. */
  type Word = String

  /** A sentence is a `List` of words. */
  type Sentence = List[Word]

  /**
   * `Occurrences` is a `List` of pairs of characters and positive integers saying
   *  how often the character appears.
   *  This list is sorted alphabetically w.r.t. to the character in each pair.
   *  All characters in the occurrence list are lowercase.
   *
   *  Any list of pairs of lowercase characters and their frequency which is not sorted
   *  is **not** an occurrence list.
   *
   *  Note: If the frequency of some character is zero, then that character should not be
   *  in the list.
   */
  type Occurrences = List[(Char, Int)]

  /**
   * The dictionary is simply a sequence of words.
   *  It is predefined and obtained as a sequence using the utility method `loadDictionary`.
   */
  val dictionary: List[Word] = loadDictionary //filter(word => word forall (chr => chr.isLetter))

  /**
   * Converts the word into its character occurence list.
   *
   *  Note: the uppercase and lowercase version of the character are treated as the
   *  same character, and are represented as a lowercase character in the occurrence list.
   */
  def wordOccurrences(w: Word): Occurrences = ((w.toLowerCase.groupBy(c => c) mapValues (_.length)).toList).sortBy(_._1)

  /** Converts a sentence into its character occurrence list. */
  def sentenceOccurrences(s: Sentence): Occurrences = wordOccurrences(s.mkString)

  /**
   * The `dictionaryByOccurrences` is a `Map` from different occurrences to a sequence of all
   *  the words that have that occurrence count.
   *  This map serves as an easy way to obtain all the anagrams of a word given its occurrence list.
   *
   *  For example, the word "eat" has the following character occurrence list:
   *
   *     `List(('a', 1), ('e', 1), ('t', 1))`
   *
   *  Incidentally, so do the words "ate" and "tea".
   *
   *  This means that the `dictionaryByOccurrences` map will contain an entry:
   *
   *    List(('a', 1), ('e', 1), ('t', 1)) -> Seq("ate", "eat", "tea")
   *
   *    Para cada palabra del diccionario calculo su wordOccurrences
   *    Despues los agrupo (groupBy) por su lista de occurrencias
   *
   */
  lazy val dictionaryByOccurrences: Map[Occurrences, List[Word]] = dictionary.groupBy((word: Word) => wordOccurrences(word)) withDefaultValue List()

  /** Returns all the anagrams of a given word. */
  def wordAnagrams(word: Word): List[Word] = {
    dictionaryByOccurrences.find(x => x._1 == wordOccurrences(word)) match {
      case Some(x) => x._2
      case None => List()
    }
  }

  /**
   * Returns the list of all subsets of the occurrence list.
   *  This includes the occurrence itself, i.e. `List(('k', 1), ('o', 1))`
   *  is a subset of `List(('k', 1), ('o', 1))`.
   *  It also include the empty subset `List()`.
   *
   *  Example: the subsets of the occurrence list `List(('a', 2), ('b', 2))` are:
   *
   *    List(
   *      List(),
   *      List(('a', 1)),
   *      List(('a', 2)),
   *      List(('b', 1)),
   *      List(('a', 1), ('b', 1)),
   *      List(('a', 2), ('b', 1)),
   *      List(('b', 2)),
   *      List(('a', 1), ('b', 2)),
   *      List(('a', 2), ('b', 2))
   *    )
   *
   *  Note that the order of the occurrence list subsets does not matter -- the subsets
   *  in the example above could have been displayed in some other order.
   *
   *  Hint: investigate how you can use for-comprehensions to implement parts of this method.
   *
   *  Hint: mirar countChange y el problema de las reinas
   *
   *  Para intentar entender el funcionamiento del "for"
   *  https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2155
   *
   *  Product problem:
   *  https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2193
   */
  def combinations(occurrences: Occurrences): List[Occurrences] = {
    if (occurrences.isEmpty) List(List())
    else
      for {
        x <- combinations(occurrences.tail)
        y <- 0 to occurrences.head._2
      } yield if (occurrences.head._2 - y != 0) ((occurrences.head._1, occurrences.head._2 - y) :: x) else x
  }

  /**
   * Subtracts occurrence list `y` from occurrence list `x`.
   *
   *  The precondition is that the occurrence list `y` is a subset of
   *  the occurrence list `x` -- any character appearing in `y` must
   *  appear in `x`, and its frequency in `y` must be smaller or equal
   *  than its frequency in `x`.
   *
   *  Note: the resulting value is an occurrence - meaning it is sorted (.sortBy(_._1))
   *  and has no zero-entries. (.filter( x => x._2 >0))
   */
  def subtract(x: Occurrences, y: Occurrences): Occurrences = {
    val x_map: Map[Char, Int] = x.toMap withDefaultValue 0
    ((y foldLeft x_map)(addTerm)).toList.filter(x => x._2 > 0).sortBy(_._1)
  }

  def addTerm(terms: Map[Char, Int], term: (Char, Int)): Map[Char, Int] = {
     terms.map(x => if (x._1 == term._1) (x._1, x._2 - term._2) else (x._1, x._2))
  }
  

  /**
   * Returns a list of all anagram sentences of the given sentence.
   *
   *  An anagram of a sentence is formed by taking the occurrences of all the characters of
   *  all the words in the sentence, and producing all possible combinations of words with those characters,
   *  such that the words have to be from the dictionary.
   *
   *  The number of words in the sentence and its anagrams does not have to correspond.
   *  For example, the sentence `List("I", "love", "you")` is an anagram of the sentence `List("You", "olive")`.
   *
   *  Also, two sentences with the same words but in a different order are considered two different anagrams.
   *  For example, sentences `List("You", "olive")` and `List("olive", "you")` are different anagrams of
   *  `List("I", "love", "you")`.
   *
   *  Here is a full example of a sentence `List("Yes", "man")` and its anagrams for our dictionary:
   *
   *    List(
   *      List(en, as, my),
   *      List(en, my, as),
   *      List(man, yes),
   *      List(men, say),
   *      List(as, en, my),
   *      List(as, my, en),
   *      List(sane, my),
   *      List(Sean, my),
   *      List(my, en, as),
   *      List(my, as, en),
   *      List(my, sane),
   *      List(my, Sean),
   *      List(say, men),
   *      List(yes, man)
   *    )
   *
   *  The different sentences do not have to be output in the order shown above - any order is fine as long as
   *  all the anagrams are there. Every returned word has to exist in the dictionary.
   *
   *  Note: in case that the words of the sentence are in the dictionary, then the sentence is the anagram of itself,
   *  so it has to be returned in this list.
   *
   *  Note: There is only one anagram of an empty sentence. The anagram of the empty sentence is the empty sentence itself.
   *
   *  First, define a function that takes Occurrences instead of Sentence.
   *  This can be solved with a base case for empty lists and a for loop over combinations with the recursive call.
   *  
   *  1 generator goes through all combinations of the input occurrences
   *  2 generator depends on a first generator and basically gives you dictionaryByOccurence of the combination from the first generator
   *  3 generator substracts the choosen combination from the first generator from the input occurrences, 
   *  and recursively calls the function (i mean itself) on the result of that subtraction. 
   *  You just need to yield the cons of the output of second, and third generator.
   *  
   *	REPASAR EL VIDEO ULTIMO DE LA SEMANA 6 A PARTIR DEL MIN 16
   */
  
  def sentenceAnagrams(sentence: Sentence): List[Sentence] = {
    def sentenceAnagramsAux(sentenceOcc: Occurrences): List[Sentence] ={
      if(sentenceOcc.isEmpty) List(List())
      else
    	  for {
    		comb <- combinations(sentenceOcc)
    		word <- dictionaryByOccurrences(comb)
    		//_= println("ocurrencias de la frase: " + sentenceOcc)
    		rest <- sentenceAnagramsAux(subtract(sentenceOcc, comb))
    	  }yield word :: rest 
    }
    sentenceAnagramsAux(sentenceOccurrences(sentence))
  }

}