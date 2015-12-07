package com.example.readbooks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.support.v4.app.FragmentActivity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import org.json.JSONException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends FragmentActivity {
    public SlidingMenu menu;
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

        MultiDex.install(this);
    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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

    private void showRecomendation(){
        new Thread(new Runnable() {
                public void run() {
                    ServerConnector sc = new ServerConnector();
                    ArrayList<Book> bookRates = new ArrayList<Book>();
                    Book b1 = new Book();
                    b1.id = new Long(330332775);
                    b1.user_rating = 6.0;

                    /*Book b2 = new Book();
                    b2.id = new Long(330332775);
                    b2.user_rating = 6.0;*/

                    Book b3 = new Book();
                    b3.id = new Long(380973839);
                    b3.user_rating = 10.0;

                    Book b4 = new Book();
                    b4.id = new Long(307001164);
                    b4.user_rating = 9.0;

                    Book b5 = new Book();
                    b5.id = new Long(140620338);
                    b5.user_rating = 8.0;

                    Book b6 = new Book();
                    b6.id = new Long(99771519);
                    b6.user_rating = 10.0;

                    Book b7 = new Book();
                    b7.id = new Long(345325818);
                    b7.user_rating = 8.0;

                    Book b8 = new Book();
                    b8.id = new Long(451187903);
                    b8.user_rating = 9.0;

                    Book b9 = new Book();
                    b9.id = new Long(671032658);
                    b9.user_rating = 10.0;

                    Book b10 = new Book();
                    b10.id = new Long(345339703);
                    b10.user_rating = 10.0;

                    Book b11 = new Book();
                    b11.id = new Long(61020710);
                    b11.user_rating = 9.0;

                    /*Book b12 = new Book();
                    b12.id = new Long(330332775);
                    b12.user_rating = 6.0;*/

                    /*Book b13 = new Book();
                    b13.id = new Long(446605239);
                    b13.user_rating = 9.0;*/

                    Book b14 = new Book();
                    b14.id = new Long(575041714);
                    b14.user_rating = 10.0;

                    /*Book b15 = new Book();
                    b15.id = new Long(671027387);
                    b15.user_rating = 8.0;*/

                    bookRates.add(b1);
                    //bookRates.add(b2);
                    bookRates.add(b3);
                    bookRates.add(b4);
                    bookRates.add(b5);
                    bookRates.add(b6);
                    bookRates.add(b7);
                    bookRates.add(b8);
                    bookRates.add(b9);
                    bookRates.add(b10);
                    bookRates.add(b11);
                    //bookRates.add(b12);
                    //bookRates.add(b13);
                    bookRates.add(b14);
                   // bookRates.add(b15);

                    Long id = new Long(0);
                    try {
                        ArrayList<Book> recommendedBooks1 = sc.getRecommendations(id, bookRates);
                        while (recommendedBooks1 == null) {
                            recommendedBooks1 = sc.getRecommendations(id, bookRates);
                        }
                        final ArrayList<Book>  recommendedBooks = recommendedBooks1 ;
                        runOnUiThread(new Runnable() {
                            public void run() {
                                adapter.clear();
                                setContentView(R.layout.my_library);
                                ((ListView) findViewById(R.id.my_library)).setAdapter(adapter);
                                for (Book book:recommendedBooks){
                                    adapter.add(book);
                                    adapter.notifyDataSetChanged();
                                }
                            }
                        });
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
    }

    private void changeFragment(int position) {
        switch (position) {
            case 0:
                adapter.clear();
                setContentView(R.layout.my_library);
                ((ListView) findViewById(R.id.my_library)).setAdapter(adapter);
                /**Book newBook5 = new Book(671027360, getResources().getString(R.string.author6),
                        getResources().getString(R.string.title6),
                        getResources().getString(R.string.book6), 4.0, 4.6,"",
                        getResources().getString(R.string.cover6),"");
                adapter.add(newBook5);*/
                Book newBook6 = new Book(330332775, getResources().getString(R.string.author7),
                        getResources().getString(R.string.title7),
                        getResources().getString(R.string.book7), 3.0, 3.9,"",
                        getResources().getString(R.string.cover7),"");
                adapter.add(newBook6);
                /**Book newBook7 = new Book(671027387, getResources().getString(R.string.author8),
                        getResources().getString(R.string.title8),
                        getResources().getString(R.string.book8), 5.0, 4.8,"",
                        getResources().getString(R.string.cover8),"");
                adapter.add(newBook7);*/
                Book newBook8 = new Book(380973839, getResources().getString(R.string.author9),
                        getResources().getString(R.string.title9),
                        getResources().getString(R.string.book9), 5.0, 4.9,"",
                        getResources().getString(R.string.cover9),"");
                adapter.add(newBook8);
                /**Book newBook9 = new Book(743424425, getResources().getString(R.string.author10),
                        getResources().getString(R.string.title10),
                        getResources().getString(R.string.book10), 5.0, 4.8,"",
                        getResources().getString(R.string.cover10),"");
                adapter.add(newBook9);*/
                Book newBook10 = new Book(307001164, getResources().getString(R.string.author11),
                        getResources().getString(R.string.title11),
                        getResources().getString(R.string.book11), 5.0, 4.8,"",
                        getResources().getString(R.string.cover11),"");
                adapter.add(newBook10);
                Book newBook11 = new Book(140620338, getResources().getString(R.string.author12),
                        getResources().getString(R.string.title12),
                        getResources().getString(R.string.book12), 5.0, 4.8,"",
                        getResources().getString(R.string.cover12),"");
                adapter.add(newBook11);
                Book newBook12 = new Book(99771519, getResources().getString(R.string.author13),
                        getResources().getString(R.string.title13),
                        getResources().getString(R.string.book13), 5.0, 4.8,"",
                        getResources().getString(R.string.cover13),"");
                adapter.add(newBook12);
                /**Book newBook13 = new Book(425050750, getResources().getString(R.string.author14),
                        getResources().getString(R.string.title14),
                        getResources().getString(R.string.book14), 5.0, 4.8,"",
                        getResources().getString(R.string.cover14),"");
                adapter.add(newBook13);*/
                Book newBook14 = new Book(345325818, getResources().getString(R.string.author15),
                        getResources().getString(R.string.title15),
                        getResources().getString(R.string.book15), 5.0, 4.8,"",
                        getResources().getString(R.string.cover15),"");
                adapter.add(newBook14);
                /**Book newBook15 = new Book(950547001, getResources().getString(R.string.author16),
                        getResources().getString(R.string.title16),
                        getResources().getString(R.string.book16), 5.0, 4.8,"",
                        getResources().getString(R.string.cover16),"");
                adapter.add(newBook15);*/
                Book newBook16 = new Book(451187903, getResources().getString(R.string.author17),
                        getResources().getString(R.string.title17),
                        getResources().getString(R.string.book17), 5.0, 4.8,"",
                        getResources().getString(R.string.cover17),"");
                adapter.add(newBook16);
                Book newBook17 = new Book(671032658, getResources().getString(R.string.author18),
                        getResources().getString(R.string.title18),
                        getResources().getString(R.string.book18), 5.0, 4.8,"",
                        getResources().getString(R.string.cover18),"");
                adapter.add(newBook17);
                Book newBook18 = new Book(345339703, getResources().getString(R.string.author19),
                        getResources().getString(R.string.title19),
                        getResources().getString(R.string.book19), 5.0, 4.8,"",
                        getResources().getString(R.string.cover19),"");
                adapter.add(newBook18);
               /** Book newBook19 = new Book(5, getResources().getString(R.string.author20),
                        getResources().getString(R.string.title20),
                        getResources().getString(R.string.book20), 5.0, 4.8,"",
                        getResources().getString(R.string.cover20),"");
                adapter.add(newBook19);*/
                /**Book newBook20 = new Book(5, getResources().getString(R.string.author21),
                        getResources().getString(R.string.title21),
                        getResources().getString(R.string.book21), 5.0, 4.8,"",
                        getResources().getString(R.string.cover21),"");
                adapter.add(newBook20);*/
                Book newBook21 = new Book(61020710, getResources().getString(R.string.author22),
                        getResources().getString(R.string.title22),
                        getResources().getString(R.string.book22), 5.0, 4.8,"",
                        getResources().getString(R.string.cover22),"");
                adapter.add(newBook21);
               /** Book newBook22 = new Book(446605239, getResources().getString(R.string.author23),
                        getResources().getString(R.string.title23),
                        getResources().getString(R.string.book23), 5.0, 4.8,"",
                        getResources().getString(R.string.cover23),"");
                adapter.add(newBook22);*/
                Book newBook23 = new Book(575041714, getResources().getString(R.string.author24),
                        getResources().getString(R.string.title24),
                        getResources().getString(R.string.book24), 5.0, 4.8,"",
                        getResources().getString(R.string.cover24),"");
                adapter.add(newBook23);
                adapter.notifyDataSetChanged();
                break;
            case 1:
                adapter.clear();
                setContentView(R.layout.what_read);
                Button getRecomend = (Button) findViewById(R.id.get_recomend);
                getRecomend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showRecomendation();
                    }
                });
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
            ((TextView) convertView.findViewById(R.id.title))
                    .setText(book.title);
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

