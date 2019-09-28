package seedu.address.testutil.mappingutil;

import seedu.address.model.group.GroupId;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.PersonId;

/**
 * Typical Mappings.
 */
public class TypicalMappings {

    public static final PersonToGroupMapping MAP00 =
            new PersonToGroupMapping(new PersonId(0), new GroupId(0));

    public static final PersonToGroupMapping MAP01 =
            new PersonToGroupMapping(new PersonId(0), new GroupId(1));

    public static final PersonToGroupMapping MAP02 =
            new PersonToGroupMapping(new PersonId(0), new GroupId(2));

    public static final PersonToGroupMapping MAP10 =
            new PersonToGroupMapping(new PersonId(1), new GroupId(0));

    public static final PersonToGroupMapping MAP11 =
            new PersonToGroupMapping(new PersonId(1), new GroupId(1));

    public static final PersonToGroupMapping MAP22 =
            new PersonToGroupMapping(new PersonId(2), new GroupId(2));

    public static final PersonToGroupMapping MAP20 =
            new PersonToGroupMapping(new PersonId(2), new GroupId(0));

    /**
     * Generates a typical MappingList.
     *
     * @return PersonToGroupMappingList
     */
    public static PersonToGroupMappingList generateTypicalMappingList() {
        PersonToGroupMappingList mappingList = new PersonToGroupMappingList();
        mappingList.addPersonToGroupMapping(MAP00);
        mappingList.addPersonToGroupMapping(MAP01);
        mappingList.addPersonToGroupMapping(MAP02);
        mappingList.addPersonToGroupMapping(MAP10);
        mappingList.addPersonToGroupMapping(MAP11);
        mappingList.addPersonToGroupMapping(MAP22);
        return mappingList;
    }


}

