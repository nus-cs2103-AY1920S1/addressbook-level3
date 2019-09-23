package seedu.address.model.personToGroupMapping;

import seedu.address.model.group.Group;
import seedu.address.model.group.GroupID;
import seedu.address.model.person.PersonID;

import java.util.ArrayList;

public class PersonToGroupMappingList {
    private ArrayList<PersonToGroupMapping> mappings;

    public PersonToGroupMappingList(){
        this.mappings = new ArrayList<PersonToGroupMapping>();
    }

    public void addPersonToGroupMapping(PersonToGroupMapping map){
        this.mappings.add(map);
    }

    public PersonToGroupMapping findPersonToGroupMapping(PersonID personID, GroupID groupID){
        int i;
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).getPersonID().equals(personID) && mappings.get(i).getGroupID().equals(groupID)){
                return mappings.get(i);
            }
        }
        return null;
    }

    public boolean deletePersonToGroupMapping(PersonToGroupMapping mapping){
        int i;
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).equals(mapping)){
                mappings.remove(i);
                return true;
            }
        }
        return false;
    }

    public void deletePersonFromMapping(PersonID personID){
        int i;
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).getPersonID().equals(personID)){
                mappings.remove(i);
                i -= 1;
            }
        }
    }

    public void deleteGroupFromMapping(GroupID groupID){
        int i;
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).getGroupID().equals(groupID)){
                mappings.remove(i);
                i -= 1;
            }
        }
    }

    public String toString(){
        int i;
        String output = "";
        for(i = 0; i < mappings.size(); i++){
            output += mappings.get(i).toString();
            output += "\n";
        }
        return output;
    }
}
