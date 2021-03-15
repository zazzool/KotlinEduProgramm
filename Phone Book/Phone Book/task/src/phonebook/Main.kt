package phonebook

import java.io.File
import java.util.*

fun main() {
    PhoneBook.start()
}

object PhoneBook {
    private var indexed = mutableMapOf<String, MutableList<Long>>()
    private var data = mutableListOf<String>()
    private var queries = mutableListOf<String>()
    private val userInput = Scanner(System.`in`)
    private var linearSearchTime = 0L

    fun start() {
        data = File("C:\\tst\\directory.txt").readLines().toMutableList()
        queries = File("C:\\tst\\find.txt").readLines().toMutableList()
        linearSearch()

//        readFileToList()
        bubbleSort()
//        searchByFile()
//        menu()
    }

    private fun bubbleSort() {
        var length = data.size
        println("Start bubble sorting of $length lines...")
        val start = System.currentTimeMillis()
        var endTime = 0L
        while (length > 0) {
            for (i in 0 until length - 1) {
                val now = System.currentTimeMillis() - start
                if(now > linearSearchTime * 10) {
                    println("Sorting time: ${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", now)} - STOPPED, moved to linear search")
                    linearSearch()
                    return
                }

                val first = data[i].split(" ").toTypedArray().drop(1)
                val second = data[i + 1].split(" ").toTypedArray().drop(1)
                if (first.toString() > second.toString()) {
                    data[i] = data[i + 1].also { data[i + 1] = data[i] }
                }

            }
            length--
        }
        endTime = System.currentTimeMillis() - start
        println("Sorted all entries. Time taken: " +
                "${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", endTime)}" )
    }

    private fun jumpSearch() {

        for (q in queries) {
            // @TODO: Сделать прыжковый поиск
        }
    }

    private fun linearSearch() {

        val total = queries.size
        println("Start searching (linear search)...")
        val start = System.currentTimeMillis()
        var count = 0

        for (i in data.indices) {
            for (r in queries) {
                if (data[i].contains(r)) count++
            }
        }

        if (count > total) count = total
        val endTime = System.currentTimeMillis() - start
        linearSearchTime = endTime

        println("Found $count / $total entries. Time taken: " +
                "${String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", endTime)}" )
    }

    private fun menu() {
        println("""
            1. Search with index
            2. Search native
            0. Exit
        """.trimIndent())
        when (userInput.nextLine().toInt()) {
            1 -> {
                print("Enter a query: ")
                val request = userInput.nextLine()
                searchIndexed(request)
            }
            2 -> {
                print("Enter a query: ")
                val request = userInput.nextLine()
                searchNative(request)
            }
            0 -> return
        }
        menu()
    }

    private fun searchNative(value: String) {
        println("Start native searching...")
        val start = System.currentTimeMillis()
        val found = data.filter { it.contains(value) }
        if (found.isEmpty()) {
            println("No records found")
        } else {
            found.map {
                println(it)
            }
            println("Found ${found.size} / ${data.size}")
        }
        val endTime = System.currentTimeMillis() - start

        println(String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", endTime))
    }

    private fun searchIndexed(value: String) {
        println("Start searching with index...")
        val start = System.currentTimeMillis()
        val indexedValue = indexed[value.toLowerCase()] ?: listOf<Long>()
        val found = data.filterIndexed { index, _ -> indexedValue.contains(index.toLong()) }
        if (found.isEmpty()) {
            println("No records found")
        } else {
            println(found.size)
            found.map {
                println(it)
            }
            println("Found ${found.size} / ${data.size}")
        }
        val endTime = System.currentTimeMillis() - start
        println(String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", endTime))
    }

    private fun readFileToList() {
        println("Start indexing...")
        val start = System.currentTimeMillis()
        var idx = 0L
        file.forEachLine {
            data.add(it)
            it.split(" ").drop(1).map { el ->
                val i = el.toLowerCase()
                if (indexed.containsKey(i)) {
                    indexed[i]?.add(idx)
                } else indexed.put(i, mutableListOf(idx))
            }
            idx++
        }
        val endTime = System.currentTimeMillis() - start
        println(String.format("%1\$tM min. %1\$tS sec. %1\$tL ms.", endTime))
        println("Indexed ${indexed.size} words.")
    }
}