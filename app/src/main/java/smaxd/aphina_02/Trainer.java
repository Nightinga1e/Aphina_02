package smaxd.aphina_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Trainer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trainer);
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
        Intent intent = new Intent(this, Sled.class);
        startActivity(intent);
    }
    public void ToVihr(View view) {
        Intent intent = new Intent(this, Vihr.class);
        startActivity(intent);
    }
    public void ToMatem(View view) {
        Intent intent = new Intent(this, Matem.class);
        startActivity(intent);
    }

}
