package smaxd.aphina_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class howtosled extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtosled);
    }
    public void BackToMenu(View view) {
        Intent intent = new Intent(this, Sled_menu.class);
        startActivity(intent);
    }
    public void Starttrain(View view) {
        Intent startintent = new Intent(this, Sled.class);
        this.startActivity(startintent);
    }
}
