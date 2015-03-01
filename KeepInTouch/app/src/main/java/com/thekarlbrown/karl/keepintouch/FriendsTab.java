package com.thekarlbrown.karl.keepintouch;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsTab extends Fragment {

    View view;
    MainActivity mainActivity;
    ListView listView;
    FriendsAdapter friendsAdapter;
    public static FriendsTab newInstance() {
        FriendsTab fragment = new FriendsTab();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    public FriendsTab()
    {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=inflater.inflate(R.layout.fragment_friends_tab, container, false);
        mainActivity=(MainActivity)getActivity();
        //adapt it once I know the parameters
        //
        friendsAdapter = new FriendsAdapter(mainActivity.friends_list,mainActivity,false,"   ");
        listView = (ListView) view.findViewById(R.id.friends_list_every);
        listView.setAdapter(friendsAdapter);
        return view;

    }

    public void updateMessagesConvo()
    {
        friendsAdapter=new FriendsAdapter(mainActivity.message_convo,mainActivity,true,mainActivity.username_account);
        listView=(ListView)view.findViewById(R.id.friends_list_every);
        listView.setAdapter(friendsAdapter);
        //do I need this
        view.invalidate();
    }


}
