package main.java.LocalServer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class JsonDecoder {
    public UserRates decodeInput (JSONObject jsonObject)
            throws JSONException {
        HashMap<Long, Double> rates = new HashMap<Long, Double>();
        JSONArray array = jsonObject.getJSONArray("bookRates");
        for (int i = 0; i < array.length(); i++)
        {
            JSONObject jOb = array.getJSONObject(i);
            rates.put(jOb.getLong("id"),jOb.getDouble("rate") );
        }
        UserRates userRatesClass = new UserRates();
        userRatesClass.setUserId(jsonObject.getLong("userID"));
        userRatesClass.setRates (rates);
        return  userRatesClass;
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
