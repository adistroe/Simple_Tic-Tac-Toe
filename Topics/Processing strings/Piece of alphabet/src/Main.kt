const val EN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"

fun main() {
    // write your code here
    val input = readln().uppercase()
    println(input == "" || EN_ALPHABET.contains(input))
}