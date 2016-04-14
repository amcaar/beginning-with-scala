package recfun
import common._

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }
  }

  /**
   * Exercise 1
   */
  def pascal(c: Int, r: Int): Int = {
    val rr = r + 1
    if (c == 0) 1 else pascal(c - 1, r) * (rr - c) / c
  }

  /**
   * Exercise 2
   */
  def balance(chars: List[Char]): Boolean = {
    def balanceAux(chars: List[Char], count: Int): Boolean = {
      if (chars.isEmpty) 
        count == 0
      else 
        if(count<0) false else
        balanceAux(chars.tail, if (chars.head == '(') count + 1 else if (chars.head == ')') count -1 else count)
    }
    balanceAux(chars, 0)
  }


  /**
   * Exercise 3
   */
      
  def countChange(money: Int, coins: List[Int]): Int ={
        if (money == 0) 1
        else
        	if (money < 0) 0
        	else
        		if (coins.isEmpty && money >= 0) 0
      			else countChange(money, coins.tail) + countChange(money- coins.head, coins);
  } 
    
}
