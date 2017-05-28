package smaxd.aphina_02;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.content.SharedPreferences;
import android.graphics.Color;
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

public class Assoc extends Activity implements
        View.OnClickListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    private boolean mAutoStartSignInFlow = true;

    private GoogleApiClient mGoogleApiClient;

    private int level = 0, answer = 1, lifecount= 3;
    private int entAns;
    private int ans1,ans2,ans3,ans4,ans5,ans6,ans7,ans8;
    private int anstype,anstype2,anscolor,anscolor2;
   // private String enteredAnswer;
  //  private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
  //          {2, 3, 5}};
  //  private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
  //          {10, 50, 100}};
    private int lvl= 1,check= 1;

    private SharedPreferences AssocPrefs;
    public static final String ASSOC_PREFS = "AssocFile";

    private ImageButton life1,life2,life3;
    private ImageButton answer1,answer2,answer3,answer4;
    private Random random;
    private TextView scoreTxt;
    private ImageView response;
    private TextView mTimer;

    private TextView question;

    private TextView question2;
    private int[] colors = {
            Color.rgb(233, 30, 99),
            Color.rgb(13, 84, 202),
            Color.rgb(244, 67, 54),
            Color.rgb(255, 235, 59),
            Color.rgb(255, 255, 255),
            Color.rgb(76, 175, 80)
    };


    private int[] Imgarr = {
            R.mipmap.pinkcircle,
            R.mipmap.bluecircle,
            R.mipmap.redcircle,
            R.mipmap.yellowcircle,
            R.mipmap.whitecircle,
            R.mipmap.greencircle,

            R.mipmap.pinksquare,
            R.mipmap.bluesquare,
            R.mipmap.redsquare,
            R.mipmap.yellowquare,
            R.mipmap.whitesquare,
            R.mipmap.greenquare,

            R.mipmap.pinktriangle,
            R.mipmap.bluetriangle,
            R.mipmap.redtriangle,
            R.mipmap.yellowtriangle,
            R.mipmap.whitetriangle,
            R.mipmap.greentriangle,

            R.mipmap.pinktwo,
            R.mipmap.bluetwo,
            R.mipmap.redtwo,
            R.mipmap.yellowtwo,
            R.mipmap.whitetwo,
            R.mipmap.greentwo,

            R.mipmap.pinkzero,
            R.mipmap.bluezero,
            R.mipmap.redzero,
            R.mipmap.yellowzero,
            R.mipmap.whitezero,
            R.mipmap.greenzero,

            R.mipmap.pinkstar,
            R.mipmap.bluestar,
            R.mipmap.redstar,
            R.mipmap.yellowstar,
            R.mipmap.whitestar,
            R.mipmap.greenstar
            };

    private int[] shapearray = {1, 2, 3, 4, 5, 6};
    private int[] colorarray = {1, 2, 3, 4, 5, 6};

    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assoc);


        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .build();

        AssocPrefs = getSharedPreferences(ASSOC_PREFS, 0);

        answer1 =(ImageButton) findViewById(R.id.answer1);
        answer2 =(ImageButton) findViewById(R.id.answer2);
        answer3 =(ImageButton) findViewById(R.id.answer3);
        answer4 =(ImageButton) findViewById(R.id.answer4);

        answer1.setOnClickListener(this);
        answer2.setOnClickListener(this);
        answer3.setOnClickListener(this);
        answer4.setOnClickListener(this);

        response = (ImageView) findViewById(R.id.response);
        scoreTxt = (TextView) findViewById(R.id.score);
        response.setVisibility(View.INVISIBLE);

        life1 = (ImageButton) findViewById(R.id.life1);
        life2 = (ImageButton) findViewById(R.id.life2);
        life3 = (ImageButton) findViewById(R.id.life3);
        life3.setVisibility(View.VISIBLE);
        life2.setVisibility(View.VISIBLE);
        life1.setVisibility(View.VISIBLE);
        mTimer = (TextView) findViewById(R.id.timer);
        question = (TextView) findViewById(R.id.question);
        question2 = (TextView) findViewById(R.id.question2);

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
            SharedPreferences.Editor scoreEdit = AssocPrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = AssocPrefs.getString("highScores", "");

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
        if (anstype!=1){
            switch (view.getId()) {
                case R.id.answer1:
                    entAns = ans1;
                    break;
                case R.id.answer2:
                    entAns = ans2;
                    break;
                case R.id.answer3:
                    entAns = ans3;
                    break;
                case R.id.answer4:
                    entAns = ans4;
                    break;
            }}else
        {
            switch (view.getId()) {
                case R.id.answer1:
                    entAns = ans5;
                    break;
                case R.id.answer2:
                    entAns = ans6;
                    break;
                case R.id.answer3:
                    entAns = ans7;
                    break;
                case R.id.answer4:
                    entAns = ans8;
                    break;
            }
        }

        if(entAns!=0) {
            int exScore = getScore();

            if (answer == entAns) {
                //correct
                scoreTxt.setText("Score: " + (exScore + 1));
                if (exScore+1==40 && mGoogleApiClient.isConnected()){
                    Games.Achievements.unlock(mGoogleApiClient, getString(R.string.achievement_associate_this));
                }
                response.setImageResource(R.drawable.tick);
                response.setVisibility(View.VISIBLE);
                lvl = lvl + 1;
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
                chooseField();
            }
        }
    }

    public static void shuffleArray(int[] a) {
        int n = a.length;
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(int[] a, int i, int change) {
        int temp = a[i];
        a[i] = a[change];
        a[change] = temp;

    }



    private void chooseField() {

        shuffleArray(shapearray);
        shuffleArray(colorarray);

        anstype = random.nextInt(2);
        anstype2 = random.nextInt(4);
        anscolor = random.nextInt(6);
        anscolor2 = random.nextInt(4);

            answer1.setImageResource(Imgarr[shapearray[0] * 6 - 6 + colorarray[0] - 1]);
            answer2.setImageResource(Imgarr[shapearray[1] * 6 - 6 + colorarray[1] - 1]);
            answer3.setImageResource(Imgarr[shapearray[2] * 6 - 6 + colorarray[2] - 1]);
            answer4.setImageResource(Imgarr[shapearray[3] * 6 - 6 + colorarray[3] - 1]);

            ans1=shapearray[0];
            ans2=shapearray[1];
            ans3=shapearray[2];
            ans4=shapearray[3];

            ans5=colorarray[0];
            ans6=colorarray[1];
            ans7=colorarray[2];
            ans8=colorarray[3];



        switch (anstype) {
            case 0:
                question.setText("Значение");

                switch (shapearray[anstype2]) {
                    case 1:
                        question2.setText("круг");
                        break;
                    case 2:
                        question2.setText("квадрат");
                        break;
                    case 3:
                        question2.setText("треугольник");
                        break;
                    case 4:
                        question2.setText("два");
                        break;
                    case 5:
                        question2.setText("ноль");
                        break;
                    case 6:
                        question2.setText("звезда");
                        break;
                }
                question.setTextColor(colors[anscolor]);
                question2.setTextColor(colors[anscolor2]);
                answer=shapearray[anstype2];
                break;

            case 1:
                question.setText("Цвет");

                switch (colorarray[anscolor2]) {
                    case 1:
                        question2.setText("зеленый");
                        question2.setTextColor(colors[0]);
                        answer=1;
                        break;
                    case 2:
                        question2.setText("белый");
                        question2.setTextColor(colors[1]);
                        answer=2;
                        break;
                    case 3:
                        question2.setText("желтый");
                        question2.setTextColor(colors[2]);
                        answer=3;
                        break;
                    case 4:
                        question2.setText("красный");
                        question2.setTextColor(colors[3]);
                        answer=4;
                        break;
                    case 5:
                        question2.setText("синий");

                        question2.setTextColor(colors[4]);
                        answer=5;
                        break;
                    case 6:
                        question2.setText("розовый");
                        question2.setTextColor(colors[5]);
                        answer=6;
                        break;
                }
                question.setTextColor(colors[anscolor]);
                break;
        }
    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }

}