package streams

object pruebas {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
   val level =
       """So
         |oo
         |oT""".stripMargin                       //> level  : String = So
                                                  //| oo
                                                  //| oT
         
  val levelVector = Vector(Vector('S', 'o'), Vector('o', 'o'), Vector('o', 'T'))
                                                  //> levelVector  : scala.collection.immutable.Vector[scala.collection.immutable.
                                                  //| Vector[Char]] = Vector(Vector(S, o), Vector(o, o), Vector(o, T))
  levelVector(0)                                  //> res0: scala.collection.immutable.Vector[Char] = Vector(S, o)
  levelVector(0)(1)                               //> res1: Char = o
  
  levelVector.indexOf('S')                        //> res2: Int = -1
  levelVector(0).indexOf('S')                     //> res3: Int = 0
  levelVector.indexWhere( x => x.indexOf('T') >= 0)
                                                  //> res4: Int = 2
  levelVector(levelVector.indexWhere( x => x.indexOf('T') >= 0)).indexOf('T')
                                                  //> res5: Int = 1
  val res = (levelVector.indexWhere( x => x.indexOf('T') >= 0),   levelVector(levelVector.indexWhere( x => x.indexOf('T') >= 0)).indexOf('T'))
                                                  //> res  : (Int, Int) = (2,1)

	val level2 =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin                //> level2  : String = ooo-------
                                                  //| oSoooo----
                                                  //| ooooooooo-
                                                  //| -ooooooooo
                                                  //| -----ooToo
                                                  //| ------ooo-
        
  val levelVector2: Vector[Vector[Char]] = Vector(level2.split("\n").map(str => Vector(str: _*)): _*)
                                                  //> levelVector2  : Vector[Vector[Char]] = Vector(Vector(o, o, o, -, -, -, -, -,
                                                  //|  -, -), Vector(o, S, o, o, o, o, -, -, -, -), Vector(o, o, o, o, o, o, o, o,
                                                  //|  o, -), Vector(-, o, o, o, o, o, o, o, o, o), Vector(-, -, -, -, -, o, o, T,
                                                  //|  o, o), Vector(-, -, -, -, -, -, o, o, o, -))
  
	 val res2 = (levelVector2.indexWhere( x => x.indexOf('T') >= 0),   levelVector2(levelVector2.indexWhere( x => x.indexOf('T') >= 0)).indexOf('T'))
                                                  //> res2  : (Int, Int) = (4,7)

	val prueba = levelVector.isDefinedAt(2) && levelVector(2).isDefinedAt(1)
                                                  //> prueba  : Boolean = true
	val prueba2 = levelVector.isDefinedAt(0) && levelVector(0).isDefinedAt(0) && levelVector(0)(0) != 'o'
                                                  //> prueba2  : Boolean = true

}