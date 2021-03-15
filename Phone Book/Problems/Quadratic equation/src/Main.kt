import kotlin.math.pow
import kotlin.math.sqrt

fun main(args: Array<String>) {
    val (a, b, c) = DoubleArray(3) { readLine()!!.toDouble() }
    val d = b.pow(2) - 4 * a * c

    println(listOf<Double>((-b + sqrt(d)) / (2.0 * a), (-b - sqrt(d)) / (2.0 * a)).sorted().joinToString(" "))
}