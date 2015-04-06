package lv.edi.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.security.Timestamp;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Emil Syundyukov on 28/03/15.
 */
public class Databasehandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "FLexionAnglesDB";
    private static final String TABLE_FLEXION_ANGLES = "flexionAngles";

    // flexionAngles table columns names
    private static final String ID = "id";
    private static final String FLEXION_VALUE = "flexion_value";
    private static final String FLEXION_TIME = "flexion_time";

    private static final String[] COLUMNS = {ID,FLEXION_VALUE,FLEXION_TIME};


    public Databasehandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // SQL statement to create DB table
        String CREATE_FLEXION_ANGLES_TABLE = "CREATE TABLE flexionAngles ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "flexion_value INTEGER NOT_NULL, " +
                "flexion_time DEFAULT CURRENT_TIMESTAMP )";

        // create flexionAngles table
        sqLiteDatabase.execSQL(CREATE_FLEXION_ANGLES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        // Drop older DB version if it exists
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS flexionAngles");

        // Create new table's version
        this.onCreate(sqLiteDatabase);
    }

    // Method for pushing new Flexion values to the DB
    public void addFlexionStats(FlexionStats flexionStats) {
        // Logging
        Log.d("addBook", flexionStats.toString());
        // Get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        // Create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(FLEXION_VALUE, flexionStats.getFlexion_value());
       // values.put(FLEXION_TIME, currentTimeMillis());
        // Insert Data
        db.insert(TABLE_FLEXION_ANGLES,     // table
                  null,                     // nullColumnHack
                  values);                  // key/value -> keys = column names/values = column values
        db.close();
    }

    // Method for getting one certain Flexion Value,
    // by passing id to the method
    public FlexionStats getFlexionValue(int id) {
        // Getting reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        // Building Query
        Cursor cursor =
                db.query(TABLE_FLEXION_ANGLES,              // table
                         COLUMNS,                           // column names
                        " id = ?",                          // selections
                        new String[] {String.valueOf(id) }, // selection args
                        null,                               // group by
                        null,                               // having
                        null,                               // order by
                        null);                              // limit

        // If we got results get the first one
        FlexionStats flexionStats = new FlexionStats();
        if (cursor != null && cursor.moveToFirst())
        {
            // Building FlexionStats object

            flexionStats.setId(Integer.parseInt(cursor.getString(0)));
            flexionStats.setFlexion_value(Integer.parseInt(cursor.getString(1)));
            flexionStats.setFlexion_time(java.sql.Timestamp.valueOf(cursor.getString(2)));
            // Log
            Log.d("getFlexionStats(" + id + ")", flexionStats.toString());
            // Returning flexionStats object
        }
        return flexionStats;
    }

    // Method for calculating the Maximum Knee Flexion Value from the DB entries
    public int getMaxFlexionValue() {
        FlexionStats flexionStats = new FlexionStats();
        int maxValue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(flexion_value) FROM flexionAngles",null);
        if (cursor != null && cursor.moveToFirst()) {
            maxValue = Integer.parseInt(cursor.getString(0));
            // Log
            Log.d("getMaxFlexionValue", flexionStats.toString());
        }
        return maxValue;
    }

    // Method to calculate an Average Knee Flexion Value from the DB entries
    public double getAverageFlexionValue() {
        FlexionStats flexionStats = new FlexionStats();
        double averageFlexionValue = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT AVG(flexion_value) FROM flexionAngles",null);

        if (cursor != null && cursor.moveToFirst()) {
            averageFlexionValue = Double.parseDouble(cursor.getString(0));
            // Log
            Log.d("getAverageFlexionValue", flexionStats.toString());
        }
        return averageFlexionValue;
    }

    // Method for getting all Flexion Stats from the DB
    public List<FlexionStats> getAllFlexionStats() {
        List<FlexionStats> flexionStats = new LinkedList<FlexionStats>();

        // Building the Query
        String query = "SELECT * FROM " + TABLE_FLEXION_ANGLES;
        // Getting reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        // Go over each row, build object and add it to the list
        FlexionStats flexionStat = null;
        if (cursor.moveToFirst()) {
            do {
                flexionStat = new FlexionStats();
                flexionStat.setId(Integer.parseInt(cursor.getString(0)));
                flexionStat.setFlexion_value(cursor.getInt(1));
                flexionStat.setFlexion_time(java.sql.Timestamp.valueOf(cursor.getString(2)));

                // Add flexionStat to FlexionStats
                flexionStats.add(flexionStat);
            } while (cursor.moveToNext());
        }
        Log.d("getAllFlexionStats() ", flexionStats.toString());

        db.close();
        return flexionStats;
    }

    // Method for clearing the whole DB
    public void deleteFlexionStats() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FLEXION_ANGLES,null,null);
        db.close();
    }
}
