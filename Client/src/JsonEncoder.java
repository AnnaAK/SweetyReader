/* Json Encoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JsonEncoder {
    public JSONObject encodeRates(Long id, Map<Long,Double> rates){
        JSONArray ja = new JSONArray();
        for(Map.Entry<Long, Double> entry : rates.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();

            JSONObject obj = new JSONObject();
            obj.put("id", key);
            obj.put("rate", value);
            ja.put(obj);
        }
        JSONObject mainObj = new JSONObject();
        mainObj.put("bookRates", ja);
        mainObj.put("userID", id);
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
        HashMap<Long, Double> bookRates = new HashMap<Long, Double>();
        bookRates.put(new Long(375759778), 9.8);
        bookRates.put(new Long(425182908), 7.8);
        bookRates.put(new Long(679810307), 6.3);

        Long id = new Long(8);
        JsonEncoder je = new JsonEncoder();
        JSONObject obj = je.encodeRates(id, bookRates);

        StringWriter out = new StringWriter();
        obj.write(out);
        String jsonText = out.toString();
            System.out.print(jsonText);
    }

}
