package edu.android.testmypage;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    private final int GALLERY_CODE=1112;
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
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setData(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);

//        Intent intent = new Intent();
//        intent.setType("images/*");
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요. "), 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                case GALLERY_CODE:

                    filePath = data.getData();

                    FragmentManager fm = getSupportFragmentManager();
                    Fragment fragment =fm.findFragmentById(R.id.fragment);
                    ImageView imageView = fragment.getActivity().findViewById(R.id.imageView);
                    Glide.with(this).load(filePath).into(imageView);

                    uploadFile();

//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }



//                    String filePathToString = filePath.toString();
//                    MyPageFragment fragment = new MyPageFragment();
//                    Bundle bundle = new Bundle(1);
//                    bundle.putString("imgUri", filePathToString);
//                    fragment.setArguments(bundle);

//                    try {
//                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


//                    sendPicture(data.getData()); //갤러리에서 가져오기
                    break;
//                case CAMERA_CODE:
//                    getPictureForPhoto(); //카메라에서 가져오기
//                    break;

                default:
                    break;
            }

        }

//        // request코드가 0이고 OK를 선택했고 data에 뭔가가 들어 있다면
//        if(requestCode == 0 && resultCode == RESULT_OK) {
//            filePath = data.getData();
//
//            // Uri 파일을 Bitmap으로 만들어서 ImageView에 넣음
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
//                // TODO: 프래그먼트에 프로필 사진으로 setImage
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
    }

    private void uploadFile() {
        if (filePath != null) {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("프로필 사진 업데이트 중...");
            progressDialog.show();

            // storage
            FirebaseStorage storage = FirebaseStorage.getInstance();

            // 파일명 지정
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMHH_mmss");
//            Date curDate = new Date();
//            String filename = dateFormat.format(curDate) + ".png";
            String filename = "curProImg.jpg";

            // storage 주소와 폴더, 파일명 지정
            StorageReference storageRef = storage.getReferenceFromUrl("gs://testmypage-f314b.appspot.com/").child("images/" + filename);
            storageRef.putFile(filePath)

                    // 성공했을 때
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(getApplicationContext(), "업데이트 완료!", Toast.LENGTH_SHORT).show();
                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    String s = downloadUri.toString();

                }
            })
                    // 실패했을 때
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "업데이트 실패..", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getApplicationContext(), "사진을 먼저 선택하세요.", Toast.LENGTH_SHORT).show();
        }
    }
}
