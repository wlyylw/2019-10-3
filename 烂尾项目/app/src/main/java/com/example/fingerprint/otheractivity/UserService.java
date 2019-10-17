package com.example.fingerprint.otheractivity;

public class UserService {
    boolean logined;
    String username;
    private UserService()
    {
        logined=false;
    }
    public boolean Login(String username,String password)
    {
        this.username =username;
        logined=true;
        return true;
    }
    public boolean isLogined()
    {
        return logined;
    }
    public String getUsername()
    {
        return username;
    }
    private static UserService instance;
    public static UserService getInstance()
    {
        if(instance == null)
        {
            instance =new UserService();
        }
        return instance;
    }

}
