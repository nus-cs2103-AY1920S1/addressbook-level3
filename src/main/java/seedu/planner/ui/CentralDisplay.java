package seedu.planner.ui;

import java.time.LocalDate;
import java.util.logging.Logger;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Accordion;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.Region;

import seedu.planner.commons.core.LogsCenter;
import seedu.planner.logic.commands.result.ResultInformation;
import seedu.planner.logic.commands.result.UiFocus;
import seedu.planner.model.accommodation.Accommodation;
import seedu.planner.model.activity.Activity;
import seedu.planner.model.contact.Contact;
import seedu.planner.model.day.Day;
import seedu.planner.model.field.Name;
import seedu.planner.ui.panels.AccommodationListPanel;
import seedu.planner.ui.panels.ActivityListPanel;
import seedu.planner.ui.panels.ContactListPanel;
import seedu.planner.ui.panels.HelpListPanel;
import seedu.planner.ui.panels.InfoListPanel;

//@@author 1nefootstep
/**
 * A UI for the window that is displayed below the command box and feedbackDisplay.
 */
public class CentralDisplay extends UiPart<Region> {

    private static final String FXML = "CentralDisplay.fxml";
    private static final Logger logger = LogsCenter.getLogger(CentralDisplay.class);
    private SimpleObjectProperty<LocalDate> startDateProperty;
    private SimpleObjectProperty<Name> nameProperty;
    private ObservableList<Day> dayList;

    @FXML
    private Accordion sideDisplay;
    @FXML
    private TitledPane activityPane;
    @FXML
    private TitledPane contactPane;
    @FXML
    private TitledPane accommodationPane;
    @FXML
    private TabPane tabDisplay;
    @FXML
    private Tab agendaTab;
    @FXML
    private Tab infoTab;
    @FXML
    private Tab helpTab;

    private final InfoListPanel infoListPanel;
    private final HelpListPanel helpListPanel;
    private final AgendaManager agendaManager;

    public CentralDisplay(ObservableList<Day> dayList, ObservableList<Accommodation> accommodationList,
                          ObservableList<Activity> activityList, ObservableList<Contact> contactList,
                          SimpleObjectProperty<LocalDate> startDateProperty,
                          SimpleObjectProperty<Name> nameProperty) {
        super(FXML);
        logger.fine("Initializing with the following: " + dayList + accommodationList + activityList + contactList
                + startDateProperty + " and " + nameProperty);
        this.startDateProperty = startDateProperty;
        this.dayList = dayList;
        this.nameProperty = nameProperty;
        this.infoListPanel = new InfoListPanel();
        this.helpListPanel = new HelpListPanel();
        this.agendaManager = new AgendaManager(this.startDateProperty.getValue(), dayList);

        setupDesign();
        setupContent(dayList, accommodationList, activityList, contactList);
        setupListeners();
    }

    /**
     * Setup the colour, height, width of parts in the central display.
     */
    private void setupDesign() {
        // allows the tabDisplay to expand according to the width of user display
        tabDisplay.prefWidthProperty().bind(this.getRoot().prefWidthProperty());
    }

    /**
     * Setups the content contained within the central display.
     */
    private void setupContent(ObservableList<Day> dayList, ObservableList<Accommodation> accommodationList,
                              ObservableList<Activity> activityList, ObservableList<Contact> contactList) {
        accommodationPane.setContent((new AccommodationListPanel(accommodationList)).getRoot());
        activityPane.setContent((new ActivityListPanel(activityList)).getRoot());
        contactPane.setContent((new ContactListPanel(contactList)).getRoot());
        // expands the activity pane
        sideDisplay.setExpandedPane(activityPane);

        infoTab.setContent(infoListPanel.getRoot());
        helpTab.setContent(helpListPanel.getRoot());

        agendaTab.setText(nameProperty.getValue().toString() + " Itinerary");
        agendaManager.updateAgenda(dayList);

        if (dayList.size() == 0) {
            agendaTab.setContent(null);
        } else {
            agendaTab.setContent(agendaManager.getAgenda());
        }
    }


    /**
     * Setup listeners that will update the central display.
     */
    private void setupListeners() {
        startDateProperty.addListener((observable, oldValue, newValue) -> {
            agendaManager.updateAgenda(dayList);
            agendaManager.setDisplayedLocalDateTime(newValue.atStartOfDay());
        });
        nameProperty.addListener((observable, oldValue, newValue) -> {
            agendaTab.setText(newValue.toString() + " Itinerary");
        });
        agendaTab.setOnSelectionChanged((event) -> {
            if (agendaTab.isSelected()) {
                agendaManager.updateAgenda(dayList);
                agendaManager.updateSkin(dayList, startDateProperty.getValue());
            }
        });
        helpTab.setOnSelectionChanged((event) -> {
            if (helpTab.isSelected()) {
                generateCommandHelpSummary();
            }
        });

    }

    /**
     * Displays the relevant information in the infoTab.
     */
    public void changeInfo(ResultInformation[] resultInformation) {
        infoListPanel.changeInfo(resultInformation);
    }

    /**
     * Expands tabs according to the provided {@code uiFocus}.
     */
    public void changeFocus(UiFocus[] uiFocus) {
        for (UiFocus u : uiFocus) {
            switch (u) {
            case AGENDA:
                tabDisplay.getSelectionModel().select(agendaTab);
                agendaManager.updateSkin(dayList, startDateProperty.getValue());
                agendaManager.updateAgenda(dayList);
                break;
            case INFO:
                tabDisplay.getSelectionModel().select(infoTab);
                break;
            case HELP:
                tabDisplay.getSelectionModel().select(helpTab);
                break;
            case ACCOMMODATION:
                sideDisplay.setExpandedPane(accommodationPane);
                break;
            case ACTIVITY:
                sideDisplay.setExpandedPane(activityPane);
                break;
            case CONTACT:
                sideDisplay.setExpandedPane(contactPane);
                break;
            default:
                throw new AssertionError(u.toString() + " is not handled in changeFocus.");
            }
        }
    }

    /**
     * Generates a complete summary of all commands available in plan2travel.
     */
    public void generateCommandHelpSummary() {
        helpListPanel.generateCommandHelpSummary();
    }
}
