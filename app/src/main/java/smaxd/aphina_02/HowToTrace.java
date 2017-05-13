package smaxd.aphina_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HowToTrace extends AppCompatActivity {

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(HowToTrace.this, TraceMenu.class);
        startActivity(intent);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtosled);
    }
    public void BackToMenu(View view) {
        Intent intent = new Intent(this, TraceMenu.class);
        startActivity(intent);
    }
    public void Starttrain(View view) {
        Intent startintent = new Intent(this, Trace.class);
        this.startActivity(startintent);
    }
}
