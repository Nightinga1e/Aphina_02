package smaxd.aphina_02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HowTo extends AppCompatActivity {

    private String[] levelNames = {"Легко", "Нормально", "Сложно" };

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(HowTo.this, Matem.class);
        startActivity(intent);
        super.onBackPressed();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to);
    }

    public void BackToMenu(View view) {
        Intent intent = new Intent(this, Matem.class);
        startActivity(intent);
    }

    private void startPlay(int chosenLevel) {
        // start gameplay
        Intent playIntent = new Intent(this, Matem_game.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }

    public void Starttrain(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("").setSingleChoiceItems(levelNames,
                0, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        // start gameplay
                        startPlay(which);
                    }
                });
        AlertDialog ad = builder.create();
        ad.show();
    }
}
