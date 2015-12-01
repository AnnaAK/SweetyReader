package LocalServer

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book
import Recommender.BookRecommender
import SearchEngine.SearchEngine
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import Recommender.BookRecommender1
import org.json.JSONObject
import java.io.*
import java.util.ArrayList

@WebServlet(name = "RecommendationServlet")
class RecommendationServlet : HttpServlet() {
    @Throws(ServletException::class)
    override fun init() {
        println("----------")
        println("---------- CrunchifyExample Servlet Initialized successfully ----------")
        println("----------")
    }

    private var recommender: BookRecommender? = null
    private var jsonEncoder: JsonEncoder? = null
    private var jsonDecoder: JsonDecoder? = null
    private var searchEngine: SearchEngine? = null

    @Throws(ServletException::class, IOException::class)
    override fun doGet(request: HttpServletRequest,
                       response: HttpServletResponse) {
        response.contentType = "text/html"
        val out = response.writer
        out.println("<h1>" + "Hello Sweaty reader!" + "</h1>")
    }

    @Throws(ServletException::class, IOException::class)
    override fun doPost(request: HttpServletRequest, response: HttpServletResponse) {
        if (jsonEncoder == null) {
            recommender = BookRecommender();
            jsonEncoder = JsonEncoder()
            jsonDecoder = JsonDecoder()
            searchEngine = SearchEngine()
        }
        val jsonString = request.getParameter("jsonOb")
        val bookIds = jsonDecoder!!.decodeBookIds(JSONObject(jsonString))
        response.contentType = "application/json"

        val books = searchEngine!!.getBooks(bookIds)
        val jsonObject = jsonEncoder!!.encodeBooks(books)

        val out = response.writer
        out.print(jsonObject.toString())
    }
}



