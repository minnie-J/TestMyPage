package edu.android.testmypage;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


import static edu.android.testmypage.MainActivity.TAG;

public class UserDataDao {

    private Gson gson = new Gson();
    private static UserDataDao instance = null;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();
    private List<UserData> essaySrc = new ArrayList<>();

    private UserDataDao() {
        getAllUserData();
    }

    private void getAllUserData() {
        myRef.child("aaa111navercom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object obj = dataSnapshot.getValue();
                Log.i(TAG, "dataSnapshot.getValue(): " + dataSnapshot.getValue());
//                UserData data = dataSnapshot.getValue(UserData.class);
                String snapshot = String.valueOf(obj);
                UserData data = gson.fromJson(snapshot, UserData.class);
                essaySrc.add(data);
                Log.i(TAG, "data.getName(): " + data.getTitle());
                int size = essaySrc.size();
                Log.i(TAG, "essaySrc.size(): " + size);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public List<UserData> getUserData() {
        return essaySrc;
    }

    public static UserDataDao getInstance() {
        if (instance == null) {
            instance = new UserDataDao();

        }
        return instance;
    }

}
