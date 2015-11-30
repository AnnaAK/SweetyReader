/* Json Encoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.StringWriter;
import java.util.ArrayList;

public class JsonEncoder {
    public JSONObject encodeBookIds (ArrayList<Long> ids){
        JSONArray ja = new JSONArray();
        for (Long id: ids){
            ja.put(id);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("bookIDs", ja);
        return mainObj;
    }
    //Server side, to check decoder
    /*private JSONObject encodeBook(Book book) {
        JSONObject obj = new JSONObject();
        try {
            obj.put("id", book.id);
            obj.put("title", book.title);
            obj.put("author", book.author);
            obj.put("coverS", book.cover_small);
            obj.put("coverM", book.cover_medium);
            obj.put("coverL", book.cover_big);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return obj;
    }
    public JSONObject encodeBooks(ArrayList<Book> books) throws JSONException {
        JSONArray ja = new JSONArray();
        for (Book book : books) {
            JSONObject jo = encodeBook(book);
            ja.put(jo);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("books", ja);
        return mainObj;
    }*/
    public static void main(String[] args) {
        ArrayList<Long> bookIds = new ArrayList();
        bookIds.add(new Long(375759778));
        bookIds.add(new Long(425182908));
        bookIds.add(new Long(679810307));

        JsonEncoder je = new JsonEncoder();
        JSONObject obj = je.encodeBookIds(bookIds);

        StringWriter out = new StringWriter();
        obj.write(out);
        String jsonText = out.toString();
            System.out.print(jsonText);
    }

}
