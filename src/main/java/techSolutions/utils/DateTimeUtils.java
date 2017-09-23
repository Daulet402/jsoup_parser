package techSolutions.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(Constants.DEFAULT_DATE_TIME_FORMATTER_PATTERN);

    public static LocalDate toLocalDateTime(String toParse) {
        return LocalDate.parse(toParse, DATE_TIME_FORMATTER);
    }
}
