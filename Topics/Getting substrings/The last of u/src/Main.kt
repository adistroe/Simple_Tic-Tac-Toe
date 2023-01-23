const val DELIMITER = 'u'

fun main() {
    val string = readln()
    // write here
    val wordLettersAfterDelimiter = string.substringAfterLast(DELIMITER).uppercase()
    println(string.replaceAfterLast(DELIMITER, wordLettersAfterDelimiter))
}