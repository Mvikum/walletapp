package com.example.walletone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Expense extends AppCompatActivity {

    EditText amount,description;
    Button submitExpense;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        setElementId();
        //here listerner
        submitExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TransactionModel transactionModel = null;
                try {
                    transactionModel = new TransactionModel(-1,"Expense",Double.parseDouble(amount.getText().toString()),description.getText().toString());
                    //Toast.makeText(Expense.this, transactionModel.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Expense.this,MainActivity.class);
                    startActivity(intent);


                }catch (Exception e){
                    Toast.makeText(Expense.this, "Enter valid amount...", Toast.LENGTH_SHORT).show();
                    //transactionModel = new TransactionModel(-1,"Expense",0.0,null);
                    Intent intent = new Intent(Expense.this,MainActivity.class);
                    startActivity(intent);
                }

                MyDBHelper myDBHelper = new MyDBHelper(Expense.this);
                boolean success = myDBHelper.addOne(transactionModel);
                if(success == true){
                    Toast.makeText(Expense.this, "Successfully Inserted to DB..", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Expense.this, "Error occured..", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
    void setElementId(){
        amount = findViewById(R.id.amount);
        description = findViewById(R.id.description);
        submitExpense= findViewById(R.id.submitExpense);
    }


}