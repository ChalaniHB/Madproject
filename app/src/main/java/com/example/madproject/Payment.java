package com.example.madproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {



    EditText txtName, txtNumber, txtCode, txtDate;
    Button btnSave,btnShow,btnUpadate,btnDelete;
    DatabaseReference dbRef;
    Customer cus;

    private void clearControls(){
        txtName.setText("");
        txtNumber.setText("");
        txtCode.setText("");
        txtDate.setText("");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        txtName = findViewById(R.id.editText3);
        txtNumber = findViewById(R.id.editText4);
        txtCode = findViewById(R.id.editText5);
        txtDate = findViewById(R.id.editText6);

        btnSave = findViewById(R.id.button2);
        btnShow = findViewById(R.id.button3);
        btnUpadate = findViewById(R.id.button4);
        btnDelete = findViewById(R.id.button5);
        cus = new Customer();



        dbRef = FirebaseDatabase.getInstance().getReference().child("Customer");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    if (TextUtils.isEmpty(txtName.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter  Name", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtNumber.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter  Number", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtCode.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter  Code", Toast.LENGTH_SHORT).show();
                    else if (TextUtils.isEmpty(txtDate.getText().toString()))
                        Toast.makeText(getApplicationContext(), "Please enter Date", Toast.LENGTH_SHORT).show();
                    else {
                        cus.setName(txtName.getText().toString().trim());
                        cus.setNumber(txtNumber.getText().toString().trim());
                        cus.setCode(txtCode.getText().toString().trim());
                        cus.setDate(txtDate.getText().toString().trim());

                        dbRef.child("cus1").setValue(cus);
                        Toast.makeText(getApplicationContext(), "Data Saved Successfully", Toast.LENGTH_SHORT).show();
                        clearControls();
                    }
                }




        });
        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference readRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("cus1");
                readRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChildren()) {
                            txtName.setText(dataSnapshot.child("name").getValue().toString());
                            txtNumber.setText(dataSnapshot.child("number").getValue().toString());
                            txtCode.setText(dataSnapshot.child("code").getValue().toString());
                            txtDate.setText(dataSnapshot.child("date").getValue().toString());
                        } else
                            Toast.makeText(getApplicationContext(), "No Source to Display", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }




                });


            }
        });
        btnUpadate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference upRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                upRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.hasChild("cus1")) {

                                cus.setName(txtName.getText().toString().trim());
                                cus.setNumber(txtNumber.getText().toString().trim());
                                cus.setCode(txtCode.getText().toString().trim());
                                cus.setDate(txtDate.getText().toString().trim());

                                dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("cus1");
                                dbRef.setValue(cus);
                                clearControls();
                                Toast.makeText(getApplicationContext(), "Data Updated Successfully", Toast.LENGTH_SHORT).show();


                        }
                        else
                            Toast.makeText(getApplicationContext(),"Data Updated Successfully",Toast.LENGTH_SHORT).show();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }

        });

        btnDelete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("Customer");
                delRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.hasChild("cus1")){
                            dbRef = FirebaseDatabase.getInstance().getReference().child("Customer").child("cus1");
                            dbRef.removeValue();
                            clearControls();
                            Toast.makeText(getApplicationContext(),"Data Deleted Successfully",Toast.LENGTH_SHORT).show();

                        }
                        else
                            Toast.makeText(getApplicationContext(),"No Source to Delete",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}
