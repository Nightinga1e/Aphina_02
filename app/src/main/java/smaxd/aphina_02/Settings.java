package smaxd.aphina_02;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;

public class Settings extends AppCompatActivity {

    TextView MSGTime;

    public void TimeSelect(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(Settings.this);
                builder.setTitle("Выберите время")

                .setCancelable(false)
                .setNegativeButton("Назад",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        })
                .setPositiveButton("Готово",
                         new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                    }
                });

        AlertDialog alert = builder.create();
        alert.show();
    }

        @Override
    protected void onCreate(Bundle savedInstanceState) {
            getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
            }


    public void ToSettings(View view) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
    public void ToProfile(View view) {
        Intent intent = new Intent(this, Profile.class);
        startActivity(intent);
    }
    public void ToQuest(View view) {
        Intent intent = new Intent(this, Quest.class);
        startActivity(intent);
    }
    public void ToTrainers(View view) {
        Intent intent = new Intent(this, Trainer.class);
        startActivity(intent);
    }
    public void ToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}
