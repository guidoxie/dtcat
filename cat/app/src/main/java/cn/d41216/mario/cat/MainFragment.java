package cn.d41216.mario.cat;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.baidu.ocr.sdk.OCR;
import com.baidu.ocr.sdk.OnResultListener;
import com.baidu.ocr.sdk.exception.OCRError;
import com.baidu.ocr.sdk.model.AccessToken;
import com.baidu.ocr.ui.camera.CameraActivity;

import org.json.JSONObject;

/**
 * Created by 断魂一叶 on 2017/11/12.
 * 功能主界面
 */

public class MainFragment extends Fragment  {

    private ImageView bankcard; //银行卡识别
    private ImageView idCard; //身份证识别
    private ImageView car;  //车型识别
    private ImageView dish; //菜品识别
    private ImageView word; //一般文字识别
    private ImageView plant; //植物识别
    private ImageView animal; //动物识别
    private ImageView logo; //商标识别
    private ImageView star; //明星识别
    private ImageView plate; // 车牌识别

    private View tuView;

    private TuDetect tuDetect;
    private MOnTouch mOnTouch;
    private boolean hasGotToken = false;

    public JSONObject res;
    public String  strRes = "";

    public String title;

    private View loadingView;
    private AlertDialog.Builder alertDialog;

    private AlertDialog.Builder alertLoaging;
    private AlertDialog loading;

    public boolean up = false;
    public boolean down = false;


