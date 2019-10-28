package seedu.address.model.util;

import seedu.address.model.group.GroupList;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Sample MappingList.
 */
public class SampleMappingList {

    /**
     * Generates a sample MappingList.
     */
    public static PersonToGroupMappingList generateMappingList(PersonList personList, GroupList groupList) {
        try {
            PersonToGroupMappingList mappingList = new PersonToGroupMappingList();

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.BOBBY.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP1.getGroupName()).getGroupId()
                    )
            );

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.CHARLIE.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP1.getGroupName()).getGroupId()
                    )
            );

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.DENISE.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP1.getGroupName()).getGroupId()
                    )
            );

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.BOBBY.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP2.getGroupName()).getGroupId()
                    )
            );

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.CHARLIE.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP2.getGroupName()).getGroupId()
                    )
            );

            mappingList.addPersonToGroupMapping(
                    new PersonToGroupMapping(
                            personList.findPerson(SamplePersonList.EMILY.getName()).getPersonId(),
                            groupList.findGroup(SampleGroupList.GROUP2.getGroupName()).getGroupId()
                    )
            );

            return mappingList;
        } catch (PersonNotFoundException | DuplicateMappingException | GroupNotFoundException e) {
            e.printStackTrace();
            return null;
        }


    }

}
