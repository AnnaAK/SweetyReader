package Recommender

/* Item based recommender tests
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import org.apache.mahout.cf.taste.common.TasteException
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.IOException
import java.util.*

class RecommenderTests : BookRecommender() {
    //to check that recommender make recommendations
    // (for users who have enough data)
    private fun checkWork(): Long {
        run {
            val it = model!!.userIDs
            while (it.hasNext()) {
                val userId = it.nextLong()
                val recommendations = cachingRecommender!!.
                        recommend(userId, recommendationAmt)
                if (recommendations.size != 0) {
                    return userId
                }
            }
        }
        val it = model!!.userIDs
        while (it.hasNext()) {
            val userId = it.nextLong()
            val recommendations = cachingRecommender!!.recommend(userId, recommendationAmt)
            if (recommendations.size != 0) {
                return userId
            }
        }
        return 0
    }

    @Test
    fun recommendForSomeoneTest() {
        assertEquals(8, checkWork())
    }

    @Test
    fun recommendUser() {
        val recommender = BookRecommender()
        try {
            val recommendations = recommender.getRecommendations(8, HashMap())
            assertEquals(5, recommendations.size)
            assertEquals(60972785, recommendations[0])
        } catch (e1: IOException) {
            e1.printStackTrace()
        } catch (e1: TasteException) {
            e1.printStackTrace()
        }

    }
}