    public MainFragment(){

        tuDetect = new TuDetect(3);
        mOnTouch = new MOnTouch();

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        tuView = inflater.inflate(R.layout.layout_cat,container,false);
        loadingView = View.inflate(MainActivity.application,R.layout.layout_loading,null);

        alertDialog = new AlertDialog.Builder(getContext());
        alertLoaging = new AlertDialog.Builder(getContext());
        loading = alertLoaging.setTitle("识别中").setView(loadingView).create();

        bankcard = (ImageView) tuView.findViewById(R.id.bankcardImage);
        idCard = (ImageView) tuView.findViewById(R.id.idCardImage);
        car = (ImageView) tuView.findViewById(R.id.carImage);
        dish = (ImageView) tuView.findViewById(R.id.dishImage);
        word = (ImageView) tuView.findViewById(R.id.generalImage);
        plant = (ImageView)tuView.findViewById(R.id.plantImage);
        animal = (ImageView) tuView.findViewById(R.id.animalImage);
        logo = (ImageView) tuView.findViewById(R.id.logoImage);
        star = (ImageView) tuView.findViewById(R.id.starImage);
        plate = (ImageView) tuView.findViewById(R.id.plateImage);

        //设置触摸监听
        bankcard.setOnTouchListener(mOnTouch);
        idCard.setOnTouchListener(mOnTouch);
        car.setOnTouchListener(mOnTouch);
        dish.setOnTouchListener(mOnTouch);
        word.setOnTouchListener(mOnTouch);
        plant.setOnTouchListener(mOnTouch);
        animal.setOnTouchListener(mOnTouch);
        logo.setOnTouchListener(mOnTouch);
        star.setOnTouchListener(mOnTouch);
        plate.setOnTouchListener(mOnTouch);

        //获取token
        initAccessToken();


        return tuView;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==QequestCode.BANK_CARD && resultCode == Activity.RESULT_OK){
            WenDetect.recBankCard(FileUtil.getSaveFile(getContext()).getAbsolutePath(),
                    new WenDetect.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            strRes = result;
                            title = "银行卡识别";
                            handler.sendEmptyMessage(0);
                        }
                    });

            loadingView = View.inflate(MainActivity.application,R.layout.layout_loading,null);
            loading.setView(loadingView);
            loading.show();
        }else if(requestCode == QequestCode.WORDS && resultCode == Activity.RESULT_OK){

            WenDetect.recAccurateBasic(FileUtil.getSaveFile(getContext()).getAbsolutePath(),
                    new WenDetect.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            strRes = result;
                            title = "通用文字识别";
                            handler.sendEmptyMessage(0);
                        }
                    });

            loading.show();

        }else if(requestCode == QequestCode.LICENSE_PLATE && resultCode == Activity.RESULT_OK){
            WenDetect.recLicensePlate(FileUtil.getSaveFile(getContext()).getAbsolutePath(),
                    new WenDetect.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            strRes = result;
                            title = "车牌识别";
                            handler.sendEmptyMessage(0);
                        }
                    });

            loading.show();
        }else if(requestCode == QequestCode.ID_FRONT  && resultCode == Activity.RESULT_OK){
            WenDetect.recIDCard(FileUtil.getSaveFile(getContext()).getAbsolutePath(),
                    new WenDetect.ServiceListener() {
                        @Override
                        public void onResult(String result) {
                            strRes = result;
                            title = "身份证识别";
                            handler.sendEmptyMessage(0);
                        }
                    });

            loading.show();

        }else if(requestCode == QequestCode.CAR  && resultCode == Activity.RESULT_OK){

            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            new mThread(requestCode,filePath).start();

            loading.show();

        }else if(requestCode == QequestCode.DISH  && resultCode == Activity.RESULT_OK){

            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            new mThread(requestCode,filePath).start();

            loading.show();
        }else if(requestCode == QequestCode.PLANT  && resultCode == Activity.RESULT_OK){

            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            new mThread(requestCode,filePath).start();

            loading.show();
        }else if(requestCode == QequestCode.ANINMAL  && resultCode == Activity.RESULT_OK){

            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            new mThread(requestCode,filePath).start();

            loading.show();
        }else if(requestCode == QequestCode.LOGO  && resultCode == Activity.RESULT_OK){

            String filePath = FileUtil.getSaveFile(getContext()).getAbsolutePath();
            new mThread(requestCode,filePath).start();

            loading.show();
        }


    }



    private boolean checkTokenStatus() {
        if (!hasGotToken) {
            Toast.makeText(getContext(), "token还未成功获取", Toast.LENGTH_LONG).show();
        }
        return hasGotToken;
    }

    //获取token
    private void initAccessToken() {
        OCR.getInstance().initAccessToken(new OnResultListener<AccessToken>() {
            @Override
            public void onResult(AccessToken accessToken) {
                String token = accessToken.getAccessToken();
                hasGotToken = true;
            }

            @Override
            public void onError(OCRError error) {
                error.printStackTrace();
                title = "错误";
                strRes = error.getMessage();
                handler.sendEmptyMessage(0);
            }
        }, getContext());
    }

    //handler 操作ui
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            loading.dismiss();
            alertDialog.setTitle(title)
                    .setMessage(strRes)
                    .setPositiveButton("关闭", null)

                    .setNegativeButton("复制", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //把内容复制到剪切板
                            ClipboardManager cm = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                            ClipData clipData = ClipData.newPlainText(null, strRes);
                            cm.setPrimaryClip(clipData);
                        }
                    })
                    .show();



        }
    };


    //图像识别线程
    class mThread extends Thread{

        private int type;
        private String filePath;

        public mThread(int type, String filePath){
            this.type = type;
            this.filePath = filePath;
        }

        @Override
        public void run() {
            super.run();

            switch (type){

                case QequestCode.CAR:
                    title = "车型识别";
                    strRes = tuDetect.carDe(filePath).toString();
                    handler.sendEmptyMessage(0);
                    break;

                case QequestCode.DISH:
                    title = "菜品识别";
                    strRes = tuDetect.dishDe(filePath).toString();
                    handler.sendEmptyMessage(0);
                    break;
                case QequestCode.PLANT:
                    title = "植物识别";
                    strRes = tuDetect.plantDe(filePath).toString();
                    handler.sendEmptyMessage(0);
                    break;

                case QequestCode.ANINMAL:

                    title = "动物识别";
                    strRes = tuDetect.animalDe(filePath).toString();
                    handler.sendEmptyMessage(0);
                    break;

                case QequestCode.LOGO:
                    title = "商标识别";
                    strRes = tuDetect.logoSe(filePath).toString();
                    handler.sendEmptyMessage(0);
                    break;

            }
        }
    }



    //ImageView触摸处理
    class MOnTouch implements View.OnTouchListener{

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if(view == bankcard) {
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        bankcard.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        bankcard.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        bankcard.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    if (!checkTokenStatus()) {
                        return true;
                    }
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(MainActivity.application).getAbsolutePath());

                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_BANK_CARD);

                    startActivityForResult(intent, QequestCode.BANK_CARD);
                    up = false;
                    down = false;
                }
            }else if(view == idCard){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        idCard.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        idCard.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        idCard.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(MainActivity.application).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE, CameraActivity.CONTENT_TYPE_ID_CARD_FRONT);
                    startActivityForResult(intent, QequestCode.ID_FRONT);
                    up = false;
                    down = false;
                }

            }else if(view == car){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        car.setAlpha(0.7f);
                        down = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        car.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        car.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.CAR);

                    up = false;
                    down = false;
                }

            }else if(view == dish){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        dish.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        dish.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        dish.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.DISH);
                    up = false;
                    down = false;
                }

            }else if(view == word){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        word.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        word.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        word.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {

                    if (!checkTokenStatus()) {
                        return true;
                    }
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.WORDS);
                    up = false;
                    down = false;
                }

            }else if(view == plant){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        plant.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        plant.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        plant.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.PLANT);
                    up = false;
                    down = false;
                }

            }else if(view == animal){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        animal.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        animal.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        animal.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.ANINMAL);
                    up = false;
                    down = false;
                }

            }else if(view == logo){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        logo.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        logo.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        logo.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.LOGO);
                    up = false;
                    down = false;
                }

            }else if(view == star){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        star.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        star.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        star.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    Toast.makeText(getContext(), "时间有限，功能尚在开发中", Toast.LENGTH_SHORT).show();
                    up = false;
                    down = false;
                }

            }else if(view == plate){
                switch (motionEvent.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        plate.setAlpha(0.7f);
                        down = true;

                        break;
                    case MotionEvent.ACTION_UP:
                        plate.setAlpha(1.0f);
                        up = true;
                        break;

                    case MotionEvent.ACTION_CANCEL:
                        plate.setAlpha(1.0f);
                        up = false;
                        down = false;
                        break;
                }

                if (down && up) {
                    if (!checkTokenStatus()) {
                        return true;
                    }
                    Intent intent = new Intent(getContext(), CameraActivity.class);
                    intent.putExtra(CameraActivity.KEY_OUTPUT_FILE_PATH,
                            FileUtil.getSaveFile(getContext()).getAbsolutePath());
                    intent.putExtra(CameraActivity.KEY_CONTENT_TYPE,
                            CameraActivity.CONTENT_TYPE_GENERAL);
                    startActivityForResult(intent, QequestCode.LICENSE_PLATE);
                    up = false;
                    down = false;
                }

            }

            return true;
        }
    }



}
