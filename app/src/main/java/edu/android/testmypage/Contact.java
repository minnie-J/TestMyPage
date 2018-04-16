package edu.android.testmypage;

import java.util.List;

public class Contact {
    private boolean openInfo;
    private String name;
    private String id;
    private String proImg;
    private List<Double> curLoc;
//    private List<String> friendList;
//    private List<String> reqList;

    public Contact(){}
    public Contact(String name, String id, String proImg){}
    public Contact(boolean openInfo, String name, String id, String proImg, List<Double> curLoc, List<String> friendList, List<String> reqList) {
        this.openInfo = openInfo;
        this.name = name;
        this.id = id;
        this.proImg = proImg;
        this.curLoc = curLoc;
//        this.friendList = friendList;
//        this.reqList = reqList;
    }

    public boolean isOpenInfo() {
        return openInfo;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public String getProImg() {
        return proImg;
    }

    public List<Double> getCurLoc() {
        return curLoc;
    }

//    public List<String> getFriendList() {
//        return friendList;
//    }

//    public List<String> getReqList() {
//        return reqList;
//    }
}
