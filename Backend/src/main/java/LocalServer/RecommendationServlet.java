package main.java.LocalServer;

/* Servlet to get
   recommendations
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import main.java.Basic.Book;
import main.java.Recommender.BookRecommender;
import main.java.SearchEngine.SearchEngine;
import org.json.JSONObject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;

@WebServlet(name = "RecommendationServlet1")
public class RecommendationServlet extends HttpServlet {
    public void init() throws ServletException {
        super.init();
        System.out.println("----------");
        System.out.println("---------- CrunchifyExample Servlet Initialized successfully ----------");
        System.out.println("----------");
        recommender = new BookRecommender();
        jsonEncoder = new JsonEncoder();
        jsonDecoder = new JsonDecoder();
        searchEngine = new SearchEngine();
    }

    private BookRecommender recommender = null;
    private JsonEncoder jsonEncoder = null;
    private JsonDecoder jsonDecoder = null;
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
        //String jsonString = request.getParameter("jsonOb");
        InputStream inn = request.getInputStream();
        BufferedReader br = new BufferedReader(
                new InputStreamReader(inn));
        String jsonString = "";
        String output;
        while ((output = br.readLine()) != null) {
            jsonString += output;
        }
        UserRates bookRatesClass = jsonDecoder.decodeInput (new JSONObject(jsonString ));
        response.setContentType("application/json");

       /* ArrayList<Long> bookIds = new ArrayList<Long>();
        for (Map.Entry<Long, Double> e : bookRatesClass.getRates().entrySet()){
            bookIds.add(e.getKey());
        }*/
        Long id = bookRatesClass.getUserId();
        Map<Long, Double> userPreferences = bookRatesClass.getRates();
        ArrayList<Long>  recommendedBooks =recommender.getRecommendations(
                id,userPreferences);

        //val bookIds = recommender!!.getRecommendations(bookRatesClass.id,
        //       bookRatesClass.rates)
        ArrayList<Book> books = searchEngine.getBooks(recommendedBooks);
        JSONObject jsonObject = jsonEncoder.encodeBooks(books);

        PrintWriter out = response.getWriter();
        out.print(jsonObject.toString());
    }
}



