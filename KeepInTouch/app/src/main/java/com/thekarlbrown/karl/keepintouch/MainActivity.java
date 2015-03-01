package com.thekarlbrown.karl.keepintouch;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import org.json.JSONArray;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;


//import com.facebook.Session;

public class MainActivity extends Activity implements MessageAdapter.MessageAdapterMainInterface,FriendsAdapter.FriendAdapterMainInterface {
FragmentManager fm;
    FragmentTransaction ft;
    ImageView imageView;
    int selected=0;
    List<Message> message_list=new ArrayList<Message>();;
    List<Message> message_convo=new ArrayList<Message>();;
    List<Message> friends_list=new ArrayList<Message>();;
   private LoginFacebook loginFacebook;
    private MessageTab messageTab;
    private FriendsTab friendsTab;
    String username_account;
   String currrent_friend;
    int user_id;
    //httprequest shizzz
    JSONParser jsonParser;
    JSONArray jsonArray;
    String http_request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        message_list.add(new Message.Builder().date().message("Here is a test message").read(false).receiver("karl").sender("nigel").build());

        for(int x =0;x<7;x++) {
            message_convo.add(new Message.Builder().date().message("Here is a test convo").read(false).receiver("karl").sender("nigel").build());
        }
        for(int x =0;x<7;x++) {
            message_convo.add(new Message.Builder().date().message("Here is a test response").read(true).receiver("nigel").sender("karl").build());
        }
        for(int x =0;x<7;x++) {
            friends_list.add(new Message.Builder().date().message("Here is a test friends list").read(true).receiver("karl").sender("nigel").build());
        }
        for(int x =0;x<7;x++) {
            friends_list.add(new Message.Builder().date().message("Here is a test friends list").read(false).receiver("karl").sender("juan").build());
        }

    }
    public void onConversationFetch(String json) {
        String temp=json;
        for (int x = 0; x < 15; x++) {
        }
    }
    @Override
    public void onStart()
    {
        super.onStart();
        loginFacebook=new LoginFacebook();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, loginFacebook, "loginFacebook")
                .commit();
    }
    public void createBarClick()
    {
        imageView=(ImageView)findViewById(R.id.bar_messages);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=1)
                {
                    deselectedTab(selected);
                    selected=1;
                    imageView=(ImageView)findViewById(R.id.bar_messages);
                    imageView.setBackgroundColor(Color.parseColor("#e23374"));
                            //update for messages data
                            messageTab = new MessageTab();
                    fm=getFragmentManager();
                    ft=fm.beginTransaction().replace(R.id.current_fragment, messageTab, "messageTab");
                    ft.commit();
                }
            }
        });
        imageView=(ImageView)findViewById(R.id.bar_address);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=2)
                {
                    deselectedTab(selected);
                    selected=2;
                    imageView=(ImageView)findViewById(R.id.bar_address);
                    imageView.setBackgroundColor(Color.parseColor("#e23374"));
                    //update for friends data
                    friendsTab=new FriendsTab();
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.current_fragment, friendsTab, "friendsTab")
                            .commit();
                }
            }
        });
        imageView=(ImageView)findViewById(R.id.bar_notifications);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=3)
                {
                    deselectedTab(selected);
                    selected=3;
                    imageView=(ImageView)findViewById(R.id.bar_notifications);
                    imageView.setBackgroundColor(Color.parseColor("#e23374"));

                }
            }
        });
        imageView=(ImageView)findViewById(R.id.bar_settings);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected!=4)
                {
                    deselectedTab(selected);
                    selected=4;
                    imageView=(ImageView)findViewById(R.id.bar_settings);
                    imageView.setBackgroundColor(Color.parseColor("#e23374"));
                    //we implement settings later
                }
            }
        });
    }
    public void addTabs(String user)
    {
        username_account=user;
        user_id=4; //function
        http_request="https://nodejs-cnuhacks.rhcloud.com/api/conversations/user/" + user_id;
        messageTab=new MessageTab();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, messageTab, "messageTab")
                .commit();
        selected=1;
        imageView=(ImageView)findViewById(R.id.bar_messages);
        imageView.setBackgroundColor(Color.parseColor("#e23374"));

        createBarClick();

        AsyncParser asyncParser=new AsyncParser();
        try{
            asyncParser.execute();
            jsonArray=asyncParser.get();
        }catch(Exception e)
        {
            Log.println(0, "Error", e.getMessage());
        }
        Log.println(0,"","Take a break");
    }

@Override
    public void messageListToConversation(String sender) {
        //update my message array here
        currrent_friend=sender;
        messageTab.updateMessagesConvo();
        selected=-1;
    }
    @Override
    public void friendsListToConversation(String sender)
    {
        //update my message array here
        currrent_friend=sender;
        friendsTab.updateMessagesConvo();
        selected=-2;
    }


    public void sendTextMessages(String message)
    {
        //send text
        //update messages array
        message_convo.add(0,new Message.Builder().date().receiver(currrent_friend).sender(username_account).read(false).message(message).build());
        messageTab.updateMessagesConvo();
    }
    public void sendTextFriends(String message)
    {
        //send text
        //update messages array
        message_convo.add(0,new Message.Builder().date().receiver(currrent_friend).sender(username_account).read(false).message(message).build());
        friendsTab.updateMessagesConvo();
    }
    public void deselectedTab(int x) {
        if (x == 1 || x == -1) {
            imageView = (ImageView) findViewById(R.id.bar_messages);
        } else if (x == 2 || x == -2) {
            imageView = (ImageView) findViewById(R.id.bar_address);
        } else if (x == 3 || x == -3) {
            imageView = (ImageView) findViewById(R.id.bar_notifications);
        } else if (x == 4 || x == -4) {
            imageView = (ImageView) findViewById(R.id.bar_settings);
        }
        if (x != 0) {
            imageView.setBackgroundColor(Color.parseColor("#e30054"));
        }
    }
    private class AsyncParser extends AsyncTask<String, String, JSONArray>
    {
        JSONParser jsonParser=new JSONParser();
        JSONArray jsonArray;
        @Override
        protected JSONArray doInBackground(String... params) {
            Log.println(0, "", "Starting JSON parse...");
            try {
                jsonArray=jsonParser.makeHttpRequest(http_request, "GET");
            }catch(IOException e){
                Log.println(0, "Error", e.getMessage());
            }
            Log.println(0, "", "Done with JSON parse...");
            return jsonArray;
        }
    }
}

