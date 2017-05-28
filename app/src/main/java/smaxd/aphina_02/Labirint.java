package smaxd.aphina_02;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;

public class Labirint extends Activity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {


    private GoogleApiClient mGoogleApiClient;
    private int level = 0, answer = 0, lifecount= 3, hercules_coordl =0, hercules_coordm =0;
//    private int enteredAnswer;

    private int entAns;
  //  private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
  //          {2, 3, 5}};
  //  private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
   //         {10, 50, 100}};

    private SharedPreferences LabPrefs;
    public static final String LAB_PREFS = "LabirFile";

    private int[] Imgarr2 = {
            R.mipmap.ic_labirint,
            R.mipmap.ic_hercules
    };

    private int[] Imgarr1= {
            R.mipmap.ic_left,
            R.mipmap.ic_right,
            R.mipmap.ic_up,
            R.mipmap.ic_down
    };

   // private int hercules_coords;
    private ImageButton life1,life2,life3;
    private Random random;
    private TextView scoreTxt;
    private ImageView response;
    private TextView mTimer;


    private int[][] fillarray = {
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
    };
    private List<ImageButton> imgbuttons;
    private static final int[] IMGBUTTON_IDS = {
            R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6,
            R.id.img7, R.id.img8, R.id.img9, R.id.img10, R.id.img11, R.id.img12,
            R.id.img13, R.id.img14, R.id.img15, R.id.img16, R.id.img17, R.id.img18,
            R.id.img19, R.id.img20, R.id.img21, R.id.img22, R.id.img23, R.id.img24,
            R.id.img25, R.id.img26, R.id.img27, R.id.img28, R.id.img29, R.id.img30,
            R.id.img31, R.id.img32, R.id.img33, R.id.img34, R.id.img35, R.id.img36
    };


