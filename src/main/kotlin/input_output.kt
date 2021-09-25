import java.io.File
import java.io.IOException

// Цветной вывод
object Color {
    const val ANSI_RESET = "\u001B[0m"
    const val ANSI_RED = "\u001B[31m"
    const val ANSI_GREEN = "\u001B[32m"
    const val ANSI_BLUE = "\u001B[34m"
}

fun printlnRed(string: String) {
    println(Color.ANSI_RED + string + Color.ANSI_RESET)
}
fun printlnGreen(string: String) {
    println(Color.ANSI_GREEN + string + Color.ANSI_RESET)
}
fun printlnBlue(string: String) {
    println(Color.ANSI_BLUE + string + Color.ANSI_RESET)
}
fun printBlue(string: String) {
    print(Color.ANSI_BLUE + string + Color.ANSI_RESET)
}

// Возвращает содержимое файла filename, разделённое на строки
fun readFileLines(filename: String): List<String> {
        return File(filename).readLines()
}

// Записывает блоки удалённых и добавленных строк с их заголовками в файл filename
fun writeFileLines(filename: String, lines: List<DiffLineBlock>) {
    fun writeDiffLineBlock(filename: String, block: DiffLineBlock) {
        val file = File(filename)
        // Вывод заголовка блока
        val ind = if (block.first != block.last) "${block.first}-${block.last}" else "${block.first}"
        if (block.add)
            file.appendText("\n@ a $ind @")
        else
            file.appendText("\n@ d $ind @")
        // Вывод содержимого блока
        for (str in block.strings)
            file.appendText("\n> $str")
    }
    val file = File(filename)
    // Проверка на существование файла
    // Если такого файла нет, то создаётся новый
    if (!file.exists()) {
        file.createNewFile()
    }
    file.writeText("----------------------------SUCCESS------------------------------------------------------------------------------------")
    for (block in lines)
        writeDiffLineBlock(filename, block)
}

// Записывает блоки удалённых и добавленных строк с их заголовками на экран
fun writeScreenLines(lines: List<DiffLineBlock>) {
    fun writeDiffLineBlock(block: DiffLineBlock) {
        // Вывод заголовка блока
        val ind = if (block.first != block.last) "${block.first}-${block.last}" else "${block.first}"
        if (block.add)
            printBlue("\n@ a $ind @")
        else
            printBlue("\n@ d $ind @")
        // Вывод содержимого блока
        if (block.add) {
            for (str in block.strings)
                printlnGreen("> $str")
        } else {
            for (str in block.strings)
                printlnRed("> $str")
        }
    }
    for (block in lines)
        writeDiffLineBlock(block)
}

// Просит вводить путь к файлу, называя его fileAlias, пока пользователь не введёт корректный путь
fun getCorrectPath(fileAlias: String): String {
    println("Enter the path of $fileAlias")
    var path = readLine()
    while (path == null || !File(path).isFile) {
        println("Unable to convert file to text. Please, the path of another file.")
        path = readLine()
    }
    return path.toString()
}

// Читает файл, путь к которому указал пользователь
fun readFileUsingPath(fileAlias: String): List<String> {
    val path = getCorrectPath(fileAlias)
    return readFileLines(path)
}

fun userMeanScr(userAns: String): Boolean {
    return userAns.isEmpty() || "[s,S][c,C][r,R][e,E][e,E][n,N]".toRegex().matches(userAns)
}

fun userMeanYes(userAns: String): Boolean {
    return userAns.isEmpty() || "[y,Y]".toRegex().matches(userAns[0].toString())
}

// Записывает lines в файл, путь к которому указал пользователь
fun writeResult(lines: List<DiffLineBlock>) {
    println("If you want print result onto screen enter \"scr\", but if you want write result into file enter a file-path")
    var branch = 0
    var path: String? = ""
    while (branch == 0) {
        path = readLine()
        if (path == null || userMeanScr(path.toString())) {
            branch = 1
            break
        }
        if (File(path).exists()) {
            println("You are trying to rewrite an existing file. It may contain important data. Continue anyway?")
            println("(press Y to continue anyway, press N to reenter a path...)")
            val userAns = readLine()
            if (userAns == null || userMeanYes(userAns)) {
                branch = 2
            } else {
                println("If you want print result onto screen press enter \"scr\", but if you want write result into file enter a file-path")
                branch = 0
            }
        } else {
            branch = 3
        }
    }
    when (branch) {
        1 -> writeScreenLines(lines)
        2 -> writeFileLines(path.toString(), lines)
        3 -> {
            try {
                File(path).createNewFile()
                writeFileLines(path.toString(), lines)
            } catch (e: IOException) {
                // do anything with exceptions
            } finally {
                println("An error occurred while creating the file. Probably, you have not enough permissions to create file")
                println("in this directory or path was not found...")
            }

        }
    }

}
