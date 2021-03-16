package com.DYI.android.home;

import org.litepal.crud.LitePalSupport;

public class user extends LitePalSupport {
    private String userId;
    private String userPassword;
    private boolean isManagers;
    public String getUserId() {
        return userId;
    }
    public void setUserId(String UserId){
        this.userId=UserId;
    }
    public String getUserPassword(){
        return userPassword;
    }
    public void  setUserPassword(String UserPassword){
        this.userPassword=UserPassword;
    }
    public void setManagersPositive(){
     isManagers=true;
    }
    public void setManagersNegative(){
        isManagers=false;
    }
    public boolean getManagers(){
        return isManagers;
    }
}
