package com.example.woori_base.until;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUntil {

    public static String convertTmsDt() {
        LocalDateTime curentDate = LocalDateTime.now();
        return curentDate.format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }

    public static String convertTmsTm() {
        LocalDateTime curentDate = LocalDateTime.now();
        return curentDate.format(DateTimeFormatter.ofPattern("hhmmss"));
    }
}
