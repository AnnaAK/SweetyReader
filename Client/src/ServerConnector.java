/* Client side
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ServerConnector{
    public ArrayList<Book> getRecommendations(Long id,Map<Long, Double> rates )
            throws IOException {
       // String host = "192.168.1.49";
       // String host = "172.20.153.119";
        String localhost = "127.0.0.1";
        String port = "8080";
        //String address ="http://localhost:8080/directURL";
        String address = "http://"+ localhost + ":"+port + "/directURL";
        JsonEncoder jsonEncoder = new JsonEncoder();
        JsonDecoder jsonDecoder = new JsonDecoder();

        JSONObject jsonObject = jsonEncoder.encodeRates(id, rates);
        ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        postParameters.add(new BasicNameValuePair("jsonOb", jsonObject.toString()));

        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(address);
        httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse response = httpClient.execute(httppost);
        HttpEntity input = response.getEntity();

        InputStream inputStream = input.getContent();

        BufferedReader br = new BufferedReader(
                new InputStreamReader((input.getContent())));
        String output;
        String res = "";
        while ((output = br.readLine()) != null) {
            res += output;
        }
        String jsonString = res;
        JSONObject jsonObjectOut = new JSONObject (jsonString);
        System.out.println(res);
        return jsonDecoder.decodeBooks(jsonObjectOut);
    }

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        ServerConnector sc = new ServerConnector();
        HashMap<Long, Double> bookRates = new HashMap<Long, Double>();
        bookRates.put(new Long(195153448), 9.8);
        bookRates.put(new Long(425182908), 7.8);
        bookRates.put(new Long(679810307), 6.3);

        Long id = new Long(8);
        try {
            ArrayList<Book> books = sc.getRecommendations(id, bookRates);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
