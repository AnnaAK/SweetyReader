package LocalServer

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Recommender.BookRecommender
import SearchEngine.SearchEngine
import org.json.JSONObject
import java.io.IOException
import java.util.*
import javax.servlet.ServletException
import javax.servlet.annotation.WebServlet
import javax.servlet.http.HttpServlet
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@WebServlet(name = "RecommendationServlet")
class RecommendationServlet : HttpServlet() {
    @Throws(ServletException::class)
    override fun init() {
        println("----------")
        println("---------- CrunchifyExample Servlet Initialized successfully ----------")
        println("----------")
    }

    private val recommender: BookRecommender? = null
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
            //recommender = new BookRecommender();
            jsonEncoder = JsonEncoder()
            jsonDecoder = JsonDecoder()
            searchEngine = SearchEngine()
        }
        val jsonString = request.getParameter("jsonOb")
        val bookRatesClass = jsonDecoder!!.decodeInput(JSONObject(jsonString))
        response.contentType = "application/json"

        val bookIds = ArrayList<Long>()
        for (e in bookRatesClass.rates.entries) {
            bookIds.add(e.key)
        }

        //val bookIds = recommender!!.getRecommendations(bookRatesClass.id,
        //       bookRatesClass.rates)
        val books = searchEngine!!.getBooks(bookIds)
        val jsonObject = jsonEncoder!!.encodeBooks(books)

        val out = response.writer
        out.print(jsonObject.toString())
    }
}



