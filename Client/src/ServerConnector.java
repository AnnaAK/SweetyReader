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
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.BasicConfigurator;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;

public class ServerConnector{
    public ArrayList<Book> getRecommendations(ArrayList<Long> bookIds ) throws IOException {
        String host = "192.168.1.49";
        String localhost = "127.0.0.1";
        String port = "8080";
        String address = "http://"+ localhost + ":"+port + "/directURL";
        JsonEncoder jsonEncoder = new JsonEncoder();
        JsonDecoder jsonDecoder = new JsonDecoder();

        JSONObject jsonObject = jsonEncoder.encodeBookIds(bookIds);
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
       // System.out.println(res);
        return jsonDecoder.decodeBooks(jsonObjectOut);
    }

    public static void main(String[] args) {
        //BasicConfigurator.configure();
        ServerConnector sc = new ServerConnector();
        ArrayList<Long> bookIds = new ArrayList();
        bookIds.add(new Long(375759778));
        bookIds.add(new Long(425182908));
        bookIds.add(new Long(679810307));
        try {
            ArrayList<Book> books = sc.getRecommendations(bookIds);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
