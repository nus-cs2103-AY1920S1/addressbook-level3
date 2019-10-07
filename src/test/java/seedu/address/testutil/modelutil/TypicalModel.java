package seedu.address.testutil.modelutil;

import seedu.address.model.ModelManager;
import seedu.address.model.TimeBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupList;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonList;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.mappingutil.TypicalMappings;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;

/**
 * Typical Model.
 */
public class TypicalModel {

    /**
     * Generates a Typical Model.
     *
     * @return Model
     */
    public static ModelManager generateTypicalModel() {
        Person.counterReset();
        Group.counterReset();

        PersonList personList = TypicalPersonDescriptor.generateTypicalPersonList();
        GroupList groupList = TypicalGroups.generateTypicalGroupList();
        PersonToGroupMappingList personToGroupMappingList = TypicalMappings.generateTypicalMappingList();

        TimeBook timeBook = new TimeBook(personList, groupList, personToGroupMappingList);

        ModelManager modelManager = new ModelManager(timeBook);

        return modelManager;
    }
}
