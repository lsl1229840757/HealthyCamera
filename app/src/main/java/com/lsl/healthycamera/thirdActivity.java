package com.lsl.healthycamera;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.lsl.healthycamera.entity.MyDish;
import com.lsl.healthycamera.entity.MyDishDao;
import com.lsl.healthycamera.entity.User;
import com.lsl.healthycamera.entity.UserDao;
import com.lsl.healthycamera.util.DateUtil;
import com.lsl.healthycamera.util.FileUtils;
import com.lsl.healthycamera.util.MyTask;

import org.w3c.dom.Text;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class thirdActivity extends AppCompatActivity {
    MyDishDao myDishDao = MyApplication.getInstance().getDaoSession().getMyDishDao();
    UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();
    User user = userDao.queryBuilder().where(UserDao.Properties.Username
                                                                  .eq(MyApplication.getUsername()
                                                                   )).unique();
    float totalCalorie = 0f;
    LineChart mLineChart;
    public Date myDate = new Date();
    List<MyDish> myDishes = new ArrayList<>();
    public TextView CalorieView;
    public TextView timeView;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        //初始化组件
        CalorieView  = findViewById(R.id.textView13);
        timeView =  findViewById(R.id.textView8);
        TextView unameView = findViewById(R.id.textView10);
        TextView weightView = findViewById(R.id.textView12);
        TextView heightView = findViewById(R.id.textView11);
        myDate = new Date(); //初始化时间
        //初始化图表
        mLineChart = findViewById(R.id.lineChart);
        /*myDishes = myDishDao.queryBuilder().where(MyDishDao.Properties.Username
                .eq(MyApplication.getUsername()), MyDishDao.Properties.Date.between(DateUtil.formatDate(myDate)
                ,DateUtil.formatNextDay(myDate))).list();
        for (MyDish myDish:myDishes){
            float calorie = myDish.getCalorie();
            totalCalorie+=calorie;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(myDate);
        timeView.setText("选择时间为："+dateStr);*/
        freshChart(myDate);
        //设置不变信息
        unameView.setText(user.getName()+"您好");
        heightView.setText("身高："+user.getHeight()+"cm");
        weightView.setText("身高："+user.getWeight()+"kg");


        //CalorieView.setText("今天总共摄入:"+totalCalorie+"KJ卡路里");
        Button button2 = findViewById(R.id.button3);
        button2.setOnClickListener(e->{
            // 打开系统的图片选择器
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            intent.putExtra("crop", true);
            intent.putExtra("return-data", true);
            startActivityForResult(intent, 0);
        });
        Button button = findViewById(R.id.button2);
        button.setOnClickListener(e->{
            showCamera();
        });
        Button button1 = findViewById(R.id.button4);
        final Calendar calendar = Calendar.getInstance();
        button1.setOnClickListener(e->{
            new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    myDate = calendar.getTime();
                    freshChart(myDate);
                }
            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH)
                    , calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

        //刷新图表
        freshChart(myDate);
    }
    private void showCamera() {
        // 跳转到系统照相机
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, 1);
    }

    public void freshChart(Date day){
        totalCalorie=0;
        myDishes = myDishDao.queryBuilder().where(MyDishDao.Properties.Username
                .eq(MyApplication.getUsername()), MyDishDao.Properties.Date.between(DateUtil.formatDate(day)
                ,DateUtil.formatNextDay(day))).list();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(day);
        timeView.setText("选择时间为："+dateStr);
        if(myDishes.size()==0){
            mLineChart.clear();
            mLineChart.invalidate();
            mLineChart.animateY(2000);
            CalorieView.setText("今天总共摄入:"+totalCalorie+"KJ卡路里");
            return;
        }
        XAxis xAxis = mLineChart.getXAxis();
        //设置X轴的文字在底部
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //设置描述文字
        ArrayList<Entry> values = new ArrayList<>();
        int i = 0;
        for (MyDish myDish:myDishes){
            String name = myDish.getName();
            float calorie = myDish.getCalorie();
            totalCalorie+=calorie;
            values.add(new Entry(i, calorie, name));
            i++;
        }
        CalorieView.setText("今天总共摄入:"+totalCalorie+"KJ卡路里");
        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        LineDataSet set1 = new LineDataSet(values, "燃烧我的卡路里！");
        set1.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        set1.setColor(Color.WHITE);
        set1.setCircleColor(Color.parseColor("#AAFFFFFF"));
        set1.setHighLightColor(Color.WHITE);//设置点击交点后显示交高亮线的颜色
        set1.setHighlightEnabled(true);//是否使用点击高亮线
        set1.setDrawCircles(true);
        set1.setValueTextColor(Color.WHITE);
        set1.setLineWidth(1f);//设置线宽
        set1.setCircleRadius(2f);//设置焦点圆心的大小
        set1.setHighlightLineWidth(0.5f);//设置点击交点后显示高亮线宽
        set1.enableDashedHighlightLine(10f, 5f, 0f);//点击后的高亮线的显示样式
        set1.setValueTextSize(12f);//设置显示值的文字大小
        set1.setDrawFilled(true);//设置使用 范围背景填充

        mLineChart.getXAxis().setValueFormatter(new IAxisValueFormatter() {
            @Override
            public String getFormattedValue(float value, AxisBase axis) {
                int index = (int) value;
                if (index < 0 || index >= values.size()) {
                    return "";
                } else {
                    return values.get(index).getData()+"";
                }
            }

        });

        set1.setDrawValues(false);
        //构建一个类型为LineDataSet的ArrayList 用来存放所有 y的LineDataSet   他是构建最终加入LineChart数据集所需要的参数
        LineData lineData = new LineData(set1);
        //将数据插入
        mLineChart.setData(lineData);
        mLineChart.invalidate();
        mLineChart.animateY(2000);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 0) {
            // 打开相机
          /*  Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = this.getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            //picturePath就是图片在储存卡所在的位置
            String picturePath = cursor.getString(columnIndex);*/
            if (resultCode==RESULT_OK) {
                Uri dataUri = data.getData();
                String picturePath = FileUtils.getFilePathByUri(this, dataUri);
                new MyTask().execute(picturePath, this);
            }
        }else if(requestCode == 1){
            // 开始拍照
            if(resultCode==RESULT_OK){
                // 选择成功
                String picturePath = saveCameraImage(data);
                new MyTask().execute(picturePath, this);
            }
        }
    }



    /** 保存相机的图片 **/
    private String saveCameraImage(Intent data) {
        // 检查sd card是否存在
        if (!Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return null;
        }
        // 为图片命名啊
        String name = new DateFormat().format("yyyymmdd",
                Calendar.getInstance(Locale.CHINA))
                + ".jpg";
        Bitmap bmp = (Bitmap) data.getExtras().get("data");// 解析返回的图片成bitmap
        // 保存文件c
        FileOutputStream fos = null;
        String dirPath = Environment.getExternalStorageDirectory().getPath();
        String fileName = dirPath+File.separator+ name;// 保存路径
        try {// 写入SD card
            fos = new FileOutputStream(fileName);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.flush();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
