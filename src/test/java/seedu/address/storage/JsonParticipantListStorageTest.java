package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import seedu.address.AlfredException;
import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.ParticipantList;

class JsonParticipantListStorageTest {

    @org.junit.jupiter.api.Test
    void getParticipantListFilePath() {
        Name n = new Name("p one");
        Id i = new Id(PrefixType.P, 1);
        Email e = new Email("p1@gmail.com");
        Phone p = new Phone("91231233");
        Participant p1 = new Participant(new Name("p one"),
                                         new Id(PrefixType.P, 1),
                                         new Email("p1@gmail.com"),
                                         new Phone("91231233"));
        Participant p2 = new Participant(new Name("p two"),
                new Id(PrefixType.P, 2),
                new Email("p2@gmail.com"),
                new Phone("92222222"));
        Participant p3 = new Participant(new Name("p three"),
                new Id(PrefixType.P, 3),
                new Email("p3@gmail.com"),
                new Phone("93333333"));

        ParticipantList pList = new ParticipantList();
        try {
            pList.add(p1);
            pList.add(p2);
            pList.add(p3);
        } catch (AlfredException ae) {
            System.out.println("Oops");
        }

        JsonParticipantListStorage j = new JsonParticipantListStorage(Paths.get("data" , "participantlist.json"));
        try {
            j.saveParticipantList(pList);
        } catch (IOException io) {
            fail("Problem saving Participant List");
        }

        try {
            ParticipantList newPList = j.readParticipantList(Paths.get("data", "participantlist.json")).get();
            List<Participant> newList = newPList.getSpecificTypedList();
            List<Participant> origList = pList.getSpecificTypedList();
            assertEquals(newList.get(0).getEmail(), origList.get(0).getEmail());
            assertEquals(newList.get(0).getName(), origList.get(0).getName());
            assertEquals(newList.get(0).getId(), origList.get(0).getId());
            assertEquals(newList.get(0).getPhone(), origList.get(0).getPhone());
            assertEquals(newList, origList);
            //assertEquals(newList, pList.getSpecificTypedList());
        } catch (Exception newE) {
            fail("Exception encountered reading ParticipantList: " + newE.getMessage());
        }



    }

    //@org.junit.jupiter.api.Test
    //void readParticipantList() {
    //}
    //
    //@org.junit.jupiter.api.Test
    //void testReadParticipantList() {
    //}
    //
    //@org.junit.jupiter.api.Test
    //void saveParticipantList() {
    //}
    //
    //@org.junit.jupiter.api.Test
    //void testSaveParticipantList() {
    //}
}
