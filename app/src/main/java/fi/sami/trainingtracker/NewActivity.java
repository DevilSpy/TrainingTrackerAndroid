package fi.sami.trainingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Sami on 20.10.2015.
 */
public class NewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
    }

    public void closeActivity(View view){
        Intent intent = new Intent();
        EditText editText = (EditText) findViewById(R.id.editText);
        intent.putExtra("text", editText.getText().toString());
        setResult(RESULT_OK,intent);
        finish();
    }
}
