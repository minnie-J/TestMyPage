package edu.android.testmypage;

import java.util.List;

public class UserData {
    private String userId;
    private String name;
    private String photoInEssay;
    private List<Double> locInEssay;
    private String title;
    private String content;
    private String recDate;

    public UserData(){}

    public UserData(String userId, String name, String photoInEssay, List<Double> locInEssay, String title, String content, String recDate) {
        this.userId = userId;
        this.name = name;
        this.photoInEssay = photoInEssay;
        this.locInEssay = locInEssay;
        this.title = title;
        this.content = content;
        this.recDate = recDate;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getPhotoInEssay() {
        return photoInEssay;
    }

    public List<Double> getLocInEssay() {
        return locInEssay;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getRecDate() {
        return recDate;
    }
}
