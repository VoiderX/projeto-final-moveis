package Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gabriel.projetomoveis.R;


public class SharedUtils {
    private static final String FILE_NAME = "RMA_ASSISTANT";
    private static final String THEME_ID = "THEME_ID";

    public static final String RED = "RED";
    public static final String BLUE = "BLUE";
    public static final String GREEN = "GREEN";

    public static void saveChosenTheme(Context context, String theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_ID, theme);
        editor.commit();
    }

    public static void setChosenTheme(Context context, boolean hasActionBar) {
        String theme = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(THEME_ID, BLUE);
        switch (theme) {
            case RED:
                if (hasActionBar) {
                    context.setTheme(R.style.AppThemeRed);
                } else {
                    context.setTheme(R.style.AppTheme_NoActionBarRed);
                }
                break;
            case BLUE:
                if (hasActionBar) {
                    context.setTheme(R.style.AppTheme);
                } else {
                    context.setTheme(R.style.AppTheme_NoActionBar);
                }
                break;
            case GREEN:
                if (hasActionBar) {
                    context.setTheme(R.style.AppThemeGreen);
                } else {
                    context.setTheme(R.style.AppTheme_NoActionBarGreen);
                }
                break;

        }
    }
}
