package com.example.readbooks;
/* Json Encoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
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
    public JSONObject encodeRates(Long id, ArrayList<Book> rates) throws JSONException {
        JSONArray ja = new JSONArray();
        for(Book book : rates) {
               JSONObject obj = new JSONObject();
            obj.put("id", book.id);
            obj.put("rate", book.user_rating);
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
}


