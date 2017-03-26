package smaxd.aphina_02;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class number extends Activity implements OnClickListener {

    private int level = 0, answer = 0, lifecount= 3;
    String enteredAnswer;
    private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
            {2, 3, 5}};
    private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
            {10, 50, 100}};

    private SharedPreferences numPrefs;
    public static final String NUM_PREFS = "NumberFile";

    private int[] Imgarr = {R.mipmap.pic1,
            R.mipmap.pic2,
            R.mipmap.pic3,
            R.mipmap.pic4,
            R.mipmap.pic5,
            R.mipmap.pic6};

    private ImageButton life1,life2,life3;
    private Random random;
    private TextView scoreTxt;
    private ImageView response;
    private Button btn1, btn2, btn3, btn4;
    private TextView mTimer;

    private int[] fillarray = {0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0};

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
        setContentView(R.layout.activity_number);

        numPrefs = getSharedPreferences(NUM_PREFS, 0);

        imgbuttons = new ArrayList<ImageButton>();
        // or slightly better
        // buttons = new ArrayList<Button>(BUTTON_IDS.length);
        for (int id : IMGBUTTON_IDS) {
            ImageButton imgbut = (ImageButton) findViewById(id);
            //  imgbut.setOnClickListener(this); //
            imgbuttons.add(imgbut);
        }

        response = (ImageView) findViewById(R.id.response);
        scoreTxt = (TextView) findViewById(R.id.score);
        response.setVisibility(View.INVISIBLE);

        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);

        btn1.setOnClickListener(this);
        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        btn4.setOnClickListener(this);

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

        //Создаем таймер обратного отсчета на 20 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        countDownTimer = new CountDownTimer(10000, 1000) {
            //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
            public void onTick(long millisUntilFinished) {
                mTimer.setText("" + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
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
                chooseField();
                countDownTimer.start();
            }
        };
        countDownTimer.start();
    }


    private void setHighScore() {
        // set high score
        int exScore = getScore();
        if (exScore > 0) {
            SharedPreferences.Editor scoreEdit = numPrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = numPrefs.getString("highScores", "");

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
            case R.id.btn1:
                enteredAnswer = (btn1).getText().toString();
                break;
            case R.id.btn2:
                enteredAnswer = (btn2).getText().toString();
                break;
            case R.id.btn3:
                enteredAnswer = (btn3).getText().toString();
                break;
            case R.id.btn4:
                enteredAnswer = (btn4).getText().toString();
                break;
        }
        if(enteredAnswer!=null){
            int exScore = getScore();
            if(enteredAnswer==(Integer.toString(answer))){
                //correct
                scoreTxt.setText("Score: "+(exScore+1));
                response.setImageResource(R.drawable.tick);
                response.setVisibility(View.VISIBLE);
                countDownTimer.start();

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
                //    Intent intent = new Intent(this, number_menu.class);
                 //   startActivity(intent);
                    finish();
                }
                response.setVisibility(View.VISIBLE);
            }
            chooseField();
            countDownTimer.start();
            }
    }

    private void chooseField() {
        // get a field
        answer = 0;
        int otv = random.nextInt(4) + 1;

        for (int l = 0; l < fillarray.length; l++) {
            int chance = random.nextInt(100) + 1;
                if (chance < 40) {
                int imgtype = random.nextInt(6);
                fillarray[l] = imgtype;
                    answer=answer+1;
              } else if (chance>=40){
                fillarray[l] = 6;
              }
        }

            for (int l = 0; l < fillarray.length; l++) {
                if (fillarray[l] <6) {
                    imgbuttons.get(l).setVisibility(View.VISIBLE);
                    imgbuttons.get(l).setImageResource(Imgarr[fillarray[l]]);
                } else if (fillarray[l] == 6) {
                    imgbuttons.get(l).setVisibility(View.INVISIBLE);

                }
            }

            if (otv == 1) {
                btn1.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn3.setText(Integer.toString(answer - 1));
                btn4.setText(Integer.toString(answer + 2));
            } else if (otv == 2) {
                btn2.setText(Integer.toString(answer));
                btn1.setText(Integer.toString(answer + 1));
                btn3.setText(Integer.toString(answer - 1));
                btn4.setText(Integer.toString(answer + 2));
            } else if (otv == 3) {
                btn3.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn1.setText(Integer.toString(answer - 1));
                btn4.setText(Integer.toString(answer + 2));
            } else if (otv == 4) {
                btn4.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn1.setText(Integer.toString(answer - 1));
                btn3.setText(Integer.toString(answer + 2));
            }
        }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }
    }