package resumeapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {

    public static final LocalDate NOW = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

    public static LocalDate of(int year, Month month) {
        return LocalDate.of(year, month, 1);
    }
}
