package LocalServer;

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book1;
import SearchEngine.SearchEngine1;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Recommender.BookRecommender1;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

@WebServlet(name = "RecommendationServlet")
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
        ArrayList<Long> bookIds = jsonDecoder.decodeBookIds
                (new JSONObject(jsonString ));
        response.setContentType("application/json");

        ArrayList<Book1> books = searchEngine.getBooks(bookIds);
        JSONObject jsonObject = jsonEncoder.encodeBooks(books);

        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }
}



