package main.java.Basic;

/* Book class
   made by Guzel Garifullina
   for Sweaty Reader project
*/
public class Book {
    private Long id = new Long(0);
    public Long getId (){return id;}
    public void setId (Long newId) {id = newId;}

    private String title = "";
    public String getTitle (){return title;}
    public void setTitle (String newTitle) {title = newTitle;}

    private String author = "";
    public String getAuthor (){return author;}
    public void setAuthor (String newAuthor) {author = newAuthor;}

    private String ps = "";
    public String getPs (){return ps;}
    public void setPs (String newPic) {ps = newPic;}

    private String pm = "";
    public String getPm (){return pm;}
    public void setPm (String newPic) {pm = newPic;}

    private String pl = "";
    public String getPl (){return pl;}
    public void setPl (String newPic) {pl = newPic;}
}

