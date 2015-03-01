package com.thekarlbrown.karl.keepintouch;


import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class MessageTab extends Fragment  {

View view;
    MainActivity mainActivity;
    ListView listView;
    MessageAdapter messageAdapter;
    EditText editText;
    Button button;
    LinearLayout linearLayout;
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
        messageAdapter = new MessageAdapter(mainActivity.message_list,mainActivity,false,mainActivity.username_account,mainActivity.current_friend_username,mainActivity.user_id);
        listView = (ListView) view.findViewById(R.id.message_list_every);
        listView.setAdapter(messageAdapter);
        button=(Button)view.findViewById(R.id.message_send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String temp = ((EditText) view.findViewById(R.id.message_send_message)).getText().toString();
                if (Pattern.matches("(?i)^([^\"\\[:\\]\\|=\\+\\*\\?<>\\\\\\/\\r\\n]+)$", temp) && (temp.length() < 26)) {
                    hideSoftKeyboard();
                    mainActivity.sendTextMessages(temp);
                    ((EditText) view.findViewById(R.id.message_send_message)).setText(null);
                } else {
                    ((EditText) view.findViewById(R.id.message_send_message)).setText(null);
                }
            }
        });
        linearLayout=(LinearLayout)view.findViewById(R.id.message_textbar);
        linearLayout.setVisibility(View.GONE);
        return view;

    }
    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateMessagesConvo()
    {
        linearLayout=(LinearLayout)view.findViewById(R.id.message_textbar);
        linearLayout.setVisibility(View.VISIBLE);
        messageAdapter=new MessageAdapter(mainActivity.message_convo,mainActivity,true,mainActivity.username_account,mainActivity.current_friend_username,mainActivity.user_id);
        listView=(ListView)view.findViewById(R.id.message_list_every);
        listView.setAdapter(messageAdapter);
        //do I need this
        view.invalidate();
    }


}
