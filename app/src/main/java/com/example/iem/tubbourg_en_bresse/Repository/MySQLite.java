package com.example.iem.tubbourg_en_bresse.Repository;

/**
 * Created by iem on 18/10/2016.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MySQLite extends SQLiteOpenHelper {

        private final Context mycontext;
        private static MySQLite sInstance;

        private static final int DATABASE_VERSION = 1;
        private String DATABASE_PATH;
        private static final String DATABASE_NAME = "sql/tub.sqlite";

        public static synchronized MySQLite getInstance(Context context) {
            if (sInstance == null) { sInstance = new MySQLite(context); }
            return sInstance;
        }

        private MySQLite(Context context) {

            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.mycontext=context;
            String filesDir = context.getFilesDir().getPath();
            DATABASE_PATH = filesDir.substring(0, filesDir.lastIndexOf("/")) + "/sql/";
            if (!checkdatabase()) {
                copydatabase();
            }
        }

        private boolean checkdatabase() {
            File dbfile = new File(DATABASE_PATH + DATABASE_NAME);
            return dbfile.exists();
        }

        private void copydatabase() {

            final String outFileName = DATABASE_PATH + DATABASE_NAME;

            InputStream myInput;
            try {
                myInput = mycontext.getAssets().open(DATABASE_NAME);

                File pathFile = new File(DATABASE_PATH);
                if(!pathFile.exists()) {
                    if(!pathFile.mkdirs()) {
                        Toast.makeText(mycontext, "Erreur : copydatabase(), pathFile.mkdirs()", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                OutputStream myOutput = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = myInput.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                myInput.close();
            }
            catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(mycontext, "Erreur : copydatabase()", Toast.LENGTH_SHORT).show();
            }

            try{
                SQLiteDatabase checkdb = SQLiteDatabase.openDatabase(DATABASE_PATH + DATABASE_NAME, null, SQLiteDatabase.OPEN_READWRITE);
                checkdb.setVersion(DATABASE_VERSION);
            }
            catch(SQLiteException e) {
                e.printStackTrace();
            }

        }

        @Override
        public void onCreate(SQLiteDatabase db) {}

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < newVersion){
                mycontext.deleteDatabase(DATABASE_NAME);
                copydatabase();
            }
        }

}