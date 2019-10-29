package seedu.address.model.util;

import seedu.address.model.TimeBook;
import seedu.address.model.group.GroupList;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.PersonList;

/**
 * Sample TimeBook.
 */
public class SampleTimeBook {

    /**
     * Generates a sample TimeBook.
     */
    public static TimeBook generateSampleTimeBook() {

        PersonList personList = SamplePersonList.generateSamplePersonList();
        GroupList groupList = SampleGroupList.generateSampleGroupList();
        PersonToGroupMappingList mappingList = SampleMappingList.generateMappingList(personList, groupList);

        TimeBook timeBook = new TimeBook(personList, groupList, mappingList);
        return timeBook;
    }

}
