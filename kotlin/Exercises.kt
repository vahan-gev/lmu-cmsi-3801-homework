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
    try {
        BufferedReader(FileReader(filePath)).use { reader ->
            reader.lineSequence().forEach { line ->
                if (line.isNotBlank() && !line.trimStart().startsWith("#")) {
                    count++
                }
            }
        }
    } catch(e: IOException) {
        throw IOException("No such file")
    }
    return count
}
// Write your Quaternion data class here
data class Quaternion(val a: Double, val b: Double, val c: Double, val d: Double) {

    init {
        if (a.isNaN() || b.isNaN() || c.isNaN() || d.isNaN()) {
            throw IllegalArgumentException("Coefficients cannot be NaN")
        }
    }

    companion object {
        val ZERO = Quaternion(0.0, 0.0, 0.0, 0.0)
        val I = Quaternion(0.0, 1.0, 0.0, 0.0)
        val J = Quaternion(0.0, 0.0, 1.0, 0.0)
        val K = Quaternion(0.0, 0.0, 0.0, 1.0)
    }

    operator fun plus(q: Quaternion): Quaternion {
        return Quaternion(
            this.a + q.a,
            this.b + q.b,
            this.c + q.c,
            this.d + q.d
        )
    }

    operator fun times(q: Quaternion): Quaternion {
        return Quaternion(
            this.a * q.a - this.b * q.b - this.c * q.c - this.d * q.d,
            this.a * q.b + this.b * q.a + this.c * q.d - this.d * q.c,
            this.a * q.c - this.b * q.d + this.c * q.a + this.d * q.b,
            this.a * q.d + this.b * q.c - this.c * q.b + this.d * q.a
        )
    }

    fun conjugate(): Quaternion {
        return Quaternion(this.a, -this.b, -this.c, -this.d)
    }

    fun coefficients(): List<Double> {
        return listOf(a, b, c, d)
    }

    override fun toString(): String {
        if (a == 0.0 && b == 0.0 && c == 0.0 && d == 0.0) {
            return "0"
        }

        val result = StringBuilder()

        if (a != 0.0 || (b == 0.0 && c == 0.0 && d == 0.0)) {
            result.append(a)
        }
        if (b != 0.0) {
            if (b > 0 && result.isNotEmpty()) result.append("+")
            result.append(if (b == 1.0) "" else if (b == -1.0) "-" else b).append("i")
        }
        if (c != 0.0) {
            if (c > 0 && result.isNotEmpty()) result.append("+")
            result.append(if (c == 1.0) "" else if (c == -1.0) "-" else c).append("j")
        }
        if (d != 0.0) {
            if (d > 0 && result.isNotEmpty()) result.append("+")
            result.append(if (d == 1.0) "" else if (d == -1.0) "-" else d).append("k")
        }

        return if (result.isEmpty()) "0" else result.toString()
    }
}

// Write your Binary Search Tree interface and implementing classes here
sealed interface BinarySearchTree {

    fun insert(value: String): BinarySearchTree
    fun contains(value: String): Boolean
    fun size(): Int

    object Empty : BinarySearchTree {
        override fun insert(value: String): BinarySearchTree {
            return Node(value, Empty, Empty)
        }

        override fun contains(value: String): Boolean = false
        override fun size(): Int = 0

        override fun toString(): String = "()"
    }

    data class Node(
        val value: String,
        val left: BinarySearchTree,
        val right: BinarySearchTree
    ) : BinarySearchTree {

        override fun insert(newValue: String): BinarySearchTree {
            return when {
                newValue < value -> Node(value, left.insert(newValue), right)
                newValue > value -> Node(value, left, right.insert(newValue))
                else -> this
            }
        }

        override fun contains(value: String): Boolean {
            return when {
                value < this.value -> left.contains(value)
                value > this.value -> right.contains(value)
                else -> true
            }
        }

        override fun size(): Int {
            return 1 + left.size() + right.size()
        }

        override fun toString(): String {
            val leftStr = if (left is Empty) "" else left.toString()
            val rightStr = if (right is Empty) "" else right.toString()
            return "($leftStr$value$rightStr)"
        }
    }
}

