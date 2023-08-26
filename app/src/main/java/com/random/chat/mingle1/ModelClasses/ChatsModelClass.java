package com.random.chat.mingle1.ModelClasses;

public class ChatsModelClass {
    public String name;
    public String userId;
    public String chatRoomId;
    public String gender;
    public String age;

    public String getChatRoomId() {
        return chatRoomId;
    }


    public void setChatRoomId(String chatRoomId) {
        this.chatRoomId = chatRoomId;
    }

    public ChatsModelClass(String name, String userId, String chatRoomId, String gender, String age) {
        this.name = name;
        this.userId = userId;
        this.chatRoomId = chatRoomId;
        this.gender = gender;
        this.age = age;


    }

    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
