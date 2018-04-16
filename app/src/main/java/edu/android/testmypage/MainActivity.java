package edu.android.testmypage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    public static final String TAG = "android.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "11111111111");

//        Contact contact = new Contact("KSJ","KIMMY","My pic");

//        List<Double> loc03 = new ArrayList<>();
//        loc03.add(555.66);
//        loc03.add(777.88);
//
//        UserData userData03 = new UserData
//                ("aaa111navercom", "Irene", "333", loc03, "제목3", "내용3", "시간3");
//        myRef.child("aaa111navercom").push().setValue(userData03);
    }
}
