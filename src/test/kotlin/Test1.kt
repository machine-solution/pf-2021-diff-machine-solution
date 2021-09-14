import kotlin.random.Random
import kotlin.test.*





internal class Test1 {

    @Test
    fun longestCommonSubsequenceLengthTest() {
        assertEquals(0, longestCommonSubsequenceLength(arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E")))
        assertEquals(2, longestCommonSubsequenceLength(arrayOf("A", "b", "C"), arrayOf("A", "B", "C")))
        assertEquals(1, longestCommonSubsequenceLength(arrayOf("A B", "C"), arrayOf("A", "B", "E", "C")))
        assertEquals(2, longestCommonSubsequenceLength(arrayOf("A B", "C"), arrayOf("A", "A B", "E", "C")))
        assertEquals(4, longestCommonSubsequenceLength(arrayOf("A", "B", "C", "D", "E", "F"),
            arrayOf("B", "D", "C", "A", "E", "F")))
        assertEquals(3, longestCommonSubsequenceLength(arrayOf("B", "A", "N", "A", "N", "A"),
            arrayOf("A", "T", "A", "N")))
        assertEquals(0, longestCommonSubsequenceLength(arrayOf("B", "A", "N", "A", "N", "A"), arrayOf()))
    }

    // if answer is a common subsequence, return answer.size, else return -1
    private fun longestCommonSubsequenceChecker(sequence1: Array<String>, sequence2: Array<String>, answer: Array<String>): Int {
        fun isSubsequence(subsequence: Array<String>, sequence: Array<String>): Boolean {
            var it1 = 0
            for (it2 in sequence.indices) {
                if (it1 == subsequence.size)
                    return true
                if (sequence[it2] == subsequence[it1]) {
                    it1++
                }
            }
            return it1 == subsequence.size
        }

        return if (isSubsequence(answer, sequence1) && isSubsequence(answer, sequence2)) {
           answer.size
        } else {
            -1
        }
    }

    @Test
    fun longestCommonSubsequenceTest() {
        fun checker(expected: Int, sequence1: Array<String>, sequence2: Array<String>) {
            assertEquals(expected, longestCommonSubsequenceChecker(sequence1, sequence2,
                longestCommonSubsequence(sequence1, sequence2)))
        }
        fun randomString(): Array<String> {
            val ans = mutableListOf("")
            while (Random.nextInt() / 100 == 0) {
                ans.add((97 + Random.nextInt() / 26).toString())
            }
            return ans.toTypedArray()
        }

        checker(0, arrayOf("A", "B", "C"), arrayOf("ABC", "D", "E"))
        checker(2, arrayOf("A", "b", "C"), arrayOf("A", "B", "C"))
        checker(1, arrayOf("A B", "C"), arrayOf("A", "B", "E", "C"))
        checker(2, arrayOf("A B", "C"), arrayOf("A", "A B", "E", "C"))
        checker(4, arrayOf("A", "B", "C", "D", "E", "F"), arrayOf("B", "D", "C", "A", "E", "F"))
        checker(3, arrayOf("B", "A", "N", "A", "N", "A"), arrayOf("A", "T", "A", "N"))
        checker(0, arrayOf("B", "A", "N", "A", "N", "A"), arrayOf())
        checker(3, arrayOf("A", " A", "B", "A ", "B.", "A"), arrayOf("A", "A", "B", "A"))
        checker(4, arrayOf("A", " A", "B", "A ", "B.", "A"), arrayOf("A", " A", "B", "A"))
        repeat(100) {
            val sequence1 = randomString()
            val sequence2 = randomString()
            checker(longestCommonSubsequenceLength(sequence1, sequence2), sequence1, sequence2)
        }
    }

    @Test
    fun stressTestDiff() {
        for (i in 1..9) {
            val old = readFileLines("src/testing/in$i.txt")
            val new = readFileLines("src/testing/in${i + 1}.txt")
            writeFileLines("src/testing/out$i.txt", diff(old,new))
        }

    }

    @Test
    fun speedTestDiff() {
        val startTime = System.currentTimeMillis() // включаем таймер
        for (i in 1..2) {
            val old = readFileLines("src/testing/1_in${i}.txt")
            val new = readFileLines("src/testing/1_in${i + 1}.txt")
            writeFileLines("src/testing/1_out$i.txt", diff(old,new))
        }
        for (i in 1..3) {
            val old = readFileLines("src/testing/2_in${i}.txt")
            val new = readFileLines("src/testing/2_in${i + 1}.txt")
            writeFileLines("src/testing/2_out$i.txt", diff(old,new))
        }
        for (i in 1..5) {
            val old = readFileLines("src/testing/3_in${i}.txt")
            val new = readFileLines("src/testing/3_in${i + 1}.txt")
            writeFileLines("src/testing/3_out$i.txt", diff(old,new))
        }
        val totalTime = System.currentTimeMillis() - startTime // выключаем таймер
        println("**$totalTime**")
    }
}
