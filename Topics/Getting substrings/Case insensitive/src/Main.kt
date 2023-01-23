fun main() {
    // put your code here
    val (lineOne, lineTwo) = List(2) { readln().lowercase() }
    println(lineOne == lineTwo)
}