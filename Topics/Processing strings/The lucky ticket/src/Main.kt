const val LUCKY = "Lucky"
const val REGULAR = "Regular"

fun main() {
    // write your code here
    val input = readln().map { it.digitToInt() }
    val sumOfFirstThree = input.subList(0, 3).sum()
    val sumOfLastThree = input.subList(3, 6).sum()
    print(
        if (sumOfFirstThree == sumOfLastThree) LUCKY else REGULAR
    )
}