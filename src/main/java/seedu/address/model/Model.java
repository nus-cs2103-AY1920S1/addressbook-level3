package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.sidepanel.SidePanelDisplay;
import seedu.address.model.display.sidepanel.SidePanelDisplayType;
import seedu.address.model.group.Group;
import seedu.address.model.group.GroupDescriptor;
import seedu.address.model.group.GroupId;
import seedu.address.model.group.GroupList;
import seedu.address.model.group.GroupName;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.group.exceptions.NoGroupFieldsEditedException;
import seedu.address.model.mapping.PersonToGroupMapping;
import seedu.address.model.mapping.PersonToGroupMappingList;
import seedu.address.model.mapping.Role;
import seedu.address.model.mapping.exceptions.DuplicateMappingException;
import seedu.address.model.mapping.exceptions.MappingNotFoundException;
import seedu.address.model.module.AcadYear;
import seedu.address.model.module.Module;
import seedu.address.model.module.ModuleId;
import seedu.address.model.module.ModuleList;
import seedu.address.model.module.SemesterNo;
import seedu.address.model.module.Venue;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonDescriptor;
import seedu.address.model.person.PersonId;
import seedu.address.model.person.PersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventClashException;
import seedu.address.model.person.exceptions.NoPersonFieldsEditedException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.schedule.Event;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    //=========== UserPrefs ==================================================================================

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' App settings.
     */
    public AppSettings getAppSettings();

    /**
     * Sets the user prefs' App settings.
     */
    public void setAppSettings(AppSettings appSettings);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the App setting's acadYear.
     */
    public AcadYear getDefaultAcadYear();

    /**
     * Returns the App setting's semesterNo.
     */
    public SemesterNo getDefaultSemesterNo();

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the filtered person list
     */
    ObservableList<Person> getObservablePersonList();

    //=========== Person Accessors =============================================================

    /**
     * Returns the list of Persons.
     */
    PersonList getPersonList();

    /**
     * Adds a person with personDescriptor to the list of Persons.
     */
    Person addPerson(PersonDescriptor personDescriptor) throws DuplicatePersonException;

    /**
     * Finds a person with a given Name.
     */
    Person findPerson(Name name) throws PersonNotFoundException;

    /**
     * Finds a person with a given PersonId.
     */
    Person findPerson(PersonId personId);

    /**
     * Adds an Event to the schedule of a Person with the given Name.
     */
    void addEvent(Name name, Event event) throws PersonNotFoundException, EventClashException;

    /**
     * Edits the person with given Name with given PersonDescriptor.
     */
    Person editPerson(Name name, PersonDescriptor personDescriptor)
            throws PersonNotFoundException, NoPersonFieldsEditedException, DuplicatePersonException;

    /**
     * Deletes a person with given PersonId.
     */
    void deletePerson(PersonId personId) throws PersonNotFoundException;

    /**
     * Deletes a person with given Name.
     */
    void deletePerson(Name name) throws PersonNotFoundException;

    /**
     * Returns the list of GroupIds which person with PersonId is in.
     */
    ArrayList<GroupId> findGroupsOfPerson(PersonId personId);

    /**
     * Checks if current event of Person clashes with other events in the schedule.
     */
    boolean isEventClash(Name name, Event event) throws PersonNotFoundException;

    //=========== Group Accessors =============================================================

    /**
     * Returns the list of Groups.
     */
    GroupList getGroupList();

    /**
     * Returns an observable list of groups.
     */
    ObservableList<Group> getObservableGroupList();

    /**
     * Adds a Group with groupDescriptor into the list of Groups.
     */
    Group addGroup(GroupDescriptor groupDescriptor) throws DuplicateGroupException;

    /**
     * Edits the person with given Name with given PersonDescriptor.
     */
    Group editGroup(GroupName groupName, GroupDescriptor groupDescriptor)
            throws GroupNotFoundException, NoGroupFieldsEditedException, DuplicateGroupException;

    /**
     * Finds a Group with given GroupName.
     */
    Group findGroup(GroupName groupName) throws GroupNotFoundException;

    /**
     * Finds a Group with given GroupId.
     */
    Group findGroup(GroupId groupId) throws GroupNotFoundException;

    /**
     * Deletes a Group with given GroupId.
     */
    void deleteGroup(GroupId groupId) throws GroupNotFoundException;

    /**
     * Deletes a Group with given GroupName.
     */
    void deleteGroup(GroupName groupName) throws GroupNotFoundException;

    /**
     * Returns a list of PersonId that is in a Group with given GroupId.
     */
    ArrayList<PersonId> findPersonsOfGroup(GroupId groupId);

    //=========== Mapping Accessors =============================================================

    /**
     * Returns the list of PersonToGroupMappings.
     */
    PersonToGroupMappingList getPersonToGroupMappingList();

    /**
     * Adds a person to group mapping to the list of mappings.
     */
    void addPersonToGroupMapping(PersonToGroupMapping mapping) throws DuplicateMappingException;

    /**
     * Returns a mapping with given PersonId and GroupId.
     */
    PersonToGroupMapping findPersonToGroupMapping(PersonId personId, GroupId groupId) throws MappingNotFoundException;

    /**
     * Deletes a mapping with given PersonId and GroupId.
     */
    void deletePersonToGroupMapping(PersonToGroupMapping mapping) throws MappingNotFoundException;

    /**
     * Deletes all mappings with PersonId.
     */
    void deletePersonFromMapping(PersonId personId);

    /**
     * Deletes all mappings with GroupId.
     */
    void deleteGroupFromMapping(GroupId groupId);

    /**
     * Finds the role of the specified mapping.
     */
    Role findRole(PersonId personId, GroupId groupId) throws MappingNotFoundException;

    //=========== UI Model =============================================================

    /**
     * Returns the current main window display model.
     */
    DetailWindowDisplay getDetailWindowDisplay();

    /**
     * Returns the current side panel display model.
     */
    SidePanelDisplay getSidePanelDisplay();

    /**
     * Updates the current main window display.
     */
    void updateDetailWindowDisplay(DetailWindowDisplay detailWindowDisplay);

    /**
     * Updates the current main window display with a Person's schedule.
     */
    void updateDetailWindowDisplay(Name name, LocalDateTime time, DetailWindowDisplayType type);

    /**
     * Updates the current main window display with a Group's schedule.
     */
    void updateDetailWindowDisplay(GroupName groupName, LocalDateTime time, DetailWindowDisplayType type);

    /**
     * Updates the current side panel display.
     */
    void updateSidePanelDisplay(SidePanelDisplay sidePanelDisplay);

    /**
     * Updates the current side panel display of a type.
     */
    void updateSidePanelDisplay(SidePanelDisplayType type);

    //=========== Suggesters =============================================================

    /**
     * Returns a list of Person's names that starts with prefix.
     */
    ArrayList<String> personSuggester(String prefix);

    /**
     * Returns a list of Person's names that starts with prefix in a Group.
     */
    ArrayList<String> personSuggester(String prefix, String groupName);

    /**
     * Returns a list of Group's names that starts with prefix.
     */
    ArrayList<String> groupSuggester(String prefix);

    //=========== NusModsData ================================================================================

    NusModsData getNusModsData();

    /**
     * Returns a module for the given ModuleId (academic year and module code).
     * Tries to find the module from the 3 sources in order:
     * 1. Model.NusModsData.ModuleList (in-memory)
     * 2. Cache Folder
     * 3. NusModsApi
     */
    Module findModule(ModuleId id);

    String getAcadSemStartDateString(AcadYear acadYear, SemesterNo semesterNo);

    List<String> getHolidayDateStrings();

    ModuleList getModuleList();

    void addModule(Module module);

    //=========== GoogleMaps ================================================================================

    /**
     * Returns the common closest location.
     *
     * @param venues ArrayList of venues object
     * @return
     */
    String getClosestLocationVenues(ArrayList<Venue> venues);

    /**
     * Returns the common closest location.
     *
     * @param venues ArrayList of string object
     * @return
     */
    String getClosestLocationString(ArrayList<String> venues);

    //=========== Others =============================================================

    /**
     * Returns a summary of all Persons, Groups, and Mappings.
     */
    String list();

    TimeBook getTimeBook();


}
