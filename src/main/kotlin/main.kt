import kotlin.math.*

fun longestCommonSubsequenceLenth(sequence1: Array<String>, sequence2: Array<String>): Int {
    var length: Array<Array<Int>> = Array(sequence1.size + 1, { Array(sequence2.size + 1, {0}) })
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

fun main(args: Array<String>) {

}
