package com.example.notandi.carouselq;

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
            }default:{
                return "";
            }
        }
    }
}
