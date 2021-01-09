fun main() {
    val str = readLine()!!
    var result = 0
    var count = 0
    var stri = str.first().toString()
    for (c in str) {
        if (isVowel(c) == isVowel(stri.last())) {
            count++
            if (count % 2 != 0 && count > 2) result++
            continue
        }
        stri += c
        count = 1
    }
    println(result)
    }

 fun isVowel(c: Char): Boolean {
     val vowels = "aeiouy"
     return c in vowels
 }
