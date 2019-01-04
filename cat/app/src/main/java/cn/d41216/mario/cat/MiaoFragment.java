package cn.d41216.mario.cat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 断魂一叶 on 2017/11/12.
 * 关于此软件说明的fragment
 */

public class MiaoFragment extends Fragment {

    public MiaoFragment(){


    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_about,container,false);

        return view;
    }


}