    private List<ImageButton> putbuttons;
    private static final int[] PUT_IDS = {
            R.id.put1, R.id.put2, R.id.put3, R.id.put4, R.id.put5, R.id.put6
    };

    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labirint);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        LabPrefs = getSharedPreferences(LAB_PREFS, 0);

        imgbuttons = new ArrayList<ImageButton>();
        for (int id : IMGBUTTON_IDS) {
            ImageButton imgbut = (ImageButton) findViewById(id);
              imgbut.setOnClickListener(this); //
            imgbuttons.add(imgbut);
        }
        for (int i=0;i<fillarray.length;i++){
            imgbuttons.get(i).setTag(i);
        }

        putbuttons = new ArrayList<ImageButton>();
        for (int id : PUT_IDS) {
            ImageButton putbut = (ImageButton) findViewById(id);
            putbuttons.add(putbut);
        }

        scoreTxt = (TextView) findViewById(R.id.score);

        life1 = (ImageButton) findViewById(R.id.life1);
        life2 = (ImageButton) findViewById(R.id.life2);
        life3 = (ImageButton) findViewById(R.id.life3);
        life3.setVisibility(View.VISIBLE);
        life2.setVisibility(View.VISIBLE);
        life1.setVisibility(View.VISIBLE);

        response = (ImageView) findViewById(R.id.response);
        response.setVisibility(View.INVISIBLE);

        mTimer = (TextView) findViewById(R.id.timer);

        if (savedInstanceState != null) {
            // restore state
            level = savedInstanceState.getInt("level");
            int exScore = savedInstanceState.getInt("score");
            scoreTxt.setText("Score: " + exScore);
        } else {
            Bundle extras = getIntent().getExtras();
            if (extras != null) {
                int passedLevel = extras.getInt("level", -1);
                if (passedLevel >= 0)
                    level = passedLevel;
            }
        }

        random = new Random();
        chooseField();

        //Создаем таймер обратного отсчета на 10 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        countDownTimer = new CountDownTimer(60000, 1000) {
            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                mTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mTimer.setText("0");
                finish();
            }
        };
        countDownTimer.start();

    }


    private void setHighScore() {
        // set high score
        int exScore = getScore();
        if (exScore > 0) {
            SharedPreferences.Editor scoreEdit = LabPrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = LabPrefs.getString("highScores", "");

            if (scores.length() > 0) {
                // we have existing scores
                List<Score> scoreStrings = new ArrayList<Score>();
                String[] exScores = scores.split("\\|");
                for (String eSc : exScores) {
                    String[] parts = eSc.split(" - ");
                    scoreStrings.add(new Score(parts[0], Integer
                            .parseInt(parts[1])));
                }

                Score newScore = new Score(dateOutput, exScore);
                scoreStrings.add(newScore);
                Collections.sort(scoreStrings);

                StringBuilder scoreBuild = new StringBuilder("");
                for (int s = 0; s < scoreStrings.size(); s++) {
                    if (s >= 10)
                        break;// only want ten
                    if (s > 0)
                        scoreBuild.append("|");// pipe separate the score
                    // strings
                    scoreBuild.append(scoreStrings.get(s).getScoreText());
                }
                // write to prefs
                scoreEdit.putString("highScores", scoreBuild.toString());
                scoreEdit.commit();
            } else {
                // no existing scores
                scoreEdit.putString("highScores", "" + dateOutput + " - "
                        + exScore);
                scoreEdit.commit();
            }
        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        // findViewById(R.id.sign_in_button).setVisibility(View.GONE);
        //  findViewById(R.id.sign_out_button).setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        //  mGoogleApiClient.connect();
    }
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //  if (mResolvingConnectionFailure) {
        // already resolving
        return;
    }
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }


    protected void onDestroy() {
        setHighScore();
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        //     save state
        int exScore = getScore();
        savedInstanceState.putInt("score", exScore);
        savedInstanceState.putInt("level", level);
        super.onSaveInstanceState(savedInstanceState);
    }



    @Override
    public void onClick(View view) {
        //  response.setVisibility(View.INVISIBLE);
        switch (view.getId()){
            case R.id.img1:
                entAns = 1;
                break;
            case R.id.img2:
                entAns = 1;
                break;
            case R.id.img3:
                entAns = 2;
                break;
            case R.id.img4:
                entAns = 3;
                break;
            case R.id.img5:
                entAns = 4;
                break;
            case R.id.img6:
                entAns = 5;
                break;
            case R.id.img7:
                entAns = 6;
                break;
            case R.id.img8:
                entAns = 7;
                break;
            case R.id.img9:
                entAns = 8;
                break;
            case R.id.img10:
                entAns = 9;
                break;
            case R.id.img11:
                entAns = 10;
                break;
            case R.id.img12:
                entAns = 11;
                break;
            case R.id.img13:
                entAns = 12;
                break;
            case R.id.img14:
                entAns = 13;
                break;
            case R.id.img15:
                entAns = 14;
                break;
            case R.id.img16:
                entAns = 15;
                break;
            case R.id.img17:
                entAns = 16;
                break;
            case R.id.img18:
                entAns = 17;
                break;
            case R.id.img19:
                entAns = 18;
                break;
            case R.id.img20:
                entAns = 19;
                break;
            case R.id.img21:
                entAns = 20;
                break;
            case R.id.img22:
                entAns = 21;
                break;
            case R.id.img23:
                entAns = 22;
                break;
            case R.id.img24:
                entAns = 23;
                break;
            case R.id.img25:
                entAns = 24;
                break;
            case R.id.img26:
                entAns = 25;
                break;
            case R.id.img27:
                entAns = 26;
                break;
            case R.id.img28:
                entAns = 27;
                break;
            case R.id.img29:
                entAns = 28;
                break;
            case R.id.img30:
                entAns = 29;
                break;
            case R.id.img31:
                entAns = 30;
                break;
            case R.id.img32:
                entAns = 31;
                break;
            case R.id.img33:
                entAns = 32;
                break;
            case R.id.img34:
                entAns = 33;
                break;
            case R.id.img35:
                entAns = 34;
                break;
            case R.id.img36:
                entAns = 35;
                break;
        }

        if(entAns!=0){
            int exScore = getScore();
            if((Integer.toString(entAns))==(Integer.toString(answer))){
                //correct
                scoreTxt.setText("Score: "+(exScore+1));
                if (exScore+1==15 && mGoogleApiClient.isConnected()){
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_pathfinder));
                }
                response.setImageResource(R.drawable.tick);
                response.setVisibility(View.VISIBLE);
                chooseField();

            }else{
                //incorrect
                scoreTxt.setText("Score: "+(exScore));
                response.setImageResource(R.drawable.cross);
                lifecount-=1;
                if (lifecount==2) {
                    life1.setVisibility(View.INVISIBLE);
                }else if (lifecount==1) {
                    life2.setVisibility(View.INVISIBLE);
                }else if (lifecount==0){
                    finish();
                }
                response.setVisibility(View.VISIBLE);
            }
        }
    }

    private void chooseField() {

        // get a field
        answer = 0;
        hercules_coordl = random.nextInt(6);
        hercules_coordm = random.nextInt(6);
        for (int l = 0; l < 6; l++) {
            for (int m = 0; m < 6; m++) {
                fillarray[l][m] = 0;
                if (l == hercules_coordl && m ==hercules_coordm) {
                    fillarray[l][m] = 1;

                    }
            }
        }

        for (int l = 0; l < 6; l++) {
            for (int m = 0; m < 6; m++) {
                    imgbuttons.get(l*6+m).setImageResource(Imgarr2[fillarray[l][m]]);
            }
        }

        int tempcoordl =hercules_coordm;
        int tempcoordm =hercules_coordl;
        int hard=0;
        if (level==0) {
            hard = 4 ;
            putbuttons.get(4).setVisibility(View.INVISIBLE);
            putbuttons.get(5).setVisibility(View.INVISIBLE);
        }else if (level==1) {
            hard = 5 ;
            putbuttons.get(5).setVisibility(View.INVISIBLE);
        }else if (level==2) {
            hard = 6 ;
        }
        for (int l = 0; l < hard ;l++){
            int exither =random.nextInt(4);
            switch (exither){
                case 0:
                    if (tempcoordl-1>0) {
                        putbuttons.get(l).setImageResource(Imgarr1[0]);
                        tempcoordl=tempcoordl-1;
                    }else{
                        putbuttons.get(l).setImageResource(Imgarr1[1]);
                        tempcoordl=tempcoordl+1;
                    }
                    break;
                case 1:
                    if(tempcoordl+1<6){
                        putbuttons.get(l).setImageResource(Imgarr1[1]);
                        tempcoordl=tempcoordl+1;
                    }else {
                        putbuttons.get(l).setImageResource(Imgarr1[0]);
                        tempcoordl=tempcoordl-1;
                    }
                    break;
                case 2:
                    if(tempcoordm-1>-1){
                        putbuttons.get(l).setImageResource(Imgarr1[2]);
                        tempcoordm=tempcoordm-1;
                    }else {
                        putbuttons.get(l).setImageResource(Imgarr1[3]);
                        tempcoordm=tempcoordm+1;
                    }
                    break;
                case 3:
                    if(tempcoordm+1<6){
                        putbuttons.get(l).setImageResource(Imgarr1[3]);
                        tempcoordm=tempcoordm+1;
                    }else{
                        putbuttons.get(l).setImageResource(Imgarr1[2]);
                        tempcoordm=tempcoordm-1;
                    }
                    break;
            }

        }
         answer = tempcoordm*6+tempcoordl;

    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }
}