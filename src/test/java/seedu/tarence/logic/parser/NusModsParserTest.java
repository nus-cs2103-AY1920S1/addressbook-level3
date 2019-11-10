package seedu.tarence.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;

import seedu.tarence.logic.parser.exceptions.ParseException;
import seedu.tarence.model.module.ModCode;
import seedu.tarence.model.tutorial.TutName;
import seedu.tarence.model.tutorial.Tutorial;
import seedu.tarence.model.tutorial.Week;

public class NusModsParserTest {

    /**
     * Assumes data from AY19/20 S1
     */
    @Test
    public void urlToTutorials_parseTimetableUrl_parseSuccess() throws ParseException {

        // Timetable contains two different modules, two different types of tutorials, and one lecture
        final String url = "https://nusmods.com/timetable/sem-1/share?CS1101S=TUT:09F,REC:16,LEC:2&GET1029=TUT:W6";

        List<Tutorial> actualTutorialList = NusModsParser.urlToTutorials(url);

        List<Tutorial> expectedTutorialList = new ArrayList<>();

        Set<Week> allWeeks = new TreeSet<>();
        for (int i = 1; i <= 13; i++) {
            allWeeks.add(new Week(i));
        }
        // add CS1101S Tut 09F
        expectedTutorialList.add(new Tutorial(new TutName("Tutorial 09F"), DayOfWeek.valueOf("TUESDAY"),
                LocalTime.parse("14:00:00"), allWeeks, Duration.ofMinutes(120), new ArrayList<>(),
                new ModCode("CS1101S"), null, null, null));

        // add CS1101S Rec 16
        expectedTutorialList.add(new Tutorial(new TutName("Recitation 16"), DayOfWeek.valueOf("THURSDAY"),
                LocalTime.parse("13:00:00"), allWeeks, Duration.ofMinutes(60), new ArrayList<>(),
                new ModCode("CS1101S"), null, null, null));

        // add GET1029 Tut W6
        Set<Week> limitedWeeks = new TreeSet<>();
        for (int i = 3; i <= 13; i++) {
            limitedWeeks.add(new Week(i));
        }
        expectedTutorialList.add(new Tutorial(new TutName("Tutorial W6"), DayOfWeek.valueOf("MONDAY"),
                LocalTime.parse("09:00:00"), limitedWeeks, Duration.ofMinutes(60), new ArrayList<>(),
                new ModCode("GET1029"), null, null, null));

        assertEquals(expectedTutorialList, actualTutorialList);
    }
}
