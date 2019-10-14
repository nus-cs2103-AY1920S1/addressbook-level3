package seedu.address.ui;

import java.util.Set;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Panel containing detailed information of the specified Person including
 * the usual details on PersonCard, and also associated Visit information.
 */
public class ProfilePanel extends UiPart<Stage> {
    private static final String FXML = "ProfilePanel.fxml";

    private int target;

    @FXML
    private TextArea nameField;

    @FXML
    private TextArea tagField;

    @FXML
    private TextArea phoneField;

    @FXML
    private TextArea emailField;

    @FXML
    private TextArea addressField;

    @FXML
    private TextArea visitField;


    public ProfilePanel(Stage root) {
        super(FXML, root);
    }

    /**
     * Creates a new ProfilePanel.
     */
    public ProfilePanel() {
        this(new Stage());
        this.getRoot().initStyle(StageStyle.UTILITY);
    }

    /**
     *
     * @param person
     */
    public void setup(Person person) {
        nameField.setText(stringifyName(person.getName()));
        tagField.setText(stringifyTags(person.getTags()));
        phoneField.setText(stringifyPhone(person.getPhone()));
        emailField.setText(stringifyEmail(person.getEmail()));
        addressField.setText(stringifyAddress(person.getAddress()));
        // TODO: Implement the displaying of Visit information neatly
        //visitField.setText(person.getVisitList());
    }

    /**
     *
     * @param name
     * @return
     */
    private String stringifyName(Name name) {
        return name.fullName;
    }

    /**
     *
     * @param tagSet
     * @return
     */
    private String stringifyTags(Set<Tag> tagSet) {
        StringBuilder sb = new StringBuilder();

        if (tagSet.size() > 0) {
            for (Tag tag : tagSet) {
                sb.append(tag.tagName);
                sb.append("; ");
            }
        }

        return sb.toString();
    }

    /**
     *
     * @param phone
     * @return
     */
    private String stringifyPhone(Phone phone) {
        return phone.value;
    }

    /**
     *
     * @param email
     * @return
     */
    private String stringifyEmail(Email email) {
        return email.value;
    }

    /**
     *
     * @param address
     * @return
     */
    private String stringifyAddress(Address address) {
        return address.value;
    }

    // private String stringify

    /**
     * Shows the Profile Panel.
     * @throws IllegalStateException
     * <ul>
     *     <li>
     *         if this method is called on a thread other than the JavaFX Application Thread.
     *     </li>
     *     <li>
     *         if this method is called during animation or layout processing.
     *     </li>
     *     <li>
     *         if this method is called on the primary stage.
     *     </li>
     *     <li>
     *         if {@code dialogStage} is already showing.
     *     </li>
     * </ul>
     */
    public void show() {
        getRoot().show();
        getRoot().centerOnScreen();

        getRoot().addEventHandler(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {
            if (KeyCode.ESCAPE == event.getCode()) {
                getRoot().hide();
            }
        }
        );
    }

    /**
     * Returns true if the Profile panel is currently being shown.
     */
    public boolean isShowing() {
        return getRoot().isShowing();
    }

    /**
     * Hides the Profile panel.
     */
    public void hide() {
        getRoot().hide();
    }

    /**
     * Focuses on the Profile panel.
     */
    public void focus() {
        getRoot().requestFocus();
    }

    @FXML
    void mouseEnterExit(MouseEvent e) {
        Label exitLabel = (Label) e.getSource();
        exitLabel.setUnderline(true);
    }

    @FXML
    void mouseLeaveExit(MouseEvent e) {
        Label exitLabel = (Label) e.getSource();
        exitLabel.setUnderline(false);
    }

    @FXML
    void mouseClickExit(MouseEvent e) {
        getRoot().hide();
    }
}
