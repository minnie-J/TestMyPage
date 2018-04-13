package edu.android.testmypage;

import java.util.ArrayList;
import java.util.List;

public class UserDataDao {

    private List<UserData> userData = new ArrayList<>();
    private static UserDataDao instance = null;

    private UserDataDao() {}

    public List<UserData> getUserData() {
        return userData;
    }

    public static UserDataDao getInstance() {
        if (instance == null) {
            instance = new UserDataDao();
        }
        return instance;
    }
}
