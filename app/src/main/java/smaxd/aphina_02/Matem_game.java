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
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class Matem_game extends Activity implements OnClickListener {

    private int level = 0, answer = 0, operator = 0, operand1 = 0,
            operand2 = 0, lifecount= 3;
    private String enteredAnswer;
    private final int ADD_OPERATOR = 0, SUBTRACT_OPERATOR = 1,
            MULTIPLY_OPERATOR = 2, DIVIDE_OPERATOR = 3;
    private String[] operators = { "+", "-", "x", "/" };

    private int[][] levelMin = { { 1, 11, 21 }, { 1, 5, 10 }, { 2, 5, 10 },
            { 2, 3, 5 } };
    private int[][] levelMax = { { 10, 25, 50 }, { 10, 20, 30 }, { 5, 10, 15 },
            { 10, 50, 100 } };

    private Random random;
    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "ArithmeticFile";

    private TextView question, answerTxt, scoreTxt;
    private ImageView response;
    private ImageButton life1,life2,life3;
    private Button btn1, btn2, btn3, btn4;
    private TextView mTimer;

    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matem_game);

        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

        question = (TextView) findViewById(R.id.question);
        answerTxt = (TextView) findViewById(R.id.answer);
        response = (ImageView) findViewById(R.id.response);
        scoreTxt = (TextView) findViewById(R.id.score);
        life1 = (ImageButton) findViewById(R.id.life1);
        life2 = (ImageButton) findViewById(R.id.life2);
        life3 = (ImageButton) findViewById(R.id.life3);
        life3.setVisibility(View.VISIBLE);
        life2.setVisibility(View.VISIBLE);
        life1.setVisibility(View.VISIBLE);
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
        chooseQuestion();

        //Создаем таймер обратного отсчета на 10 секунд с шагом отсчета
        //в 1 секунду (задаем значения в миллисекундах):
        if (level == 0) {
            countDownTimer = new CountDownTimer(10000, 1000) {
                //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                public void onTick(long millisUntilFinished) {
                    mTimer.setText("" + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    response.setImageResource(R.drawable.cross);
                    lifecount -= 1;
                    if (lifecount == 2) {
                        life1.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 1) {
                        life2.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 0) {
                        life3.setVisibility(View.INVISIBLE);
                        finish();
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(Matem_game.this);
                        builder.setTitle("")
                                .setMessage("Попытки закончились!")
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                finish();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();*/
                    }
                    response.setVisibility(View.VISIBLE);
                    chooseQuestion();
                    countDownTimer.start();
                }
            };
            countDownTimer.start();
        }else if (level == 1) {
            countDownTimer = new CountDownTimer(8000, 1000) {
                //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                public void onTick(long millisUntilFinished) {
                    mTimer.setText("" + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    response.setImageResource(R.drawable.cross);
                    lifecount -= 1;
                    if (lifecount == 2) {
                        life1.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 1) {
                        life2.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 0) {
                        life3.setVisibility(View.INVISIBLE);
                        finish();
                       /* AlertDialog.Builder builder = new AlertDialog.Builder(Matem_game.this);
                        builder.setTitle("")
                                .setMessage("Попытки закончились!")
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                finish();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();*/
                    }
                    response.setVisibility(View.VISIBLE);
                    chooseQuestion();
                    countDownTimer.start();
                }
            };
            countDownTimer.start();
        } else if (level == 2) {
            countDownTimer = new CountDownTimer(6000, 1000) {
                //Здесь обновляем текст счетчика обратного отсчета с каждой секундой
                public void onTick(long millisUntilFinished) {
                    mTimer.setText("" + millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    response.setImageResource(R.drawable.cross);
                    lifecount -= 1;
                    if (lifecount == 2) {
                        life1.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 1) {
                        life2.setVisibility(View.INVISIBLE);
                    } else if (lifecount == 0) {
                        life3.setVisibility(View.INVISIBLE);
                        finish();
                        /*AlertDialog.Builder builder = new AlertDialog.Builder(Matem_game.this);
                        builder.setTitle("")
                                .setMessage("Попытки закончились!")
                                .setNegativeButton("ОК",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                                finish();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();*/
                    }
                    response.setVisibility(View.VISIBLE);
                    chooseQuestion();
                    countDownTimer.start();
                }
            };
            countDownTimer.start();
        }
    }

    private void setHighScore() {
        // set high score
        int exScore = getScore();
        if (exScore > 0) {
            SharedPreferences.Editor scoreEdit = gamePrefs.edit();
            DateFormat dateForm = new SimpleDateFormat("dd MMMM yyyy");
            String dateOutput = dateForm.format(new Date());
            String scores = gamePrefs.getString("highScores", "");

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
    public void onClick(View view) {
        // TODO Auto-generated method stub
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


        if(enteredAnswer!=null && lifecount!=0){
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
                    finish();
                  //  Intent intent = new Intent(this, Matem.class);
                   // startActivity(intent);
                   /* AlertDialog.Builder builder = new AlertDialog.Builder(Matem_game.this);
                    builder.setTitle("")
                            .setMessage("Попытки закончились!")
                            .setNegativeButton("ОК",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                            finish();
                                        }
                                    });
                    AlertDialog alert = builder.create();
                    alert.show();*/

                }
                response.setVisibility(View.VISIBLE);
            }
            chooseQuestion();
            countDownTimer.start();
            //response.setVisibility(View.INVISIBLE);
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

    private void chooseQuestion() {
        // get a question
        answerTxt.setText("= ?");
        operator = random.nextInt(operators.length);
        int otv = random.nextInt(4) + 1;
        operand1 = getOperand();
        operand2 = getOperand();

        if (operator == SUBTRACT_OPERATOR) {
            while (operand2 > operand1) {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        } else if (operator == DIVIDE_OPERATOR) {
            while ((((double) operand1 / (double) operand2) % 1 > 0)
                    || (operand1 == operand2)) {
                operand1 = getOperand();
                operand2 = getOperand();
            }
        }

        switch (operator) {
            case ADD_OPERATOR:
                answer = operand1 + operand2;
                break;
            case SUBTRACT_OPERATOR:
                answer = operand1 - operand2;
                break;
            case MULTIPLY_OPERATOR:
                answer = operand1 * operand2;
                break;
            case DIVIDE_OPERATOR:
                answer = operand1 / operand2;
                break;
            default:
                break;
        }
        question.setText(operand1 + " " + operators[operator] + " " + operand2);
        if (otv == 1) {
            btn1.setText(Integer.toString(answer));
            btn2.setText(Integer.toString(answer + 1));
            btn3.setText(Integer.toString(answer + 2));
            btn4.setText(Integer.toString(answer + 3));
        } else if (otv == 2) {
            btn2.setText(Integer.toString(answer));
            btn1.setText(Integer.toString(answer - 1));
            btn3.setText(Integer.toString(answer + 1));
            btn4.setText(Integer.toString(answer + 2));
        } else if (otv == 3) {
            btn3.setText(Integer.toString(answer));
            btn2.setText(Integer.toString(answer - 1));
            btn1.setText(Integer.toString(answer - 2));
            btn4.setText(Integer.toString(answer + 1));
        } else if (otv == 4) {
            btn4.setText(Integer.toString(answer));
            btn2.setText(Integer.toString(answer - 2));
            btn1.setText(Integer.toString(answer - 3));
            btn3.setText(Integer.toString(answer - 1));
        }
    }

    private int getOperand() {
        // return operand number
        return random.nextInt(levelMax[operator][level] - levelMin[operator][level] + 1) + levelMin[operator][level];
    }

    private int getScore(){
        String scoreStr = scoreTxt.getText().toString();
        return Integer.parseInt(scoreStr.substring(scoreStr.lastIndexOf(" ")+1));
    }
}