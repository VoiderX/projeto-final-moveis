package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getBitmapFromURI(Context ctx, String location) {
        if (location == null) {
            return null;
        }
        try {
            System.out.println(location);
            InputStream imageStream = ctx.getContentResolver().openInputStream(Uri.parse(location));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 16;
            Bitmap bm = BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();
            return bm;
        } catch (Exception e) {

        }
        return null;
    }
}
