package LocalServer

/* Json encoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

import java.util.ArrayList

class JsonEncoder {
    private fun encodeBook(book: Book): JSONObject {
        val obj = JSONObject()
        try {
            obj.put("id", book.id)
            obj.put("title", book.title)
            obj.put("author", book.author)
            obj.put("coverS", book.ps)
            obj.put("coverM", book.pm)
            obj.put("coverL", book.pl)
            return obj
        } catch (e: JSONException) {
            e.printStackTrace()
        }

        return obj
    }
    fun encodeBooks(books: ArrayList<Book>): JSONObject {
        val ja = JSONArray()
        for (book in books) {
            val jo = encodeBook(book)
            ja.put(jo)
        }
        val mainObj = JSONObject()
        mainObj.put("books", ja)
        return mainObj
    }
}
