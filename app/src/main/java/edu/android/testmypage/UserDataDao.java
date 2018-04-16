package edu.android.testmypage;

import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;


import static edu.android.testmypage.MainActivity.TAG;

public class UserDataDao {

    private List<UserData> userData = new ArrayList<>();
    private static UserDataDao instance = null;
    private Gson gson = new Gson();

    private UserDataDao() {
        getAllUserData();
    }

    private void getAllUserData() {
        FirebaseDatabase.getInstance().getReference().child("aaa111navercom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Object obj = dataSnapshot.getValue();
//                UserData data = dataSnapshot.getValue(UserData.class);
                String snapshot = String.valueOf(obj);
                Log.i(TAG, "dataSnapshot.getValue(): " + dataSnapshot.getValue());
                UserData data = gson.fromJson(snapshot, UserData.class);
                Log.i(TAG, "String.valueOf(obj): " + snapshot);
                userData.add(data);
                Log.i(TAG, "data.getName(): " + data.getName());
                int size = userData.size();
                Log.i(TAG, "userData.size(): " + size);

//                UserData data = dataSnapshot.getValue(UserData.class);
//                Log.i(TAG, "data.getName: " + data.getName());
//
//                String userId = data.getUserId();
//                String userName = data.getName();
//                List<Double> location = data.getLocInEssay();
//                String title = data.getTitle();
//                String photoUrl = data.getPhotoInEssay();
//                String content = data.getContent();
//                String date = data.getRecDate();
//                UserData data1 = new UserData
//                        (userId, userName, photoUrl, location, title, content, date);
//
//                userData.add(data1);

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
        return userData;
    }

    public static UserDataDao getInstance() {
        if (instance == null) {
            instance = new UserDataDao();

        }
        return instance;
    }

    public int getUserDataSize(){
        return userData.size();
    }
}
