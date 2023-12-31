package com.example.plasticfreeriver;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link feedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class feedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ArrayList<Integer> arRegistered,arCleaned;
    int arRegTotal,arCleanedTotal;
    ArrayList<post> ar;
    ArrayList<postWithoutstatus> arWithoutstatus;
    RecyclerView recyclerView;
    FirebaseDatabase database;


    public feedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment feedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static feedFragment newInstance(String param1, String param2) {
        feedFragment fragment = new feedFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        arRegistered=new ArrayList<>();
        arCleaned=new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_feed, container, false);
        database=FirebaseDatabase.getInstance();
        ar=new ArrayList<>();
        recyclerView=view.findViewById(R.id.rv_feed);
        MyAdapter postadapter=new MyAdapter(getContext(), ar,arWithoutstatus);
        LinearLayoutManager layoutManager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(postadapter);

        database.getReference().child("posts").addValueEventListener(new ValueEventListener() {
            @SuppressLint("SuspiciousIndentation")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
               ar.clear();
                arRegistered.clear();
                arCleaned.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {


                    try {
                        post p = dataSnapshot.getValue(post.class);

                         if (p != null && dataSnapshot.getChildrenCount()>3  ) {
                            p.setPostId(dataSnapshot.getKey());
                             //int l=p.getCount().length();
if(p.getTitle().equals("This has been done")) {
    p.setStatus(true);

    arCleaned.add(Integer.parseInt(p.getCount()));
}
                     ar.add(p);

                                 arRegistered.add(Integer.parseInt(p.getCount()));




                         } else {
                            // Handle the case where data couldn't be converted to the post class
                        }
                    }
                    catch (DatabaseException e) {
                            // Handle database-related exceptions (e.g., data structure mismatch)
                        e.printStackTrace();
                    }
                    for(int i:arRegistered)
                        arRegTotal+=i;
                    for(int j:arCleaned)
                        arCleanedTotal+=j;


                }

                postadapter.notifyDataSetChanged();
                database.getReference().child("plastic").child("totalReg").setValue(arRegTotal);
                database.getReference().child("plastic").child("totalCleaned").setValue(arCleanedTotal);

//                for(int i:arRegistered)
//                    arRegTotal+=i;
//                for(int j:arCleaned)
//                    arCleanedTotal+=j;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
         return view;
    }
}