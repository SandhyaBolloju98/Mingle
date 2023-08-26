package com.random.chat.mingle1.ModelClasses;

public class MessagesModelClass {
    public String message_user_id;
    public String message_user_name;
    public String message;
    public long message_time;
    private boolean banner_ad;

    public  MessagesModelClass(String message_user_id,
                               String message_user_name,
                               String message,
                               long message_time,
                               boolean banner_ad) {
        this.message_user_id = message_user_id;
        this.message_user_name = message_user_name;
        this.message = message;
        this.message_time = message_time;
        this.banner_ad = banner_ad;

    }

    public String getMessage_user_id() {
        return message_user_id;
    }

    public void setMessage_user_id(String message_user_id) {
        this.message_user_id = message_user_id;
    }

    public String getMessage_user_name() {
        return message_user_name;
    }

    public void setMessage_user_name(String message_user_name) {
        this.message_user_name = message_user_name;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getMessage_time() {
        return message_time;
    }

    public boolean isBanner_ad() {
        return banner_ad;
    }

    public void setBanner_ad(boolean banner_ad) {
        this.banner_ad = banner_ad;
    }

    public void setMessage_time(long message_time) {
        this.message_time = message_time;
    }
}
