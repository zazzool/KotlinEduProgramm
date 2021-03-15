import kotlin.math.ceil
import kotlin.math.*

fun main(args: Array<String>) {
    val (a, b) = arrayOf(1, 3)
    println(a + b)
    println(floor((readLine()!!.toDouble() % 1.0) * 10.0).toInt())
}