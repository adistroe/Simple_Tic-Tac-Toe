fun printOctAndHex(value: Int) {
    // Write your code here
    val hexFormat = String.format("%#x", value)
    val octFormat = String.format("%#o", value)
    println("$octFormat $hexFormat")
}