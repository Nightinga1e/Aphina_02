package smaxd.aphina_02;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LabirintMenu extends Activity implements OnClickListener {

    private String[] levelNames = {"Легко", "Нормально", "Сложно" };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labirint_menu);

        Button playBtn = (Button) findViewById(R.id.play_btn);
        Button helpBtn = (Button) findViewById(R.id.Menubut);
        Button highBtn = (Button) findViewById(R.id.high_btn);

        playBtn.setOnClickListener(this);
        helpBtn.setOnClickListener(this);
        highBtn.setOnClickListener(this);
    }
/*
    private void startPlay(int chosenLevel) {
        // start gameplay
        Intent playIntent = new Intent(this, Labirint.class);
        playIntent.putExtra("level", chosenLevel);
        this.startActivity(playIntent);
    }
*/

@Override
public void onBackPressed() {

    Intent intent = new Intent(LabirintMenu.this, Trainer.class);
    startActivity(intent);
    super.onBackPressed();
}


    @Override
    public void onClick(View view) {
        //
        if (view.getId() == R.id.play_btn) {
            // play button
            if (view.getId() == R.id.play_btn) {
                Intent helpIntent = new Intent(this, HowToLabirint.class);
                this.startActivity(helpIntent);
            }
            } else if (view.getId() == R.id.Menubut) {
            // how to play button
            Intent helpIntent = new Intent(this, Trainer.class);
            this.startActivity(helpIntent);
        } else if (view.getId() == R.id.high_btn) {
            // high scores button
            Intent highIntent = new Intent(this, LabirintScore.class);
            this.startActivity(highIntent);
        }
        // high scores button
    }
}