package forcomp
import forcomp.Anagrams._

object pruebas {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val word: Word = "patatas"                      //> word  : forcomp.Anagrams.Word = patatas
  wordOccurrences(word)                           //> res0: forcomp.Anagrams.Occurrences = List((a,3), (p,1), (s,1), (t,2))
  
  val sentence: Sentence = List("I", "love", "you")
                                                  //> sentence  : forcomp.Anagrams.Sentence = List(I, love, you)
  sentenceOccurrences(sentence)                   //> res1: forcomp.Anagrams.Occurrences = List((e,1), (i,1), (l,1), (o,2), (u,1),
                                                  //|  (v,1), (y,1))
 
  
  val dictionary: List[Word] = loadDictionary     //> dictionary  : List[forcomp.Anagrams.Word] = List(Aarhus, Aaron, Ababa, aback
                                                  //| , abaft, abandon, abandoned, abandoning, abandonment, abandons, abase, abase
                                                  //| d, abasement, abasements, abases, abash, abashed, abashes, abashing, abasing
                                                  //| , abate, abated, abatement, abatements, abater, abates, abating, Abba, abbe,
                                                  //|  abbey, abbeys, abbot, abbots, Abbott, abbreviate, abbreviated, abbreviates,
                                                  //|  abbreviating, abbreviation, abbreviations, Abby, abdomen, abdomens, abdomin
                                                  //| al, abduct, abducted, abduction, abductions, abductor, abductors, abducts, A
                                                  //| be, abed, Abel, Abelian, Abelson, Aberdeen, Abernathy, aberrant, aberration,
                                                  //|  aberrations, abet, abets, abetted, abetter, abetting, abeyance, abhor, abho
                                                  //| rred, abhorrent, abhorrer, abhorring, abhors, abide, abided, abides, abiding
                                                  //| , Abidjan, Abigail, Abilene, abilities, ability, abject, abjection, abjectio
                                                  //| ns, abjectly, abjectness, abjure, abjured, abjures, abjuring, ablate, ablate
                                                  //| d, ablates, ablating, ab
                                                  //| Output exceeds cutoff limit.
  val dictionaryByOccurrences: Map[Occurrences, List[Word]] = dictionary.groupBy((word: Word) => wordOccurrences(word))
                                                  //> dictionaryByOccurrences  : Map[forcomp.Anagrams.Occurrences,List[forcomp.Ana
                                                  //| grams.Word]] = Map(List((c,2), (e,2), (h,1), (r,1), (s,1)) -> List(screech),
                                                  //|  List((a,2), (l,1), (r,1), (s,1), (t,1)) -> List(altars, astral), List((e,1)
                                                  //| , (f,2), (i,1), (n,1), (s,2), (t,1)) -> List(stiffens), List((b,1), (d,1), (
                                                  //| e,3), (h,1), (i,1), (k,1), (r,1), (s,1), (w,1)) -> List(bewhiskered), List((
                                                  //| c,1), (d,1), (e,2), (f,1), (i,1), (n,1), (t,1)) -> List(infected), List((a,1
                                                  //| ), (h,1), (k,1), (n,1), (s,1), (t,1)) -> List(thanks), List((c,1), (d,1), (e
                                                  //| ,1), (g,1), (i,1), (m,1), (n,2), (o,1), (t,1), (u,1)) -> List(documenting), 
                                                  //| List((a,1), (b,1), (e,1), (i,1), (k,1), (n,1), (t,1)) -> List(beatnik), List
                                                  //| ((a,1), (d,1), (e,3), (i,1), (m,1), (o,1), (r,1), (s,1), (t,2), (v,1)) -> Li
                                                  //| st(overestimated), List((a,1), (e,1), (i,2), (n,1), (p,1), (r,1), (s,2), (u,
                                                  //| 1), (z,1)) -> List(Prussianize), List((a,1), (b,1), (d,1), (e,1), (p,1), (s,
                                                  //| 1), (t,1), (u,1)) -> Lis
                                                  //| Output exceeds cutoff limit.
	dictionaryByOccurrences get (sentenceOccurrences(sentence))
                                                  //> res2: Option[List[forcomp.Anagrams.Word]] = None
	dictionaryByOccurrences get (wordOccurrences("love"))
                                                  //> res3: Option[List[forcomp.Anagrams.Word]] = Some(List(love))
	
