package com.refanzzzz.tokonyadia.util;

import com.refanzzzz.tokonyadia.constant.Constant;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {
    public static String parseDateToString(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constant.DATE_FORMAT);
        return dateTimeFormatter.format(localDateTime);
    }
}
