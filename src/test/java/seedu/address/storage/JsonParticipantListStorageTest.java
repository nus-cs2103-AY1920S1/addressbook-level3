package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.*;

import seedu.address.model.entity.Email;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Phone;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.AlfredException;

class JsonParticipantListStorageTest {

    @org.junit.jupiter.api.Test
    void getParticipantListFilePath() {
        Participant p1 = new Participant(new Name("p1"),
                                         new Id(PrefixType.P, 1),
                                         new Email("p1@gmail.com"),
                                         new Phone("91231233"));
        Participant p2 = new Participant(new Name("p2"),
                new Id(PrefixType.P, 2),
                new Email("p2@gmail.com"),
                new Phone("92222222"));
        Participant p3 = new Participant(new Name("p3"),
                new Id(PrefixType.P, 3),
                new Email("p3@gmail.com"),
                new Phone("93333333"));

        try {
            ParticipantList pList = new ParticipantList();
            pList.add(p1);
            pList.add(p2);
            pList.add(p3);
        } catch (AlfredException e) {
            System.out.println("Oops");
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