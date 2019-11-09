package seedu.address.ui.schedule;

import java.util.List;
import java.util.function.Function;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.mapping.Role;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.ui.UiPart;

/**
 * A class to show the members of a group.
 */
public class MemberList extends UiPart<Region> {

    private static final String FXML = "MemberList.fxml";

    @FXML
    private GridPane listHeaders;

    @FXML
    private VBox listMembers;

    @FXML
    private ScrollPane listHeaderContainer;

    @FXML
    private ScrollPane listMemberContainer;

    public MemberList(List<Name> memberNames, List<Email> emails, List<Role> roles,
                      Function<Integer, String> colorGenerator, List<Name> filteredMembers) {
        super(FXML);
        for (int i = 0; i < memberNames.size(); i++) {
            MemberCard memberCard = new MemberCard(memberNames.get(i).fullName,
                    emails.get(i).value, roles.get(i).toString(), colorGenerator.apply(i));
            if (filteredMembers != null && !filteredMembers.contains(memberNames.get(i))) {
                memberCard.reduceOpacity();
            }
            listMembers.getChildren().add(memberCard.getRoot());
        }
        listHeaderContainer.hvalueProperty().bindBidirectional(listMemberContainer.hvalueProperty());
    }

    /**
     * A subclass of MemberList to show MemberCard of group members.
     */
    class MemberCard extends UiPart<Region> {
        private static final String FXML = "MemberCard.fxml";

        @FXML
        private GridPane memberCard;
        @FXML
        private Label memberName;
        @FXML
        private Label memberEmail;
        @FXML
        private Label memberRole;
        @FXML
        private Region colorRegion;

        public MemberCard(String name, String email, String role, String color) {
            super(FXML);

            this.memberName.setText(name);

            if (email.equals("")) {
                this.memberEmail.setText("No Email Available");
            } else {
                this.memberEmail.setText(email);
            }

            if (role.equals("")) {
                this.memberRole.setText("No Role Available");
            } else {
                this.memberRole.setText(role);
            }

            setUpCard(color);
        }

        private GridPane setUpCard(String color) {
            //Inline styling because the colour is dependent what is passed.
            DropShadow shadowEffect = new DropShadow();
            shadowEffect.setOffsetY(3);
            shadowEffect.setOffsetX(3);
            memberCard.setEffect(shadowEffect);
            colorRegion.setStyle("-fx-background-color:" + color + ";"
                    + "-fx-border-radius: 2;"
                    + "-fx-background-radius: 2;"
                    + "-fx-min-height: 50;");
            return memberCard;
        }

        private void reduceOpacity() {
            memberCard.setStyle("-fx-opacity: 0.5;");
        }
    }
}
