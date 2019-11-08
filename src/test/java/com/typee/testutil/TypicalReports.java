package com.typee.testutil;

import java.time.LocalDateTime;

import com.typee.model.report.Report;

/**
 * A utility class containing a list of {@code Report} objects to be used in tests.
 */
public class TypicalReports {
    public static final Report TYPICAL_REPORT = new Report(new EngagementBuilder().withDescription("Appointment")
            .withStartAndEndTime(LocalDateTime.of(2019, 10, 20, 10, 0),
                    LocalDateTime.of(2019, 10, 20, 11, 0))
            .buildAsAppointment(),
            new PersonBuilder().withName("Jason").build(),
            new PersonBuilder().withName("Gihun").build());

    public static final Report TYPICAL_REPORT_DIFF = new Report(new EngagementBuilder().withDescription("Appointment")
            .withStartAndEndTime(LocalDateTime.of(2019, 10, 20, 10, 0),
                    LocalDateTime.of(2019, 10, 20, 11, 0))
            .buildAsAppointment(),
            new PersonBuilder().withName("Harry").build(),
            new PersonBuilder().withName("Amanda").build());

    private TypicalReports() {

    }
}
