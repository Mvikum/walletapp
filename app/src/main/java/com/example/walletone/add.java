package com.example.walletone;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class add extends AppCompatActivity {

    Button submit_deposit;
    EditText description,amount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        description = findViewById(R.id.description);
        amount = findViewById(R.id.amount);

        submit_deposit = findViewById(R.id.submitDeposit);
        submit_deposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TransactionModel transactionModel = null;
                try {
                    transactionModel = new TransactionModel(-1,"Deposit",Double.parseDouble(amount.getText().toString()),description.getText().toString());
                    //Toast.makeText(add.this, transactionModel.toString(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(add.this,MainActivity.class);
                    startActivity(intent);

                }catch (Exception e){
                    Toast.makeText(add.this, "Enter valid amount...", Toast.LENGTH_SHORT).show();
                    //transactionModel = new TransactionModel(-1,"Deposit",0.0,null);
                    Intent intent = new Intent(add.this,MainActivity.class);
                    startActivity(intent);
                }

                MyDBHelper myDBHelper = new MyDBHelper(add.this);
                boolean success = myDBHelper.addOne(transactionModel);
                if(success == true){
                    Toast.makeText(add.this, "Successfully Inserted to DB..", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(add.this, "Error occured..", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

}