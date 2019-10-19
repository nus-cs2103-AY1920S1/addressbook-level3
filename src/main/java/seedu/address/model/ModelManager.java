package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.AppSettings;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.internal.gmaps.ClosestLocation;
import seedu.address.model.display.detailwindow.DetailWindowDisplay;
import seedu.address.model.display.detailwindow.DetailWindowDisplayType;
import seedu.address.model.display.detailwindow.WeekSchedule;
import seedu.address.model.display.sidepanel.GroupDisplay;
import seedu.address.model.display.sidepanel.PersonDisplay;
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
import seedu.address.model.module.exceptions.ModuleNotFoundException;
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
import seedu.address.model.person.schedule.Schedule;
import seedu.address.websocket.Cache;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final UserPrefs userPrefs;

    private TimeBook timeBook = null;

    private PersonList personList;
    private GroupList groupList;
    private PersonToGroupMappingList personToGroupMappingList;

    private NusModsData nusModsData;
    private ClosestLocation closestLocation;

    // UI display
    private DetailWindowDisplay detailWindowDisplay;
    private SidePanelDisplay sidePanelDisplay;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(TimeBook timeBook,
                        ReadOnlyUserPrefs userPrefs,
                        NusModsData nusModsData,
                        ClosestLocation closestLocation) {

        this.timeBook = timeBook;
        this.personList = timeBook.getPersonList();
        this.groupList = timeBook.getGroupList();
        this.personToGroupMappingList = timeBook.getPersonToGroupMappingList();
        this.closestLocation = closestLocation;
        this.nusModsData = nusModsData;

        int personCounter = -1;
        for (int i = 0; i < personList.getPersons().size(); i++) {
            if (personList.getPersons().get(i).getPersonId().getIdentifier() > personCounter) {
                personCounter = personList.getPersons().get(i).getPersonId().getIdentifier();
            }
        }

        int groupCounter = -1;
        for (int i = 0; i < groupList.getGroups().size(); i++) {
            if (groupList.getGroups().get(i).getGroupId().getIdentifier() > groupCounter) {
                groupCounter = groupList.getGroups().get(i).getGroupId().getIdentifier();
            }
        }

        // sets the appropriate counter for person and group constructor
        Person.setCounter(personCounter + 1);
        Group.setCounter(groupCounter + 1);

        this.userPrefs = new UserPrefs(userPrefs);
    }

    public ModelManager(TimeBook timeBook) {
        this(timeBook, new UserPrefs(), new NusModsData(), new ClosestLocation());
    }

    public ModelManager() {
        this(new TimeBook());
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        /*ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);*/

        return false;
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public AppSettings getAppSettings() {
        return userPrefs.getAppSettings();
    }

    @Override
    public void setAppSettings(AppSettings appSettings) {
        requireNonNull(appSettings);
        userPrefs.setAppSettings(appSettings);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public AcadYear getDefaultAcadYear() {
        return userPrefs.getAcadYear();
    }

    @Override
    public SemesterNo getDefaultSemesterNo() {
        return userPrefs.getSemesterNo();
    }

    //=========== Person Accessors =============================================================

    @Override
    public PersonList getPersonList() {
        return personList;
    }

    @Override
    public ObservableList<Person> getObservablePersonList() {
        return timeBook.getUnmodifiablePersonList();
    }

    @Override
    public Person addPerson(PersonDescriptor personDescriptor) throws DuplicatePersonException {
        Person isAdded = this.personList.addPerson(personDescriptor);
        return isAdded;
    }

    @Override
    public Person findPerson(Name name) throws PersonNotFoundException {
        Person person = personList.findPerson(name);
        if (person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public Person findPerson(PersonId personId) {
        Person person = personList.findPerson(personId);
        if (person != null) {
            return person;
        } else {
            return null;
        }
    }

    @Override
    public void addEvent(Name name, Event event) throws PersonNotFoundException, EventClashException {
        personList.addEvent(name, event);
    }

    @Override
    public Person editPerson(Name name, PersonDescriptor personDescriptor)
            throws PersonNotFoundException, NoPersonFieldsEditedException, DuplicatePersonException {
        return personList.editPerson(name, personDescriptor);
    }

    @Override
    public void deletePerson(PersonId personId) throws PersonNotFoundException {
        deletePersonFromMapping(personId);
        personList.deletePerson(personId);
    }

    @Override
    public void deletePerson(Name name) throws PersonNotFoundException {
        Person person = findPerson(name);
        deletePerson(person.getPersonId());
    }

    @Override
    public ArrayList<GroupId> findGroupsOfPerson(PersonId personId) {
        return personToGroupMappingList.findGroupsOfPerson(personId);
    }

    @Override
    public boolean isEventClash(Name name, Event event) throws PersonNotFoundException {
        Person person = findPerson(name);
        Schedule schedule = person.getSchedule();
        if (schedule.isClash(event)) {
            return true;
        } else {
            return false;
        }
    }

    //=========== Group Accessors =============================================================

    @Override
    public GroupList getGroupList() {
        return groupList;
    }

    @Override
    public ObservableList<Group> getObservableGroupList() {
        return timeBook.getUnmodifiableGroupList();
    }

    @Override
    public Group addGroup(GroupDescriptor groupDescriptor) throws DuplicateGroupException {
        Group isAdded = this.groupList.addGroup(groupDescriptor);
        return isAdded;
    }

    @Override
    public Group editGroup(GroupName groupName, GroupDescriptor groupDescriptor)
            throws GroupNotFoundException, NoGroupFieldsEditedException, DuplicateGroupException {
        return groupList.editGroup(groupName, groupDescriptor);
    }

    @Override
    public Group findGroup(GroupName groupName) throws GroupNotFoundException {
        return groupList.findGroup(groupName);
    }

    @Override
    public Group findGroup(GroupId groupId) throws GroupNotFoundException {
        return groupList.findGroup(groupId);
    }

    @Override
    public void deleteGroup(GroupId groupId) throws GroupNotFoundException {
        deleteGroupFromMapping(groupId);
        groupList.deleteGroup(groupId);
    }

    @Override
    public void deleteGroup(GroupName groupName) throws GroupNotFoundException {
        Group group = groupList.findGroup(groupName);
        deleteGroup(group.getGroupId());
    }

    @Override
    public ArrayList<PersonId> findPersonsOfGroup(GroupId groupId) {
        return personToGroupMappingList.findPersonsOfGroup(groupId);
    }

    //=========== Mapping Accessors =============================================================

    @Override
    public PersonToGroupMappingList getPersonToGroupMappingList() {
        return personToGroupMappingList;
    }

    @Override
    public void addPersonToGroupMapping(PersonToGroupMapping mapping) throws DuplicateMappingException {
        personToGroupMappingList.addPersonToGroupMapping(mapping);
    }

    @Override
    public PersonToGroupMapping findPersonToGroupMapping(PersonId personId, GroupId groupId)
            throws MappingNotFoundException {
        return personToGroupMappingList.findPersonToGroupMapping(personId, groupId);
    }

    @Override
    public void deletePersonToGroupMapping(PersonToGroupMapping mapping) throws MappingNotFoundException {
        personToGroupMappingList.deletePersonToGroupMapping(mapping);
    }

    @Override
    public void deletePersonFromMapping(PersonId personId) {
        personToGroupMappingList.deletePersonFromMapping(personId);
    }

    @Override
    public void deleteGroupFromMapping(GroupId groupId) {
        personToGroupMappingList.deleteGroupFromMapping(groupId);
    }

    @Override
    public Role findRole(PersonId personId, GroupId groupId) throws MappingNotFoundException {
        return personToGroupMappingList.findRole(personId, groupId);
    }

    //=========== UI Model =============================================================

    @Override
    public DetailWindowDisplay getDetailWindowDisplay() {
        return detailWindowDisplay;
    }

    @Override
    public SidePanelDisplay getSidePanelDisplay() {
        return sidePanelDisplay;
    }

    @Override
    public void updateDetailWindowDisplay(DetailWindowDisplay detailWindowDisplay) {
        this.detailWindowDisplay = detailWindowDisplay;
    }

    @Override
    public void updateDetailWindowDisplay(Name name, LocalDateTime time, DetailWindowDisplayType type) {
        ArrayList<WeekSchedule> weekSchedules = new ArrayList<>();
        try {
            WeekSchedule weekSchedule = new WeekSchedule(name.toString(), time, findPerson(name), Role.emptyRole());
            weekSchedules.add(weekSchedule);
            DetailWindowDisplay detailWindowDisplay = new DetailWindowDisplay(weekSchedules, type);
            updateDetailWindowDisplay(detailWindowDisplay);
        } catch (PersonNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateDetailWindowDisplay(GroupName groupName, LocalDateTime time, DetailWindowDisplayType type) {
        try {
            Group group = groupList.findGroup(groupName);
            GroupId groupId = group.getGroupId();
            GroupDisplay groupDisplay = new GroupDisplay(group);
            ArrayList<PersonId> personIds = findPersonsOfGroup(group.getGroupId());
            ArrayList<WeekSchedule> weekSchedules = new ArrayList<>();
            for (int i = 0; i < personIds.size(); i++) {
                Person person = findPerson(personIds.get(i));
                Role role = findRole(personIds.get(i), groupId);
                if (role == null) {
                    role = Role.emptyRole();
                }
                WeekSchedule weekSchedule = new WeekSchedule(groupName.toString(), time, person, role);
                weekSchedules.add(weekSchedule);
            }
            DetailWindowDisplay detailWindowDisplay = new DetailWindowDisplay(weekSchedules, type, groupDisplay);
            updateDetailWindowDisplay(detailWindowDisplay);
        } catch (GroupNotFoundException | MappingNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateSidePanelDisplay(SidePanelDisplay sidePanelDisplay) {
        this.sidePanelDisplay = sidePanelDisplay;
    }

    @Override
    public void updateSidePanelDisplay(SidePanelDisplayType type) {
        SidePanelDisplay sidePanelDisplay;
        ArrayList<PersonDisplay> displayPersons = new ArrayList<>();
        ArrayList<GroupDisplay> displayGroups = new ArrayList<>();
        ArrayList<Person> persons = timeBook.getPersonList().getPersons();
        ArrayList<Group> groups = timeBook.getGroupList().getGroups();
        for (int i = 0; i < persons.size(); i++) {
            displayPersons.add(new PersonDisplay(persons.get(i)));
        }
        for (int i = 0; i < groups.size(); i++) {
            displayGroups.add(new GroupDisplay(groups.get(i)));
        }
        sidePanelDisplay = new SidePanelDisplay(displayPersons, displayGroups, type);
        updateSidePanelDisplay(sidePanelDisplay);
    }

    //=========== Suggesters =============================================================

    @Override
    public ArrayList<String> personSuggester(String prefix) {
        ArrayList<String> suggestions = new ArrayList<>();
        ArrayList<Person> persons = timeBook.getPersonList().getPersons();

        for (int i = 0; i < persons.size(); i++) {
            String name = persons.get(i).getName().toString();
            if (name.startsWith(prefix)) {
                suggestions.add(name);
            }
        }
        return suggestions;
    }

    @Override
    public ArrayList<String> personSuggester(String prefix, String groupName) {

        ArrayList<String> suggestions = new ArrayList<>();

        Group group;
        try {
            group = findGroup(new GroupName(groupName));
        } catch (GroupNotFoundException e) {
            return suggestions;
        }

        ArrayList<PersonId> personIds = findPersonsOfGroup(group.getGroupId());
        for (int i = 0; i < personIds.size(); i++) {
            String name = findPerson(personIds.get(i)).getName().toString();
            if (name.startsWith(prefix)) {
                suggestions.add(name);
            }
        }
        return suggestions;
    }

    @Override
    public ArrayList<String> groupSuggester(String prefix) {
        ArrayList<String> suggestions = new ArrayList<>();
        ArrayList<Group> groups = timeBook.getGroupList().getGroups();

        for (int i = 0; i < groups.size(); i++) {
            String name = groups.get(i).getGroupName().toString();
            if (name.startsWith(prefix)) {
                suggestions.add(name);
            }
        }
        return suggestions;
    }

    //=========== NusModsData ================================================================================

    @Override
    public NusModsData getNusModsData() {
        return nusModsData;
    }

    @Override
    public Module findModule(ModuleId moduleId) throws ModuleNotFoundException {
        Module module;
        try {
            module = nusModsData.getModuleList().findModule(moduleId);
        } catch (ModuleNotFoundException ex1) {
            Optional<Module> moduleOptional = Cache.loadModule(moduleId);
            if (moduleOptional.isEmpty()) {
                throw new ModuleNotFoundException();
            }
            module = moduleOptional.get();
        }
        return module;
    }

    @Override
    public ModuleList getModuleList() {
        return nusModsData.getModuleList();
    }

    @Override
    public void addModule(Module module) {
        nusModsData.addModule(module);
    }

    public String getAcadSemStartDateString(AcadYear acadYear, SemesterNo semesterNo) {
        return nusModsData.getAcadCalendar().getStartDateString(acadYear, semesterNo);
    }

    public List<String> getHolidayDateStrings() {
        return nusModsData.getHolidays().getHolidayDates();
    }

    //=========== ClosestLocation ================================================================================

    @Override
    public String getClosestLocationVenues(ArrayList<Venue> venues) {
        return closestLocation.closestLocationVenues(venues);
    }

    @Override
    public String getClosestLocationString(ArrayList<String> venues) {
        return closestLocation.closestLocationString(venues);
    }

    //=========== Others =============================================================

    @Override
    public String list() {
        String output = "";
        output += "PERSONS:\n";
        output += personList.toString();

        output += "--------------------------------------------\n";
        output += "GROUPS:\n";
        output += groupList.toString();

        output += "--------------------------------------------\n";
        output += "MAPPINGS: \n";
        output += personToGroupMappingList.toString();

        return output;
    }

    @Override
    public TimeBook getTimeBook() {
        return this.timeBook;
    }

}
