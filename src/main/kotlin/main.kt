import java.io.File
import kotlin.math.*

class DiffLineBlock(var add: Boolean, val first: Int = 0, val last: Int = 0,
                    val strings: List<String> = List(1){""} ): Comparable<DiffLineBlock> {
    override fun compareTo(other: DiffLineBlock): Int = if (this.first == other.first)
        this.last.compareTo(other.last)
    else
        first.compareTo(other.first)
}

fun longestCommonSubsequenceLength(sequence1: Array<String>, sequence2: Array<String>): Int {
    val length: Array<Array<Int>> = Array(sequence1.size + 1) { Array(sequence2.size + 1) { 0 } }
    for (it1 in 1 .. sequence1.size)
        for (it2 in 1 .. sequence2.size) {
            length[it1][it2] =  if (sequence1[it1 - 1] == sequence2[it2 - 1]) {
                length[it1 - 1][it2 - 1] + 1 }
            else {
                max(length[it1 - 1][it2], length[it1][it2 - 1])
            }
        }

    return length[sequence1.size][sequence2.size]
}

fun longestCommonSubsequence(sequence1: Array<String>, sequence2: Array<String>): Array<String> {
    val length: Array<Array<Int>> = Array(sequence1.size + 1) { Array(sequence2.size + 1) { 0 } }
    for (it1 in 1 .. sequence1.size)
        for (it2 in 1 .. sequence2.size) {
            length[it1][it2] =  if (sequence1[it1 - 1] == sequence2[it2 - 1]) {
                length[it1 - 1][it2 - 1] + 1 }
            else {
                max(length[it1 - 1][it2], length[it1][it2 - 1])
            }
        }
    val subsequence: Array<String> = Array(length[sequence1.size][sequence2.size]) { "" }
    var it1: Int = sequence1.size
    var it2: Int = sequence2.size
    while (it1 > 0 && it2 > 0) {
        if (sequence1[it1 - 1] == sequence2[it2 - 1]) {
            subsequence[length[it1][it2] - 1] = sequence1[it1 - 1]
            it1--
            it2--
        }
        else if (length[it1 - 1][it2] > length[it1][it2 - 1]) {
            it1--
        }
        else {
            it2--
        }
    }

    return subsequence
}

fun diff(sequence1: Array<String>, sequence2: Array<String>): List<DiffLineBlock> {
    val subsequence: Array<String> = longestCommonSubsequence(sequence1, sequence2)
    val answer : MutableList<DiffLineBlock> = mutableListOf()

    var it1 = 0
    var it2 = 0

    for (itSub in subsequence.indices)
    {
        if (it1 < sequence1.size && sequence1[it1] != subsequence[itSub]) {
            val strings = MutableList(0){""}
            val first = it1 + 1
            while (it1 < sequence1.size && sequence1[it1] != subsequence[itSub]) {
               strings.add(sequence1[it1++])
            }
            val last = it1
            answer.add(DiffLineBlock(false,first,last,strings))
        }
        ++it1

        if (it2 < sequence2.size && sequence2[it2] != subsequence[itSub]) {
            val strings = MutableList(0){""}
            val first = it2 + 1
            while (it2 < sequence2.size && sequence2[it2] != subsequence[itSub]) {
                strings.add(sequence2[it2++])
            }
            val last = it2
            answer.add(DiffLineBlock(true,first,last,strings))
        }
        ++it2

    }
    if (it1 < sequence1.size) {
        val strings = MutableList(0){""}
        val first = it1 + 1
        while (it1 < sequence1.size) {
            strings.add(sequence1[it1++])
        }
        val last = it1
        answer.add(DiffLineBlock(true,first,last,strings))
    }

    if (it2 < sequence2.size) {
        val strings = MutableList(0){""}
        val first = it2 + 1
        while (it2 < sequence2.size) {
            strings.add(sequence2[it2++])
        }
        val last = it2
        answer.add(DiffLineBlock(false,first,last,strings))
    }

    answer.sort()

    return answer
}

fun readFileLines(filename: String): Array<String> {
    fun readList(filename: String): List<String> {
        return File(filename).readLines()
    }
    val list: List<String> =readList(filename)
    val answer = Array(list.size){""}
    for (it in answer.indices)
        answer[it] = list[it]

    return answer
}

fun writeFileLines(filename: String, lines: List<DiffLineBlock>) {
    fun writeDiffLineBlock(filename: String, block: DiffLineBlock) {
        val file = File(filename)
        if (block.add)
            file.appendText('\n' + "@ a ${block.first}-${block.last} @")
        else
            file.appendText('\n' + "@ d ${block.first}-${block.last} @")
        for (str in block.strings)
            file.appendText("\n> $str")
    }
    val file = File(filename)
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText("----------------------------SUCCESS------------------------------------------------------------------------------------")
    for (block in lines)
        writeDiffLineBlock(filename, block)
}

fun main() {
    println("Place an old- and new-version of your file in the files \"old.txt\" and \"new.txt\" respectively")
    println("and press ENTER...")
    readLine()
    val old = readFileLines("old.txt")
    val new = readFileLines("new.txt")
    println("Do not open a file \"result.txt\" while a program is running")
    writeFileLines("result.txt", diff(old,new))
    println("You may open a file \"result.txt\" :)")
}
