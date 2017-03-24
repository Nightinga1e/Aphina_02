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

import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class number extends Activity implements OnClickListener {

    private int level = 0, answer = 0;
    String enteredAnswer;
    private int[][] levelMin = {{1, 11, 21}, {1, 5, 10}, {2, 5, 10},
            {2, 3, 5}};
    private int[][] levelMax = {{10, 25, 50}, {10, 20, 30}, {5, 10, 15},
            {10, 50, 100}};

    private int[] Imgarr = {R.mipmap.pic1,
            R.mipmap.pic2,
            R.mipmap.pic3,
            R.mipmap.pic4,
            R.mipmap.pic5,
            R.mipmap.pic6};

    private Random random;
    private SharedPreferences gamePrefs;
    public static final String GAME_PREFS = "NumberFile";

    private TextView scoreTxt;
    private ImageView response;
    private Button btn1, btn2, btn3, btn4;
 //   private ImageButton imgtest, img1, img2, img3, img4, img5, img6, img7, img8, img9, img10;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number);
        gamePrefs = getSharedPreferences(GAME_PREFS, 0);

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


        random = new Random();
        chooseField();
    }

    @Override
    public void onClick(View view) {
        // TODO Auto-generated method stub
        switch (view.getId()) {
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
    }

    private void chooseField() {
        // get a field
        answer = 0;
        int otv = random.nextInt(4) + 1;

        for (int l = 0; l < fillarray.length; l++) {
            int chance = random.nextInt(100) + 1;
            int imgtype = random.nextInt(6);
            if (chance < 40) {
                fillarray[l] = imgtype;
            } else {
                fillarray[l] = 0;
                answer += 1;
            }
        }

            for (int l = 0; l < fillarray.length; l++) {
                if (fillarray[l] > 0) {
                    imgbuttons.get(l).setVisibility(View.VISIBLE);
                    imgbuttons.get(l).setImageResource(Imgarr[fillarray[l]]);
                } else if (fillarray[l] == 0) {
                    imgbuttons.get(l).setVisibility(View.INVISIBLE);
                }
            }
/*
            if (otv == 1) {
                btn1.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn3.setText(Integer.toString(answer - otv));
                btn4.setText(Integer.toString(answer + otv));
            } else if (otv == 2) {
                btn2.setText(Integer.toString(answer));
                btn1.setText(Integer.toString(answer + 1));
                btn3.setText(Integer.toString(answer - otv));
                btn4.setText(Integer.toString(answer + otv));
            } else if (otv == 3) {
                btn3.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn1.setText(Integer.toString(answer - otv));
                btn4.setText(Integer.toString(answer + otv));
            } else if (otv == 4) {
                btn4.setText(Integer.toString(answer));
                btn2.setText(Integer.toString(answer + 1));
                btn1.setText(Integer.toString(answer - otv));
                btn3.setText(Integer.toString(answer + otv));
            }*/
        }
    }