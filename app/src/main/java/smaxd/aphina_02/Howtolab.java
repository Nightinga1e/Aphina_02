package smaxd.aphina_02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Howtolab extends AppCompatActivity {

    private String[] levelNames = {"Легко", "Нормально", "Сложно" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_howtolab);
    }
    public void BackToMenu(View view) {
        Intent intent = new Intent(this, Labirint_menu.class);
        startActivity(intent);
    }
    private void startPlay(int chosenLevel) {
        // start gameplay
        Intent playIntent = new Intent(this, Labirint.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }

    public void Starttrain(View view) {
        // play button
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
