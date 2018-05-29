package Utils;

import android.arch.persistence.room.TypeConverter;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ConverterUtils {
    public static String convertDateToString(Date value) {
        return DateFormat.getDateInstance(DateFormat.SHORT).format(value);
    }
    public static Date convertStringToDate(String value) throws ParseException {
        return DateFormat.getDateInstance(DateFormat.SHORT).parse(value);
    }
    @TypeConverter
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    @TypeConverter
    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
