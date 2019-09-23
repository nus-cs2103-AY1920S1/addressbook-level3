package seedu.address.model.person;

public class PersonID {
    private final Integer identifier;

    public PersonID(Integer id) {
        this.identifier = id;
    }

    public Integer getIdentifier() {
        return this.identifier;
    }

    public String toString(){
        return identifier.toString();
    }

    public boolean equals(PersonID id){
        if(identifier.intValue() == id.getIdentifier().intValue()){
            return true;
        } else {
            return false;
        }
    }
}
