package com.lsl.healthycamera;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.lsl.healthycamera.entity.User;
import com.lsl.healthycamera.entity.UserDao;

public class MainActivity extends AppCompatActivity {

    UserDao  userDao = MyApplication.getInstance().getDaoSession().getUserDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button loginButtn = findViewById(R.id.login);
        Button register = findViewById(R.id.register);
        EditText editTextUsername = findViewById(R.id.editText);
        EditText editTextPassword = findViewById(R.id.editText2);
        // 注册登录事件
        loginButtn.setOnClickListener(
            e->{
                //查询用户
                User user = userDao.queryBuilder().where(UserDao.Properties.Username
                        .eq(editTextUsername.getText().toString())).unique();
                if(user==null){
                    Toast.makeText(MainActivity.this, "不存在该用户",  Toast.LENGTH_LONG)
                            .show();
                }else if(!user.getPassword().equals(editTextPassword.getText().toString())){
                    Toast.makeText(MainActivity.this, "密码错误",  Toast.LENGTH_LONG)
                            .show();
                }else{
                    Intent intent = new Intent(this, thirdActivity.class);
                    startActivity(intent);
                }
            }
        );
        // 注册登录事件,在第二界面
        register.setOnClickListener(e->{
//            String username = editTextUsername.getText().toString();
////            String password = editTextPassword.getText().toString();
////            if ((!username.equals(""))&&(!password.equals(""))){
////
////            }else{
////                Toast.makeText(MainActivity.this, "请重新输入数据！",  Toast.LENGTH_LONG)
////                        .show();
////            }
            Intent intent = new Intent(this, secondActivity.class);
            startActivity(intent);
        });
    }
}
