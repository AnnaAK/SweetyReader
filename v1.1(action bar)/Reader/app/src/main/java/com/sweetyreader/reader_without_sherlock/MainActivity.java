package com.sweetyreader.reader_without_sherlock;


import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.support.v4.app.FragmentActivity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_articles);

        ActionBar actionBar = getActionBar();
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.CYAN));


        SlidingMenu menu = new SlidingMenu(this);
        menu.setMode(SlidingMenu.RIGHT);
        menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
        menu.setFadeDegree(0.35f);
        menu.attachToActivity(this, SlidingMenu.SLIDING_WINDOW);
        menu.setMenu(R.layout.sidemenu);
        menu.setBehindWidthRes(R.dimen.slidingmenu_behind_width);


        String[] items = {"Моя библиотека","Что почитать?","Хочу прочитать", "Справка"};
        ((ListView) findViewById(R.id.sidemenu)).setAdapter(
                new ArrayAdapter<Object>(
                        this,
                        R.layout.sidemenu_item,
                        R.id.text,
                        items
                )
        );

       /** ListView listView = (ListView)findViewById(R.id.what_read_list);

        final String[] books = {
                "Мастер и Маргарита","Мор, ученик смерти"
                getResources().getString(R.string.athour1),
                getResources().getString(R.string.athour2),
                getResources().getString(R.string.athour3),
                getResources().getString(R.string.athour4),
        };
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, books);
        listView.setAdapter(adapter);*/


        ((ListView) findViewById(R.id.sidemenu)).setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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
                Your_library firstFragment = new Your_library();
                firstFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, firstFragment).commit();
                break;
            case 1:
                What_read secondFragment = new What_read();
                secondFragment.setArguments(getIntent().getExtras());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, secondFragment).commit();
                break;
        }
    }
}

