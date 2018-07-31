package com.lamba.quizboxadmin;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class start extends AppCompatActivity {

    ViewPagerAdapter adapter;

    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().hide();

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabslayout);

        adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.AddFragment(new AddQuiz(),"add Quiz");
        adapter.AddFragment(new NewPost(),"New Post");

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);


    }
}
