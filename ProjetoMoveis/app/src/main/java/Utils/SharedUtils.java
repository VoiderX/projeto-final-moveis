package Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.gabriel.projetomoveis.R;


public class SharedUtils {
    private static final String FILE_NAME = "RMA_ASSISTANT";
    private static final String THEME_ID = "THEME_ID";

    private static final String COMPRESSION_LEVEL_ID = "COMPRESSION_LEVEL_ID";

    public static final String RED = "RED";
    public static final String BLUE = "BLUE";
    public static final String GREEN = "GREEN";

    public static void saveCompressionLevel(Context context, int level) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(COMPRESSION_LEVEL_ID, level);
        editor.commit();
    }

    public static int readCompressionLevel(Context context) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt(COMPRESSION_LEVEL_ID, 8);
    }

    public static void saveChosenTheme(Context context, String theme) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(THEME_ID, theme);
        editor.commit();
    }

    public static void setChosenTheme(Context context, boolean hasActionBar) {
        String theme = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(THEME_ID, GREEN);
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
