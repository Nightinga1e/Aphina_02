package smaxd.aphina_02;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AssocScore extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
       // getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assocscore);

        Button ResetBtn = (Button) findViewById(R.id.button2);
        ResetBtn.setOnClickListener(this);

        TextView scoreView = (TextView) findViewById(R.id.high_scores_list);
        SharedPreferences scorePrefs = getSharedPreferences(
                Assoc.ASSOC_PREFS, 0);
        String[] savedScores = scorePrefs.getString("highScores", "").split(
                "\\|");
        StringBuilder scoreBuild = new StringBuilder("");
        for (String score : savedScores) {
            scoreBuild.append(score + "\n");
        }
        scoreView.setText(scoreBuild.toString());
    }


    @Override
    public void onClick(View view) {
        //
        if (view.getId() == R.id.button2) {
            Reset();
        }
        if (view.getId() == R.id.button8) {
           // Intent intent = new Intent(this, AssocMenu.class);
             //   startActivity(intent);
            finish();
                    }
    }

    public void Reset() {
        TextView scoreView = (TextView) findViewById(R.id.high_scores_list);
        SharedPreferences scorePrefs = getSharedPreferences(
                Assoc.ASSOC_PREFS, 0);
        String[] savedScores = scorePrefs.getString("highScores", "").split(
                "\\|");
        StringBuilder scoreBuild = new StringBuilder("");
        for (String score : savedScores) {
            scoreBuild.append("" + "\n");
        }
        scoreView.setText(scoreBuild.toString());
        getSharedPreferences(Assoc.ASSOC_PREFS,0).edit().clear().commit();
    }
}
