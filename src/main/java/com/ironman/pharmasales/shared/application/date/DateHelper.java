package com.ironman.pharmasales.shared.application.date;

import java.time.LocalDate;

public class DateHelper {

    public String localDateToString(LocalDate localDate) {
        return localDate != null ? localDate.toString() : null;
    }
}
