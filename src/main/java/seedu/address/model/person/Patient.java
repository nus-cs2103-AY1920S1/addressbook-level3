package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.common.PatientReferenceId;
import seedu.address.model.tag.Tag;


public class Patient extends Person {

    public Patient(PatientReferenceId referenceId, Name name, Phone phone,
                   Email email, Address address, Set<Tag> tags) {
        super(referenceId, name, phone, email, address, tags);
    }

}
