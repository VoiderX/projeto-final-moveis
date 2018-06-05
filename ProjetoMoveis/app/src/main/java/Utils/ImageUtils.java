package Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import java.io.InputStream;

public class ImageUtils {
    public static Bitmap getBitmapFromURI(Context ctx, String location) {
        if (location == null) {
            return BitmapFactory.decodeResource(ctx.getResources(), android.R.drawable.ic_menu_report_image);
        }
        try {
            InputStream imageStream = ctx.getContentResolver().openInputStream(Uri.parse(location));
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = SharedUtils.readCompressionLevel(ctx);
            Bitmap bm = BitmapFactory.decodeStream(imageStream, null, options);
            imageStream.close();
            return bm;
        } catch (Exception e) {
            return BitmapFactory.decodeResource(ctx.getResources(), android.R.drawable.ic_menu_report_image);
        }
    }
}
