package SearchEngine;

/* Search Engine for books
   stored csv files
   made by Guzel Garifullina
   for Sweaty Reader project
*/

import Basic.Book;
import Recommender.BookRecommender1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SearchEngine1 {
    private String filePath  = "/home/guzel/" +
            "Programming/SweetyReader/Backend/src/main/java/data/";
    private String fileName = "Formated_BX-Books.csv";
    private File file = new File(filePath + fileName);
    private Book getBook(String[] tokens){
        Book book = new Book();
        book.setId(Long.parseLong(tokens[0]));
        book.setTitle(tokens[1]);
        book.setAuthor(tokens[2]);
        book.setPs(tokens[3]);
        book.setPm(tokens[4]);
        book.setPl(tokens[5]);
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
        BookRecommender1 br = new BookRecommender1();
        try {
            SearchEngine1 searchEngine = new SearchEngine1();
            ArrayList items  = br.getRecommendations(new Long(8));
            if (items == null){
                System.out.println("Recommendation fail");
                return;
            }
            ArrayList<Book> books = searchEngine.getBooks(items);
            System.out.println("Ok");
            System.out.println(books.get(0).getPm());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
