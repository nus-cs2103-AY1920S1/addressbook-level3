package seedu.address.model.personToGroupMapping;

import seedu.address.model.group.GroupID;
import seedu.address.model.person.PersonID;

import java.util.ArrayList;

public class PersonToGroupMappingList {
    private ArrayList<PersonToGroupMapping> mappings;

    public PersonToGroupMappingList(){
        this.mappings = new ArrayList<PersonToGroupMapping>();
    }

    public boolean addPersonToGroupMapping(PersonToGroupMapping map){
        int i;
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).equals(map)){
                return false;
            }
        }
        this.mappings.add(map);
        return true;
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

    public ArrayList<GroupID> findGroupsOfPerson(PersonID personID){
        int i;
        ArrayList<GroupID> groups = new ArrayList<GroupID>();
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).getPersonID().equals(personID)){
                groups.add(mappings.get(i).getGroupID());
            }
        }
        return groups;
    }

    public ArrayList<PersonID> findPersonsOfGroup(GroupID groupID){
        int i;
        ArrayList<PersonID> persons = new ArrayList<PersonID>();
        for(i = 0; i < mappings.size(); i++){
            if(mappings.get(i).getGroupID().equals(groupID)){
                persons.add(mappings.get(i).getPersonID());
            }
        }
        return persons;
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
