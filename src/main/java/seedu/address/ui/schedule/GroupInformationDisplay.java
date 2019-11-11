package seedu.address.ui.schedule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.ui.UiPart;

/**
 * A class to handle the view of a group.
 */
public class GroupInformationDisplay extends UiPart<Region> {

    private static final String FXML = "GroupInformationDisplay.fxml";

    @FXML
    private StackPane groupDetails;

    @FXML
    private StackPane groupMembers;

    public GroupInformationDisplay(List<PersonDisplay> members, List<Name> filteredNames,
                                   GroupDisplay groupDisplay, Function<Integer, String> colorGenerator) {
        super(FXML);
        ArrayList<Name> names = members.stream().map(p -> p.getName())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Email> emails = members.stream().map(p -> p.getEmail())
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Role> roles = members.stream().map(p -> p.getRole())
                .collect(Collectors.toCollection(ArrayList::new));

        GroupDetailCard groupCard = new GroupDetailCard(groupDisplay);
        groupDetails.getChildren().add(groupCard.getRoot());
        MemberList memberList = new MemberList(names, emails, roles, colorGenerator, filteredNames);
        groupMembers.getChildren().add(memberList.getRoot());
    }

}
