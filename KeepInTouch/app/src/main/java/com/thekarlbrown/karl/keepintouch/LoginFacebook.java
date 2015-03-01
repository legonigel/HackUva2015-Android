
package com.thekarlbrown.karl.keepintouch;


import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.regex.Pattern;


public class LoginFacebook extends Fragment {
EditText editText;
    MainActivity mainActivity;
Button button;
    TextView t;
    Activity activity;
    public LoginFacebook() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivity=((MainActivity)getActivity());
        final View view = inflater.inflate(R.layout.fragment_login_facebook, container, false);
        //LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
        //authButton.setReadPermissions(Arrays.asList("public_profile"));
        editText=(EditText)view.findViewById(R.id.initial_username);
        button=(Button)view.findViewById(R.id.initial_user_submit);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideSoftKeyboard();
                String temp=((EditText) view.findViewById(R.id.initial_username)).getText().toString();
                if(Pattern.matches("^[A-Za-z0-9]+", temp)&&(temp.length()<26))
                {
                    hideSoftKeyboard();
                    sendUsername(temp);
                }else
                {
                    ((EditText) view.findViewById(R.id.initial_username)).setText(null);
                    t = (TextView) view.findViewById(R.id.initial_login_text);
                    t.setText(getString(R.string.initial_login_text) + "\n" +getString( R.string.initial_login_error_entry));
                }
            }
        });
        return view;
    }
    public void sendUsername(String user)
    {
        //
        mainActivity.addTabs(user);
    }

    public void hideSoftKeyboard() {
        try {
            activity=getActivity();
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

