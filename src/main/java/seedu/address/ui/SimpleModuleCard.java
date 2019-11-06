package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.module.Module;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays information of a {@code Module} without prereqs.
 */
public class SimpleModuleCard extends UiPart<Region> {

    private static final String FXML = "SimpleModuleListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Module module;

    @FXML
    private HBox moduleCardPane;
    @FXML
    private Label name;
    @FXML
    private Label mcCount;
    @FXML
    private FlowPane tags;

    public SimpleModuleCard(Module module) {
        super(FXML);
        this.module = module;
        name.setText(module.getModuleCode().value + " " + module.getName().fullName);
        mcCount.setText(Integer.toString(module.getMcCount()));
        module.getTags().asUnmodifiableObservableList().stream()
                .sorted(Comparator.comparing(Tag::getTagName))
                .forEach(tag -> {
                    Label tagLabel = new Label(tag.getTagName());
                    tagLabel.setWrapText(true);
                    if (tag.isDefault()) {
                        tagLabel.setId("defaultTag");
                    } else {
                        tagLabel.setId("userTag");
                    }
                    tags.getChildren().add(tagLabel);
                });
    }
}
