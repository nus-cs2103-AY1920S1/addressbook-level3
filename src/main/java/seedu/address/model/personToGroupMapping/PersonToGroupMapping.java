package seedu.address.model.personToGroupMapping;

import seedu.address.model.group.GroupID;
import seedu.address.model.person.PersonID;

public class PersonToGroupMapping {
    private final GroupID groupID;
    private final PersonID personID;

    public PersonToGroupMapping(PersonID personID, GroupID groupID){
        this.groupID = groupID;
        this.personID = personID;
    }

    public GroupID getGroupID(){
        return this.groupID;
    }

    public PersonID getPersonID(){
        return this.personID;
    }

    public String toString(){
        String s = "MAP: " + personID.toString() + " - " + groupID.toString();
        return s;
    }

    public boolean equals(PersonToGroupMapping mapping){
        if(mapping.getPersonID().equals(personID) && mapping.getGroupID().equals(groupID)){
            return true;
        } else {
            return false;
        }
    }
}
