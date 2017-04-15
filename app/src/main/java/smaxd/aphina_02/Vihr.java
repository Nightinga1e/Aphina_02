package smaxd.aphina_02;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Vihr extends Activity implements OnClickListener {

    private int level = 0, answer = 1, lifecount= 3;
    private int entAns;
    private String enteredAnswer;
    private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
            {2, 3, 5}};
    private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
            {10, 50, 100}};
    private int lvl= 1;

    private SharedPreferences VihrPrefs;
    public static final String VIHR_PREFS = "VihrFile";

    private int[] Imgarr = {R.mipmap.pic1,
            R.mipmap.pic2,
            R.mipmap.pic3,
            R.mipmap.pic4,
            R.mipmap.pic5,
            R.mipmap.pic6,
            R.mipmap.ic_pic7,
            R.mipmap.ic_pic8,
            R.mipmap.ic_pic9,
            R.mipmap.ic_pic10,
            R.mipmap.ic_pic11,
            R.mipmap.ic_pic12,
            R.mipmap.ic_pic13,
            R.mipmap.ic_pic14,
            R.mipmap.ic_pic15,
            R.mipmap.ic_pic16,
            R.mipmap.ic_pic17,
            R.mipmap.ic_pic18,
            R.mipmap.ic_pic19,
            R.mipmap.ic_pic20,
            R.mipmap.ic_pic21,
            R.mipmap.ic_pic22,
            R.mipmap.ic_pic23,
            R.mipmap.ic_pic24,
            R.mipmap.ic_pic25,
            R.mipmap.ic_pic26,
            R.mipmap.ic_pic27,
            R.mipmap.ic_pic28,
            R.mipmap.ic_pic29,
            R.mipmap.ic_pic30,
            R.mipmap.ic_pic31,
            R.mipmap.ic_pic32,
            R.mipmap.ic_pic33,
            R.mipmap.ic_pic34,
            R.mipmap.ic_pic35,
            R.mipmap.ic_pic36};

    private ImageButton life1,life2,life3;
    private Random random;
    private TextView scoreTxt,answerfu;
    private ImageView response;
    private TextView mTimer;

    private int[] fillarray = {0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};
    private int[] picnumarray = {1, 2, 3, 4, 5, 6,
            7, 8, 9, 10, 11, 12,
            13, 14, 15, 16, 17, 18,
            19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35};

    private List<ImageButton> imgbuttons;
    private static final int[] IMGBUTTON_IDS = {
            R.id.img1, R.id.img2, R.id.img3, R.id.img4, R.id.img5, R.id.img6,
            R.id.img7, R.id.img8, R.id.img9, R.id.img10, R.id.img11, R.id.img12,
            R.id.img13, R.id.img14, R.id.img15, R.id.img16, R.id.img17, R.id.img18,
            R.id.img19, R.id.img20, R.id.img21, R.id.img22, R.id.img23, R.id.img24,
            R.id.img25, R.id.img26, R.id.img27, R.id.img28, R.id.img29, R.id.img30,
            R.id.img31, R.id.img32, R.id.img33, R.id.img34, R.id.img35, R.id.img36
    };

    CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vihr);

        VihrPrefs = getSharedPreferences(VIHR_PREFS, 0);


        shuffleArray(picnumarray);


        imgbuttons = new ArrayList<ImageButton>();
        // or slightly better
        // buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for (int id : IMGBUTTON_IDS) {
            ImageButton imgbut = (ImageButton) findViewById(id);
            imgbut.setOnClickListener(this); //
            imgbuttons.add(imgbut);
        }

        response = (ImageView) findViewById(R.id.response);
        scoreTxt = (TextView) findViewById(R.id.score);
        response.setVisibility(View.INVISIBLE);

        answerfu =(TextView) findViewById(R.id.Answerfu);

        answerfu.setVisibility(View.INVISIBLE);

        life1 = (ImageButton) findViewById(R.id.life1);
        life2 = (ImageButton) findViewById(R.id.life2);
        life3 = (ImageButton) findViewById(R.id.life3);
        life3.setVisibility(View.VISIBLE);
        life2.setVisibility(View.VISIBLE);
        life1.setVisibility(View.VISIBLE);
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
                for (int l = 0; l < fillarray.length; l++) {
                    imgbuttons.get(l).setVisibility(View.INVISIBLE);
                }
                finish();
            }
        };
        countDownTimer.start();
    }


    private void setHighScore() {
        // set high score
        int exScore = getScore();
        if (exScore > 0) {
            SharedPreferences.Editor scoreEdit = VihrPrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = VihrPrefs.getString("highScores", "");

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
                entAns = 2;
                break;
            case R.id.img3:
                entAns = 3;
                break;
            case R.id.img4:
                entAns = 4;
                break;
            case R.id.img5:
                entAns = 5;
                break;
            case R.id.img6:
                entAns = 6;
                break;
            case R.id.img7:
                entAns = 7;
                break;
            case R.id.img8:
                entAns = 8;
                break;
            case R.id.img9:
                entAns = 9;
                break;
            case R.id.img10:
                entAns = 10;
                break;
            case R.id.img11:
                entAns = 11;
                break;
            case R.id.img12:
                entAns = 12;
                break;
            case R.id.img13:
                entAns = 13;
                break;
            case R.id.img14:
                entAns = 14;
                break;
            case R.id.img15:
                entAns = 15;
                break;
            case R.id.img16:
                entAns = 16;
                break;
            case R.id.img17:
                entAns = 17;
                break;
            case R.id.img18:
                entAns = 18;
                break;
            case R.id.img19:
                entAns = 19;
                break;
            case R.id.img20:
                entAns = 20;
                break;
            case R.id.img21:
                entAns = 21;
                break;
            case R.id.img22:
                entAns = 22;
                break;
            case R.id.img23:
                entAns = 23;
                break;
            case R.id.img24:
                entAns = 24;
                break;
            case R.id.img25:
                entAns = 25;
                break;
            case R.id.img26:
                entAns = 26;
                break;
            case R.id.img27:
                entAns = 27;
                break;
            case R.id.img28:
                entAns = 28;
                break;
            case R.id.img29:
                entAns = 29;
                break;
            case R.id.img30:
                entAns = 30;
                break;
            case R.id.img31:
                entAns = 31;
                break;
            case R.id.img32:
                entAns = 32;
                break;
            case R.id.img33:
                entAns = 33;
                break;
            case R.id.img34:
                entAns = 34;
                break;
            case R.id.img35:
                entAns = 35;
                break;
            case R.id.img36:
                entAns = 36;
                break;
        }

        if(entAns!=0){
            int exScore = getScore();
            if((Integer.toString(entAns))==(Integer.toString(answer))){
                //correct
                scoreTxt.setText("Score: "+(exScore+1));
                response.setImageResource(R.drawable.tick);
                response.setVisibility(View.VISIBLE);
                //  countDownTimer.start();
                lvl=lvl+1;
                if (lvl==35){
                    finish();
                }
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

        answerfu.setVisibility(View.INVISIBLE);
        // get a field

        for (int l = 0; l < 36; l++) {
            fillarray[l]=0;
        }

        for (int l=0; l < lvl; l++){
            fillarray[l]= picnumarray[l];

        }

        shuffleArray(fillarray);

        for (int l=0; l < 36; l++){
            if (fillarray[l]==picnumarray[lvl-1] && l!=0) {
                answer = l+1;
            }
        }


        for (int l = 0; l < 36; l++) {
            if (fillarray[l]!=0) {
                imgbuttons.get(l).setVisibility(View.VISIBLE);
                imgbuttons.get(l).setImageResource(Imgarr[fillarray[l]]);
            } else {
                imgbuttons.get(l).setVisibility(View.INVISIBLE);
            }
        }
    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }
}