/* Client side
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
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
        String address = "http://"+ localhost + ":"+port + "/directURL";
        JsonEncoder jsonEncoder = new JsonEncoder();
        JsonDecoder jsonDecoder = new JsonDecoder();

        JSONObject jsonObject = jsonEncoder.encodeRates(id, rates);
        //ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
        //postParameters.add(new BasicNameValuePair("jsonOb", jsonObject.toString()));
        /*DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(address);
        httppost.setEntity(new UrlEncodedFormEntity(postParameters));
        HttpResponse response = httpClient.execute(httppost);
        HttpEntity input = response.getEntity();
        InputStream inputStream = input.getContent();*/

        URL url = new URL(address);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestProperty("Content-Type","application/json");
        connection.setFixedLengthStreamingMode(jsonObject.toString().length());
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        connection.connect();
        try {
            PrintWriter out = new PrintWriter(connection.getOutputStream());
            out.print(jsonObject.toString());
            out.close();

            //Get Response
            String response= "";
            InputStream inputStream = connection.getInputStream();
            Scanner inStream = new Scanner(inputStream);

            while(inStream.hasNextLine())
                response+=(inStream.nextLine());

            String jsonString = response;
            JSONObject jsonObjectOut = new JSONObject (jsonString);
            System.out.println(jsonString);
            return jsonDecoder.decodeBooks(jsonObjectOut);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(connection != null) {connection.disconnect();
            }
        }
    }
       /* BufferedReader br = new BufferedReader(
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
    }*/

    public static void main(String[] args) {
        ServerConnector sc = new ServerConnector();
        Long id = new Long(0);
        HashMap<Long, Double> userPreferences = new HashMap<Long, Double>();
        userPreferences.put(new Long(671027360), 8.);
        userPreferences.put(new Long(330332775), 6.0);
        userPreferences.put(new Long(671027387), 8.0);
        userPreferences.put(new Long(380973839), 10.0);
        userPreferences.put(new Long(743424425), 9.0);
        userPreferences.put(new Long(307001164), 9.0);
        userPreferences.put(new Long(140620338), 8.0);
        userPreferences.put(new Long(99771519), 10.0);
        userPreferences.put(new Long(345325818), 8.0);
        userPreferences.put(new Long(99771519), 10.0);
        userPreferences.put(new Long(345325818), 8.0);

        try {
            ArrayList<Book> books = sc.getRecommendations(id, userPreferences);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
