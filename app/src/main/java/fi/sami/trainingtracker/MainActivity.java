package fi.sami.trainingtracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends Activity {
    private final int NEW_ACTIVITY = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startNewActivity(View view){
        Intent intent = new Intent(this, NewActivity.class);
        startActivity(intent);
    }

    public void startNewActivityForResult(View view){
        Intent intent = new Intent(this, NewActivity.class);
        startActivityForResult(intent, NEW_ACTIVITY);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == NEW_ACTIVITY && resultCode == Activity.RESULT_OK){
            Log.d("NEW ACTIVITY", "HERE");
            Bundle extras = data.getExtras();
            String text = extras.getString("text");
            TextView resultTextView = (TextView) findViewById(R.id.resultTextView);
            resultTextView.setText(text);

        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
