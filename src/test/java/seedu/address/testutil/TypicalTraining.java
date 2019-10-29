package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;
import static seedu.address.testutil.TypicalPersons.GEORGE;

import java.util.ArrayList;
import java.util.HashMap;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.date.AthletickDate;
import seedu.address.model.person.Person;
import seedu.address.model.training.Training;

/**
 * A sample class of a Hashmap consisting of players and their attendance.
 */
public class TypicalTraining {

    public static final HashMap<Person, Boolean> TRAINING = new HashMap<Person, Boolean>();

    public TypicalTraining() {
        TRAINING.put(ALICE, true);
        TRAINING.put(BENSON, true);
        TRAINING.put(CARL, false);
        TRAINING.put(ELLE, true);
        TRAINING.put(GEORGE, false);
        TRAINING.put(FIONA, false);
        TRAINING.put(DANIEL, false);
    }

    public HashMap<Person, Boolean> getTraining() {
        return new HashMap<Person, Boolean>(TRAINING);
    }


}
