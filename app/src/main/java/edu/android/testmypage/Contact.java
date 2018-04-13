package edu.android.testmypage;

import java.util.List;

public class Contact {
    private boolean openInfo;
    private String name;
    private String id;
    private String proImg;
    private int[] curLoc;
    private List<String> friendList;
    private List<String> reqList;

    public Contact(boolean openInfo, String name, String id, String proImg, int[] curLoc, List<String> friendList, List<String> reqList) {
        this.openInfo = openInfo;
        this.name = name;
        this.id = id;
        this.proImg = proImg;
        this.curLoc = curLoc;
        this.friendList = friendList;
        this.reqList = reqList;
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

    public int[] getCurLoc() {
        return curLoc;
    }

    public List<String> getFriendList() {
        return friendList;
    }

    public List<String> getReqList() {
        return reqList;
    }
}
