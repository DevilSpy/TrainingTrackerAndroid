package fi.sami.trainingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import fi.sami.trainingtracker.model.User;

/**
 * Created by Sami on 20.10.2015.
 */
public class LoginActivity extends Activity {

    Button loginButton;
    EditText usernameText;
    EditText passwordText;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.buttonSignIn);
        usernameText = (EditText) findViewById(R.id.editTextUsername);
        passwordText = (EditText) findViewById(R.id.editTextPassword);

        List<User> users = User.listAll(User.class);

        if(users == null || users.isEmpty()) {
            User user = new User("Sami", "pwd");
            User user2 = new User("Keke", "keke");
            User user3 = new User("Jane", "jane");
            User user4 = new User("Make", "make");

            user.save();
            user2.save();
            user3.save();
            user4.save();
        }
    }

    public void login(View view) {
        String name = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        List<User> users = User.find(User.class, "name = ? and password = ?", name, password);

        String dbName = null;
        String dbPassword = null;

        if(users != null && !users.isEmpty() && users.get(0) != null) {
            dbName = users.get(0).getName();
            dbPassword = users.get(0).getPassword();
        }

        if(dbName != null && dbPassword != null) {
            if(name.equals(dbName) && password.equals(dbPassword)) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Wrong credentials", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
