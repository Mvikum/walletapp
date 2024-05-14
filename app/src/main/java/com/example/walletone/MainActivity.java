package com.example.walletone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView amountValue;
    Button btnDeposit,btnExpense,btnshow;
    ListView listView;
    private double totalValue;
    ArrayAdapter transactionArrayAdapter;
    MyDBHelper mydb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLayout values
        getLayoutValues();
        listView = findViewById(R.id.listView);
        mydb = new MyDBHelper(MainActivity.this);

        btnDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, add.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "Here is the deposit", Toast.LENGTH_SHORT).show();


            }
        });

        btnExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Expense.class);
                startActivity(intent);
                //Toast.makeText(MainActivity.this, "Here is the expense", Toast.LENGTH_SHORT).show();

            }
        });
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);
                transactionArrayAdapter = new ArrayAdapter<TransactionModel>(MainActivity.this, android.R.layout.simple_list_item_1,mydb.readAll());
                listView.setAdapter(transactionArrayAdapter);
                //List<TransactionModel> allData = myDBHelper.readAll();
                // read all the data and returns a toast
                //Toast.makeText(MainActivity.this, allData.toString(), Toast.LENGTH_SHORT).show();

                //transactionArrayAdapter = new ArrayAdapter<TransactionModel>(MainActivity.this, android.R.layout.simple_list_item_1,allData);
                listView.setAdapter(transactionArrayAdapter);
                //update
                MyDBHelper.updateAmountDisplay(MainActivity.this);
            }
        });
        //set Total to mainlayout
        mydb = new MyDBHelper(MainActivity.this);
        double total = mydb.getTotal();
        amountValue.setText(String.valueOf(total));
        //display all the data
//        transactionArrayAdapter = new ArrayAdapter<TransactionModel>(MainActivity.this, android.R.layout.simple_list_item_1,mydb.readAll());
//        listView.setAdapter(transactionArrayAdapter);

        //delete selected item
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                TransactionModel clickedTransaction = (TransactionModel) parent.getItemAtPosition(position);
//                mydb.deleteOne(clickedTransaction);
//                Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
//            }
//        });
        //deleteClickItem();
        deleteOnLongPress();

    }

    public ListView getListView() {
        return listView;
    }

    //get layout values
    void getLayoutValues(){
        amountValue = findViewById(R.id.amountValue);
        btnDeposit = findViewById(R.id.btn_deposit);
        btnExpense = findViewById(R.id.btn_expense);
        btnshow = findViewById(R.id.btnshow);

        //String val = String.valueOf(amountValue);
        //Toast.makeText(this, val, Toast.LENGTH_SHORT).show();
    }

    //update the amount
//    void updateAmountDisplay() {
//        MyDBHelper myDBHelper = new MyDBHelper(MainActivity.this);
//        double totalValue = myDBHelper.getTotal();
//        amountValue.setText(String.valueOf(totalValue));
//    }
    void deleteClickItem(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TransactionModel clickedTransaction = (TransactionModel) parent.getItemAtPosition(position);
                mydb.deleteOne(clickedTransaction);
                Toast.makeText(MainActivity.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void deleteOnLongPress(){
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // Perform actions when an item is long-clicked
                TransactionModel clickedTransaction = (TransactionModel) parent.getItemAtPosition(position);

                // Display confirmation dialog for deleting the item
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setMessage("Are you sure you want to delete this item?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Delete the item
                                mydb.deleteOne(clickedTransaction);
                                Toast.makeText(MainActivity.this, "Item deleted successfully", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        });
                // Create the AlertDialog object and return it
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                // Return true to indicate that the long click event has been consumed
                return true;
            }
        });
    }

}