package LocalServer

/* Json decoder
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.json.JSONObject
import java.util.*

class JsonDecoder {
    fun decodeInput(jsonObject: JSONObject): UserRates {
        val rates = HashMap<Long, Double>()
        val arr = jsonObject.getJSONArray("bookRates")
        for (i in 0..arr.length() - 1) {
            val jOb = arr.getJSONObject(i)
            rates.set(jOb.getLong("id"),jOb.getDouble("rate") )
        }
        val userRatesClass = UserRates()
        userRatesClass.id = jsonObject.getLong("userID")
        userRatesClass.rates = rates
        return  userRatesClass
    }
}
