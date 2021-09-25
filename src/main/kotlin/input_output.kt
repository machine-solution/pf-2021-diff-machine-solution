import java.io.File

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
            printlnBlue("\n@ a $ind @")
        else
            printlnBlue("\n@ d $ind @")
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
    var file = File(path)
    while (!file.isFile) {
        println("Unable to convert file to text. Please, the path of another file.")
        path = readLine()
        file = File(path)
    }

    return path.toString()
}

// Читает файл, путь к которому указал пользователь
fun readFileUsingPath(fileAlias: String): List<String> {
    val path = getCorrectPath(fileAlias)
    return readFileLines(path)
}

// Записывает lines в файл, путь к которому указал пользователь
fun writeFileUsingPath(fileAlias: String, lines: List<DiffLineBlock>) {
    val path = getCorrectPath(fileAlias)
    writeFileLines(path, lines)
}
