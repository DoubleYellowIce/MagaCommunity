package com.DYI.android.bulletin;

public class UsersLoginState {
    private static boolean loginstate=false;
    private static boolean isManager=false;
    public static boolean getLoginstate(){
        return loginstate;
    }
    public static boolean getIsManger(){
        return isManager;
    }
    public static void setLoginstatePositive(){
        loginstate=true;
    }
    public static void setLoginstateNegative(){
        loginstate =false;
    }
    public static void setIsManagerstatePositive(){
        isManager=true;
    }
    public static void setIsManagerstateNegative(){
        isManager =false;
    }
}
