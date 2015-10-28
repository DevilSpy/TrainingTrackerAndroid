package fi.sami.trainingtracker;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.Toast;

/**
 * Created by Sami on 20.10.2015.
 */
public class LoginActivity extends Activity {
    private final String DATABASE_TABLE = "user";
    private final int DELETED_ID = 0;
    private SQLiteDatabase db;
    private Cursor cursor;

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

        // get db instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();

    }

    public void login(View view) {
        String name = usernameText.getText().toString();
        String password = passwordText.getText().toString();

        String dbName = getName(name);
        String dbPassword = getPassword(name);

        if(dbName != null) {
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


    public String getName(String name) {
        cursor = db.rawQuery("SELECT name FROM " + DATABASE_TABLE + " WHERE name='" + name + "'", null);

        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return null;
        }


    }

    public String getPassword(String name) {
        cursor = db.rawQuery("SELECT password FROM " + DATABASE_TABLE + " WHERE name='" + name + "'", null);

        if(cursor.moveToFirst()) {
            return cursor.getString(0);
        } else {
            return null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }
}
