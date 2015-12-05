package com.example.readbooks;
/* Json decoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonDecoder {
    public ArrayList<Book> decodeBooks (JSONObject jsonObject)
            throws JSONException {
        ArrayList<Book> books = new ArrayList<Book>();
        JSONArray arr = jsonObject.getJSONArray("books");
        for (int i = 0; i < arr.length(); i++)
        {
            Book book = new Book();
            JSONObject obj = arr.getJSONObject(i);
            book.id = obj.getLong("id");
            book.title = obj.getString("title");
            book.author = obj.getString("author");
            book.cover_small = obj.getString("coverS");
            book.cover_medium = obj.getString("coverM");
            book.cover_big = obj.getString("coverL");
            books.add(book);
        }
        return books;
    }
    /*public static void main(String[] args) {
        ArrayList<Book> books = new ArrayList();
        Book b = new Book();
        b.id = new Long(375759778);
        Book b1 = new Book();
        b1.id = new Long(425182908);
        books.add(b1);
        books.add(b);

        JsonEncoder je = new JsonEncoder();
        JSONObject obj = null;
        try {
            obj = je.encodeBooks(books);
            JsonDecoder jd = new JsonDecoder();
            ArrayList<Book> res = jd.decodeBooks(obj);
            for (Book e : res) {
                System.out.println(e.id);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }*/
}
