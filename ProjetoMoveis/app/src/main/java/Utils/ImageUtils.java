package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getBitmapFromURI(Context ctx, Uri imageUri) {
        try {
            InputStream imageStream = ctx.getContentResolver().openInputStream(imageUri);
            return BitmapFactory.decodeStream(imageStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
