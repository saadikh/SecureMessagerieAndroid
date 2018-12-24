package android.mbds.fr.appct.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.mbds.fr.appct.models.ChatMessage;
import android.mbds.fr.appct.models.Contact;
import android.mbds.fr.appct.models.Person;
import android.provider.BaseColumns;

import java.util.ArrayList;
import java.util.Date;

/*
 *
 * classe representant le DAO
 * */
public class Database {
    private static Database idatabase;
    private MyHelper mDbHelper;

    private Database(MyHelper sqlp){
        mDbHelper=sqlp;
    }

    public static Database getIstance(Context ctxt){

        if(idatabase==null) {
            idatabase =new Database(new MyHelper(ctxt));
        }
        return idatabase;

    }

    private Database() {
    }

    public final class RegisterContract {
        private RegisterContract() {}
        public class FeedRegister implements BaseColumns
        {
            public static final String TABLE_NAME = "Register";
            public static final String COLUMN_NAME_USERNAME = "Username";
            public static final String COLUMN_NAME_PASSWORD = "Password";
        }
    }

    public final class ContactContract {
        private ContactContract() {}
        public class FeedContact implements BaseColumns
        {
            public static final String TABLE_NAME = "Contact";
            public static final String COLUMN_NAME_USERNAME = "Username";

        }
    }

    public final class MessageContract {
        private MessageContract() {}
        public class FeedMessage implements BaseColumns
        {
            public static final String TABLE_NAME = "Message";
            public static final String COLUMN_NAME_MESSAGE_TXT = "MessageTxt";
            public static final String COLUMN_NAME_RECIPIENT_USERNAME = "RecipientUsername";
            public static final String COLUMN_NAME_MESSAGE_TIME = "MessageTime";

        }
    }

    // *************************** for Contact *************************


    public void addContact(String username){

// Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase(); //creation reelle de la BD
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ContactContract.FeedContact.COLUMN_NAME_USERNAME, username);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ContactContract.FeedContact.TABLE_NAME, null, values);
    }


    public ArrayList<Contact> readContact(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase(); //lecture de la BD
        String[] projection = {
                BaseColumns._ID,
                ContactContract.FeedContact.COLUMN_NAME_USERNAME
        };

        String selection = "";
        String[] selectionArgs = null;

        String sortOrder = ContactContract.FeedContact.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                ContactContract.FeedContact.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList contacts = new ArrayList<Contact>();
        while(cursor.moveToNext())
        {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(ContactContract.FeedContact._ID));
            String username = cursor.getString(cursor.getColumnIndex(ContactContract.FeedContact.COLUMN_NAME_USERNAME));
            contacts.add(new Contact(username));
        }
        cursor.close();

        return contacts;
    }



    // **********************  for message ***********************************

    public void addMessage(String messageTxt, String recipientUsername){

// Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase(); //creation reelle de la BD
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TXT, messageTxt);
        values.put(MessageContract.FeedMessage.COLUMN_NAME_RECIPIENT_USERNAME, recipientUsername);

        long messageTime = new Date().getTime();
        values.put(MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TIME, messageTime);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(MessageContract.FeedMessage.TABLE_NAME, null, values);
    }

    public ArrayList<ChatMessage> readMessage(){
        SQLiteDatabase db = mDbHelper.getReadableDatabase(); //lecture de la BD
        String[] projection = {
                BaseColumns._ID,
                MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TXT,
                MessageContract.FeedMessage.COLUMN_NAME_RECIPIENT_USERNAME,
                MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TIME,

        };


        String selection = "";
        String[] selectionArgs = null;

        String sortOrder =
                MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TIME + " DESC";

        Cursor cursor = db.query(
                MessageContract.FeedMessage.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList messages = new ArrayList<ChatMessage>();
        while(cursor.moveToNext())
        {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(MessageContract.FeedMessage._ID));
            String messageTxt = cursor.getString(cursor.getColumnIndex(MessageContract.FeedMessage.COLUMN_NAME_MESSAGE_TXT));
            String recipientUsername = cursor.getString(cursor.getColumnIndex(MessageContract.FeedMessage.COLUMN_NAME_RECIPIENT_USERNAME));
            messages.add(new ChatMessage(messageTxt,recipientUsername));
        }
        cursor.close();

        return messages;
    }


    // *********************-** for register *******************************************

    public void addPerson(String username,String password)
    {
// Gets the data repository in write mode
        SQLiteDatabase db = mDbHelper.getWritableDatabase(); //creation reelle de la BD
// Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(RegisterContract.FeedRegister.COLUMN_NAME_USERNAME, username);
        values.put(RegisterContract.FeedRegister.COLUMN_NAME_PASSWORD, password);
// Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(RegisterContract.FeedRegister.TABLE_NAME, null, values);
    }

    public ArrayList<Person> readPerson()
    {
        SQLiteDatabase db = mDbHelper.getReadableDatabase(); //lecture de la BD
        String[] projection = {
                BaseColumns._ID,
                RegisterContract.FeedRegister.COLUMN_NAME_USERNAME,
                RegisterContract.FeedRegister.COLUMN_NAME_PASSWORD
        };


        String selection = "";
        String[] selectionArgs = null;

        String sortOrder =
                RegisterContract.FeedRegister.COLUMN_NAME_USERNAME + " DESC";

        Cursor cursor = db.query(
                RegisterContract.FeedRegister.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );

        ArrayList persons = new ArrayList<Person>();
        while(cursor.moveToNext())
        {
            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(RegisterContract.FeedRegister._ID));
            String username = cursor.getString(cursor.getColumnIndex(RegisterContract.FeedRegister.COLUMN_NAME_USERNAME));
            String password = cursor.getString(cursor.getColumnIndex(RegisterContract.FeedRegister.COLUMN_NAME_PASSWORD));
            persons.add(new Person(username,password));
        }
        cursor.close();

        return persons;
    }
}
