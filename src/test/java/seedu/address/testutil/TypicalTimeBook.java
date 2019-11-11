package seedu.address.testutil;

import java.util.List;

import seedu.address.model.TimeBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonList;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.mappingutil.TypicalMappings;

/**
 * This class is used to fer the typical TimeBook object for testing
 */
public class TypicalTimeBook {
    public static TimeBook get() {
        PersonList personListObj = new PersonList();
        List<Person> personList = TypicalPersons.getTypicalPersons();
        for (int i = 0; i < personList.size(); i++) {
            personListObj.addPerson(personList.get(i));
        }
        TimeBook timeBook = new TimeBook(personListObj,
                TypicalGroups.generateTypicalGroupList(),
                TypicalMappings.generateTypicalMappingList());
        return timeBook;
    }
}

