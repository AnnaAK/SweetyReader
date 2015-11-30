package SearchEngine;

/* Search Engine for books
   stored csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/
import Recommender.BookRecommender;
import org.apache.mahout.cf.taste.common.TasteException;
import java.io.*;
import java.util.ArrayList;
import Basic.Book;

public class SearchEngine {
    private String filePath  = "/home/guzel/" +
            "Programming/SweetyReader/Backend/src/main/java/data/";
    private String fileName = "Formated_BX-Books.csv";
    private File file = new File(filePath + fileName);
    private Book getBook(String[] tokens){
        Book book = new Book();
        book.id = Long.parseLong(tokens[0]);
        book.title = tokens[1];
        book.author = tokens[2];
        book.ps = tokens[3];
        book.pm = tokens[4];
        book.pl = tokens[5];
        return book;
    }
    public ArrayList<Book> getBooks (ArrayList<Long> arr) throws IOException {
        ArrayList<Book> books = new ArrayList<Book>();
        BufferedReader br = null;
        int amt = 0;
        try {
            br = new BufferedReader(new FileReader(file));
            String line;
            amt = arr.size();
            while ((line = br.readLine()) != null) {
                String delims = ";";
                String[] tokens = line.split(delims);
                Long index = Long.parseLong(tokens[0]);
                for (Long e : arr){
                    if (e.equals(index)){
                        amt --;
                        books.add(getBook(tokens));
                        if (amt == 0){
                            return books;
                        }
                    }
                }
            }
        }catch (IOException e) {
            System.err.println("Cannot find file");
        } finally {
            if (br != null) br.close();
            /*if (amt != 0  ){
                System.out.println("Can't find some books");
                for (long e : arr ){
                    System.out.print(e);
                    System.out.print("; ");
                }
                System.out.println();
                for (Book e : books ){
                    System.out.print(e.id);
                    System.out.print("; ");
                }
                throw new IOException();
            }*/
            return books;
        }
    }
    public static void main(String[] args) {
        BookRecommender br = new BookRecommender();
        try {
            SearchEngine searchEngine = new SearchEngine();
            ArrayList items  = br.getRecommendations(8);
            if (items == null){
                System.out.println("Recommendation fail");
                return;
            }
            ArrayList<Book> books = searchEngine.getBooks(items);
            System.out.println("Ok");
            System.out.println(books.get(0).author);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
