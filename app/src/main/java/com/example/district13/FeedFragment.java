package com.example.district13;

import android.os.Bundle;

import androidx.annotation.ContentView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.district13.sticker_user.StickerUserAdapter;
import com.example.district13.teatalks_feed.FeedItem;
import com.example.district13.teatalks_feed.FeedItemAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FeedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FeedFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FeedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FeedFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FeedFragment newInstance(String param1, String param2) {
        FeedFragment fragment = new FeedFragment();
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
    }
    
    private List<FeedItem> feedItemList;
    private RecyclerView feedItemRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        feedItemList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            feedItemList.add(new FeedItem("100014", "Chang Guan", "2022/12/06", "https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU", "https://i.picsum.photos/id/0/5000/3333.jpg?hmac=_j6ghY5fCfSD6tvtcV74zXivkJSPIfR9B8w34XeQmvU", "Hello, this is the instruction \nThis is the second line\n This is the third line \nThis is the fourth line", true));
        }
        View view = inflater.inflate(R.layout.fragment_feed, container, false);
        feedItemRecyclerView = view.findViewById(R.id.recyclerView_feed);
        feedItemRecyclerView.setHasFixedSize(true);
        feedItemRecyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        feedItemRecyclerView.setAdapter(new FeedItemAdapter(feedItemList, getContext()));
        return view;
    }
}