package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Descriptor of a person for person construction.
 */
public class PersonDescriptor {

    private static final Name DEFAULT_NAME = Name.emptyName();
    private static final Phone DEFAULT_PHONE = Phone.emptyPhone();
    private static final Email DEFAULT_EMAIL = Email.emptyEmail();
    private static final Address DEFAULT_ADDRESS = Address.emptyAddress();
    private static final Remark DEFAULT_REMARK = Remark.emptyRemark();

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;

    public PersonDescriptor() {
        this.name = DEFAULT_NAME;
        this.phone = DEFAULT_PHONE;
        this.email = DEFAULT_EMAIL;
        this.address = DEFAULT_ADDRESS;
        this.remark = DEFAULT_REMARK;
        this.tags = null;
    }

    /**
     * Checks if any field has been edited.
     *
     * @return boolean
     */
    public boolean isAnyFieldEdited() {
        if (this.name.equals(DEFAULT_NAME) && this.phone.equals(DEFAULT_PHONE) && this.email.equals(DEFAULT_EMAIL)
                && this.address.equals(DEFAULT_ADDRESS)
                && this.remark.equals(DEFAULT_REMARK) && this.tags == null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Compares if this PersonDescriptor is equal to another.
     * @param personDescriptor to compare
     * @return boolean
     */
    public boolean equals(PersonDescriptor personDescriptor) {
        if (personDescriptor == null) {
            return false;
        } else if (!this.name.equals(personDescriptor.name)) {
            return false;
        } else if (!this.phone.equals(personDescriptor.phone)) {
            return false;
        } else if (!this.email.equals(personDescriptor.email)) {
            return false;
        } else if (!this.address.equals(personDescriptor.address)) {
            return false;
        } else if (!this.remark.equals(personDescriptor.remark)) {
            return false;
        } else {
            return true;
        }
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Phone getPhone() {
        return phone;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Remark getRemark() {
        return remark;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public Set<Tag> getTags() {
        return tags;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
