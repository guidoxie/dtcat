package cn.d41216.mario.cat;

import android.content.Context;

import java.io.File;
/**
 * Created by 断魂一叶 on 2017/11/13.
 * 获取图片
 */

public class FileUtil {

    public static File getSaveFile(Context context){

        File file = new File(context.getFilesDir(), "pic.jpg");
        return file;

    }
}
