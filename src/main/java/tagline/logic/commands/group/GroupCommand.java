//@@author e0031374
package tagline.logic.commands.group;

import static tagline.model.contact.ContactModel.PREDICATE_SHOW_ALL_CONTACTS;
import static tagline.model.group.GroupModel.PREDICATE_SHOW_ALL_GROUPS;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import tagline.logic.commands.Command;
import tagline.logic.commands.exceptions.CommandException;
import tagline.model.Model;
import tagline.model.contact.Contact;
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

    public static final String MESSAGE_GROUP_NOT_FOUND = "The group name provided could not be found.";

    /**
     * Checks and returns if {@code Model} contains with the {@code Contact} with {@code ContactId}
     * matching {@code contactId}.
     */
    private static boolean modelContainsContactId(Model model, String contactId) {
        return model.getFilteredContactListWithPredicate(PREDICATE_SHOW_ALL_CONTACTS).stream()
            .map(Contact::getContactId)
            .map(id -> id.value.toString())
            .anyMatch(contact -> contact.equalsIgnoreCase(contactId));
    }

    /**
     * Checks and returns a set of {@code MemberId} which cannot be found as {@code ContactId} in {@code Contact}
     * from {@code Model}.
     */
    public static Set<MemberId> memberIdDoesntExistInContactModel(Model model, Collection<MemberId> members)
        throws CommandException {
        return members.stream()
            .filter(member -> !modelContainsContactId(model, member.value))
            .collect(Collectors.toSet());
    }

    /**
     * Adds a set of {@code MemberId} as String to a provided {@code StringBuilder}
     */
    private static void addMembersToStringBuilder(StringBuilder builder, Collection<MemberId> members) {
        if (members.size() <= 0) {
            builder.append("None");
        } else {
            members.forEach(builder::append);
        }
    }

    /**
     * Appends {@code MemberId} to {@code StringBuilder}
     */
    public static String notFoundString(Collection<MemberId> members) {
        if (members.isEmpty()) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("\n\nThe following members were not found:\n");
        addMembersToStringBuilder(sb, members);
        return sb.toString();
    }

    /**
     * Finds and returns a {@code Group} with the GroupName of {@code groupName}
     * from {@code Model}.
     */
    public static Group findOneGroup(Model model, GroupNameEqualsKeywordPredicate predicate)
        throws CommandException {
        List<Group> filteredGroupList = model.getFilteredGroupListWithPredicate(predicate);
        Optional<Group> optionalGroup = filteredGroupList.stream().findFirst();

        if (optionalGroup.isEmpty()) {
            throw new CommandException(MESSAGE_GROUP_NOT_FOUND);
        }

        return optionalGroup.get();
    }

    /**
     * Finds and returns a {@code Group} with the GroupName of {@code groupName}
     * from {@code Model}.
     */
    public static Group findOneGroup(Model model, String groupName) throws CommandException {
        // doesnt seem to work with emptystring, im not sure why
        //assert groupName != "";

        if (!GroupName.isValidGroupName(groupName)) {
            throw new CommandException(GroupName.MESSAGE_CONSTRAINTS);
        }

        List<GroupName> keywords = new ArrayList<>();
        keywords.add(new GroupName(groupName));
        return findOneGroup(model, new GroupNameEqualsKeywordPredicate(keywords));
    }

    /**
     * Checks and returns a {@code Set<MemberId>} with the MemberId of {@code targetGroup}
     * that can be found as {@code ContactId} in {@code Model}.
     */
    private static Set<MemberId> verifyMemberIdWithModel(Model model, Group targetGroup) {
        List<Contact> filteredContactList = model.getFilteredContactListWithPredicate(
            memberIdsToContactIdPredicate(targetGroup.getMemberIds()));

        // this bit to ensure groupmembers are as updated in case of storage error
        // done by getting all the MemberIds in the group, AddressBook
        // for those contacts, then make them into MemberIds
        Set<MemberId> updatedGroupMemberIds = filteredContactList
            .stream()
            .map(contact -> contact.getContactId().value.toString())
            .map(member -> new MemberId(member))
            .collect(Collectors.toSet());

        return updatedGroupMemberIds;
    }

    /**
     * Checks and returns a {@code Group} with the MemberId of {@code targetGroup}
     * that can be found as {@code ContactId} in {@code Model}.
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

    /**
     * Verifies and all MemberId of {@code targetGroup} can be found as {@code Contact} containing
     * {@code ContactId} in {@code Model}, procedure which side effect replaces Group with verifiedGroup.
     */
    private static void verifyGroupWithModelAndSet(Model model, Group targetGroup) {
        Group verifiedGroup = verifyGroupWithModel(model, targetGroup);
        model.setGroup(targetGroup, verifiedGroup);
    }

    /**
     * Verifies and all MemberId of all {@code Group} in {@code Model} can be found as {@code Contact} containing
     * {@code ContactId} in {@code Model}, procedure with side effect mutating Model
     */
    public static void syncGroupBook(Model model) {
        // makes a copy to prevent mutating Model state while iterating through Model(Group) state
        List<Group> allGroups = model.getFilteredGroupListWithPredicate(PREDICATE_SHOW_ALL_GROUPS)
            .stream().collect(Collectors.toList());
        allGroups.stream()
            .forEach(group -> verifyGroupWithModelAndSet(model, group));
    }

    /**
     * Generates {@code ContactIdEqualsSearchIdsPredicate} from {@code Collection<MemberId>}
     * for convenience.
     */
    public static ContactIdEqualsSearchIdsPredicate memberIdsToContactIdPredicate(Collection<MemberId> members) {
        List<String> membersString = members
            .stream()
            .map(member -> member.value)
            .collect(Collectors.toList());

        // to display all contacts which are Group members
        return new ContactIdEqualsSearchIdsPredicate(membersString);
    }

    /**
     * Generates {@code ContactIdEqualsSearchIdsPredicate} from {@code group}
     * for convenience.
     */
    public static ContactIdEqualsSearchIdsPredicate groupToContactIdPredicate(Group group) {
        return memberIdsToContactIdPredicate(group.getMemberIds());
    }
}
