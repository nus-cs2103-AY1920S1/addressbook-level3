package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.*;

import java.util.HashMap;

import seedu.address.model.person.Person;

public class TypicalTraining {

	public static final HashMap<Person, Boolean> training = new HashMap<Person, Boolean>();

	public TypicalTraining() {
		training.put(ALICE, true);
		training.put(BENSON, true);
		training.put(CARL, false);
		training.put(ELLE, true);
		training.put(GEORGE, false);
		training.put(FIONA, false);
		training.put(DANIEL, false);
	}

	public HashMap<Person, Boolean> getTraining() {
		return new HashMap<Person, Boolean>(training);
	}


}
