package SearchEngine

/* Search Engine for books
   stored csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import Basic.Book
import Recommender.BookRecommender1
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*

class SearchEngine {
    private val filePath = "/home/guzel/" + "Programming/SweetyReader/Backend/src/main/java/data/"
    private val fileName = "Formated_BX-Books.csv"
    private val file = File(filePath + fileName)
    private fun getBook(tokens: Array<String>): Book {
        val book = Book()
        book.id = java.lang.Long.parseLong(tokens[0])
        book.title = tokens[1]
        book.author = tokens[2]
        book.ps = tokens[3]
        book.pm = tokens[4]
        book.pl = tokens[5]
        return book
    }

    fun getBooks(arr: ArrayList<Long>): ArrayList<Book> {
        val books = ArrayList<Book>()
        var br: BufferedReader? = null
        var amt = 0
        try {
            br = BufferedReader(FileReader(file))
            var line: String
            amt = arr.size
            line = br.readLine()
            while (line != null) {
                val delims = ";"
                val tokens =
                        line.split(delims.toRegex()).
                                dropLastWhile { it.isEmpty() }.toTypedArray()
                val index = java.lang.Long.parseLong(tokens[0])
                for (e in arr) {
                    if (e == index) {
                        amt--
                        books.add(getBook(tokens))
                        if (amt == 0) {
                            return books
                        }
                    }
                }
                line = br.readLine()
            }
        } catch (e: IOException) {
            System.err.println("Cannot find file")
        } finally {
            if (br != null) br.close()
            return books
        }
    }
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val br = BookRecommender1()
            try {
                val searchEngine = SearchEngine1()
                val items = br.getRecommendations(8)
                if (items == null) {
                    println("Recommendation fail")
                    return
                }
                val books = searchEngine.getBooks(items)
                println("Ok")
                println(books[0].author)
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}
