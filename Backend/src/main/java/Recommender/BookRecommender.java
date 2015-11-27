package Recommender;

/* Item based recommender
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.BooleanUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.recommender.Recommender;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class BookRecommender {
    String fileDir  = "/home/guzel/Programming/" +
            "SweetyReader/Backend/src/main/java/data/";
    //String fileName ="smallDataset.csv" ;
    private String fileName ="Formated_BX-Book-Ratings.csv" ;
    private String fileFull = fileDir + fileName;
    private int concurrentUsers = 100;
    protected int recommendationAmt = 5;

    protected File file;
    protected DataModel model;
    protected PlusAnonymousConcurrentUserDataModel anonymousModel;
    protected BookRecommenderBuilder brb ;
    protected Recommender cachingRecommender ;
    public BookRecommender() {
        File file = new File(fileFull);
        try {
            model = new  FileDataModel(file);
            anonymousModel = new PlusAnonymousConcurrentUserDataModel(
                    model , concurrentUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
        brb =  new BookRecommenderBuilder();
        try {
            cachingRecommender = brb.buildRecommender(anonymousModel);
        } catch (TasteException e) {
            e.printStackTrace();
        }

    }
    protected class BookRecommenderBuilder implements RecommenderBuilder {
        public Recommender buildRecommender(DataModel model) throws TasteException{
            ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender
                    (model, similarity);
            Recommender cachingRecommender = new CachingRecommender(recommender);
            return cachingRecommender;
        }
    }
    private boolean userExistsInDataModel(DataModel model, int id){
        return true;
    }
    private List<RecommendedItem> recommendForExistingUser(long id)
            throws TasteException, IOException {
        List<RecommendedItem> recommendations = cachingRecommender.
                recommend(id, recommendationAmt);
        return recommendations;
    }
    private List<RecommendedItem> recommendForNewUser
            (Map<Long, Float> userPreferences) throws IOException, TasteException {
        //Get new user id
        long newUserID = anonymousModel.takeAvailableUser();
        // fill a Mahout data structure with the user's preferences
        GenericUserPreferenceArray tempPrefs =
                new GenericUserPreferenceArray(userPreferences.size());
        int i = 0;
        for(Map.Entry<Long, Float> entry : userPreferences.entrySet()) {
            Long key = entry.getKey();
            Float value = entry.getValue();

            tempPrefs.setUserID(i, newUserID);
            tempPrefs.setItemID(i, key);
            tempPrefs.setValue(i, value);

            i++;
        }
        // Add the temporary preferences to model
        anonymousModel.setTempPrefs(tempPrefs, newUserID);
        return recommendForExistingUser(newUserID);
    }

    public List<RecommendedItem> getRecommendations(int id)
            throws TasteException, IOException {
        return recommendForExistingUser(id);
    }
    public double evaluate(double trainingPercentage, double evaluationPercentage) throws TasteException, IOException {
        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new BookRecommenderBuilder();
        double result = evaluator.evaluate(builder, null, model,
                trainingPercentage, evaluationPercentage);
        return result;
    }
    public static void main(String[] args) {
        BookRecommender br = new BookRecommender();
        try {
            System.out.println(br.evaluate(0.2,0.1));
        } catch (TasteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
