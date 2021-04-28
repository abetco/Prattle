package edu.illinois.cs465.prattle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class User_login extends AppCompatActivity implements View.OnClickListener {
    private EditText e_password;
    private EditText e_username;
    private TextView c_account;
    private Button login_button;

    private String username_override = "admin";
    private String password_override = "1234";
    private Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_login);

        e_password = findViewById(R.id.ed_password);
        e_username = findViewById(R.id.ed_username);
        c_account = findViewById(R.id.create_account);
        login_button = findViewById(R.id.login_btn);

        login_button.setOnClickListener(this);
        c_account.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                String inputName = e_username.getText().toString();
                String inputPassword = e_password.getText().toString();
                if(inputName.isEmpty() || inputPassword.isEmpty()){
                    Toast.makeText(User_login.this, "Content Empty", Toast.LENGTH_SHORT).show();
                } else {
                    flag = validate(inputName,inputPassword);
                    if(!flag){
                        Toast.makeText(User_login.this, "Incorrect", Toast.LENGTH_SHORT).show();
                    } else{
                        //send to main activity
                        Toast.makeText(User_login.this, "Correct", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.create_account:
                Intent intent = new Intent(User_login.this, Create_account.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
    private boolean validate (String username, String password){
        if (Create_account.newUser != null){
            if ((username.equals(username_override) && password.equals(password_override)) || (username.equals(Create_account.newUser.getName()) && password.equals(Create_account.newUser.getPassword()))) {
                return true;
            }
        }
        return false;
    }
}