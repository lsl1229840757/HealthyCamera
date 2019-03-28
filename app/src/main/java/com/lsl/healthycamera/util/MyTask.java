package com.lsl.healthycamera.util;

import android.os.AsyncTask;
import android.widget.Toast;

import com.lsl.healthycamera.MyApplication;
import com.lsl.healthycamera.entity.MyDish;
import com.lsl.healthycamera.entity.MyDishDao;
import com.lsl.healthycamera.thirdActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MyTask extends AsyncTask<Object, Void, JSONObject> {

    private String picturePath;
    private String NOFOOD = "非菜";
    private thirdActivity context;

    // 耗费时间的线程在这里执行
    @Override
    protected JSONObject doInBackground(Object[] objects) {
        picturePath = (String)objects[0];
        context = (thirdActivity)objects[1];
        String string = Dish.dish(picturePath);
        try {
            return new JSONObject(string);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    // 这个就是网络请求完成之后在主线程中更新UI
    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        String name = null;
        Float calorie = 0f;
        try {
            name = jsonObject.getJSONArray("result").getJSONObject(0)
                    .getString("name");
            if(name!=null && !NOFOOD.equals(name)){
                calorie = Float.valueOf(""+jsonObject.getJSONArray("result").getJSONObject(0)
                        .getDouble("calorie"));
                MyDish myDish = new MyDish();
                myDish.setDate(new Date());
                myDish.setCalorie(calorie);
                myDish.setUsername(MyApplication.getUsername());
                myDish.setName(name);
                MyDishDao myDishDao = MyApplication.getInstance().getDaoSession().getMyDishDao();
                myDishDao.insert(myDish);
                context.freshChart(context.myDate);
                Toast.makeText(context, "该食物是"+name, Toast.LENGTH_LONG).show();
            }else{
                Toast.makeText(context, "鉴别失败！", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
