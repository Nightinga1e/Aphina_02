package smaxd.aphina_02;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class number_menu extends Activity implements OnClickListener {

    private String[] levelNames = {"Легко", "Нормально", "Сложно" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_menu);

        Button playBtn = (Button) findViewById(R.id.play_btn);
        Button helpBtn = (Button) findViewById(R.id.help_btn);
        Button highBtn = (Button) findViewById(R.id.high_btn);

        playBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        highBtn.setOnClickListener(this);
    }

  /*  @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    private void startPlay(int chosenLevel) {
        // start gameplay
        Intent playIntent = new Intent(this, number.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }

    @Override
    public void onClick(View view) {
        //
        if (view.getId() == R.id.play_btn) {
            // play button
            if (view.getId() == R.id.play_btn) {
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
        } else if (view.getId() == R.id.help_btn) {
            // how to play button
            Intent helpIntent = new Intent(this, HowToNumber.class);
            this.startActivity(helpIntent);
        } else if (view.getId() == R.id.high_btn) {
            // high scores button
            Intent highIntent = new Intent(this, numberscore.class);
            this.startActivity(highIntent);
        }
        // high scores button
    }
}