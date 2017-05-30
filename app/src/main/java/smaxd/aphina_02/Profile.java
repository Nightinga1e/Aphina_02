package smaxd.aphina_02;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Profile extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;

    private static final int REQUEST_ACHIEVEMENTS = 5;
   // private GoogleApiClient mGoogleApiClient;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();
    }



    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        //  findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
       //  Attempt to reconnect
          mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //  if (mResolvingConnectionFailure) {
        // already resolving
        return;
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public void ToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void ToProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void ToQuest(View view) {
        Intent intent = new Intent(this, Quest.class);
        startActivity(intent);
    }
    public void ToTrainers(View view) {
        Intent intent = new Intent(this, Trainer.class);
        startActivity(intent);
    }
    public void ToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void ToStats(View view) {
        Intent intent = new Intent(this, Statistics.class);
        startActivity(intent);
    }
    public void ToAchievements(View view) {

        if (mGoogleApiClient.isConnected()){
        startActivityForResult(Games.Achievements.getAchievementsIntent(MainActivity.mGoogleApiClient),
                REQUEST_ACHIEVEMENTS);
        }
        }
    }


