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
import android.widget.TextView;

import java.util.regex.Pattern;


/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsTab extends Fragment {

    View view;
    MainActivity mainActivity;
    ListView listView;
    FriendsAdapter friendsAdapter;
    TextView textView;
    EditText editText;
    Button button;
    LinearLayout linearLayout;
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
        editText=(EditText)view.findViewById(R.id.friends_send_message);
        button=(Button)view.findViewById(R.id.friends_send_button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String temp = ((EditText) view.findViewById(R.id.friends_send_message)).getText().toString();
                if (Pattern.matches("(?i)^([^\"\\[:\\]\\|=\\+\\*\\?<>\\\\\\/\\r\\n]+)$", temp) && (temp.length() < 26)) {
                    hideSoftKeyboard();
                    mainActivity.sendTextFriends(temp);
                    ((EditText) view.findViewById(R.id.friends_send_message)).setText(null);
                } else {
                    ((EditText) view.findViewById(R.id.friends_send_message)).setText(null);
                }
            }
        });
        linearLayout=(LinearLayout)view.findViewById(R.id.friends_textbar);
        linearLayout.setVisibility(View.GONE);
        return view;
    }

    public void updateMessagesConvo()
    {
        linearLayout=(LinearLayout)view.findViewById(R.id.friends_textbar);
        linearLayout.setVisibility(View.VISIBLE);
        friendsAdapter=new FriendsAdapter(mainActivity.message_convo,mainActivity,true,mainActivity.username_account);
        listView=(ListView)view.findViewById(R.id.friends_list_every);
        listView.setAdapter(friendsAdapter);
        //do I need this
        view.invalidate();
    }
    public void hideSoftKeyboard() {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) mainActivity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(mainActivity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
