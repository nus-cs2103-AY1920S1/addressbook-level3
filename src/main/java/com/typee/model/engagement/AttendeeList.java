package com.typee.model.engagement;

import com.typee.model.person.Name;
import com.typee.model.person.Person;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AttendeeList {

    public static final String MESSAGE_CONSTRAINTS = "AttendeeList should be a list of persons.";

    private final List<Person> attendees;

    public AttendeeList(List<Person> attendees) {
        this.attendees = attendees;
    }

    public static boolean isValid(String string) {
        final String stringWithoutBrackets = string.substring(1, string.length() - 1);
        if (stringWithoutBrackets.isBlank()) {
            return false;
        }

        return Arrays.stream(stringWithoutBrackets.split(","))
                .map(person -> person.trim())
                .allMatch(name -> Name.isValidName(name));
    }

    public static AttendeeList getListGivenValidInput(String attendees) {
        final String stringWithoutBrackets = attendees.substring(1, attendees.length() - 1);

        List<Person> listOfAttendees = Arrays.stream(stringWithoutBrackets.split(","))
                .map(str -> str.trim())
                .map(name -> new Person(new Name(name)))
                .collect(Collectors.toList());

        return new AttendeeList(listOfAttendees);
    }

    @Override
    public String toString() {
        return attendees.toString();
    }
}
