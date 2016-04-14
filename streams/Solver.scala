package streams

import common._

/**
 * This component implements the solver for the Bloxorz game
 *
 * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2498
 *
 * neighborsWithHistory: Use valid neighbors from the block to get the required stream result
 * newNeighborsOnly: Simple operation on the passed in neighbors to get the result
 * from: Uses a for-expression and the neighborsWithHistory to generate the result recursively
 * pathsFromStart: Simple usage of newNeighborsOnly
 * pathsToGoal: Simple usage of pathsFromStart
 * solution: Use pathsToGoal to get the result
 */
trait Solver extends GameDef {

  /**
   * Returns `true` if the block `b` is at the final position
   */
  def done(b: Block): Boolean = (b.b1 == goal) && (b.b2 == goal)

  /**
   * This function takes two arguments: the current block `b` and
   * a list of moves `history` that was required to reach the
   * position of `b`.
   *
   * The `head` element of the `history` list is the latest move
   * that was executed, i.e. the last move that was performed for
   * the block to end up at position `b`.
   *
   * The function returns a stream of pairs: the first element of
   * the each pair is a neighboring block, and the second element
   * is the augmented history of moves required to reach this block.
   *
   * It should only return valid neighbors, i.e. block positions
   * that are inside the terrain.
   *
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2428
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2493
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2462
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2465
   */
  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] = {for{
     x <- b.legalNeighbors
  } yield (x._1, x._2 :: history)}.toStream
  
  /* def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] =
	Stream(
    	(b.left, Left :: history),
    	(b.right, Right :: history),
    	(b.up, Up :: history),
    	(b.down, Down :: history)
    ) filter {case (b,_) => b.isLegal}*/

  /*for{
     x <- b.legalNeighbors.toStream
  } yield (x._1, x._2 :: history) #:: x
  */
  /*for{
     x <- b.legalNeighbors.toStream
  } yield x #:: List((x._1, x._2 :: history)).toStream
  */

  /**
   * This function returns the list of neighbors without the block
   * positions that have already been explored. We will use it to
   * make sure that we don't explore circular paths.
   *
   * The method need to filter out blocks in stream from explored set
   * Emplear contains de Set
   */
  def newNeighborsOnly(neighbors: Stream[(Block, List[Move])],
    explored: Set[Block]): Stream[(Block, List[Move])] = neighbors.filter (x => !explored.contains(x._1))

  /**
   * The function `from` returns the stream of all possible paths
   * that can be followed, starting at the `head` of the `initial`
   * stream.
   *
   * The blocks in the stream `initial` are sorted by ascending path
   * length: the block positions with the shortest paths (length of
   * move list) are at the head of the stream.
   *
   * The parameter `explored` is a set of block positions that have
   * been visited before, on the path to any of the blocks in the
   * stream `initial`. When search reaches a block that has already
   * been explored before, that position should not be included a
   * second time to avoid circles.
   *
   * The resulting stream should be sorted by ascending path length,
   * i.e. the block positions that can be reached with the fewest
   * amount of moves should appear first in the stream.
   *
   * Note: the solution should not look at or compare the lengths
   * of different paths - the implementation should naturally
   * construct the correctly sorted stream.
   *
   * from -> Returns all available paths from the given position.
   * So, u need to recursively call from to get all paths. As the
   * return type is Stream, eventually u will ended up with head
   * path evaluation and tail will be evaluated on demand.
   * 
   * from -> 1. Checking for is initial empty and returning empty stream 
   * 2. Take the head of initial 3. In the for comprehension, the generator
   * is new neighbors only for the head of initial 4. yield the stream from 
   * the for comprehension 5. append the initial head with the from recursion 
   * by passing the result of above yielded value.
   * 
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2337
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2406
   * 
   * Aplicar BFS (Breadth First Search)
   */
  def from(initial: Stream[(Block, List[Move])], explored: Set[Block]): Stream[(Block, List[Move])] = {
    //caso base
    if (initial.isEmpty) Stream.empty
    else{ //for para generar las distintas rutas
      val more = for{
        elem <- initial
        next <- newNeighborsOnly(neighborsWithHistory(elem._1, elem._2), explored) 
        if!(explored contains next._1)
      } yield next
      //llamada recursiva
      initial #::: from(more, explored ++ (more map (x => x._1)))
    }
  }

  /**
   * The stream of all paths that begin at the starting block.
   *
   * pathsFromStart -> Returns all available paths from the starting point,
   *  which uses from method to do. It does not worry about the end state.
   */
  lazy val pathsFromStart: Stream[(Block, List[Move])] = from(Stream((startBlock, List())), Set())
  //from(newNeighborsOnly(neighborsWithHistory(startBlock, List()), Set(startBlock)), Set())

  /**
   * Returns a stream of all possible pairs of the goal block along
   * with the history how it was reached.
   *
   * pathsToGoal -> Check whether the evaluated path from start position leads to goal.
   * 
   * https://class.coursera.org/progfun-2012-001/forum/thread?thread_id=2653
   */
  lazy val pathsToGoal: Stream[(Block, List[Move])] = pathsFromStart.filter(x => done(x._1)) 
  
  //case (b,_)=> done( b )

  /**
   * The (or one of the) shortest sequence(s) of moves to reach the
   * goal. If the goal cannot be reached, the empty list is returned.
   *
   * Note: the `head` element of the returned list should represent
   * the first move that the player should perform from the starting
   * position. (reverse??)
   * 
   * If you search in a BFS manner, then the paths returned by pathsFromStart will already be in order by length, shortest paths first.
   * //pathsToGoal.sortBy(_.length)._2
   */
  lazy val solution: List[Move] = if(pathsToGoal.isEmpty) List() else pathsToGoal.head._2.reverse
}