	wordAnagrams("lives")                     //> res4: List[forcomp.Anagrams.Word] = List(Elvis, evils, Levis, lives, veils)
                                                  //| 
	wordAnagrams("love")                      //> res5: List[forcomp.Anagrams.Word] = List(love)
	wordAnagrams("eat")                       //> res6: List[forcomp.Anagrams.Word] = List(ate, eat, tea)
	
	
	
	combinations(List(('a', 2), ('b', 2)))    //> res7: List[forcomp.Anagrams.Occurrences] = List(List((a,2), (b,2)), List((a,
                                                  //| 1), (b,2)), List((b,2)), List((a,2), (b,1)), List((a,1), (b,1)), List((b,1))
                                                  //| , List((a,2)), List((a,1)), List())
	
	//http://stackoverflow.com/questions/7474709/all-combinations-with-repetition-using-scala
	/*def combinations(size: Int = word.length) : List[List[T]] = {
    if (size == 0)
        List(List())
    else {
        for {
            x  <- word.toList
            xs <- combinations(size-1)
        } yield x :: xs
    }
  }*/
  
  //val input = List('A','C','G')
  //(input ++ input ++ input) combinations(3) toList
  
  //(input.map(_ => input)).flatten.combinations(0).toList
  //val input = List(('a', 2), ('b', 2))
  
  /*for{
  i <- 0 to input.length
  }yield (input.map(_ => input)).flatten.combinations(i).toList*/
  
  //val list = List(('a', 2))
  
  /*def combine (list: List[(Char, Int)]): List[List[(Char, Int)]] = {
	  if(list.isEmpty) List(List())
    else for{
	    x <- combine(list.tail)
  		i <- 0 until list.length
  	} yield combine(List((list.head._1, list.head._2 - i))) ::: x
  }
  
  combine(list)*/
  
  //substract
  val x = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
                                                  //> x  : List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,1))
	val y = List(('r', 1))                    //> y  : List[(Char, Int)] = List((r,1))
	
	y--x                                      //> res8: List[(Char, Int)] = List()
	x--y                                      //> res9: List[(Char, Int)] = List((a,1), (d,1), (l,1))
	val mapy = y.toMap                        //> mapy  : scala.collection.immutable.Map[Char,Int] = Map(r -> 1)
	mapy.apply('r')                           //> res10: Int = 1
	val mapx = x.toMap                        //> mapx  : scala.collection.immutable.Map[Char,Int] = Map(a -> 1, d -> 1, l ->
                                                  //|  1, r -> 1)
	mapx ++ mapy                              //> res11: scala.collection.immutable.Map[Char,Int] = Map(a -> 1, d -> 1, l -> 
                                                  //| 1, r -> 1)
	x.filter( x => x._2 >0)                   //> res12: List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,1))
	def f(x: (Char, Int), y: (Char, Int)) = if (x._1 == y._1) (x._1, x._2 - y._2) else (x._1, x._2)
                                                  //> f: (x: (Char, Int), y: (Char, Int))(Char, Int)
  f(x.last, y.head)                               //> res13: (Char, Int) = (r,0)
  //x.map {case (x, y) => f(x,y)}.filter(e => e._2 >= 0)
  //x.map (x=> f(x)).filter(e => e._2 >= 0)
  subtract(x, y)                                  //> res14: forcomp.Anagrams.Occurrences = List((a,1), (d,1), (l,1))
  for{
  	i <- 0 until x.length
  	j <- 0 until y.length
  } yield f(x(i), y(j))                           //> res15: scala.collection.immutable.IndexedSeq[(Char, Int)] = Vector((a,1), (
                                                  //| d,1), (l,1), (r,0))
  val a = List(('j', 1), ('i', 1), ('m', 2), ('y', 1))
                                                  //> a  : List[(Char, Int)] = List((j,1), (i,1), (m,2), (y,1))
  val b = List(('m', 1), ('y', 1))                //> b  : List[(Char, Int)] = List((m,1), (y,1))
  a--b                                            //> res16: List[(Char, Int)] = List((j,1), (i,1), (m,2))
  val c = subtract(a, b)                          //> c  : forcomp.Anagrams.Occurrences = List((i,1), (j,1), (m,1))
  
