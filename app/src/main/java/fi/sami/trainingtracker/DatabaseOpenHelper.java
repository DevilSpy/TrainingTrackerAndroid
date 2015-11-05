package fi.sami.trainingtracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Sami on 26.10.2015.
 */
public class DatabaseOpenHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "training_tracker_db";
    private final String DATABASE_TABLE = "user";
    private final String NAME = "name";
    private final String PASSWORD = "password";

    public DatabaseOpenHelper(Context context) {
        // Context, db name, optional cursor factory, db version
        super(context, DATABASE_NAME, null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // create new table
        db.execSQL("CREATE TABLE " + DATABASE_TABLE + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " TEXT, " + PASSWORD + " TEXT);");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO " + DATABASE_TABLE + "(" + NAME + ", " + PASSWORD + ")");
        stringBuilder.append(" VALUES ");
        stringBuilder.append("('Sami', 'sala'), ");
        stringBuilder.append("('S', 's'), ");
        stringBuilder.append("('Marko', 'marko'), ");
        stringBuilder.append("('Pena', 'pena');");

        db.execSQL(stringBuilder.toString());

        db.execSQL("CREATE TABLE location(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, latitude TEXT, longitude TEXT);");
        db.execSQL("INSERT INTO location(name, latitude, longitude) VALUES ('Savelan sali', '62.241480', '25.709366'), ('Mattilanniemi', '62.229521', '25.731198');");

        // create sample data
//        ContentValues values = new ContentValues();
//        values.put(NAME, "Sami");
//        values.put(PASSWORD, "sala");

        // insert data to database, name of table, "Nullcolumnhack", values
//        db.insert(DATABASE_TABLE, null, values);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
        onCreate(db);
    }



}
