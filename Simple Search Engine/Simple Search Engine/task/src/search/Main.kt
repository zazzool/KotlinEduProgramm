package search

import java.io.File
import java.util.*

fun main(args: Array<String>) {
    Engine(File(args[1]).readLines())
}

class Engine(private val data: List<String>) {
    private val indexedData = mutableMapOf<String, MutableList<Int>>()
    private val input = Scanner(System.`in`)

    init {
        index()
        menu()
    }

    /* Индексация данных. Функция принимает массив (List) строк, разбивает их на слова, приводит слова к lowercase,
    * создает объект Map типа "слово": [0, 3, 7...], где в массиве перечисляются строки, в которых присутствует это слово
    *
    * */
    private fun index() {
        for (i in data.indices) {
            for (word in data[i].split(" ")) {
                val key = word.toLowerCase()
                if (indexedData.containsKey(key)) {
                    indexedData[key]?.plusAssign(i) // добавляем в массив с номерами строк еще один номер строки
                } else {
                    indexedData[word.toLowerCase()] = mutableListOf<Int>(i)
                }
            }
        }
    }

    private fun menu() {
        println("""
            === Menu ===
            1. Find a person
            2. Print all people
            0. Exit
        """.trimIndent())
        when(input.nextLine()) {
            "1" -> {
                println("Select a matching strategy: ALL, ANY, NONE")
                val strategy = input.nextLine()
                println("Enter data to search people:")
                val query = input.nextLine()
                println(searchIndexed(strategy, query)?.joinToString("\n") ?: "Person not found.")
                menu()
            }
            "2" -> {
                println(toString())
                menu()
            }
            "0" -> return
        }
    }

    override fun toString(): String {
        return data.joinToString("\n")
    }

    private fun searchIndexed(strategy: String, query: String): List<String>? {
        val wordsArray = query.split(" ").map { it.toLowerCase() }
        val filteredData = indexedData.filter { it.key in wordsArray }.values.flatten().toList()
        return if (filteredData.isNotEmpty()) {
            data.filterIndexed {
                index, _ -> when (strategy) {
                    "NONE" -> index !in filteredData
                    else -> index in filteredData
                }
            }
        } else {
            null
        }
    }
}