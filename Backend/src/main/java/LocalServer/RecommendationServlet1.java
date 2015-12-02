package LocalServer;

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import Basic.Book;
import Recommender.BookRecommender1;
import SearchEngine.SearchEngine1;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "RecommendationServlet1")
public class RecommendationServlet1 extends HttpServlet {
    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
        System.out.println("----------");
    }

    private BookRecommender1 recommender = null;
    private JsonEncoder1 jsonEncoder = null;
    private JsonDecoder1 jsonDecoder = null;
    private SearchEngine1 searchEngine = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Hello Sweaty reader!" + "</h1>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (jsonEncoder == null) {
            //recommender = new BookRecommender();
            jsonEncoder = new JsonEncoder1();
            jsonDecoder = new JsonDecoder1();
            searchEngine = new SearchEngine1();
        }
        String jsonString = request.getParameter("jsonOb");
        UserRates bookRatesClass = jsonDecoder.decodeInput (new JSONObject(jsonString ));
        response.setContentType("application/json");

        ArrayList<Long> bookIds = new ArrayList<Long>();
        for (Map.Entry<Long, Double> e : bookRatesClass.getRates().entrySet()){
            bookIds.add(e.getKey());
        }

        //val bookIds = recommender!!.getRecommendations(bookRatesClass.id,
        //       bookRatesClass.rates)
        ArrayList<Book> books = searchEngine.getBooks(bookIds);
        JSONObject jsonObject = jsonEncoder.encodeBooks(books);

        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }
}



