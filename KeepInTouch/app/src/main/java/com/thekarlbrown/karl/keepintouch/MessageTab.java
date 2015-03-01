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
public class MessageTab extends Fragment  {

View view;
    MainActivity mainActivity;
    ListView listView;
    MessageAdapter messageAdapter;
    public static MessageTab newInstance() {
        MessageTab fragment = new MessageTab();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }
    public MessageTab()
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
        view=inflater.inflate(R.layout.fragment_message_tab, container, false);
        mainActivity=(MainActivity)getActivity();
        //adapt it once I know the parameters
        messageAdapter = new MessageAdapter(mainActivity.message_list,mainActivity,false,mainActivity.username_account);
        listView = (ListView) view.findViewById(R.id.message_list_every);
        listView.setAdapter(messageAdapter);
        return view;

    }

    public void updateMessagesConvo()
    {
        messageAdapter=new MessageAdapter(mainActivity.message_convo,mainActivity,true,mainActivity.username_account);
        listView=(ListView)view.findViewById(R.id.message_list_every);
        listView.setAdapter(messageAdapter);
        //do I need this
        view.invalidate();
    }


}
