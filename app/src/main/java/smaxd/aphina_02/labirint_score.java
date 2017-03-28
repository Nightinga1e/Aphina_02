package smaxd.aphina_02;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class labirint_score extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labirint_score);

        TextView scoreView = (TextView) findViewById(R.id.high_scores_list);
        SharedPreferences scorePrefs = getSharedPreferences(
                Labirint.LAB_PREFS, 0);
        String[] savedScores = scorePrefs.getString("highScores", "").split(
                "\\|");
        StringBuilder scoreBuild = new StringBuilder("");
        for (String score : savedScores) {
            scoreBuild.append(score + "\n");
        }
        scoreView.setText(scoreBuild.toString());
    }
}
