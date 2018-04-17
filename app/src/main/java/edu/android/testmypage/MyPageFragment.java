package edu.android.testmypage;


        import android.content.Context;
        import android.content.Intent;
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

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.gson.Gson;

        import java.util.ArrayList;
        import java.util.List;

        import static edu.android.testmypage.MainActivity.TAG;
        import static edu.android.testmypage.R.drawable.android_2_3_ginerbread;


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


    public MyPageFragment() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");


//        dao = UserDataDao.getInstance();
        dao = UserDataDao.getInstance();
        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        dao.getAllUserData();
        final ImageView imageBtn = view.findViewById(R.id.imageView);
        imageBtn.setBackground(new ShapeDrawable(new OvalShape()));
        imageBtn.setClipToOutline(true);

        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                imageBtn.setImageResource(R.drawable.android_2_3_ginerbread);

                ((MainActivity)getActivity()).clickedProImgBotton();
//                startActivityForResult(Intent.createChooser(intent, "이미지를 선택하세요. "), 0);
//                ((MainActivity)getActivity()).onActivityResult();


                Log.i(TAG, "프래그먼트 clickedProImgBotton() 클릭 이후");
            }
        });


        essaySrc = new ArrayList<>();

        Log.i("ggg","데이터 받고 난 후");

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
        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("aaa111navercom").addChildEventListener(new ChildEventListener() {
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
                Log.i("ggg","데이터 받아옴");
                adapter.notifyDataSetChanged();
                userName = data.getName();
                TextView textView = getView().findViewById(R.id.textView);
                textView.setText(userName);
                Log.i(TAG, "유저이름1: " + userName);

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
