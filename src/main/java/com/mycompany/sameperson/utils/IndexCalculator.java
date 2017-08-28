package com.mycompany.sameperson.utils;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public enum IndexCalculator {
    INSTANCE;

    private IndexCalculator() {
    }

    public String calculateIndex(String row) {
        if (row.equals("FIO|BirthDate")) {
            return "Index";
        }

        String[] splittedRow = row.split("\\|");
        String fullName = splittedRow[0];
        String birthDate = splittedRow[1];

        ZonedDateTime fixedDate = DateParser.INSTANCE.parse(birthDate);
        String fixedName = FullNameParser.INSTANCE.parse(fullName);

        if (fixedDate == null) {
            return null;
        }
        return fixedDate.format(DateTimeFormatter.BASIC_ISO_DATE) + fixedName;
    }

}
