package patmat

import patmat.Huffman._

object pruebas {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  
  val sampleTree = makeCodeTree(makeCodeTree(Leaf('x', 1), Leaf('e', 1)), Leaf('t', 2))
                                                  //> sampleTree  : patmat.Huffman.Fork = Fork(Fork(Leaf(x,1),Leaf(e,1),List(x, e)
                                                  //| ,2),Leaf(t,2),List(x, e, t),4)
                                                  
  //val chars = List('a', 'a', 'b', 'c', 'a')
	//val map = chars.groupBy (c => c) mapValues (_.length)
	//val list = map.toList
	
	times(List('a', 'b', 'a'))                //> res0: List[(Char, Int)] = List((a,2), (b,1))
	
	//list.sortBy(_._2)
	//list.length
	//makeOrderedLeafList(list)
	
	
	val lista = List(1,2,3)                   //> lista  : List[Int] = List(1, 2, 3)
	val lista2= 4 :: lista                    //> lista2  : List[Int] = List(4, 1, 2, 3)
	lista2 ::: lista                          //> res1: List[Int] = List(4, 1, 2, 3, 1, 2, 3)
	lista :+ 0                                //> res2: List[Int] = List(1, 2, 3, 0)
	
	//chars.union(List('d', 'a'))
	
	//chars(2)
	//chars - chars(0)
	//chars.drop(2)
	
	val letra = chars(Leaf('a', 3))           //> letra  : List[Char] = List(a)
	
	//combine(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4)))
	//combine(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), Leaf('p', 5)))
  //until(singleton, combine)(List(Fork(Leaf('e',1),Leaf('t',2),List('e', 't'),3), Leaf('x',4), Leaf('p', 5)))
  val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
                                                  //> leaflist  : List[patmat.Huffman.Leaf] = List(Leaf(e,1), Leaf(t,2), Leaf(x,4
                                                  //| ))
	println(combine(leaflist))                //> List(Fork(Leaf(e,1),Leaf(t,2),List(e, t),3), Leaf(x,4))
	println(until(singleton, combine)(leaflist))
                                                  //> Fork(Fork(Leaf(e,1),Leaf(t,2),List(e, t),3),Leaf(x,4),List(e, t, x),7)
	//createCodeTree(chars)
	val bits : Bit = 1                        //> bits  : patmat.Huffman.Bit = 1
	val bit : Bit = 0                         //> bit  : patmat.Huffman.Bit = 0
	val table : CodeTable = List(('a', List(bits)), ('b', List(bit)))
                                                  //> table  : patmat.Huffman.CodeTable = List((a,List(1)), (b,List(0)))
  codeBits(table) ('c')                           //> res3: List[patmat.Huffman.Bit] = List()
  //table.filter( x => x._1 == char)
  List(1,2,3,4,5).find(n => n * n == 16) match {
  case Some(x) => "Found " + x
  case None => "Nope."
  }                                               //> res4: java.lang.String = Found 4
}