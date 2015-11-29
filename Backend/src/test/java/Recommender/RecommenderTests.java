package Recommender;

/* Item based recommender tests
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.LongPrimitiveIterator;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.junit.Test;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

public class RecommenderTests extends BookRecommender {
    //to check that recommender make recommendations
    // (for users who have enough data)
    private long checkWork () throws IOException, TasteException {
        for (LongPrimitiveIterator it = model.getUserIDs(); it.hasNext();){
                long userId = it.nextLong();
                List<RecommendedItem> recommendations =
                        cachingRecommender.recommend(userId, recommendationAmt);
                if (recommendations.size() != 0){
                    return userId;
                }
        }for (LongPrimitiveIterator it = model.getUserIDs(); it.hasNext();){
                long userId = it.nextLong();
                List<RecommendedItem> recommendations =
                        cachingRecommender.recommend(userId, recommendationAmt);
                if (recommendations.size() != 0){
                    return userId;
                }
        }
        return 0;
    }
    @Test
    public void recommendForSomeoneTest() throws IOException, TasteException {
        assertEquals(8, checkWork());
    }
    @Test
    public void recommendUser() {
        BookRecommender recommender = new BookRecommender();
        try {
            List<RecommendedItem> recommendations = recommender.
                    getRecommendedItems(8);
            assertEquals(5, recommendations.size());
            assertEquals(60972785, recommendations.get(0).getItemID());
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (TasteException e1) {
            e1.printStackTrace();
        }
    }
}
