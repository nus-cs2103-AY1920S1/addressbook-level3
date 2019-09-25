package seedu.address.model.person;

import seedu.address.model.tag.Tag;

import java.util.Set;

public class PersonDescriptor {

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Remark remark;
    private Set<Tag> tags;

    public PersonDescriptor(){
        this.name = null;
        this.phone = null;
        this.email = null;
        this.address = null;
        this.remark = null;
        this.tags = null;
    }

    public boolean isAnyFieldEdited(){
        if(this.name == null && this.phone == null && this.email == null && this.address == null && this.remark == null && this.tags == null){
            return false;
        } else {
            return true;
        }
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

    public void setName(Name name){
        this.name = name;
    }

    public void setPhone(Phone phone) {
        this.phone = phone;
    }

    public void setEmail(Email email) {
        this.email = email;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setRemark(Remark remark) {
        this.remark = remark;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }
}
