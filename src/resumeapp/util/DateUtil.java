package resumeapp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;

public class DateUtil {

    private static final SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public static final LocalDate NOW = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }

    public static LocalDate convertToLocalDate(String date) throws ParseException {
        return FORMAT.parse(date).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
}
