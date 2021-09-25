import kotlin.math.*

// Блок последовательных удаляемых или добавляемых строк под номерами с first по last, включая обе границы
// add = true, если строки блока добавляются, add = false, если строки блока удаляются
// strings - массив строк размера last - first + 1 - строки блока
class DiffLineBlock(var add: Boolean, val first: Int = 0, val last: Int = 0,
                    val strings: List<String> = List(1){""} ): Comparable<DiffLineBlock> {
    override fun compareTo(other: DiffLineBlock): Int = if (this.first == other.first)
        this.last.compareTo(other.last)
    else
        first.compareTo(other.first)
}

// Возвращает наибольшую общую подпоследовательность строковых последовательностей sequence1 и sequence2
fun longestCommonSubsequence(sequence1: List<String>, sequence2: List<String>): List<String> {
    val length: MutableList<MutableList<Int>> = MutableList(sequence1.size + 1)
        { MutableList(sequence2.size + 1) { 0 } }
    for (it1 in 1 .. sequence1.size)
        for (it2 in 1 .. sequence2.size) {
            length[it1][it2] =  if (sequence1[it1 - 1] == sequence2[it2 - 1]) {
                length[it1 - 1][it2 - 1] + 1 }
            else {
                max(length[it1 - 1][it2], length[it1][it2 - 1])
            }
        }
    val subsequence: MutableList<String> = MutableList(length[sequence1.size][sequence2.size]) { "" }
    var it1: Int = sequence1.size
    var it2: Int = sequence2.size
    // Восстановление ответа
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

// Вычисляет разность между последовательностями sequence1 и sequence2 и разбивает её на блоки подряд идущих
// удаляемых или добавляемых строк и возвращает блоки в виде отсортированного массива по номеру первой строки блока
fun diff(sequence1: List<String>, sequence2: List<String>): List<DiffLineBlock> {
    val subsequence: List<String> = longestCommonSubsequence(sequence1, sequence2)
    val answer : MutableList<DiffLineBlock> = mutableListOf()

    var it1 = 0
    var it2 = 0

    for (itSub in subsequence.indices)
    {
        // Формируется блок из удаляемых строк
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
        // Формируется блок из добавляемых строк
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
    // Формируются блоки из строк последовательностей sequence1 и sequence2, которые идут после последней
    // строки из subsequence
    if (it1 < sequence1.size) {
        val strings = MutableList(0){""}
        val first = it1 + 1
        while (it1 < sequence1.size) {
            strings.add(sequence1[it1++])
        }
        val last = it1
        answer.add(DiffLineBlock(false,first,last,strings))
    }

    if (it2 < sequence2.size) {
        val strings = MutableList(0){""}
        val first = it2 + 1
        while (it2 < sequence2.size) {
            strings.add(sequence2[it2++])
        }
        val last = it2
        answer.add(DiffLineBlock(true,first,last,strings))
    }
    // Сортирует полученные блоки по номеру первой строки в блоке
    answer.sort()

    return answer
}

fun main() {
    val old = readFileUsingPath("old file ")
    val new = readFileUsingPath("new file ")
    writeResult(diff(old,new))
    println("Program finished! You can open the result file")
}
