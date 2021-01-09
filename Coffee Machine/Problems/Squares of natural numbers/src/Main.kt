import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val i = scanner.nextInt()
    var n = 1
    while (n * n <= i) {
        println(n * n)
        n++
    }
    // put your code here
}