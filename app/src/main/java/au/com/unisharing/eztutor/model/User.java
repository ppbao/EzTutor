package au.com.unisharing.eztutor.model;

import java.util.Date;

/**
 * Class Name   :
 * Author       :
 * Created Date :
 * Description  :
 */
public class User {

    public boolean isLoginUser(){
        return true;
    }

    public String getPhotoUrl() {
        return "url";
    }

    public Date getPhotoUpdatedAt() {
        return new Date();
    }

    public boolean hasPhoto() {
        return true;
    }

    public String getNickname() {
        return "Nickname";
    }

    public String getUsername() {
        return "realname";
    }
}
