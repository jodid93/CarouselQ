package com.example.notandi.carouselq.database;

/**
 * Created by Notandi on 15.6.2016.
 */
public class urls {

    private static String mode = "dev";
    public static String createUser(String userName){
        return "";
    }

    public static String testConnection(){
        return baseService(mode)+"testConnection";
    }

    public static String registerNewUser(String userName, String hashedUserName, String queueId){
        return baseService(mode)+"addNewUser/"+userName+"/"+hashedUserName+"/"+queueId;
    }

    public static String initializeDB(){
        return baseService(mode)+"initDB";
    }

    public static String testSpotify(){
        return "https://api.spotify.com/v1/search?q=tania%20bowra&type=artist";
    }

    public static String doesQueueExist(String queueId){
        return baseService(mode)+ "/doesQueueExist/"+queueId;
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