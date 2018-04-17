package edu.android.testmypage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    private Uri filePath;

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

    // https://m.blog.naver.com/cosmosjs/220975116725

    public void clickedProImgBotton() {
        Log.i(TAG, "메인 clickedProImgBotton()");
        Intent intent = new Intent();
        intent.setType("images/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요. "), 0);
        Log.i(TAG, "intent: " + intent.getData());
//        uploadFile();
    } // end clickedProImgBotton()

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i(TAG, "onActivityResult 호출");
        super.onActivityResult(requestCode, resultCode, data);
        // request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
        if(requestCode == 0 && resultCode == RESULT_OK) {
            filePath = data.getData();

            // Uri 파일을 Bitmap으로 만들어서 ImageView에 넣음
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                // TODO: 프래그먼트에 프로필 사진으로 setImage
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }



    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("업로드중...");
            progressDialog.show();

            // storage
            FirebaseStorage storage = FirebaseStorage.getInstance();


            // 파일명 지정
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMHH_mmss");
            Date curDate = new Date();
            String filename = dateFormat.format(curDate) + ".png";

            // storage 주소와 폴더, 파일명 지정
            StorageReference storageRef = storage.getReferenceFromUrl("gs://testmypage-f314b.appspot.com/").child("images/" + filename);
            storageRef.putFile(filePath)
                    // 성공했을 때
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "업로드 완료!", Toast.LENGTH_SHORT).show();

                }
            })
                    // 실패했을 때
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업로드 실패..", Toast.LENGTH_SHORT).show();
                        }
                    })
                    // 진행중..
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            @SuppressWarnings("VisibleForTests")
                                    double progress = (100 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        } else {
            Toast.makeText(getApplicationContext(), "파일을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
