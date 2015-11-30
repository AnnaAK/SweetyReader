package LocalServer;

/* Json decoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Basic.Book;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

public class JsonDecoder {
    public ArrayList<Long> decodeBookIds (JSONObject jsonObject)
            throws JSONException {
        ArrayList<Long> res = new ArrayList<Long>();
        JSONArray arr = jsonObject.getJSONArray("bookIDs");
        for (int i = 0; i < arr.length(); i++)
        {
            res.add((arr.getLong(i)));
        }
        return res;
    }
    /*public static void main(String[] args) {
        ArrayList<Long> bookIds = new ArrayList();
        bookIds.add(new Long(375759778));
        bookIds.add(new Long(425182908));
        bookIds.add(new Long(679810307));

        JsonEncoder je = new JsonEncoder();
        JSONObject obj = null;
        try {
            obj = je.encodeBookIds(bookIds);
            JsonDecoder jd = new JsonDecoder();
            ArrayList<Long>  res = jd.decodeBookIds(obj);
            for (Long e : res){
                System.out.println(e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
