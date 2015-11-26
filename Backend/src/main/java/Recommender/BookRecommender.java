package Recommender;

/* Item based recommender
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.CachingRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.TanimotoCoefficientSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class BookRecommender {
    private String fileFull;

    BookRecommender(String file) {
        this.fileFull = fileFull;
        String fileDir  = "/home/guzel/Programming/" +
                "SweetyReader/Backend/src/main/java/data/";
        //String fileName ="smallDataset.csv" ;
        String fileName ="Formated_BX-Book-Ratings.csv" ;
        this.fileFull = fileDir + fileName;
    }

    public List<RecommendedItem> getRecommendations(int id, int amt) throws TasteException, IOException {
        //userID,itemID,value
        File file = new File(fileFull);

        DataModel model = new FileDataModel(file);
        ItemSimilarity similarity = new PearsonCorrelationSimilarity(model);
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender
                (model, similarity);
        // get the recommendations for the user
        Recommender cachingRecommender = new CachingRecommender(recommender);
        List<RecommendedItem> recommendations = cachingRecommender.
                recommend(id, amt);
        return recommendations;
    }

    //to check that recommender make recommendations
    // (for users who have enough data)
    private void checkWork (DataModel model, ItemSimilarity similarity){
        int amt = 5; // of recommendations
        GenericItemBasedRecommender recommender = new GenericItemBasedRecommender
                (model, similarity);
        try {
            for (LongPrimitiveIterator it = model.getUserIDs(); it.hasNext();){
                long userId = it.nextLong();
                List<RecommendedItem> recommendations =
                        recommender.recommend(userId, amt);
                if (recommendations.size() != 0){
                    System.out.print("User ");
                    System.out.print(userId);
                    return;
                }
            }
        } catch (TasteException e) {
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        BookRecommender recommender = new BookRecommender("");
        try {
            List<RecommendedItem> recommendations = recommender.
                    getRecommendations(8, 5);
            System.out.println (recommendations.size());
            for (RecommendedItem recommendation : recommendations) {
                System.out.println(recommendation.getItemID());
            }
        } catch (TasteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
