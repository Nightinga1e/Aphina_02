package smaxd.aphina_02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class howtopamyat extends AppCompatActivity {

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(howtopamyat.this, pamyat_menu.class);
        startActivity(intent);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtopamyat);
    }
    public void BackToMenu(View view) {
        Intent intent = new Intent(this, pamyat_menu.class);
        startActivity(intent);
    }
    public void Starttrain(View view) {
        Intent startintent = new Intent(this, pamyat.class);
        this.startActivity(startintent);
    }
}
