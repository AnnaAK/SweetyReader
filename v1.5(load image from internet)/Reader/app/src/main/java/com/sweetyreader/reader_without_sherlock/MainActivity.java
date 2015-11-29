package com.sweetyreader.reader_without_sherlock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends FragmentActivity {
    public  SlidingMenu menu;
    public List<Book> books = new ArrayList();
    public ArrayAdapter<Book> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
        adapter = new BookAdapter(this);

        menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);

        menu.setBackgroundColor(0xFF333333);
        menu.setSelectorDrawable(R.drawable.sidemenu_items_background);

        String[] items = {"Моя библиотека","Что почитать?","Хочу прочитать", "Справка"};
        ((ListView) findViewById(R.id.sidemenu)).setAdapter(
                new ArrayAdapter<Object>(
                        this,
                        R.layout.sidemenu_item,
                        R.id.text,
                        items
                )
        );

        ((ListView) findViewById(R.id.sidemenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuToggle();
                changeFragment(position);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("Search")
                .setIcon(R.drawable.ic_search_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        menu.add("Menu")
                .setIcon(R.drawable.ic_menu_white_24dp)
                .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

        return true;
    }


     private void changeFragment(int position) {
        switch (position) {
            case 0:
                setContentView(R.layout.my_library);
                ((ListView) findViewById(R.id.my_library)).setAdapter(adapter);

                Book newBook = new Book(1, getResources().getString(R.string.author1),
                        getResources().getString(R.string.book1), 5.0, 4.8,"",
                        getResources().getString(R.string.cover1),"");
                adapter.add(newBook);
                Book newBook1 = new Book(2, getResources().getString(R.string.author2),
                        getResources().getString(R.string.book2), 4.0, 4.8,"",
                        getResources().getString(R.string.cover2),"");
                adapter.add(newBook1);
                Book newBook2 = new Book(3, getResources().getString(R.string.author3),
                        getResources().getString(R.string.book3), 5.0, 4.8,"",
                        getResources().getString(R.string.cover3),"");
                adapter.add(newBook2);
                Book newBook3 = new Book(4, getResources().getString(R.string.author4),
                        getResources().getString(R.string.book4), 5.0, 4.7,"",
                        getResources().getString(R.string.cover4),"");
                adapter.add(newBook3);
                Book newBook4 = new Book(5, getResources().getString(R.string.author5),
                        getResources().getString(R.string.book5), 5.0, 4.8,"",
                        getResources().getString(R.string.cover5),"");
                adapter.add(newBook4);
                adapter.add(new Book(2, getResources().getString(R.string.author1),
                        getResources().getString(R.string.author1), 5.0, 4.8,"",
                        getResources().getString(R.string.cover1),""));
                adapter.notifyDataSetChanged();
                break;
            case 1:
                adapter.clear();
                setContentView(R.layout.what_read);
                //What_read secondFragment = new What_read();
                //secondFragment.setArguments(getIntent().getExtras());
                //getSupportFragmentManager().beginTransaction()
                        //.replace(R.id.fragment_container, secondFragment).commit();
                break;
            case 2:
                adapter.clear();
                setContentView(R.layout.new_articles);
                Description_book threadFregment = new Description_book();
                threadFregment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, threadFregment).commit();
                break;
        }
    }

     public void menuToggle(){
        if(menu.isMenuShowing())
            menu.showContent();
        else
            menu.showMenu();
    }

    private static class Book {
        public final long id;
        public final String author;
        public final String description;
        public final Double user_rating;
        public final Double book_rating;
        public final String cover_small;
        public final String cover_medium;
        public final String cover_big;

        public Book(long id, String author, String description, Double user_rating, Double book_rating,
                    String cover_small, String cover_medium, String cover_big) {
            this.id = id;
            this.author = author;
            this.description = description;
            this.user_rating = user_rating;
            this.book_rating = book_rating;
            this.cover_small = cover_small;
            this.cover_medium = cover_medium;
            this.cover_big = cover_big;
        }
    }

    private class BookAdapter extends ArrayAdapter<Book> {

        public BookAdapter(Context context) {
            super(context, R.layout.my_library_item, books);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Book book = getItem(position);


            if (convertView == null) {
                convertView = LayoutInflater.from(getContext())
                        .inflate(R.layout.my_library_item, null);
            }
            ((TextView) convertView.findViewById(R.id.author))
                    .setText(book.author);
            ((TextView) convertView.findViewById(R.id.description))
                    .setText(book.description);
            ((TextView) convertView.findViewById(R.id.rating_your_button))
                    .setText(book.user_rating.toString());
            ((TextView) convertView.findViewById(R.id.rating_book_button))
                    .setText(book.book_rating.toString());
            new DownloadImageTask((ImageView) convertView.findViewById(R.id.cover_book))
                    .execute(book.cover_medium);
            return convertView;


        }
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

