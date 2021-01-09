import java.io.File

fun main() {
//    val file = File("C:\\Users\\Flame\\IdeaProjects\\FileTest\\src\\main\\kotlin\\text.txt")
//
//    println(file.readText().split(" ").size)
    solution()
}

fun solution(strings: List<String>, str: String): Int {
    val list = readLine()!!.split(" ").toList()
    for (i in list.ind)
    val filter = readLine()!!
    return list.filter { it == filter }.size ?: -1
}