package seedu.address.model.group;

public class Group {
    private final GroupID groupID;

    private GroupName groupName;

    private static Integer counter = 0;

    public Group(GroupName groupName){
        this.groupName = groupName;
        this.groupID = new GroupID(counter);
        counter += 1;
    }

    public GroupName getGroupName(){
        return this.groupName;
    }

    public GroupID getGroupID(){
        return this.groupID;
    }

    public String toString(){
        String output = "";
        output += "ID: " + groupID.toString() + " ";
        output += "Name: " + groupName.toString() + "\n";
        return output;
    }


}
