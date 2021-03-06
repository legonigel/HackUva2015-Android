package com.thekarlbrown.karl.keepintouch;



import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/*
this is the base data adapter for ideas, implements a viewholder for performance
 */
public class FriendsAdapter extends BaseAdapter{
    Context context;
    List<Message> messages;
    LayoutInflater inflater;
    View row;
    TextView t;
    int curposition;
    boolean individual;
    String local_user;
    //for later when I am doing screen scaling
    int adjustable = 115;
    FriendAdapterMainInterface mListener;


    public FriendsAdapter(List<Message> message , Context con, boolean indiv,String user) {
        messages=message;
        context=con;
        individual=indiv;
            local_user = user;
        try{
            mListener=(FriendAdapterMainInterface)con;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString()
                    + " must implement NoticeDialogListener");
        }
        }


    public int getCount() {
        // TODO Auto-generated method stub
        //return current.Length(); should be fixed when container is properly dealt with
        return messages.size();
    }

    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return messages.size();
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return curposition;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        curposition=position;
        ViewHolderData viewHolder;
        if(convertView==null)
        {
            inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.message_container, parent, false);
            viewHolder=new ViewHolderData();
            viewHolder.user=(TextView)convertView.findViewById(R.id.message_container_user);
            viewHolder.message=(TextView) convertView.findViewById(R.id.message_container_content);
            viewHolder.imager=(ImageView) convertView.findViewById(R.id.message_container_picture_right);
            viewHolder.imagel=(ImageView)convertView.findViewById(R.id.message_container_picture_left);
            viewHolder.date=(TextView) convertView.findViewById(R.id.message_container_date);
            viewHolder.layout_listener=(LinearLayout)convertView.findViewById(R.id.message_container_click);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolderData) convertView.getTag();
        }

        //add user fragment by setting the first position onclicklistener
        if(!individual) {
            viewHolder.user.setText("Message " + messages.get(position).getSender() + " -- ");
            if(messages.get(position).getRead()) {
                viewHolder.date.setText("Online");
                viewHolder.date.setTextColor(Color.parseColor("#00ff00"));
            }else{
                viewHolder.date.setText("Offline");
                viewHolder.date.setTextColor(Color.parseColor("#ff0000"));
            }
            viewHolder.message.setText("");
            viewHolder.date.setTextSize(40);
            viewHolder.message.setTextSize(0);
            viewHolder.user.setTextSize(40);

            //viewHolder.image.setImageResource(); sender image
            final int positiontemp=position;
            viewHolder.layout_listener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.friendsListToConversation(messages.get(positiontemp).getSender());
                }
            });
        }else{
            if (messages.get(position).getMessage().length() > adjustable) {
                viewHolder.message.setText(messages.get(position).getMessage().substring(0, adjustable) + ".....");
            } else {
                viewHolder.message.setText(messages.get(position).getMessage());
            }
            viewHolder.date.setText(messages.get(position).getDate());
            if(local_user.equals(messages.get(position).getReceiver()))
            {
                //viewHolder.image.setImageResource(); sender image
                viewHolder.message.setGravity(Gravity.RIGHT);
                viewHolder.user.setGravity(Gravity.RIGHT);
                viewHolder.user.setText(messages.get(position).getReceiver());
                viewHolder.imager.setImageResource(R.drawable.yourpicplaceholder);
                viewHolder.imagel.setImageResource(R.drawable.picalt);
            }else{
                viewHolder.user.setText(messages.get(position).getSender());
                viewHolder.imager.setImageResource(R.drawable.picalt);
                viewHolder.imagel.setImageResource(R.drawable.theirpicplaceholder);
            }
        }
        return convertView;
    }

    static class ViewHolderData
    {
        TextView user;
        TextView date;
        TextView message;
        ImageView imager;
        ImageView imagel;
        LinearLayout layout_listener;
    }
    public interface FriendAdapterMainInterface{
        public void friendsListToConversation(String sender);
    }
}
