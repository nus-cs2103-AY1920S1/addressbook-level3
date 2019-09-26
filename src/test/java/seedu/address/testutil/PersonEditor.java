package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;

public class PersonEditor {

    private Person person;

    public PersonEditor(Person person){
        this.person = person;
    }

    public Person edit(PersonDescriptor personDescriptor){
        if (personDescriptor.getName() != null) {
            Name otherName = personDescriptor.getName();
            person.setName(personDescriptor.getName());
        }
        if (personDescriptor.getPhone() != null) {
            person.setPhone(personDescriptor.getPhone());
        }
        if (personDescriptor.getEmail() != null) {
            person.setEmail(personDescriptor.getEmail());
        }
        if (personDescriptor.getAddress() != null) {
            person.setAddress(personDescriptor.getAddress());
        }
        if (personDescriptor.getRemark() != null) {
            person.setRemark(personDescriptor.getRemark());
        }
        if (personDescriptor.getTags() != null) {
            person.addTags(personDescriptor.getTags());
        }
        return person;
    }
}
