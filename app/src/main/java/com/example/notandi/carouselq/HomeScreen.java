package com.example.notandi.carouselq;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends Activity {

    private Button mNewQueue;
    private Button mEnterQueue;
    private TextView mNewUser;
    private TextView mQueueID;

    private static final int ENTER_QUEUE = 0;
    private static final int NEW_QUEUE = 1;

    private DBConnector backendConnector;
    UserInfo uInfo = UserInfo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        context init = context.getInstance();
        init.init(this.getApplicationContext());


        backendConnector = DBConnector.getInstance();
        //backendConnector.initializeDB();
        //backendConnector.testConnection();

        mEnterQueue = (Button) findViewById(R.id.enter_queue_button);
        mNewQueue = (Button) findViewById(R.id.new_queue_button);

        mNewUser = (TextView) findViewById(R.id.user_name_text);
        mQueueID = (TextView) findViewById(R.id.queue_id_text);

        mNewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNewUser.getText() == null){
                    Toast.makeText(HomeScreen.this, "Please enter a username", Toast.LENGTH_SHORT).show();

                }else{
                    //uInfo.registerUser((String) mNewUser.getText());
                    //backendConnector.registerUser(uInfo.getUserName(), uInfo.getHashedUserName(), uInfo.getQueueID());
                }
                Intent i = MainActivity.newIntent(HomeScreen.this);
                startActivityForResult(i, 0);
            }
        });
    }
}
