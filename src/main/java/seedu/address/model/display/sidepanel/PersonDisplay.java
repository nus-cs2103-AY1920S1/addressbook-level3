package seedu.address.model.display.sidepanel;

import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Remark;
import seedu.address.model.tag.Tag;

/**
 * Person display model.
 */
public class PersonDisplay extends Display {
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Address address;
    private final Remark remark;
    private final Set<Tag> tags;

    public PersonDisplay(Name name, Phone phone, Email email, Address address, Remark remark, Set<Tag> tags) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.remark = remark;
        this.tags = tags;
    }

    public PersonDisplay(Person person) {
        this.name = person.getName();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        this.address = person.getAddress();
        this.remark = person.getRemark();
        this.tags = person.getTags();
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Remark getRemark() {
        return remark;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public boolean isSamePerson(PersonDisplay person) {
        if (person == this) {
            return true;
        } else if (person instanceof PersonDisplay) {
            PersonDisplay p = (PersonDisplay) person;
            return this.name.equals(p.name)
                    && this.phone.equals(p.phone)
                    && this.email.equals(p.email)
                    && this.address.equals(p.address)
                    && this.remark.equals(p.remark)
                    && this.tags.equals(p.tags);
        } else {
            return false;
        }
    }

}
