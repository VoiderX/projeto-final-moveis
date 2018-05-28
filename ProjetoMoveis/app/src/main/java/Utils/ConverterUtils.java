package Utils;

import java.text.DateFormat;
import java.util.Date;

public class ConverterUtils {
    public static String convertDateToString(Date value){
        return DateFormat.getDateInstance(DateFormat.LONG).format(value);
    }
}
