package com.example.notandi.carouselq.database;

/**
 * Created by Notandi on 15.6.2016.
 */
public class urls {

    private static String mode = "raun";
    public static String createUser(String userName){
        return "";
    }

    public static String testConnection(){
        return baseService(mode)+"testConnection";
    }

    public static String registerNewUser(String userName, String hashedUserName, String queueId, boolean owner){
        return baseService(mode)+"addNewUser/"+userName+"/"+hashedUserName+"/"+queueId+"/"+Boolean.toString(owner);
    }

    public static String initializeDB(){
        return baseService(mode)+"initDB";
    }

    public static String testSpotify(){
        return "https://api.spotify.com/v1/search?q=Muse&type=track";
    }

    public static String getSongs(String Query, String type){
        return "https://api.spotify.com/v1/search?q="+Query+"&type="+type;
    }

    public static String doesQueueExist(String queueId){
        return baseService(mode)+ "doesQueueExist/"+queueId;
    }

    public static String makeUserInactive(String hashUn) {
        return baseService(mode)+"makeUserInactive/"+hashUn;
    }

    public static String addSongToQueue(String hashName, String trackUri, String trackName, String trackBand, int trackDur){
        return baseService(mode)+"addSongToQueue/"+hashName+"/"+trackUri+"/"+trackName+"/"+trackBand+"/"+trackDur;
    }

    public static String removeSong(String hashName, String trackUri){
        return baseService(mode) + "removeSong/"+hashName+"/"+trackUri;
    }

    public static String getQueue(String queueId){
        return baseService(mode) + "getQueue/"+queueId;
    }

    //TODO:
    /*
        hér þarf að bæta við aðferðum sem kalla á viðeigandi aðgerðir á bakendanum í gegnum url og skila sögðu url-i
     */
    public static String baseService(String mode){

        switch(mode){
            case "raun": {
                return "https://carousel-q.herokuapp.com/";
            }case("dev"): {
                return "http://10.0.2.2:8080/";
                //return "http://127.0.0.1:8080/";
            }default:{
                return "";
            }
        }
    }


}
