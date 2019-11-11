package seedu.address.testutil.modelutil;

import static seedu.address.testutil.personutil.TypicalPersonDescriptor.ALICE;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.BENSON;
import static seedu.address.testutil.personutil.TypicalPersonDescriptor.CARL;

import seedu.address.model.ModelManager;
import seedu.address.model.NusModsData;
import seedu.address.model.TimeBook;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupList;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.grouputil.TypicalGroups;
import seedu.address.testutil.mappingutil.TypicalMappings;
import seedu.address.testutil.personutil.TypicalPersonDescriptor;
import seedu.address.testutil.scheduleutil.TypicalSchedule;

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

    /**
     * Generate a model with NusModsData loaded.
     *
     * @return Model.
     */
    public static ModelManager generateModelWithNusModsData() {
        ModelManager modelManager = generateTypicalModel();
        NusModsData nusModsData = new NusModsData();
        nusModsData.loadAllFromCache();
        modelManager.setNusModsData(nusModsData);
        return modelManager;
    }

    /**
     * Generates a model with sample schedules for USER, ALICE, BENSON and CARL
     */
    public static ModelManager generateModelWithSchedules() {

        ModelManager modelManager = generateTypicalModel();
        Person user = modelManager.getUser();
        PersonId userId = user.getPersonId();
        user.setSchedule(TypicalSchedule.generateTypicalSampleSchedule1(userId));

        try {
            Person alice = modelManager.findPerson(ALICE.getName());
            alice.setSchedule(TypicalSchedule.generateTypicalSampleSchedule2(alice.getPersonId()));

            Person benson = modelManager.findPerson(BENSON.getName());
            benson.setSchedule(TypicalSchedule.generateTypicalSampleSchedule3(benson.getPersonId()));

            Person carl = modelManager.findPerson(CARL.getName());
            carl.setSchedule(TypicalSchedule.generateTypicalSampleSchedule4(carl.getPersonId()));
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }

        return modelManager;
    }
}
