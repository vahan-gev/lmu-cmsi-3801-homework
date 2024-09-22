import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

fun change(amount: Long): Map<Int, Long> {
    require(amount >= 0) { "Amount cannot be negative" }
    
    val counts = mutableMapOf<Int, Long>()
    var remaining = amount
    for (denomination in listOf(25, 10, 5, 1)) {
        counts[denomination] = remaining / denomination
        remaining %= denomination
    }
    return counts
}

// Write your first then lower case function here
fun firstThenLowerCase(a: List<String>, p: (String) -> Boolean): String? {
    return a.firstOrNull { p(it) }?.lowercase()
} 
// Write your say function here
class Say private constructor(private val words: List<String>) {
    val phrase: String
        get() = words.joinToString(" ")

    fun and(word: String): Say {
        return Say(words + word)
    }

    companion object {
        operator fun invoke(): Say = Say(emptyList())
        operator fun invoke(word: String): Say = Say(listOf(word))
    }
}

fun say(): Say = Say()
fun say(word: String): Say = Say(word)
// Write your meaningfulLineCount function here
fun meaningfulLineCount(filePath: String): Long {
    var count: Long = 0

    BufferedReader(FileReader(filePath)).use { reader ->
        reader.lineSequence().forEach { line ->
            if (line.isNotBlank() && !line.trimStart().startsWith("#")) {
                count++
            }
        }
    }

    return count
}
// Write your Quaternion data class here

// Write your Binary Search Tree interface and implementing classes here
