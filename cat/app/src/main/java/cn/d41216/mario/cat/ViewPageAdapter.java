package cn.d41216.mario.cat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 断魂一叶 on 2017/11/12.
 * 实现左右滑动效果
 */

public class ViewPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragments;
    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
        mFragments = new ArrayList<Fragment>();
        mFragments.add(new MainFragment());
        mFragments.add(new MiaoFragment());
       // mFragments.add(new MiaoFragment());

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
