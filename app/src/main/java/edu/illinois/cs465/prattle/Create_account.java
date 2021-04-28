package edu.illinois.cs465.prattle;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class Create_account extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private Spinner weekday_from;
    private Spinner weekday_to;
    private Spinner weekend_from;
    private Spinner weekend_to;
    private TextView t_create;
    private EditText username;
    private EditText password;
    private EditText birthday;
    private EditText email;

    public static User_data newUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        weekday_from = findViewById(R.id.weekday_from);
        weekday_to = findViewById(R.id.weekday_to);
        weekend_from = findViewById(R.id.weekend_from);
        weekend_to = findViewById(R.id.weekend_to);
        t_create = findViewById(R.id.create);
        username = findViewById(R.id.create_username);
        password = findViewById(R.id.create_password);
        birthday = findViewById(R.id.create_birthday);
        email = findViewById(R.id.create_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Create Account");

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.times, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        weekday_from.setAdapter(adapter);
        weekday_to.setAdapter(adapter);
        weekend_from.setAdapter(adapter);
        weekend_to.setAdapter(adapter);
        //on select listeners
        weekend_to.setOnItemSelectedListener(this);
        weekday_from.setOnItemSelectedListener(this);
        weekend_to.setOnItemSelectedListener(this);
        weekend_from.setOnItemSelectedListener(this);

        t_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input_username = username.getText().toString();
                String input_password = password.getText().toString();
                String input_birthday = birthday.getText().toString();
                String input_email = email.getText().toString();
                if (validate(input_username,input_password,input_birthday,input_email)){
                    newUser = new User_data(input_username,input_password,input_birthday,input_email);
                    //startActivity(new Intent(Create_account.this, MainActivity.class));

                }

            }
        });

    }
    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String item = parent.getItemAtPosition(pos).toString();
        Toast.makeText(Create_account.this, item, Toast.LENGTH_SHORT).show();
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }
    private boolean validate (String username, String password,String b_date,String email){
        if (username.isEmpty() || password.isEmpty() || b_date.isEmpty() || email.isEmpty()) {
            Toast.makeText(Create_account.this,"A field is empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}