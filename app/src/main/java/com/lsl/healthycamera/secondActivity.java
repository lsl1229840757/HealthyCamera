package com.lsl.healthycamera;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lsl.healthycamera.entity.User;
import com.lsl.healthycamera.entity.UserDao;

public class secondActivity extends AppCompatActivity {
    UserDao userDao = MyApplication.getInstance().getDaoSession().getUserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Button button = findViewById(R.id.button);
        EditText usernameE = findViewById(R.id.editText3);
        EditText passwordE = findViewById(R.id.editText4);
        EditText heightE = findViewById(R.id.editText5);
        EditText wightE = findViewById(R.id.editText6);
        EditText nameE = findViewById(R.id.editText8);
        button.setOnClickListener(e->{
            String username = usernameE.getText().toString();
            String password = passwordE.getText().toString();
            String name = nameE.getText().toString();
            String heightS = heightE.getText().toString();
            String weightS = wightE.getText().toString();
            Double height = null;
            Double weight = null;
            try {
                height = Double.valueOf(heightS);
                weight = Double.valueOf(weightS);
            }catch (Exception e1){
                Toast.makeText(secondActivity.this, "输入数据格式不对",  Toast.LENGTH_LONG)
                        .show();
            }
            if (!username.equals("")&&!password.equals("")&&!name.equals("")){
                User user = new User();
                user.setHeight(height);
                user.setWeight(weight);
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);
                // 保存
                userDao.insert(user);
                Toast.makeText(secondActivity.this, "注册成功！",  Toast.LENGTH_LONG)
                        .show();
            }else{
                Toast.makeText(secondActivity.this, "输入数据格式不对！",  Toast.LENGTH_LONG)
                        .show();
            }
        });
    }
}
