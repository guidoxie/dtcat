package cn.d41216.mario.cat;



import com.baidu.aip.imageclassify.AipImageClassify;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.HashMap;

/**
 * Created by 断魂一叶 on 2017/11/16.
 * 图像识别
 */

public class TuDetect {

    private final String TU_APP_ID = "10463497"; //App ID
    private final String TU_API_KEY = "GOYRl3i4FGdTBjeaMRbS5SOW"; //Api Key
    private final String TU_SECRET_KEY = "RkReC1wp0Fdmj6GyczHkdx4fEGk2qZr0"; //Secret Key
    private HashMap<String, String> options;
    AipImageClassify client;

    public TuDetect(int count){

        options = new HashMap<String, String>();
        options.put("top_num", Integer.toString(count));

        client = new AipImageClassify(TU_APP_ID,TU_API_KEY,TU_SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
    }

    //菜品识别
    public String dishDe(String image){

        JSONObject js = client.dishDetect(image, options);
        JSONArray temp = null;
        double probability = 0;
        String calorie = null;
        String name = null;
        String res = "";
        String mark = "%";

        try {
            temp = js.getJSONArray("result");

            for(int i=0;i<temp.length();i++){
                if (temp.getJSONObject(i).getBoolean("has_calorie")){
                    calorie = temp.getJSONObject(i).getString("calorie");
                }else {
                    calorie = "0";
                }
                probability = temp.getJSONObject(i).getDouble("probability")*100;
                name = temp.getJSONObject(i).getString("name");
                res = String.format("%s\n%s(%.1f%s)\n卡路里：%s(每100g)\n",
                        res,
                        name,
                        probability,
                        mark,
                        calorie
                );
            }

        }catch (Exception e){
            //
            res = e.getMessage();
        }

        return res;

    }


    //车型识别
    /*
    public JSONObject carDe(String image){

        JSONObject res = client.carDetect(image, options);

        return res;
    }
    */
    public String carDe(String image){

        JSONObject js = client.carDetect(image, options);
        JSONArray temp = null;
        double score = 0;
        String name = null;
        String res = "";
        String mark = "%";

        try {
                temp = js.getJSONArray("result");

                for(int i=0;i<temp.length();i++){

                    score = temp.getJSONObject(i).getDouble("score")*100;
                    name = temp.getJSONObject(i).getString("name");
                    res = String.format("%s\n\t\t\t%s\t\t(%.1f%s)",
                            res,
                            name,
                            score,
                            mark
                    );
                }

        }catch (Exception e){
                //
                res = e.getMessage();
        }

        return res;
    }

    //商标识别
    public String logoSe(String image){

        JSONObject js = client.logoSearch(image, options);
        JSONArray temp = null;
        double probability = 0;
        String name = null;
        String res = "";
        String mark = "%";

        try {
            temp = js.getJSONArray("result");

            for(int i=0;i<temp.length();i++){

                probability = temp.getJSONObject(i).getDouble("probability")*100;
                name = temp.getJSONObject(i).getString("name");
                res = String.format("%s\n\t\t\t%s\t\t(%.1f%s)",
                        res,
                        name,
                        probability,
                        mark
                );
            }

        }catch (Exception e){
            //
            res = e.getMessage();
        }

        return res;
    }


    //动物识别
    public String animalDe(String image) {

        JSONObject js = client.animalDetect(image,options);
        JSONArray temp = null;
        double score = 0;
        String name = null;
        String res = "";
        String mark = "%";

        try {
            temp = js.getJSONArray("result");

            for(int i=0;i<temp.length();i++){

                score = temp.getJSONObject(i).getDouble("score")*100;
                name = temp.getJSONObject(i).getString("name");
                res = String.format("%s\n\t\t\t%s\t\t(%.1f%s)",
                        res,
                        name,
                        score,
                        mark
                );
            }

        }catch (Exception e){
            //
            res = e.getMessage();
        }
        return res;

    }


    //植物识别
    public String plantDe(String image) {

        JSONObject js = client.plantDetect(image, options);
        JSONArray temp = null;
        double score = 0;
        String name = null;
        String res = "";
        String mark = "%";

        try {
            temp = js.getJSONArray("result");

            for(int i=0;i<temp.length();i++){

                score = temp.getJSONObject(i).getDouble("score")*100;
                name = temp.getJSONObject(i).getString("name");
                res = String.format("%s\n\t\t\t%s\t\t(%.1f%s)",
                        res,
                        name,
                        score,
                        mark
                );
            }

        }catch (Exception e){
            //
            res = e.getMessage();
        }
        return res;

    }

}
