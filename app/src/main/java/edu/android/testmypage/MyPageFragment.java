package edu.android.testmypage;


        import android.os.Bundle;
        import android.support.v4.app.Fragment;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.TextView;

        import com.google.firebase.database.ChildEventListener;
        import com.google.firebase.database.DataSnapshot;
        import com.google.firebase.database.DatabaseError;
        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;
        import com.google.gson.Gson;

        import java.util.List;

        import static edu.android.testmypage.MainActivity.TAG;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyPageFragment extends Fragment {

    private UserDataDao dao;
    private RecyclerView recycler;
    private List<UserData> essaySrc;


    public MyPageFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");


//        dao = UserDataDao.getInstance();

        View view = inflater.inflate(R.layout.fragment_my_page, container, false);
        essaySrc = dao.getInstance().getUserData();

//        TextView textView = view.findViewById(R.id.textView);
//        textView.setText(essaySrc.get(0).getName());

        recycler = view.findViewById(R.id.essay_list);
        recycler.setHasFixedSize(true);

        recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        EssayAdapter adapter = new EssayAdapter();
        recycler.setAdapter(adapter);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
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
            return size;

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
