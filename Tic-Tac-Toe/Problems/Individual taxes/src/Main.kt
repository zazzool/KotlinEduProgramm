import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val count = scanner.nextInt()
    val incomes = IntArray(count) { scanner.nextInt() }
    val taxes = IntArray(count) { scanner.nextInt() }
    val rating = FloatArray(count) {
        if (taxes[it] != 0) incomes[it].toFloat() / 100.0f * taxes[it] else taxes[it].toFloat()
    }
    var winner = 0
    var max = 0f
    for ((i, c) in rating.withIndex()) {
        if (c > max) {
            max = c
            winner = i + 1
        }
    }
    println(winner)
}
