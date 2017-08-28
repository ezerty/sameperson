package com.mycompany.sameperson.utils;

import com.mycompany.sameperson.Configuration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum FullNameParser {
    INSTANCE;

    private final String DELIMITER_REGEX;

    private FullNameParser() {
        DELIMITER_REGEX = String.format("[%s]+", Configuration.INSTANCE.getNameDelimiters());
    }

    public String parse(String dirtyInput) {
        dirtyInput = dirtyInput.toLowerCase()
                .replaceAll("(.)\\1{1,}", "$1")
                .replaceAll("ya", "ja")
                .replaceAll("y", "i")
                .replaceAll("ks", "x")
                .replaceAll("ch", "c")
                .replaceAll("sh", "s");

        List<String> nameParts = new ArrayList<>(Arrays.asList(dirtyInput.split(DELIMITER_REGEX)));

        if (nameParts.size() == 3) {
            if (dirtyInput.replaceAll("\\s", "").matches(nameParts.get(0) + DELIMITER_REGEX + ".*")) {
                nameParts.remove(2);
            } else {
                nameParts.remove(1);
            }
        }
        Collections.sort(nameParts);

        return cutName(nameParts.get(0)) + cutName(nameParts.get(1));
    }

    private String cutName(String name) {
        return name.substring(0, name.length() > 1 ? 2 : 1);
    }
}
