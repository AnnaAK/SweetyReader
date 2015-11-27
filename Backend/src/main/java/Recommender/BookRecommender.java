package Recommender;

/* Item based recommender
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.eval.RecommenderBuilder;
import org.apache.mahout.cf.taste.eval.RecommenderEvaluator;
import org.apache.mahout.cf.taste.impl.eval.AverageAbsoluteDifferenceRecommenderEvaluator;
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

public class BookRecommender {
    String fileDir  = "/home/guzel/Programming/" +
            "SweetyReader/Backend/src/main/java/data/";
    //String fileName ="smallDataset.csv" ;
    String fileName ="Formated_BX-Book-Ratings.csv" ;
    String fileFull = fileDir + fileName;
    protected class BookRecommenderBuilder implements RecommenderBuilder {
        public Recommender buildRecommender(DataModel model) throws TasteException{
            ItemSimilarity similarity = new TanimotoCoefficientSimilarity(model);
            GenericItemBasedRecommender recommender = new GenericItemBasedRecommender
                    (model, similarity);
            Recommender cachingRecommender = new CachingRecommender(recommender);
            return cachingRecommender;
        }
    }
    protected DataModel getModel() throws IOException {
        //userID,itemID,value
        File file = new File(fileFull);
        DataModel model = new FileDataModel(file);
        return model;
    }
    private Recommender getRecommender () throws IOException, TasteException {
        BookRecommenderBuilder brb =  new BookRecommenderBuilder();
        return brb.buildRecommender(getModel());
    }
    public List<RecommendedItem> getRecommendations(int id, int amt)
            throws TasteException, IOException {
        Recommender cachingRecommender = getRecommender();
        List<RecommendedItem> recommendations = cachingRecommender.
                recommend(id, amt);
        return recommendations;
    }
    public double evaluate(double trainingPercentage, double evaluationPercentage) throws TasteException, IOException {
        DataModel model = getModel();
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
