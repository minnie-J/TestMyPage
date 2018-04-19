package edu.android.testmypage;


        import android.app.ProgressDialog;
        import android.content.Context;
        import android.graphics.Bitmap;
        import android.graphics.BitmapFactory;
        import android.graphics.drawable.ShapeDrawable;
        import android.graphics.drawable.shapes.OvalShape;
        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;
        import android.widget.TextView;

        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.firebase.storage.FileDownloadTask;
        import com.google.firebase.storage.FirebaseStorage;
        import com.google.firebase.storage.StorageReference;
        import com.google.gson.Gson;

        import java.io.File;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;

        import static edu.android.testmypage.MainActivity.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    private UserDataDao dao;
    private RecyclerView recycler;
    private List<UserData> essaySrc;
    public static EssayAdapter adapter;
    private DatabaseReference reference;
    private Gson gson = new Gson();
    private String userName;
    private String imgUri;
    private Bitmap bitmap;


    public MyPageFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//
    }

    private String getProImg() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://testmypage-f314b.appspot.com/");

        StorageReference pathReference = storageReference.child("images/curProImg.jpg");
//        pathReference.getDownloadUrl()

        try {
//            final File localFile = File.createTempFile("images", "jpg");
            final File localFile = File.createTempFile("curProImg", null, getContext().getCacheDir());
            pathReference.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    imgUri = localFile.getPath();
                    Log.i(TAG, "imgUri: " + imgUri);

                    bitmap = BitmapFactory.decodeFile(imgUri);
                    ImageView imageView = getView().findViewById(R.id.imageView);
                    imageView.setImageBitmap(bitmap);

                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgUri;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView");
        dao = UserDataDao.getInstance();
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        dao.getAllUserData();
        final ImageView imageBtn = view.findViewById(R.id.imageView);
        imageBtn.setBackground(new ShapeDrawable(new OvalShape()));
        imageBtn.setClipToOutline(true);

        getProImg();

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ((MainActivity)getActivity()).clickedProImgBotton();

                Log.i(TAG, "intent: ");

            }
        });

        essaySrc = new ArrayList<>();

        recycler = view.findViewById(R.id.essay_list);
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new EssayAdapter();
        recycler.setAdapter(adapter);

        return view;


    }


    @Override
    public void onStart() {
        super.onStart();
        if (essaySrc.size() == 0) {

            final ProgressDialog progressDialog = new ProgressDialog(getContext());
//        progressDialog.setTitle("테스트 중...");
            progressDialog.setMessage("로딩 중...");
            progressDialog.show();

            reference = FirebaseDatabase.getInstance().getReference();
            reference.child("aaa111navercom").addChildEventListener(new ChildEventListener() {

                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    Object obj = dataSnapshot.getValue();
                    String snapshot = String.valueOf(obj);
                    UserData data = gson.fromJson(snapshot, UserData.class);
                    essaySrc.add(data);
                    adapter.notifyDataSetChanged();
                    userName = data.getName();
                    TextView textView = getView().findViewById(R.id.textView);
                    textView.setText(userName);
                    Log.i(TAG, "유저이름1: " + userName);

                    progressDialog.dismiss();
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

//        progressDialog.dismiss();
    }

    class EssayAdapter extends RecyclerView.Adapter<EssayAdapter.ViewHolder> {
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.i(TAG, "onCreateViewHolder");
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View itemView = inflater.inflate(R.layout.essay, parent, false);
            ViewHolder holder = new ViewHolder(itemView);

            return holder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            Log.i(TAG, "onBindViewHolder");
            UserData userData = essaySrc.get(position);
            holder.textTitle.setText(userData.getTitle());
            holder.textLocation.setText(userData.getLocInEssay().toString());
            holder.textDate.setText(userData.getRecDate());
            // TODO: onClick - 클릭했을 때 글 하나 읽어오기
        }

        @Override
        public int getItemCount() {
            int size = essaySrc.size();
            Log.i(TAG, "essaySrc.size(): " + size);
            return essaySrc.size();

        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView textTitle;
            TextView textLocation;
            TextView textDate;

            public ViewHolder(View itemView) {
                super(itemView);
                Log.i(TAG, "ViewHolder");
                textTitle = itemView.findViewById(R.id.textTitle);
                textLocation = itemView.findViewById(R.id.textLocation);
                textDate = itemView.findViewById(R.id.textDate);
            }
        }
    }

}
