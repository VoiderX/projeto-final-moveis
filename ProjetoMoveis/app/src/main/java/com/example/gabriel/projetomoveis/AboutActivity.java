package com.example.gabriel.projetomoveis;

import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import Utils.SharedUtils;

public class AboutActivity extends AppCompatActivity {
    private ImageView redTheme, greeTheme, blueTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedUtils.setChosenTheme(this,true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startElements();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    public static void call(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

    private void startElements() {
        redTheme = findViewById(R.id.redTheme);
        greeTheme = findViewById(R.id.greenTheme);
        blueTheme = findViewById(R.id.blueTheme);

        redTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRedTheme();
            }
        });

        greeTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setGreenTheme();
            }
        });

        blueTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setBlueTheme();
            }
        });
    }

    private void setRedTheme() {
        setThemeActvity(SharedUtils.RED);
    }

    private void setGreenTheme() {
        setThemeActvity(SharedUtils.GREEN);
    }

    private void setBlueTheme() {
        setThemeActvity(SharedUtils.BLUE);
    }

    private void setThemeActvity(String theme) {
        SharedUtils.saveChosenTheme(this, theme);
        SharedUtils.setChosenTheme(this,true);
        TaskStackBuilder.create(this)
                .addNextIntent(new Intent(this, MainActivity.class))
                .addNextIntent(this.getIntent())
                .startActivities();
    }
}
