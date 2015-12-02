package Recommender

/* Item based recommender
   made by Guzel Garifullina
   for Sweaty Reader project
*/


import org.apache.mahout.cf.taste.common.TasteException
import org.apache.mahout.cf.taste.eval.RecommenderBuilder
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity
import org.apache.mahout.cf.taste.model.DataModel
import org.apache.mahout.cf.taste.recommender.RecommendedItem
import org.apache.mahout.cf.taste.recommender.Recommender
import java.io.File
import java.io.IOException
import java.util.*

open class BookRecommender {
    internal var fileDir = "/home/guzel/Programming/" + "SweetyReader/Backend/src/main/java/data/"
    //String fileName ="smallDataset.csv" ;
    private val fileName = "Formated_BX-Book-Ratings.csv"
    private val fileFull = fileDir + fileName
    private val concurrentUsers = 100
    protected var recommendationAmt = 5

    protected var file: File = File(fileFull)
    protected var model: DataModel? = null
    protected var anonymousModel: PlusAnonymousConcurrentUserDataModel? = null
    protected var brb: BookRecommenderBuilder? = null
    protected var cachingRecommender: Recommender? = null

    init {
        try {
            model = FileDataModel(file)
            anonymousModel = PlusAnonymousConcurrentUserDataModel(
                    model, concurrentUsers)
            brb = BookRecommenderBuilder()
            val brb1 = BookRecommenderBuilder()
            cachingRecommender = brb1.buildRecommender(anonymousModel)
        } catch (e: IOException) {
            e.printStackTrace()
        } catch (e: TasteException) {
            e.printStackTrace()
        }

    }
    protected inner class BookRecommenderBuilder : RecommenderBuilder {
        override fun buildRecommender(model: DataModel?): Recommender? {
            val similarity = TanimotoCoefficientSimilarity(model)
            val recommender = GenericItemBasedRecommender(model, similarity)
            val cachingRecommender = CachingRecommender(recommender)
            return cachingRecommender
        }
    }

    private fun userExistsInDataModel(id: Long): Boolean {
        return true
    }

    private fun recommendForExistingUser(id: Long): List<RecommendedItem>? {
        val recommendations = cachingRecommender!!.recommend(id, recommendationAmt)
        return recommendations
    }

    private fun recommendForNewUser(userPreferences: Map<Long, Double>): List<RecommendedItem>? {
        //Get new user id
        val newUserID = anonymousModel!!.takeAvailableUser()
        // fill a Mahout data structure with the user's preferences
        val tempPrefs = GenericUserPreferenceArray(userPreferences.size)
        var i = 0
        for (entry in userPreferences.entries) {
            val key = entry.key
            val value = entry.value

            tempPrefs.setUserID(i, newUserID)
            tempPrefs.setItemID(i, key)
            tempPrefs.setValue(i, value.toFloat())

            i++
        }
        // Add the DatasetFormatter preferences to model
        anonymousModel!!.setTempPrefs(tempPrefs, newUserID)
        return recommendForExistingUser(newUserID)
    }

    protected fun getRecommendedItems(id: Long, userPreferences: Map<Long, Double>)
            : List<RecommendedItem>? {
        if (userExistsInDataModel(id)) {
            return recommendForExistingUser(id)
        }
        return recommendForNewUser(userPreferences);
    }

    private fun convertToIndices(list: List<RecommendedItem>?): ArrayList<Long> {
        val res = ArrayList<Long>()
        if (list == null) return res
        for (e in list) {
            res.add(e.itemID)
        }
        return res
    }

    public fun getRecommendations(id: Long, userPreferences: Map<Long, Double>): ArrayList<Long> {
        try {
            return convertToIndices(getRecommendedItems(id, userPreferences))
        } catch (e: TasteException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return ArrayList()
    }

    public fun evaluate(trainingPercentage: Double, evaluationPercentage: Double): Double {
        val evaluator = AverageAbsoluteDifferenceRecommenderEvaluator()
        val builder = BookRecommenderBuilder()
        var result = 0.0
        try {
            result = evaluator.evaluate(builder, null, model,
                    trainingPercentage, evaluationPercentage)
        } catch (e: TasteException) {
            e.printStackTrace()
        }
        return result
    }

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val br = BookRecommender()
            System.out.println(br.evaluate(0.2, 0.1))
        }
    }
}
