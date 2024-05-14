package com.example.walletone;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String TRANSACTION_TABLE = "TRANSACTION_TABLE";
    public static final String COLUMN_T_TYPE = "T_TYPE";
    public static final String COLUMN_AMOUNT = "AMOUNT";
    public static final String COLUMN_DESCRIPTION = "DESCRIPTION";
    public static final String COLUMN_ID = "ID";



    private double total = 0; // Initialize total
    //private Object context;
// send total to mainactivity


    public MyDBHelper(@Nullable Context context) {
        super(context,"wallet.db",null,1);
    }

    public static void updateAmountDisplay(add add) {

    }

    //this called the first time a database is accessed. So create DB
    @Override
    public void onCreate(SQLiteDatabase db) {

        String createStatement = "CREATE TABLE " + TRANSACTION_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_T_TYPE + " TEXT, " + COLUMN_AMOUNT + " REAL NOT NULL, " + COLUMN_DESCRIPTION + " TEXT);";

        db.execSQL(createStatement);
    }

    //this called when db version is changed.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //write the data to table if success returns true
    public boolean addOne(TransactionModel transactionModel){

        //this refers to db helper class..
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_T_TYPE,transactionModel.getType());
        cv.put(COLUMN_AMOUNT,transactionModel.getAmount());
        cv.put(COLUMN_DESCRIPTION,transactionModel.getDescription());

        long insert = db.insert(TRANSACTION_TABLE, null, cv);

        if(insert == -1){
            return false;
        }else{
            return true;
        }

    }
//Read All data from the db
    public List<TransactionModel> readAll(){

        List<TransactionModel> returnList = new ArrayList<>();
        String readQuery = "SELECT * FROM "+ TRANSACTION_TABLE;
        total=0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(readQuery,null);
        //cursor got data
        if(cursor.moveToFirst()){
            do {
                int transactionID = cursor.getInt(0);
                String transactionType = cursor.getString(1);
                double amount = cursor.getDouble(2);
                String description = cursor.getString(3);
                total+=transactionID;
                TransactionModel newtransactionModel = new TransactionModel(transactionID,transactionType,amount,description);
                returnList.add(newtransactionModel);

            }while (cursor.moveToNext());

        }else{
            // null List defined so nothing gonna happen.
        }

        cursor.close();
        db.close();

        return  returnList;
    }

    //get the total
//    public double getTotal() {
//        SQLiteDatabase db = this.getReadableDatabase();
//        //Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_AMOUNT + ") FROM " + TRANSACTION_TABLE, null);
//        Cursor cursor = db.rawQuery("SELECT (SELECT COALESCE(SUM(" + COLUMN_AMOUNT + "),0) FROM "+ TRANSACTION_TABLE +" WHERE "+ COLUMN_T_TYPE + " = 'Deposit')-" +
//                        "(SELECT COALESCE(SUM(" + COLUMN_AMOUNT+"),0) FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_T_TYPE +" = 'Deposit') AS RemainingBalance;",null);
//        if (cursor.moveToFirst()) {
//            total = cursor.getDouble(0);
//        }
//        cursor.close();
//        db.close();
//        return total;
//    }
    public double getTotal() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT (SELECT COALESCE(SUM(" + COLUMN_AMOUNT + "), 0) FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_T_TYPE + " = 'Deposit') - " +
                "(SELECT COALESCE(SUM(" + COLUMN_AMOUNT + "), 0) FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_T_TYPE + " = 'Expense') AS RemainingBalance;", null);
        if (cursor.moveToFirst()) {
            total = cursor.getDouble(0);
        }
        cursor.close();
        db.close();
        return total;
    }

    public static void updateAmountDisplay(MainActivity activity) {
        MyDBHelper myDBHelper = new MyDBHelper(activity);
        double totalValue = myDBHelper.getTotal();
        activity.amountValue.setText(String.valueOf(totalValue));
    }

    public boolean deleteOne(TransactionModel transactionModel){
        //find transaction model in the database, if found delete it and return true
        // not found return false;
        SQLiteDatabase db = this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TRANSACTION_TABLE + " WHERE " + COLUMN_ID + " = " + transactionModel.getId();

        Cursor cursor = db.rawQuery(deleteQuery, null);
        if(cursor.moveToFirst()){
            return true;
        }else {
            return false;
        }

    }
    //add new delete


}
