package com.example.notandi.carouselq.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notandi.carouselq.R;
import com.example.notandi.carouselq.database.DBConnector;
import com.example.notandi.carouselq.users.UserInfo;

public class HomeScreen extends Activity {
    private String PREFS_NAME = "prefrences";

    private Button mNewQueue;
    private Button mEnterQueue;
    private TextView mNewUser;
    private TextView mQueueID;
    private TextView mOldUser;

    private static final int ENTER_QUEUE = 0;
    private static final int NEW_QUEUE = 1;

    private DBConnector backendConnector;
    UserInfo uInfo = UserInfo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);


        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String UN = settings.getString("Name", "n0th1ngatt4ll");

        if(!UN.equals("n0th1ngatt4ll")){
            uInfo.setUserName(UN);
            uInfo.setQueueId(settings.getString("queueId", "n0th1ngatt4ll"));
            uInfo.setHashedUserName(settings.getString("hashedUN", "n0th1ngatt4ll"));
            uInfo.setOwner(settings.getBoolean("owner", false));

            Intent i = MainActivity.newIntent(HomeScreen.this);
            startActivityForResult(i, 0);
        }


        context init = context.getInstance();
        init.init(this.getApplicationContext());


        backendConnector = DBConnector.getInstance();
        //backendConnector.initializeDB();
        //backendConnector.testConnection();

        mEnterQueue = (Button) findViewById(R.id.enter_queue_button);
        mNewQueue = (Button) findViewById(R.id.new_queue_button);

        mNewUser = (TextView) findViewById(R.id.user_name_text);
        mQueueID = (TextView) findViewById(R.id.queue_id_text);
        mOldUser = (TextView) findViewById(R.id.queue_user_name);

        //final ArrayAdapter<String> adapter = new ArrayAdapter<String>(context);

        mNewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queueName = mNewUser.getText().toString();
                if(queueName == null ||queueName.equals("Name")){ //TODO parse input to not allow spaces
                    Toast.makeText(HomeScreen.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                    System.out.println(mNewUser.getText() + "  -------------------------------------");
                }else{
                    uInfo.registerUser(String.valueOf(mNewUser.getText()), true);
                    backendConnector.registerUser(uInfo.getUserName(), uInfo.getHashedUserName(), uInfo.getQueueID(), uInfo.getOwner());
                    registerPersistantLogIn(uInfo.getUserName(), uInfo.getHashedUserName(),String.valueOf(mQueueID.getText()), uInfo.getOwner() );
                    Intent i = MainActivity.newIntent(HomeScreen.this);
                    startActivityForResult(i, 0);
                }

            }
        });

        mEnterQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String queueName = mQueueID.getText().toString();
                if(queueName == null ||queueName.equals("Name")){
                    Toast.makeText(HomeScreen.this, "Please enter a queue id", Toast.LENGTH_SHORT).show();
                    System.out.println(mQueueID.getText() + "  -------------------------------------");
                }else{
                    if(backendConnector.doesQueueExist(String.valueOf(mQueueID.getText()))){ //TODO parse input to not allow spaces
                        uInfo.registerUser(String.valueOf(mOldUser.getText()), false);
                        backendConnector.registerUser(uInfo.getUserName(), uInfo.getHashedUserName(),String.valueOf(mQueueID.getText()), uInfo.getOwner() );
                        uInfo.setQueueId(String.valueOf(mQueueID.getText()));
                        registerPersistantLogIn(uInfo.getUserName(), uInfo.getHashedUserName(),String.valueOf(mQueueID.getText()), uInfo.getOwner() );
                        Intent i = MainActivity.newIntent(HomeScreen.this);
                        startActivityForResult(i, 0);
                    }else{
                        Toast.makeText(HomeScreen.this, "The selected queue does not exist", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    private void registerPersistantLogIn(String UN, String hashUN, String QID, boolean owner){
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("Name",UN);
        editor.putString("queueId", QID);
        editor.putString("hashedUN", hashUN);
        editor.putBoolean("owner", owner);

        editor.commit();
    }
}
