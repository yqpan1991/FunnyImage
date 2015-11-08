package com.edus.apollo.funny.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioGroup;

import com.edus.apollo.funny.R;
import com.edus.apollo.funny.ui.EsApplication;
import com.edus.apollo.funny.ui.fragment.BaseFragment;
import com.edus.apollo.funny.ui.fragment.CategoryFragment;
import com.edus.apollo.funny.ui.fragment.MakePhotoFragment;
import com.edus.apollo.funny.ui.fragment.MarkFragment;
import com.edus.apollo.funny.ui.fragment.MyFragment;
import com.edus.apollo.funny.ui.fragment.PKFragment;
import com.edus.apollo.funny.ui.manager.NavigationBar;
import com.edus.apollo.funny.ui.manager.NavigationBar.NavigationItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends BaseFragmentActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final int INDEX_PK_FRAGMENT = 0;
    public static final int INDEX_CATEGORY_FRAGMENT = 1;
    public static final int INDEX_MAKE_FRAGMENT = 2;
    public static final int INDEX_MARK_FRAGMENT = 3;
    public static final int INDEX_MY_FRAGMENT = 4;
    private static final int FRAGMENT_COUNT = 5;

    private static final int DEFALUT_INDEX = INDEX_PK_FRAGMENT;


    private NavigationBar mNavBar;

    private ViewPager mViewPager;

    private FragmentManager mFragmentManager;

    private List<NavigationItem> mItemList = new ArrayList<>(FRAGMENT_COUNT);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        processData(intent, true);
    }

    private void processData(Intent intent, boolean isNewIntent) {

    }

    private void initView() {
        mNavBar = new NavigationBar(this, (RadioGroup) findViewById(R.id.rg_nav));
        mNavBar.setOnCheckedChangedListener(new NavigationBar.OnCheckedItemChangedListener() {
            @Override
            public void onCheckedItemChanged(NavigationBar.NavigationItem item) {
                MainActivity.this.onCheckedItemChanged(item);
            }
        });
        mViewPager = (ViewPager) findViewById(R.id.vp_content);
    }

    private void onCheckedItemChanged(NavigationBar.NavigationItem item) {
        int index = mItemList.indexOf(item);
        if(index >= 0){
            mViewPager.setCurrentItem(index);
        }
    }

    private void initData() {

        NavigationItem[] items = new NavigationItem[FRAGMENT_COUNT];
        items[INDEX_PK_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_doutu, R.mipmap.ic_launcher);
        items[INDEX_CATEGORY_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_fenlei, R.mipmap.ic_launcher);
        items[INDEX_MAKE_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_zhitu, R.mipmap.ic_launcher);
        items[INDEX_MARK_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_guanzhu, R.mipmap.ic_launcher);
        items[INDEX_MY_FRAGMENT] = new NavigationBar.NavigationItem(R.string.nav_wode, R.mipmap.ic_launcher);
        mItemList.addAll(Arrays.asList(items));


        for(int index = 0; index < items.length; index ++){
            mNavBar.addItem(items[index]);
        }
        mFragmentManager = getSupportFragmentManager();
        mViewPager.setOffscreenPageLimit(FRAGMENT_COUNT);
        mViewPager.setAdapter(new MyViewPagerAdapter(mFragmentManager));
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                mNavBar.setCurrentItemIndex(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mNavBar.setCurrentItemIndex(DEFALUT_INDEX);
    }

    private class MyViewPagerAdapter extends FragmentPagerAdapter {

        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            BaseFragment targetFragment = null;
            switch (position) {
                case INDEX_PK_FRAGMENT:
                    targetFragment = new PKFragment();
                    break;
                case INDEX_CATEGORY_FRAGMENT:
                    targetFragment = new CategoryFragment();
                    break;
                case INDEX_MAKE_FRAGMENT:
                    targetFragment = new MakePhotoFragment();
                    break;
                case INDEX_MARK_FRAGMENT:
                    targetFragment = new MarkFragment();
                    break;
                case INDEX_MY_FRAGMENT:
                    targetFragment = new MyFragment();
                    break;
            }
            return targetFragment;
        }

        @Override
        public int getCount() {
            return FRAGMENT_COUNT;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EsApplication.destroy();

    }
}
