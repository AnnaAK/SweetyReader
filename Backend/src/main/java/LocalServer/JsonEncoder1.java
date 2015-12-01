package LocalServer;

/* Json encoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book1;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonEncoder1 {
    private JSONObject encodeBook(Book1 book) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", book.id);
            obj.put("title", book.title);
            obj.put("author", book.author);
            obj.put("coverS", book.ps);
            obj.put("coverM", book.pm);
            obj.put("coverL", book.pl);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    //Client side
    /*public JSONObject encodeBookIds(ArrayList<Long> ids) throws org.codehaus.jettison.json.JSONException {
        JSONArray ja = new JSONArray();
        for (Long id : ids) {
            ja.put(id);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("bookIDs", ja);
        return mainObj;
    }*/
    public JSONObject encodeBooks(ArrayList<Book1> books) {
        JSONArray ja = new JSONArray();
        for (Book1 book : books) {
            JSONObject jo = encodeBook(book);
            ja.put(jo);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("books", ja);
        return mainObj;
    }
    /*public static void main(String[] args) {
        ArrayList<Long> bookIds = new ArrayList();
        bookIds.add(new Long(375759778));
        bookIds.add(new Long(425182908));
        bookIds.add(new Long(679810307));

        SearchEngine searchEngine = new SearchEngine();
        try {
            ArrayList<Book> books = searchEngine.getBooks(bookIds);
            if (books.size() == 0) {
                System.out.println("Empty");
            }
            JsonEncoder je = new JsonEncoder();
            JSONObject obj = je.encodeBooks(books);

            StringWriter out = new StringWriter();
            try {
                obj.write(out);

                String jsonText = out.toString();
                System.out.print(jsonText);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
