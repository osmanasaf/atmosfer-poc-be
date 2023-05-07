package org.codefirst.seed.userservice.util;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateUtil {
    public static boolean isGivenDateInTheLast2Minutes(Date date) {
        long diff = (new Date()).getTime() - date.getTime();
        long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
        return seconds <= 120;
    }
}
