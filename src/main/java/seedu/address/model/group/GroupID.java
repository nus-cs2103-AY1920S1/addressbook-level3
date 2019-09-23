package seedu.address.model.group;

public class GroupID {
    private final Integer identifier;

    public GroupID(Integer id) {
        this.identifier = id;
    }

    public Integer getIdentifier() {
        return identifier;
    }

    public String toString(){
        return identifier.toString();
    }

    public boolean equals(GroupID id){
        if(identifier.intValue() == id.getIdentifier().intValue()){
            return true;
        } else {
            return false;
        }
    }

}
