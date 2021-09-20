import kotlin.random.Random
import kotlin.test.*

internal class Test1 {

    @Test
    fun longestCommonSubsequenceLengthTest() {
        assertEquals(0, longestCommonSubsequenceLength(listOf("A", "B", "C"), listOf("ABC", "D", "E")))
        assertEquals(2, longestCommonSubsequenceLength(listOf("A", "b", "C"), listOf("A", "B", "C")))
        assertEquals(1, longestCommonSubsequenceLength(listOf("A B", "C"), listOf("A", "B", "E", "C")))
        assertEquals(2, longestCommonSubsequenceLength(listOf("A B", "C"), listOf("A", "A B", "E", "C")))
        assertEquals(4, longestCommonSubsequenceLength(listOf("A", "B", "C", "D", "E", "F"),
            listOf("B", "D", "C", "A", "E", "F")))
        assertEquals(3, longestCommonSubsequenceLength(listOf("B", "A", "N", "A", "N", "A"),
            listOf("A", "T", "A", "N")))
        assertEquals(0, longestCommonSubsequenceLength(listOf("B", "A", "N", "A", "N", "A"), listOf()))
    }

    // if "answer" is a common subsequence, return answer.size, else return -1
    private fun longestCommonSubsequenceChecker(sequence1: List<String>, sequence2: List<String>,
                                                answer: List<String>): Int {
        fun isSubsequence(subsequence: List<String>, sequence: List<String>): Boolean {
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
        // Проверяем, является ли answer подстрокой и sequence1 и sequence2
        return if (isSubsequence(answer, sequence1) && isSubsequence(answer, sequence2)) {
           answer.size
        } else {
            -1
        }
    }

    @Test
    fun longestCommonSubsequenceTest() {
        fun checker(expected: Int, sequence1: List<String>, sequence2: List<String>) {
            assertEquals(expected, longestCommonSubsequenceChecker(sequence1, sequence2,
                longestCommonSubsequence(sequence1, sequence2)))
        }

        // Возвращает случайную сроку с матожиданием длины mediumLength
        fun randomString(mediumLength: Int = 100): List<String> {
            val ans = MutableList(0){""}
            while (Random.nextInt() % mediumLength != 0) {
                ans.add(('A' + Random.nextInt() % 26).toString())
            }
            return ans
        }
        // Проверка checker-ом на стресс тестах
        checker(0, listOf("A", "B", "C"), listOf("ABC", "D", "E"))
        checker(2, listOf("A", "b", "C"), listOf("A", "B", "C"))
        checker(1, listOf("A B", "C"), listOf("A", "B", "E", "C"))
        checker(2, listOf("A B", "C"), listOf("A", "A B", "E", "C"))
        checker(4, listOf("A", "B", "C", "D", "E", "F"), listOf("B", "D", "C", "A", "E", "F"))
        checker(3, listOf("B", "A", "N", "A", "N", "A"), listOf("A", "T", "A", "N"))
        checker(0, listOf("B", "A", "N", "A", "N", "A"), listOf())
        checker(3, listOf("A", " A", "B", "A ", "B.", "A"), listOf("A", "A", "B", "A"))
        checker(4, listOf("A", " A", "B", "A ", "B.", "A"), listOf("A", " A", "B", "A"))
        repeat(10000) {
            val sequence1 = randomString(10)
            val sequence2 = randomString(10)
            checker(longestCommonSubsequenceLength(sequence1, sequence2), sequence1, sequence2)
        }

        // Проверка на ручных тестах с разными символами
        assertEquals( listOf("6","9","0","I"), longestCommonSubsequence( listOf("R","A","6","9",")","T","+","3","U","5","0","I","U","N","M"), listOf("6","9","A","C","<","Q",">","0","I","7","?")))
        assertEquals( listOf("F"), longestCommonSubsequence( listOf("*","F","/","L","*","F","Q","0","8"), listOf("=",";","Z","F","5")))
        assertEquals( listOf("."), longestCommonSubsequence( listOf("9",".","0","I","E","S","?"), listOf(":",".","Q","5","M","L")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("Z","U"), listOf("B","I","M","=")))
        assertEquals( listOf(), longestCommonSubsequence( listOf(")","J","C","F",")"), listOf("Y")))
        assertEquals( listOf("B",".","0"), longestCommonSubsequence( listOf("-",";","9","P","U","4","P","B",">","V","-","/",".","E","Z","U","P","A","Y","C","X","Z","B","7","D",",","3","+","V","Y",",",".","0","Z"), listOf("N","1","B",".","2","R","0","*","?",">","S","U","J")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("M","O","4","V"), listOf()))
        assertEquals( listOf("G",":",".","Q"), longestCommonSubsequence( listOf("Y","/","G",":","1","T","S","F","@","9",".","9",".","Q"), listOf("=","+","G","4",":","7","Q","Z","Y",".","A","7","T","W","Q","-","M","0","D","-","A","O")))
        assertEquals( listOf(), longestCommonSubsequence( listOf(), listOf("6","7","C","X",">")))
        assertEquals( listOf("4"), longestCommonSubsequence( listOf("9","N","4"), listOf("S",":","H","P","F","?","4","9","A")))
        assertEquals( listOf("R"), longestCommonSubsequence( listOf("R"), listOf("A","Z","1","+","B","J","(","+",">","1","<","6","4","4","7","7","-","V","2","A","5",".","W","U","J","0","1","R","-","R","@",")","W","(","A","R")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("L","L",".","/","5","X","Q","A","H","P","B"), listOf("<","N")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("L","=",":"), listOf("F","+","W","K")))
        assertEquals( listOf("A"), longestCommonSubsequence( listOf("F","+","A"), listOf("3","X","A","I","2","9",">","<","-","(","C","@")))
        assertEquals( listOf("<"), longestCommonSubsequence( listOf("A","H","P","5","4","W",":","+","X","L","3","H","A","-",")","R","V","P","L","0","J","<"), listOf("2","M",".","<","1","I","P")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("("), listOf("S","T","R","6",".","/","S","B","W","8","1","4","L","-","I","1","4","Y","*","L",":","T",">","A")))
        assertEquals( listOf(), longestCommonSubsequence( listOf(">","0","@","N","Z","9","-","3"), listOf("2")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("O","M","."), listOf("K","4",">","+","8",":")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("=",",","D","+","Q","T","R","K","A","/"), listOf("*")))
        assertEquals( listOf("H"), longestCommonSubsequence( listOf("+","Y","2","N","T","O","G",":","H","8","U"), listOf("D","H","5",";","D","C")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("*","3","2","N",")","(","5","A","Z","+","D","Z","R","?","N",".","W","0","B","N","=","R",";","@"), listOf("<")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("A","Z","C","=","Y","P",",","U"), listOf()))
        assertEquals( listOf("V"), longestCommonSubsequence( listOf("V"), listOf("C","/","V")))
        assertEquals( listOf("="), longestCommonSubsequence( listOf(".","?","O","9","G",")","I","Q","A","H","U","H","D","6","+","(","=","K","E","U"), listOf("=","F","W")))
        assertEquals( listOf("="), longestCommonSubsequence( listOf("="), listOf("(","R","R","-","9","Q","@","3","6","L","=","J","K",")")))
        assertEquals( listOf("T","W","1"), longestCommonSubsequence( listOf(".",".","A","6","8","T","H","Y","7","W","1"), listOf("T","W","1","6","M")))
        assertEquals( listOf(), longestCommonSubsequence( listOf("M"), listOf("B")))

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
        val time1 = System.currentTimeMillis()
        for (i in 1..3) {
            val old = readFileLines("src/testing/2_in${i}.txt")
            val new = readFileLines("src/testing/2_in${i + 1}.txt")
            writeFileLines("src/testing/2_out$i.txt", diff(old,new))
        }
        val time2 = System.currentTimeMillis()
        for (i in 1..5) {
            val old = readFileLines("src/testing/3_in${i}.txt")
            val new = readFileLines("src/testing/3_in${i + 1}.txt")
            writeFileLines("src/testing/3_out$i.txt", diff(old,new))
        }
        val time3 = System.currentTimeMillis()
        for (i in 1..9) {
            val old = readFileLines("src/testing/in$i.txt")
            val new = readFileLines("src/testing/in${i + 1}.txt")
            writeFileLines("src/testing/out$i.txt", diff(old,new))
        }
        val time4 = System.currentTimeMillis()

        println("9 comparisons of files with length about n = 100 lines produced in ${time4 - time3} ms")
        println("1 comparison produced in about ${"%.6f".format((time4 - time3)/(9.0*100*100))} * n^2 ms\n")

        println("3 comparisons of files with length about n = 300 lines produced in ${time2 - time1} ms")
        println("1 comparison produced in about ${"%.6f".format((time2 - time1)/(3.0*300*300))} * n^2 ms\n")

        println("5 comparisons of files with length about n = 1100 lines produced in ${time3 - time2} ms")
        println("1 comparison produced in about ${"%.6f".format((time3 - time2)/(5.0*1100*1100))} * n^2 ms\n")

        println("2 comparisons of files with length about n = 3000 lines produced in ${time1 - startTime} ms")
        println("1 comparison produced in about ${"%.6f".format((time1 - startTime)/(2.0*3000*3000))} * n^2 ms\n")
    }
}
