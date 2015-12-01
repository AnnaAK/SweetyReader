package LocalServer

/* Json decoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.json.JSONArray
import org.json.JSONObject

import java.util.ArrayList

class JsonDecoder {
    fun decodeBookIds(jsonObject: JSONObject): ArrayList<Long> {
        val res = ArrayList<Long>()
        val arr = jsonObject.getJSONArray("bookIDs")
        for (i in 0..arr.length() - 1) {
            res.add((arr.getLong(i)))
        }
        return res
    }
}
