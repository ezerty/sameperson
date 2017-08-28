package com.mycompany.sameperson.utils;

import com.mycompany.sameperson.Configuration;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.StringJoiner;

public enum DateParser {
    INSTANCE;

    private final String DELIMITER_REGEX;

    private DateParser() {
        DELIMITER_REGEX = String.format("[%s]", Configuration.INSTANCE.getDateDelimiters());
    }

    public ZonedDateTime parse(String dirtyDate) {
        String[] splittedDate = dirtyDate.split(DELIMITER_REGEX);
        try {
            if (splittedDate.length != 3) {
                throw new InvalidDateException();
            }

            int day;
            int month;
            int year;

            String dayRegex = "0?[1-9]|[1-2][0-9]|3[0-1]";
            String monthRegex = "0?[1-9]|1[0-2]";
            String yearRegex = "[0-9]{2}|[1-9][0-9]{3}";
            if (dirtyDate.matches(prepareRegex(yearRegex, monthRegex, dayRegex))) {
                year = Integer.valueOf(splittedDate[0]);
                month = Integer.valueOf(splittedDate[1]);
                day = Integer.valueOf(splittedDate[2]);
            } else if (dirtyDate.matches(prepareRegex(yearRegex, dayRegex, monthRegex))) {
                year = Integer.valueOf(splittedDate[0]);
                day = Integer.valueOf(splittedDate[1]);
                month = Integer.valueOf(splittedDate[2]);
            } else if (dirtyDate.matches(prepareRegex(dayRegex, monthRegex, yearRegex))) {
                day = Integer.valueOf(splittedDate[0]);
                month = Integer.valueOf(splittedDate[1]);
                year = Integer.valueOf(splittedDate[2]);
            } else if (dirtyDate.matches(prepareRegex(monthRegex, dayRegex, yearRegex))) {
                month = Integer.valueOf(splittedDate[0]);
                day = Integer.valueOf(splittedDate[1]);
                year = Integer.valueOf(splittedDate[2]);
            } else {
                throw new InvalidDateException();
            }
            if (year < 100) {
                year += Math.floorDiv(Year.now().getValue(), 100) * 100;
            }
            return LocalDate.of(year, month, day).atStartOfDay(ZoneId.of("UTC"));
        } catch (InvalidDateException e) {
            return null;
        }
    }

    private final class InvalidDateException extends Exception {
    }

    private String prepareRegex(String... regexes) {
        StringJoiner joiner = new StringJoiner(")" + DELIMITER_REGEX + "(", "(", ")");
        for (String regex : regexes) {
            joiner.add(regex);
        }
        return joiner.toString();
    }
}
