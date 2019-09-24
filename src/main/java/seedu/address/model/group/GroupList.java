package seedu.address.model.group;

import java.util.ArrayList;

public class GroupList {
    private ArrayList<Group> groups;

    public GroupList() {
        this.groups = new ArrayList<Group>();
    }

    public boolean addGroup(Group group) {
        if(findGroup(group.getGroupName()) == null){
            this.groups.add(group);
            return true;
        } else {
            return false;
        }
    }

    public Group findGroup(GroupName groupName){
        int i;
        for(i = 0; i < groups.size(); i++){
            if(groups.get(i).getGroupName().toString().equals(groupName.toString())){
                return groups.get(i);
            }
        }
        return null;
    }

    public Group findGroup(GroupID groupID){
        int i;
        for(i = 0; i < groups.size(); i++){
            if(groups.get(i).getGroupID().equals(groupID)){
                return groups.get(i);
            }
        }
        return null;
    }

    public String toString(){
        int i;
        String output = "";
        for(i = 0; i < groups.size(); i++){
            output += groups.get(i).toString();
        }
        return output;
    }

    public boolean deleteGroup(GroupID groupID){
        int i;
        for(i = 0; i < groups.size(); i++){
            if(groups.get(i).getGroupID().equals(groupID)){
                groups.remove(i);
                return true;
            }
        }
        return false;
    }
}
