package com.thekarlbrown.karl.keepintouch;

import java.util.Date;

public class Message {
    private String mDate;
    private boolean mRead;
    private String mMessage;
    private String mSender;
    private String mReceiver;
    private Message() {}

    public String getMessage() {
        return mMessage;
    };
    public String getDate(){return mDate; };
    public String getSender() {
        return mSender;
    };
    public String getReceiver() {
        return mSender;
    };
    public boolean getRead(){return mRead;};

    public static class Builder {
        private String mDate;
        private boolean mRead;
        private String mMessage;
        private String mSender;
        private String mReceiver;

        public Builder sender(String sender) {
            mSender=sender;
            return this;
        }
        public Builder receiver(String receiver) {
            mReceiver=receiver;
            return this;
        }
        public Builder message(String message) {
            mMessage = message;
            return this;
        }
        public Builder read(boolean read){
            mRead=read;
            return this;
        }
        public Builder date(){
            mDate=(new Date()).toString();
            return this;
        }
        public Message build() {
            Message message = new Message();
            message.mSender = mSender;
            message.mReceiver = mReceiver;
            message.mRead=mRead;
            message.mDate=mDate;
            message.mMessage=mMessage;
            return message;
        }
    }
}