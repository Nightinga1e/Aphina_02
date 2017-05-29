package smaxd.aphina_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_statistics);
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
    public void ToSled(View view) {
        Intent intent = new Intent(this, TraceScore.class);
        startActivity(intent);
    }
    public void ToVihr(View view) {
        Intent intent = new Intent(this, WhirlScore.class);
        startActivity(intent);
    }
    public void ToMatem(View view) {
        Intent intent = new Intent(this, HighScores.class);
        startActivity(intent);
    }
    public void Tonumber(View view) {
        Intent intent = new Intent(this, NumberScore.class);
        startActivity(intent);
    }
    public void Topamyat(View view) {
        Intent intent = new Intent(this, MemoryScore.class);
        startActivity(intent);
    }
    public void Tolabirint(View view) {
        Intent intent = new Intent(this, LabirintScore.class);
        startActivity(intent);
    }
    public void ToAssoc(View view) {
        Intent intent = new Intent(this, AssocScore.class);
        startActivity(intent);
    }
}
