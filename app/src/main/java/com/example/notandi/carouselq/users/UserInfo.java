package com.example.notandi.carouselq.users;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * Created by Notandi on 23.6.2016.
 */
public class UserInfo {

    private String hashedUserName;
    private String userName;
    private String queueId;
    private boolean owner;
    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static UserInfo instance = null;
    private UserInfo() {       }

    public static UserInfo getInstance() {
        if (instance == null) {
            instance = new UserInfo ();
        }
        return instance;
    }

    public void registerUser(String UserName, boolean owner){
        this.hashedUserName = makeHashUserName();
        this.userName = UserName;
        if(owner) {
            this.queueId = makeQueueID();
        }
        this.owner = owner;
    }

    public boolean getOwner() { return this.owner; }

    public String getUserName(){
        return this.userName;
    }

    public String getHashedUserName(){
        return this.hashedUserName;
    }

    public String getQueueID(){
        return this.queueId;
    }

    private String makeHashUserName(){
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    private String makeQueueID(){
        return randomString(5);
    }

    private String randomString( int len ){
        SecureRandom rand = new SecureRandom();
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append(AB.charAt( rand.nextInt(AB.length()) ) );
        return sb.toString();
    }

    public void setQueueId(String id) {
        this.queueId = id;
    }

    public void setUserName(String un) {
        this.userName = un;
    }

    public void setHashedUserName(String hashName) {
        this.hashedUserName = hashName;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }
}
