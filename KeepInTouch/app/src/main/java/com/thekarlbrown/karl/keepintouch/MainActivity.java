package com.thekarlbrown.karl.keepintouch;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.media.Image;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.ImageView;

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
    //private NotificationsTab notificationsTab;
    String username_account;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*if (savedInstanceState == null) {
            // Add the fragment on initial activity setup
            loginFacebook = new LoginFacebook();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.current_fragment, loginFacebook, "loginFacebook")
                    .commit();
        } else {
            // Or set the fragment from restored state info
            loginFacebook = (LoginFacebook) getSupportFragmentManager()
                    .findFragmentById(android.R.id.content);
        }
        */
        ////test cases
        username_account="karl";
        for(int x =0;x<15;x++) {
            message_list.add(new Message.Builder().date().message("Here is a test message list").read(false).receiver("karl").sender("nigel").build());
        }
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
                    //update for notificationsdata data
                    /*
                    notificationsTab=new NotificationsTab();
                    getFragmentManager()
                            .beginTransaction()
                            .add(R.id.current_fragment, notificationsTab, "notificationsTab")
                            .commit();
                            */
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
        messageTab=new MessageTab();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.current_fragment, messageTab, "messageTab")
                .commit();
        selected=1;
        imageView=(ImageView)findViewById(R.id.bar_messages);
        imageView.setBackgroundColor(Color.parseColor("#e23374"));
        createBarClick();
    }

@Override
    public void messageListToConversation(String sender, String receiver) {
        //update my message array here
        messageTab.updateMessagesConvo();
        selected=-1;
    }
    @Override
    public void friendsListToConversation(String sender)
    {
        //update my message array here
        friendsTab.updateMessagesConvo();
        selected=-2;
    }

    /**
     * dont forget me senpai
     * @param sender

    @Override
    public void notificationsListToConversation(String sender)
    {
        //update my message array here
        notificationsTab.updateMessagesConvo();
    }
     */
    public void deselectedTab(int x)
    {
        if(x==1||x==-1)
        {
            imageView=(ImageView)findViewById(R.id.bar_messages);
        }else if(x==2||x==-2)
        {
            imageView=(ImageView)findViewById(R.id.bar_address);
        }
        else if(x==3||x==-3)
        {
            imageView=(ImageView)findViewById(R.id.bar_notifications);
        }
        else if(x==4||x==-4)
        {
            imageView=(ImageView)findViewById(R.id.bar_settings);

        }
        if(x!=0) {
            imageView.setBackgroundColor(Color.parseColor("#e30054"));
        }
    }
}
