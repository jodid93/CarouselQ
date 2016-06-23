package com.example.notandi.carouselq;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class HomeScreen extends AppCompatActivity {

    private Button mNewQueue;
    private Button mEnterQueue;
    private TextView mNewUser;
    private TextView mQueueID;

    private static final int ENTER_QUEUE = 0;
    private static final int NEW_QUEUE = 1;

    DBConnector Connector = DBConnector.getInstance();
    UserInfo uInfo = UserInfo.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mEnterQueue = (Button) findViewById(R.id.enter_queue_button);
        mNewQueue = (Button) findViewById(R.id.new_queue_button);

        mNewUser = (TextView) findViewById(R.id.user_name_text);
        mQueueID = (TextView) findViewById(R.id.queue_id_text);

        Connector.initializeDB();

        mNewQueue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mNewUser.getText() == null){
                    Toast.makeText(HomeScreen.this, "Please enter a username", Toast.LENGTH_SHORT).show();
                }else{
                    uInfo.registerUser((String) mNewUser.getText());
                    Connector.registerUser(uInfo.getUserName(), uInfo.getHashedUserName(), uInfo.getQueueID());
                }
            }
        });
    }
}
