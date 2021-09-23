import java.io.File

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

// Просит вводить путь к файлу, называя его fileAlias, пока пользователь не введёт корректный путь
fun getCorrectPath(fileAlias: String): String {
    println("Enter the path of $fileAlias")
    var path = readLine()
    var file = File(path)
    while (file == null || !file.isFile) {
        if (file == null)
            println("The path of file is incorrect. Please, try again.")
        else
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
