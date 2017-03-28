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
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Labirint extends Activity implements OnClickListener {

    private int level = 0, answer = 0, lifecount= 3, hercules_coordl =0, hercules_coordm =0;
    private int enteredAnswer;

    private Button btn1, btn2, btn3, btn4;
    private String entAns;
    private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
            {2, 3, 5}};
    private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
            {10, 50, 100}};

    private SharedPreferences numPrefs;
    public static final String NUM_PREFS = "LabirintFile";

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

    private int hercules_coords;
    private ImageButton life1,life2,life3,img1, img2, img3, img4, img5, img6,img7, img8, img9, img10,
            img11 , img12, img13, img14, img15, img16, img17, img18, img19, img20, img21, img22, img23,
            img24,  img25, img26, img27, img28, img29, img30, img31, img32, img33, img34, img35, img36;
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

        numPrefs = getSharedPreferences(NUM_PREFS, 0);

        imgbuttons = new ArrayList<ImageButton>();
        for (int id : IMGBUTTON_IDS) {
            ImageButton imgbut = (ImageButton) findViewById(id);
            //  imgbut.setOnClickListener(this); //
            imgbuttons.add(imgbut);
        }
        for (int i=0;i<fillarray.length;i++){
            imgbuttons.get(i).setTag(i);
        }

        putbuttons = new ArrayList<ImageButton>();
        for (int id : PUT_IDS) {
            ImageButton putbut = (ImageButton) findViewById(id);
            //  imgbut.setOnClickListener(this); //
            putbuttons.add(putbut);
        }

        scoreTxt = (TextView) findViewById(R.id.score);

        life1 = (ImageButton) findViewById(R.id.life1);
        life2 = (ImageButton) findViewById(R.id.life2);
        life3 = (ImageButton) findViewById(R.id.life3);
        life3.setVisibility(View.VISIBLE);
        life2.setVisibility(View.VISIBLE);
        life1.setVisibility(View.VISIBLE);

        img1 = (ImageButton) findViewById(R.id.img1);
        img2 = (ImageButton) findViewById(R.id.img2);
        img3 = (ImageButton) findViewById(R.id.img3);
        img4 = (ImageButton) findViewById(R.id.img4);
        img5 = (ImageButton) findViewById(R.id.img5);
        img6 = (ImageButton) findViewById(R.id.img6);
        img7 = (ImageButton) findViewById(R.id.img7);
        img8 = (ImageButton) findViewById(R.id.img8);
        img9 = (ImageButton) findViewById(R.id.img9);
        img10 = (ImageButton) findViewById(R.id.img10);
        img11 = (ImageButton) findViewById(R.id.img11);
        img12 = (ImageButton) findViewById(R.id.img12);
        img13 = (ImageButton) findViewById(R.id.img13);
        img14 = (ImageButton) findViewById(R.id.img14);
        img15 = (ImageButton) findViewById(R.id.img15);
        img16 = (ImageButton) findViewById(R.id.img16);
        img17 = (ImageButton) findViewById(R.id.img17);
        img18 = (ImageButton) findViewById(R.id.img18);
        img19 = (ImageButton) findViewById(R.id.img19);
        img20 = (ImageButton) findViewById(R.id.img20);
        img21 = (ImageButton) findViewById(R.id.img21);
        img22 = (ImageButton) findViewById(R.id.img22);
        img23 = (ImageButton) findViewById(R.id.img23);
        img24 = (ImageButton) findViewById(R.id.img24);
        img25 = (ImageButton) findViewById(R.id.img25);
        img26 = (ImageButton) findViewById(R.id.img26);
        img27 = (ImageButton) findViewById(R.id.img27);
        img28 = (ImageButton) findViewById(R.id.img28);
        img29 = (ImageButton) findViewById(R.id.img29);
        img30 = (ImageButton) findViewById(R.id.img30);
        img31 = (ImageButton) findViewById(R.id.img31);
        img32 = (ImageButton) findViewById(R.id.img32);
        img33 = (ImageButton) findViewById(R.id.img33);
        img34 = (ImageButton) findViewById(R.id.img34);
        img35 = (ImageButton) findViewById(R.id.img35);
        img36 = (ImageButton) findViewById(R.id.img36);

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
                   // imgbuttons.get(l).setVisibility(View.INVISIBLE);
                }
              //  answerfu.setVisibility(View.VISIBLE);
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
           /* case R.id.btn1:
                entAns = "1";
                break;
            case R.id.btn2:
                entAns = "2";
                break;
            case R.id.btn3:
                entAns="3";
                break;
            case R.id.btn4:
                entAns="4";
                break;*/
            case R.id.img1:
                entAns = "0";
                break;
            case R.id.img2:
                entAns = "1";
                break;
            case R.id.img3:
                entAns = "2";
                break;
            case R.id.img4:
                entAns = "3";
                break;
            case R.id.img5:
                entAns = "4";
                break;
            case R.id.img6:
                entAns = "5";
                break;
            case R.id.img7:
                entAns = "6";
                break;
            case R.id.img8:
                entAns = "7";
                break;
            case R.id.img9:
                entAns = "8";
                break;
            case R.id.img10:
                entAns = "9";
                break;
            case R.id.img11:
                entAns = "10";
                break;
            case R.id.img12:
                entAns = "11";
                break;
            case R.id.img13:
                entAns = "12";
                break;
            case R.id.img14:
                entAns = "13";
                break;
            case R.id.img15:
                entAns = "14";
                break;
            case R.id.img16:
                entAns = "15";
                break;
            case R.id.img17:
                entAns = "16";
                break;
            case R.id.img18:
                entAns = "17";
                break;
            case R.id.img19:
                entAns = "18";
                break;
            case R.id.img20:
                entAns = "19";
                break;
            case R.id.img21:
                entAns = "20";
                break;
            case R.id.img22:
                entAns = "21";
                break;
            case R.id.img23:
                entAns = "22";
                break;
            case R.id.img24:
                entAns = "23";
                break;
            case R.id.img25:
                entAns = "24";
                break;
            case R.id.img26:
                entAns = "25";
                break;
            case R.id.img27:
                entAns = "26";
                break;
            case R.id.img28:
                entAns = "27";
                break;
            case R.id.img29:
                entAns = "28";
                break;
            case R.id.img30:
                entAns = "29";
                break;
            case R.id.img31:
                entAns = "30";
                break;
            case R.id.img32:
                entAns = "31";
                break;
            case R.id.img33:
                entAns = "32";
                break;
            case R.id.img34:
                entAns = "33";
                break;
            case R.id.img35:
                entAns = "34";
                break;
            case R.id.img36:
                entAns = "35";
                break;

        }
        if(entAns!=null){
            int exScore = getScore();
            if(entAns==(Integer.toString(answer))){
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
        hercules_coordl = random.nextInt(6);
        hercules_coordm = random.nextInt(6);
        for (int l = 0; l < 6; l++) {
            for (int m = 0; m < 6; m++) {
                fillarray[l][m] = 0;
                if (l == hercules_coordl && m ==hercules_coordm) {
                    fillarray[l][m] = 1;
                    answer = l*6+m;
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

        for (int l = 0; l < 6 ;l++){
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

    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }
}