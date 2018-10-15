package com.example.twolee.chatbot.ReviewWrite;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.twolee.chatbot.MainActivity;
import com.example.twolee.chatbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StateUpdateActivity extends AppCompatActivity{
    @BindView(R.id.state_toolbar) Toolbar state_toolbar;
    @BindView(R.id.state_message) EditText state_message;
    @BindView(R.id.stateTextLength) TextView stateTextLength;
    @BindView(R.id.updateOkButton) Button updateOkButton;

    // database
    private DatabaseReference myRef = FirebaseDatabase.getInstance().getReference();
    @NonNull private String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_state_update);

        // bind view
        ButterKnife.bind(this);
        
        getData();
        setToolbar();
        setListener();
    }

    public void getData(){
        //get data
        myRef.child("users").child(userUid).child("state").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                state_message.setText(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("failure",databaseError.toException());
            }
        });
    }

    public void setToolbar(){
        //tool bar
        setSupportActionBar(state_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        state_toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StateUpdateActivity.this, MainActivity.class));
                finish();
            }
        });
    }

    public void setListener(){
        // listener
        // TODO: 15/10/2018 입력 리스너..
        state_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 입력되는 텍스트에 변화가 있을 때
                //stateTextLength.setText(message.length());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 입력이 끝났을 때
            }

            @Override
            public void afterTextChanged(Editable s) {
                // 입력 하기 전에
                //state_message.setText(message);
                //stateTextLength.setText(message.length());
            }
        });

        updateOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child("users").child(userUid).child("state").setValue(state_message.getText().toString());
                finish();
            }
        });
    }

}
