package com.typee.model.engagement;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.typee.model.person.Name;
import com.typee.model.person.Person;

/**
 * Represents a list of people attending an engagement.
 */
public class AttendeeList {

    public static final String MESSAGE_CONSTRAINTS = "AttendeeList should be a list of persons.";

    private final List<Person> attendees;

    /**
     * Constructs an {@code AttendeeList} given a {@code List} of attendees.
     * @param attendees list of attendees.
     */
    public AttendeeList(List<Person> attendees) {
        this.attendees = attendees;
    }

    /**
     * Checks if a {@code String} represents a valid {@code AttendeeList}.
     *
     * @param string {@code String} representing an {@code AttendeeList}.
     * @return true if the {@code String} is a valid representation of an {@code AttendeeList}.
     */
    public static boolean isValid(String string) {
        final String stringWithoutBrackets = string.substring(1, string.length() - 1);
        if (stringWithoutBrackets.isBlank()) {
            return false;
        }
        return validateNames(stringWithoutBrackets);
    }

    /**
     * Checks if the list of people have valid names.
     *
     * @param stringWithoutBrackets {@code String} representing a list of people.
     * @return true if all the names are valid.
     */
    private static boolean validateNames(String stringWithoutBrackets) {
        return Arrays.stream(stringWithoutBrackets.split(","))
                .map(person -> person.trim())
                .allMatch(name -> Name.isValidName(name));
    }

    /**
     * Returns an {@code AttendeeList} made from the given list of attendees.
     * Assumes that the given {@code String} is a valid representation of attendees.
     *
     * @param attendees {@code String} representation of attendees.
     * @return the corresponding {@code AttendeeList}.
     */
    public static AttendeeList getListGivenValidInput(String attendees) {
        final String stringWithoutBrackets = attendees.substring(1, attendees.length() - 1);
        List<Person> listOfAttendees = getListOfPeople(stringWithoutBrackets);
        return new AttendeeList(listOfAttendees);
    }

    /**
     * Returns a list of people given a {@code String} input representing a list of people.
     *
     * @param stringWithoutBrackets {@code String} representation of a list of people.
     * @return List of people.
     */
    private static List<Person> getListOfPeople(String stringWithoutBrackets) {
        return Arrays.stream(stringWithoutBrackets.split(","))
                    .map(str -> str.trim())
                    .map(name -> new Person(new Name(name)))
                    .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return attendees.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            // short-circuit
            return true;
        } else if (o instanceof AttendeeList) {
            AttendeeList newAttendeeList = (AttendeeList) o;
            return attendees.equals(newAttendeeList.attendees);
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return attendees.hashCode();
    }
}
