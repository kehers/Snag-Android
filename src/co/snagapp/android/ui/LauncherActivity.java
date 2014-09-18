package co.snagapp.android.ui;

import java.util.ArrayList;
import java.util.List;

import co.snagapp.android.R;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

public class LauncherActivity extends FragmentActivity {
	
    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager);

        // Todo:
        // Check preference here and decide if to show launch screen or home
        
        mPager = (ViewPager) findViewById(R.id.viewpager);
        mPagerAdapter = new LauncherPagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            super.onBackPressed();
        } else {
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public void startBtnClicked(View v) {
    	Intent i = new Intent(this, HomeActivity.class);
    	i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(i);
    }
    
    private class LauncherPagerAdapter extends FragmentStatePagerAdapter {
    	
    	private List<Fragment> mFragments;
    	
        public LauncherPagerAdapter(FragmentManager fm) {
            super(fm);
            this.mFragments = new ArrayList<Fragment>();
            mFragments.add(new LauncherFragmentA());
            mFragments.add(new LauncherFragmentB());
		}

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }
}