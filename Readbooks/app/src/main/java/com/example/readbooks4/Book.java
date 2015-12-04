package com.example.readbooks4;

/* General class Book
   made by Guzel Garifullina
   for Sweaty Reader project
*/
public class Book {
    public  long id;
    public  String title;
    public  String author;
    public  String description;
    public  Double user_rating;
    public  Double book_rating;
    public  String cover_small;
    public  String cover_medium;
    public  String cover_big;
    public String getfullTitle (){
        return title + " " + author;
    }

    public Book(long id, String  author, String title, String description, Double user_rating, Double book_rating,
                String cover_small, String cover_medium, String cover_big) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.user_rating = user_rating;
        this.book_rating = book_rating;
        this.cover_small = cover_small;
        this.cover_medium = cover_medium;
        this.cover_big = cover_big;
    }
    public Book(){
        id = 0;
        title = "";
        author = "";
        description = "";
        user_rating = 0.;
        book_rating = 0.;
        cover_small = "";
        cover_medium = "";
        cover_big = "";
    }
}