   val r = List(('m', 1), ('y', 2))               //> r  : List[(Char, Int)] = List((m,1), (y,2))
   val s = List(('m', 1), ('y', 2))               //> s  : List[(Char, Int)] = List((m,1), (y,2))
   r--s                                           //> res17: List[(Char, Int)] = List()
   subtract(r, s)                                 //> res18: forcomp.Anagrams.Occurrences = List()
  
  val frase = List("Yes", "man")                  //> frase  : List[java.lang.String] = List(Yes, man)
  val frase2 = List("Yes")                        //> frase2  : List[java.lang.String] = List(Yes)
  sentenceAnagrams(sentence)                      //> res19: List[forcomp.Anagrams.Sentence] = List(List(you, olive), List(you, L
                                                  //| ev, Io), List(you, Io, Lev), List(olive, you), List(Lev, you, Io), List(Lev
                                                  //| , Io, you), List(Io, you, Lev), List(Io, Lev, you))
  sentenceAnagrams(frase)                         //> res20: List[forcomp.Anagrams.Sentence] = List(List(yes, man), List(say, men
                                                  //| ), List(my, sane), List(my, Sean), List(my, as, en), List(my, en, as), List
                                                  //| (sane, my), List(Sean, my), List(as, my, en), List(as, en, my), List(men, s
                                                  //| ay), List(man, yes), List(en, my, as), List(en, as, my))
  val titi=wordOccurrences("Yes")                 //> titi  : forcomp.Anagrams.Occurrences = List((e,1), (s,1), (y,1))
  val comb =combinations(titi)                    //> comb  : List[forcomp.Anagrams.Occurrences] = List(List((e,1), (s,1), (y,1))
                                                  //| , List((s,1), (y,1)), List((e,1), (y,1)), List((y,1)), List((e,1), (s,1)), 
                                                  //| List((s,1)), List((e,1)), List())
  comb - titi                                     //> res21: List[forcomp.Anagrams.Occurrences] = List(List((s,1), (y,1)), List((
                                                  //| e,1), (y,1)), List((y,1)), List((e,1), (s,1)), List((s,1)), List((e,1)), Li
                                                  //| st())
  dictionaryByOccurrences get (wordOccurrences("yes"))
                                                  //> res22: Option[List[forcomp.Anagrams.Word]] = Some(List(yes))
  val occ= sentenceOccurrences(sentence)          //> occ  : forcomp.Anagrams.Occurrences = List((e,1), (i,1), (l,1), (o,2), (u,1
                                                  //| ), (v,1), (y,1))
  combinations(occ)                               //> res23: List[forcomp.Anagrams.Occurrences] = List(List((e,1), (i,1), (l,1), 
                                                  //| (o,2), (u,1), (v,1), (y,1)), List((i,1), (l,1), (o,2), (u,1), (v,1), (y,1))
                                                  //| , List((e,1), (l,1), (o,2), (u,1), (v,1), (y,1)), List((l,1), (o,2), (u,1),
                                                  //|  (v,1), (y,1)), List((e,1), (i,1), (o,2), (u,1), (v,1), (y,1)), List((i,1),
                                                  //|  (o,2), (u,1), (v,1), (y,1)), List((e,1), (o,2), (u,1), (v,1), (y,1)), List
                                                  //| ((o,2), (u,1), (v,1), (y,1)), List((e,1), (i,1), (l,1), (o,1), (u,1), (v,1)
                                                  //| , (y,1)), List((i,1), (l,1), (o,1), (u,1), (v,1), (y,1)), List((e,1), (l,1)
                                                  //| , (o,1), (u,1), (v,1), (y,1)), List((l,1), (o,1), (u,1), (v,1), (y,1)), Lis
                                                  //| t((e,1), (i,1), (o,1), (u,1), (v,1), (y,1)), List((i,1), (o,1), (u,1), (v,1
                                                  //| ), (y,1)), List((e,1), (o,1), (u,1), (v,1), (y,1)), List((o,1), (u,1), (v,1
                                                  //| ), (y,1)), List((e,1), (i,1), (l,1), (u,1), (v,1), (y,1)), List((i,1), (l,1
                                                  //| ), (u,1), (v,1), (y,1)), List((e,1), (l,1), (u,1), (v,1), (y,1)), List((l,1
                                                  //| ), (u,1), (v,1), (y,1))
                                                  //| Output exceeds cutoff limit.
  

}