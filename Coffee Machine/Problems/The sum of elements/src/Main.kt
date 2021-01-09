import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    var i = scanner.nextInt()
    var sum = i
    while (i != 0) {
        i = scanner.nextInt()
        sum += i
    }
    println(sum)
}