import kotlin.math.*


fun main() {

    println(Vector(readLine()!!).angle(Vector(readLine()!!)))
}

class Vector(private val s: String) {
    var first: Double = 0.0
    var second: Double = 0.0


    init {
        s.split(" ").let {
            first = it[0].toDouble()
            second = it[1].toDouble()
        }
    }
    val length = { sqrt(first.pow(2) + second.pow(2)) }
    private fun prod(v2: Vector): Double = first * v2.first + second * v2.second

    fun angle(v2: Vector): Int {
        return round(acos(prod(v2) / (length() * v2.length())) / PI * 180.0).toInt()
    }
}