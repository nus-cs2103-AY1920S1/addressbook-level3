package tagline.logic.commands.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import tagline.commons.core.Messages;
import tagline.logic.commands.Command;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.group.ContactIdEqualsSearchIdsPredicate;
import tagline.model.group.Group;
import tagline.model.group.GroupDescription;
import tagline.model.group.GroupName;
import tagline.model.group.GroupNameEqualsKeywordPredicate;
import tagline.model.group.MemberId;

/**
 * Represents a group command with hidden internal logic and the ability to be executed.
 */
public abstract class GroupCommand extends Command {
    public static final String COMMAND_KEY = "group";

    /**
     * Finds and returns a {@code Group} with the GroupName of {@code groupName}
     * from {@code Model}.
     */
    public static Group findOneGroup(Model model, GroupNameEqualsKeywordPredicate predicate)
        throws CommandException {
        model.updateFilteredGroupList(predicate);
        List<Group> filteredGroupList = model.getFilteredGroupList();
        Optional<Group> optionalGroup = filteredGroupList.stream().findFirst();

        if (optionalGroup.isEmpty()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_NAME);
        }

        return optionalGroup.get();
    }

    /**
     * Finds and returns a {@code Group} with the GroupName of {@code groupName}
     * from {@code Model}.
     */
    public static Group findOneGroup(Model model, String groupName) throws CommandException {
        // doesnt seem to work with emptystring, im not sure why
        assert groupName != "";
        List<String> keywords = new ArrayList<>();
        keywords.add(groupName);
        return findOneGroup(model, new GroupNameEqualsKeywordPredicate(keywords));
    }

    /**
      * Checks and returns a {@code Set<MemberId>} with the MemberId of {@code targetGroup}
      * that can be found as {@code ContactId} in {@code Model}, side effect: sets ContactList.
      */
    private static Set<MemberId> verifyMemberIdWithModel(Model model, Group targetGroup) {
        List<String> members = targetGroup.getMemberIds()
                .stream()
                .map(member -> member.value)
                .collect(Collectors.toList());

        // to display all contacts which are Group members
        model.updateFilteredContactList(new ContactIdEqualsSearchIdsPredicate(members));

        // this bit to ensure groupmembers are as updated in case of storage error
        // done by getting all the MemberIds in the group, AddressBook
        // for those contacts, then make them into MemberIds
        Set<MemberId> updatedGroupMemberIds = model.getFilteredContactList()
                .stream()
                .map(contact -> contact.getContactId().toInteger().toString())
                .map(member -> new MemberId(member))
                .collect(Collectors.toSet());

        return updatedGroupMemberIds;
    }

    /**
     * Checks and returns a {@code Group} with the MemberId of {@code targetGroup}
     * that can be found as {@code ContactId} in {@code Model}, side effect sets ContactList.
     */
    public static Group verifyGroupWithModel(Model model, Group targetGroup) {
        // check to ensure Group members are ContactIds that can be found in Model
        GroupName editedGroupName = targetGroup.getGroupName();
        GroupDescription editedGroupDescription = targetGroup.getGroupDescription();
        Set<MemberId> verifiedGroupMemberIds = verifyMemberIdWithModel(model, targetGroup);

        // this Group should only have contactId of contacts found in ContactList after calling setGroup
        Group verifiedGroup = new Group(editedGroupName, editedGroupDescription,
                verifiedGroupMemberIds);
        return verifiedGroup;
    }

}
