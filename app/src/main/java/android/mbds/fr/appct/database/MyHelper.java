package android.mbds.fr.appct.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * classe permettant de creer et/ou mettre à jour la BD
 * = un proxy qui est execute rapidement, associe
 * */
public class MyHelper extends SQLiteOpenHelper {
    private static final String SQL_CREATE_ENTRIES_REGISTER =
            "CREATE TABLE " + Database.RegisterContract.FeedRegister.TABLE_NAME + " (" +
                    Database.RegisterContract.FeedRegister._ID + " INTEGER PRIMARY KEY," +
                    Database.RegisterContract.FeedRegister.COLUMN_NAME_USERNAME + " TEXT," +
                    Database.RegisterContract.FeedRegister.COLUMN_NAME_PASSWORD + " TEXT)";


    private static final String SQL_CREATE_ENTRIES_CONTACT =
            "CREATE TABLE " + Database.ContactContract.FeedContact.TABLE_NAME + " (" +
                    Database.ContactContract.FeedContact._ID + " INTEGER PRIMARY KEY," +
                    Database.ContactContract.FeedContact.COLUMN_NAME_USERNAME + " TEXT)";

    private static final String SQL_CREATE_ENTRIES_MESSAGE =
            "CREATE TABLE " + Database.MessageContract.FeedMessage.TABLE_NAME + " (" +
                    Database.MessageContract.FeedMessage._ID + " INTEGER PRIMARY KEY," +
                    Database.MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TXT + " TEXT," +
                    Database.MessageContract.FeedMessage.COLUMN_NAME_RECIPIENT_USERNAME + " TEXT," +
                    Database.MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TIME + " TEXT)";


    private static final String SQL_DELETE_ENTRIES_REGISTER =
            "DROP TABLE IF EXISTS " + Database.RegisterContract.FeedRegister.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_CONTACT =
            "DROP TABLE IF EXISTS " + Database.ContactContract.FeedContact.TABLE_NAME;

    private static final String SQL_DELETE_ENTRIES_MESSAGE =
            "DROP TABLE IF EXISTS " + Database.MessageContract.FeedMessage.TABLE_NAME;

    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MessagingDb.db";

    public MyHelper(Context context)
    {super(context, DATABASE_NAME, null, DATABASE_VERSION);}

    //************* methodes appellees automatiquement
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES_REGISTER);
        db.execSQL(SQL_CREATE_ENTRIES_CONTACT);
        db.execSQL(SQL_CREATE_ENTRIES_MESSAGE);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES_REGISTER);
        db.execSQL(SQL_DELETE_ENTRIES_CONTACT);
        db.execSQL(SQL_DELETE_ENTRIES_MESSAGE);
        onCreate(db);
    }

    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
