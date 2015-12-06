package main.java.Recommender;

/* Item based recommender
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.model.PlusAnonymousConcurrentUserDataModel;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

    protected File file = new File(fileFull);
    protected DataModel model;
    protected PlusAnonymousConcurrentUserDataModel anonymousModel;
    protected BookRecommenderBuilder brb ;
    protected Recommender cachingRecommender ;
    public BookRecommender() {
        try {
            model = new FileDataModel(file);
            anonymousModel = new PlusAnonymousConcurrentUserDataModel(
                    model , concurrentUsers);
            brb =  new BookRecommenderBuilder();
            cachingRecommender = brb.buildRecommender(anonymousModel);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TasteException e) {
            e.printStackTrace();
        }
    }
    protected class BookRecommenderBuilder implements RecommenderBuilder {
        public Recommender buildRecommender(DataModel model) throws TasteException{
            ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender
                    (model, similarity);
            Recommender cachingRecommender = new CachingRecommender(recommender);
            return cachingRecommender;
        }
    }
    private boolean userExistsInDataModel(DataModel model, Long id){
        return ! id.equals(new Long(0)) ;
    }
    private List<RecommendedItem> recommendForExistingUser(Long id)
            throws TasteException, IOException {
        List<RecommendedItem> recommendations = cachingRecommender.
                recommend(id, recommendationAmt);
        return recommendations;
    }
    private List<RecommendedItem> recommendForNewUser
            (Map<Long, Double> userPreferences) throws IOException, TasteException {
        //Get new user id
        long newUserID = anonymousModel.takeAvailableUser();
        // fill a Mahout data structure with the user's preferences
        GenericUserPreferenceArray tempPrefs =
                new GenericUserPreferenceArray(userPreferences.size());
        int i = 0;
        for(Map.Entry<Long, Double> entry : userPreferences.entrySet()) {
            Long key = entry.getKey();
            Double value = entry.getValue();

            tempPrefs.setUserID(i, newUserID);
            tempPrefs.setItemID(i, key);
            tempPrefs.setValue(i,  value.floatValue());

            i++;
        }
        // Add the DatasetFormatter preferences to model
        anonymousModel.setTempPrefs(tempPrefs, newUserID);
        return recommendForExistingUser(newUserID);
    }

    protected List<RecommendedItem> getRecommendedItems(Long id,
                                                        Map<Long, Double> userPreferences)
            throws TasteException, IOException {
        if (userExistsInDataModel(model, id)) {
            return recommendForExistingUser(id);
        }
        return recommendForNewUser(userPreferences);
    }
    private ArrayList<Long> convertToIndices(List<RecommendedItem> list) {
        ArrayList<Long> res = new ArrayList<Long>();
        if (list == null) return res;
        for (RecommendedItem e : list ){
            res.add(e.getItemID());
        }
        return res;
    }
    public ArrayList<Long> getRecommendations (Long id,
                                               Map<Long, Double> userPreferences) {
        try {
            return convertToIndices(getRecommendedItems(id, userPreferences));
        } catch (TasteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList();
    }
    public double evaluate(double trainingPercentage, double evaluationPercentage){
        RecommenderEvaluator evaluator =
                new AverageAbsoluteDifferenceRecommenderEvaluator();
        RecommenderBuilder builder = new BookRecommenderBuilder();
        double result = 0;
        try {
            result = evaluator.evaluate(builder, null, model,
                    trainingPercentage, evaluationPercentage);
        } catch (TasteException e) {
            e.printStackTrace();
        }
        return result;
    }
    /*public static void main(String[] args) {
        BookRecommender br = new BookRecommender();
        System.out.println(br.evaluate(0.2,0.1));
    }*/
    public static void main(String[] args){
        BookRecommender br = new BookRecommender();
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
        ArrayList<Long>  recommendedBooks = br.getRecommendations(id,userPreferences);
        for (Long book : recommendedBooks){
            System.out.println(book);
        }




    }
}
