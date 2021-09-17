import kotlin.math.*

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

fun main(args: Array<String>) {

}
