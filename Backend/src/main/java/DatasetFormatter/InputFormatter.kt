package DatasetFormatter

/* Header remover for csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import java.io.*

class InputFormatter {
    fun setFileName(str: String) {
        fileName = str
        fullName = filePath + fileName + fileFormat
    }
    private val filePath = "/home/guzel/"+
            "Programming/SweetyReader/Backend/src/main/java/data/"
    private var fileName = ""
    private val fileFormat = ".csv"
    private var fullName = filePath + fileName + fileFormat

    private fun createFile(f: File) {
        try {
            if (f.createNewFile()) {
                println("File named $f created successfully !")
            } else {
                println("File with name $f already exixts !")
            }
        } catch (e: IOException) {
            println("Error in creating")
        }
    }

    private fun notCorrect(line: String): Boolean {
        for (e in line.toCharArray()) {
            if (!Character.isDigit(e) && (e != ';') && (e != '"'))
                return true
        }
        return false
    }

    fun formatTestSet() {
        if (fileName === "") {
            println("Set fileName")
            throw IOException()
        }
        val file = File(fullName)
        val tempFile = File(filePath + "Formated_" + fileName + fileFormat)
        createFile(tempFile)

        var br: BufferedReader? = null
        var pw: PrintWriter? = null

        var amt1 = 0
        var amt2 = 0
        try {
            val br = BufferedReader(FileReader(file))
            val pw = PrintWriter(FileWriter(tempFile))

            //skip first line (our database is big, so we sure that it's not empty)
            var line = br.readLine()
            line = br.readLine()
            while (line != null) {
                amt1++
                if (notCorrect(line)) {
                    amt2++
                    line = br.readLine()
                    continue
                }
                for (e in line.toCharArray()) {
                    when (e) {
                        '"' -> {
                        }
                        ';' -> {
                            pw.print(',')
                        }
                        else -> {
                            pw.print(e)
                        }
                    }
                }
                pw.println()
                pw.flush()
                line = br.readLine()
            }
        } catch (e: IOException) {
            System.err.println("Cannot find file")
        } finally {
            fileName = ""
            pw!!.close()
            br!!.close()
            println(amt1)
            println(amt2)
        }
    }

    private fun deleteQuotes(str: String): String {
        var res = ""
        for (e in str.toCharArray()) {
            if (e != '"') {
                res += e
            }
        }
        return res
    }

    private fun parseLine(line: String): String {
        val delims = ";"
        val tokens = line.split(delims.toRegex()).
                dropLastWhile { it.isEmpty() }.toTypedArray()
        if (notCorrect(tokens[0])) {
            return ""
        }
        val n = tokens.size()
        var res = tokens[0] + ";"
        for (i in 1..n - 6 - 1) {
            res += tokens[i]
        }
        res += ";" + tokens[n - 6] + ";"
        for (i in n - 3..n - 1) {
            res += tokens[i] + ";"
        }
        return deleteQuotes(res)
    }

    fun formatBookInformationSet() {
        if (fileName === "") {
            println("Set fileName")
            throw IOException()
        }
        val file = File(fullName)
        val tempFile = File(filePath + "Formated_" + fileName + fileFormat)
        createFile(tempFile)

        var br: BufferedReader? = null
        var pw: PrintWriter? = null
        try {
            br = BufferedReader(FileReader(file))
            pw = PrintWriter(FileWriter(tempFile))

            //skip first line (our database is big,
            // so we sure that it's not empty)
            var line = br.readLine()
            line = br.readLine()
            while (line  != null) {
                line = parseLine(line)
                if (line == "") {
                    line = br.readLine()
                    continue
                }
                line = br.readLine()
                pw.println(line)
                pw.flush()
            }
        } catch (e: IOException) {
            System.err.println("Cannot find file")
        } finally {
            fileName = ""
            pw!!.close()
            br!!.close()
        }
    }

}
public fun  main(args: Array<String>) {
    val fileName = "BX-Books"
    val testSetFormatter = InputFormatter()
    testSetFormatter.setFileName(fileName)
    try {
        testSetFormatter.formatBookInformationSet()
    } catch (e: IOException) {
        System.err.println("Cannot find file")
    }
}
