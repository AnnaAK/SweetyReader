package LocalServer;

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book;
import SearchEngine.SearchEngine;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import Recommender.BookRecommender;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;
import java.io.*;
import java.util.ArrayList;

@WebServlet(name = "RecommendationServlet")
public class RecommendationServlet extends HttpServlet {
    public void init() throws ServletException {
        System.out.println("----------");
        System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
        System.out.println("----------");
    }

    private BookRecommender recommender = null;
    private JsonEncoder je = null;
    private JsonDecoder jd = null;
    private SearchEngine searchEngine = null;

    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<h1>" + "Hello Sweaty reader!" + "</h1>");
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (je == null) {
            //recommender = new BookRecommender();
            je = new JsonEncoder();
            jd = new JsonDecoder();
            searchEngine = new SearchEngine();
        }
        String jsonString = request.getParameter("jsonOb");
        ArrayList<Long> bookIds = jd.decodeBookIds
                (new JSONObject(jsonString ));
        response.setContentType("application/json");

        ArrayList<Book> books = searchEngine.getBooks(bookIds);
        JSONObject jsonObject = je.encodeBooks(books);

        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }
}



