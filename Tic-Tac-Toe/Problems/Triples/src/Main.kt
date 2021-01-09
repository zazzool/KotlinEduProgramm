import java.util.*

fun main(args: Array<String>) {
    val scanner = Scanner(System.`in`)
    val size = scanner.nextInt()
    var arr = IntArray(size)
    val tmp = IntArray(3)
    var count = 0
    repeat(size) {
        arr += scanner.nextInt()
    }
    for ((i, n) in arr.withIndex()) {
        if (i >= arr.lastIndex - 2) break
        tmp[0] = n
        tmp[1] = arr[i + 1]
        tmp[2] = arr[i + 2]
        if (tmp[2] - tmp[1] == 1 && tmp[1] - tmp[0] == 1) count++
    }
    println(count)
}
