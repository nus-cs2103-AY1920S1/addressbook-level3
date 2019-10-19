package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.Predicates;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Team;
import seedu.address.model.entitylist.MentorList;
import seedu.address.model.entitylist.ParticipantList;
import seedu.address.model.entitylist.ReadOnlyEntityList;
import seedu.address.model.entitylist.TeamList;
import seedu.address.model.person.Person;
import seedu.address.storage.AlfredStorage;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {

    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    // EntityLists
    protected ParticipantList participantList = new ParticipantList();
    protected TeamList teamList = new TeamList();
    protected MentorList mentorList = new MentorList();

    protected FilteredList<Participant> filteredParticipantList;
    protected FilteredList<Team> filteredTeamList;
    protected FilteredList<Mentor> filteredMentorList;

    // TODO: Remove the null values which are a placeholder due to the multiple constructors.
    // Also will have to change the relevant attributes to final.
    private AlfredStorage storage = null;
    private ModelHistoryManager history = null;
    private AddressBook addressBook = null;
    private final UserPrefs userPrefs;
    private FilteredList<Person> filteredPersons = null;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    public ModelManager(AlfredStorage storage, ReadOnlyUserPrefs userPrefs) {
        super();
        this.userPrefs = new UserPrefs(userPrefs);
        this.storage = storage;
        // TODO: Remove: Currently it is here to make tests pass.
        this.addressBook = new AddressBook();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    /**
     * Initializes the various lists used. If storage contains no data, it defaults to loading
     * the sample lists provided.
     */
    public void initialize() {
        // Try loading the 3 lists into memory.
        try {
            Optional<ParticipantList> storageParticipantList =
                    this.storage.readParticipantList();
            if (storageParticipantList.isEmpty()) {
                this.participantList = new ParticipantList();
            } else {
                this.participantList = storageParticipantList.get();
                int largestIdUsed = participantList.list().stream()
                        .map(participant -> ((Entity) participant).getId().getNumber())
                        .max(Integer::compare).get();
                participantList.setLastUsedId(largestIdUsed);
            }
        } catch (AlfredException e) {
            logger.warning("Initialising new ParticipantList. "
                           + "Problem encountered reading ParticipantList from storage: " + e.getMessage());
            this.participantList = new ParticipantList();
        }

        try {
            Optional<MentorList> storageMentorList = this.storage.readMentorList();
            if (storageMentorList.isEmpty()) {
                this.mentorList = new MentorList();
            } else {
                this.mentorList = storageMentorList.get();
                int largestIdUsed = mentorList.list().stream()
                        .map(mentor -> ((Entity) mentor).getId().getNumber())
                        .max(Integer::compare).get();
                mentorList.setLastUsedId(largestIdUsed);
            }
        } catch (AlfredException e) {
            logger.warning("Initialising new MentorList. "
                           + "Problem encountered reading MentorList from storage: " + e.getMessage());
            this.mentorList = new MentorList();
        }

        try {
            Optional<TeamList> storageTeamList = this.storage.readTeamList();
            if (storageTeamList.isEmpty()) {
                this.teamList = new TeamList();
            } else {
                this.teamList = storageTeamList.get();
                int largestIdUsed = teamList.list().stream()
                        .map(team -> ((Entity) team).getId().getNumber())
                        .max(Integer::compare).get();
                teamList.setLastUsedId(largestIdUsed);
            }
        } catch (AlfredException e) {
            logger.warning("Initialising new TeamList. "
                           + "Problem encountered reading TeamList from storage: " + e.getMessage());
            this.teamList = new TeamList();
        }

        //The following try-catch block is necessary to ensure that the teamList loaded is valid
        //and the data has not been tampered with.
        try {
            for (Team t: this.teamList.getSpecificTypedList()) {
                validateNewTeamObject(t);
            }
        } catch (ModelValidationException e) {
            logger.severe("Team List is not valid. New EntityLists will be initialised.");
            this.participantList = new ParticipantList();
            this.mentorList = new MentorList();
            this.teamList = new TeamList();
        }

        try {
            this.history = new ModelHistoryManager(this.participantList, ParticipantList.getLastUsedId(),
                    this.mentorList, MentorList.getLastUsedId(),
                    this.teamList, TeamList.getLastUsedId());
        } catch (AlfredModelHistoryException e) {
            logger.severe("Unable to initialise ModelHistoryManager.");
        }

        this.filteredParticipantList =
                new FilteredList<>(this.participantList.getSpecificTypedList());
        this.filteredMentorList =
                new FilteredList<>(this.mentorList.getSpecificTypedList());
        this.filteredTeamList =
                new FilteredList<>(this.teamList.getSpecificTypedList());

        // Optional TODO: reimplement this logic here.
        // Optional<ReadOnlyAddressBook> addressBookOptional;
        // ReadOnlyAddressBook initialData;
        // try {
        //    addressBookOptional = storage.readAddressBook();
        //    if (!addressBookOptional.isPresent()) {
        //        logger.info("Data file not found. Will be starting with a sample AddressBook");
        //    }
        //    initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        // } catch (DataConversionException e) {
        //    logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
        //    initialData = new AddressBook();
        // } catch (IOException e) {
        //    logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
        //    initialData = new AddressBook();
        // }
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
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
    public Path getParticipantListFilePath() {
        return userPrefs.getParticipantListFilePath();
    }

    @Override
    public Path getTeamListFilePath() {
        return userPrefs.getTeamListFilePath();
    }

    @Override
    public Path getMentorListFilePath() {
        return userPrefs.getMentorListFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //========== EntityListMethods ===============

    /**
     * Returns the participant list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getParticipantList() {
        return this.participantList;
    }

    /**
     * Returns the team list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getTeamList() {
        return this.teamList;
    }

    /**
     * Returns the mentor list located in the Model Manager.
     *
     * @return ReadableEntityList
     */
    public ReadOnlyEntityList getMentorList() {
        return this.mentorList;
    }

    public FilteredList<Participant> getFilteredParticipantList() {
        return this.filteredParticipantList;
    }

    public FilteredList<Mentor> getFilteredMentorList() {
        return this.filteredMentorList;
    }

    public FilteredList<Team> getFilteredTeamList() {
        return this.filteredTeamList;
    }

    //========== Entity Methods =============================

    /* Participant Methods */

    /**
     * Gets the participant by id.
     *
     * @param id
     * @return Participant Object
     * @throws AlfredException if the Participant cannot be found.
     */
    public Participant getParticipant(Id id) throws AlfredException {
        return this.participantList.get(id);
    }

    /**
     * Adds the participant into the list.
     *
     * @param participant
     * @throws AlfredException
     */
    public void addParticipant(Participant participant) throws AlfredException {
        this.participantList.add(participant);
        this.saveList(PrefixType.P);
    }

    /**
     * Updates the participant in the list, if any.
     *
     * @param id
     * @param participant
     */
    public void updateParticipant(Id id, Participant participant) throws AlfredException {
        Team targetTeam;
        try {
            targetTeam = this.getTeamByParticipantId(id);
        } catch (MissingEntityException e) {
            this.participantList.update(id, participant);
            this.saveList(PrefixType.P);
            return;
        }
        this.participantList.update(id, participant);

        boolean isSuccessful = targetTeam.updateParticipant(participant);
        if (!isSuccessful) {
            logger.warning("The participant is not in the team provided");
            throw new ModelValidationException("Participant is not in the team provided");
        }

        this.saveList(PrefixType.P);
        this.saveList(PrefixType.T);
    }

    /**
     * Deletes the participant by id.
     *
     * @param id
     * @return Participant
     */
    public Participant deleteParticipant(Id id) throws AlfredException {
        Team targetTeam;
        try {
            targetTeam = this.getTeamByParticipantId(id);
        } catch (MissingEntityException e) {
            Participant participantToDelete = this.participantList.delete(id);
            this.saveList(PrefixType.P);
            return participantToDelete;
        }
        Participant participantToDelete = this.participantList.delete(id);
        boolean isSuccessful = targetTeam.deleteParticipant(participantToDelete);
        if (!isSuccessful) {
            logger.warning("Participant does not exist");
            throw new ModelValidationException("Participant does not exist");
        }

        this.saveList(PrefixType.P);
        this.saveList(PrefixType.T);
        return participantToDelete;
    }

    /* Team Methods*/

    /**
     * Gets team by id.
     *
     * @param id
     * @return
     * @throws AlfredException
     */
    public Team getTeam(Id id) throws AlfredException {
        return this.teamList.get(id);
    }

    /**
     * Gets the team by participant id.
     *
     * @param participantId
     * @return Team
     * @throws MissingEntityException if the team to get does not exist.
     */
    public Team getTeamByParticipantId(Id participantId) throws MissingEntityException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        for (Team t: teams) {
            for (Participant p: t.getParticipants()) {
                if (p.getId().equals(participantId)) {
                    return t;
                }
            }
        }
        throw new MissingEntityException("Team with said participant cannot be found.");
    }

    /**
     * Gets the team by mentor id.
     *
     * @param mentorId
     * @return Team
     * @throws MissingEntityException if the team to get does not exist.
     */
    public Team getTeamByMentorId(Id mentorId) throws MissingEntityException {
        List<Team> teams = this.teamList.getSpecificTypedList();
        for (Team t: teams) {
            Optional<Mentor> mentor = t.getMentor();
            if (mentor.isPresent()) {
                if (mentor.get().getId().equals(mentorId)) {
                    return t;
                }
            }
        }
        throw new MissingEntityException("Team with said mentor cannot be found.");
    }

    /**
     * Updates the team with the given teamID.
     *
     * @param teamId
     * @param updatedTeam
     * @throws AlfredException if the update fails
     */
    public void updateTeam(Id teamId, Team updatedTeam) throws AlfredException {
        this.validateNewTeamObject(updatedTeam);
        this.teamList.update(teamId, updatedTeam);
        this.saveList(PrefixType.T);
    }

    /**
     * Adds the team.
     *
     * @param team
     * @throws AlfredException
     */
    public void addTeam(Team team) throws AlfredException {
        this.validateNewTeamObject(team);
        this.teamList.add(team);
        this.saveList(PrefixType.T);
    }

    /**
     * Adds the participant to the given team.
     *
     * @param teamId
     * @param participant
     * @throws AlfredException if the team does not exist.
     */
    public void addParticipantToTeam(Id teamId, Participant participant) throws AlfredException {
        if (!this.participantList.contains(participant.getId())) {
            throw new ModelValidationException("Participant does not exist in participantList");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.addParticipant(participant);
        if (!isSuccessful) {
            logger.severe("Participant is already present in team");
            throw new AlfredModelException("Participant is already present in team");
        }
        this.saveList(PrefixType.T);
    }

    /**
     * Adds the participant to the given team.
     *
     * @param teamId
     * @param mentor
     * @throws AlfredException if the team does not exist.
     */
    public void addMentorToTeam(Id teamId, Mentor mentor) throws AlfredException {
        if (!this.mentorList.contains(mentor.getId())) {
            throw new ModelValidationException("Mentor does not exist in mentorList.");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.addMentor(mentor);
        if (!isSuccessful) {
            logger.severe("Team already has a mentor");
            throw new AlfredModelException("Team already has a mentor");
        }
        this.saveList(PrefixType.T);
    }

    /**
     * Deletes the team.
     *
     * @param id
     * @return Team
     * @throws AlfredException if the deletion of team fails.
     */
    public Team deleteTeam(Id id) throws AlfredException {
        // First delete the Participant objects
        Team teamToDelete = this.teamList.delete(id);
        for (Participant p : teamToDelete.getParticipants()) {
            this.participantList.delete(p.getId());
        }
        this.saveList(PrefixType.T);
        this.saveList(PrefixType.P);

        return teamToDelete;
    }

    /* Mentor Methods */

    /**
     * Gets the mentor by id.
     *
     * @param id
     * @return Mentor
     * @throws AlfredException
     */
    public Mentor getMentor(Id id) throws AlfredException {
        return this.mentorList.get(id);
    }

    /**
     * Adds mentor into the list.
     *
     * @param mentor
     * @throws AlfredException
     */
    public void addMentor(Mentor mentor) throws AlfredException {
        this.mentorList.add(mentor);
        this.saveList(PrefixType.M);
    }

    /**
     * Updates the mentor.
     *
     * @param id
     * @param updatedMentor
     */
    public void updateMentor(Id id, Mentor updatedMentor) throws AlfredException {
        Team targetTeam;
        try {
            targetTeam = this.getTeamByMentorId(id);
        } catch (MissingEntityException e) {
            this.mentorList.update(id, updatedMentor);
            this.saveList(PrefixType.M);
            return;
        }

        this.mentorList.update(id, updatedMentor);
        boolean isSuccessful = targetTeam.updateMentor(updatedMentor);
        if (!isSuccessful) {
            logger.severe("Unable to update the mentor in team as it is not the "
                    + "same id");
            throw new ModelValidationException("Unable to update the mentor in team as it is not the "
                    + "same id");
        }

        this.saveList(PrefixType.M);
        this.saveList(PrefixType.T);
    }

    /**
     * Deletes the mentor.
     *
     * @param id
     * @return Mentor that is deleted
     * @throws AlfredException
     */
    public Mentor deleteMentor(Id id) throws AlfredException {
        Team targetTeam;
        try {
            targetTeam = this.getTeamByMentorId(id);
        } catch (MissingEntityException e) {
            Mentor mentorToDelete = this.mentorList.delete(id);
            this.saveList(PrefixType.M);
            return mentorToDelete;
        }

        Mentor mentorToDelete = this.getMentor(id);
        boolean isSuccessful = targetTeam.deleteMentor(mentorToDelete);
        if (!isSuccessful) {
            logger.severe("Unable to delete the mentor from the team");
            throw new AlfredModelException("Update to delete the mentor from the team");
        }

        this.saveList(PrefixType.M);
        this.saveList(PrefixType.T);
        return mentorToDelete;
    }

    //=========== Utils ==============================================================

    /**
     * Helper function to save the lists.
     * @param type
     */
    private void saveList(PrefixType type) {
        try {
            switch(type) {
            case T:
                this.storage.saveTeamList(this.teamList);
                break;
            case M:
                this.storage.saveMentorList(this.mentorList);
                break;
            case P:
                this.storage.saveParticipantList(this.participantList);
                break;
            default:
            }
        } catch (IOException e) {
            logger.severe("Failed to save the list into storage due to IOException");
        }
    }

    //=========== Utils ==================================================================

    /**
     * Validates the Participant and Mentor attributes of a CRUD team object.
     *
     * @param team
     * @throws ModelValidationException
     */
    private void validateNewTeamObject(Team team) throws ModelValidationException {
        // Check if the participants are valid, then if mentor is valid.
        for (Participant p: team.getParticipants()) {
            if (!this.participantList.contains(p.getId())) {
                throw new ModelValidationException("Participant in team does not exist in ParticipantList");
            }
        }
        Optional<Mentor> mentor = team.getMentor();
        if (!mentor.isEmpty()) {
            if (!this.mentorList.contains(mentor.get().getId())) {
                throw new ModelValidationException("Mentor in team does not exist in mentorList");
            }
        }
    }

    //=========== Find methods ==================================================================

    /**
     * This method searches for all participants whose name matches the param.
     *
     * @param name
     * @return {@code List<Participant>}
     */
    public List<Participant> findParticipantByName(String name) {
        List<Participant> results = new ArrayList<>();
        for (Participant p: this.participantList.getSpecificTypedList()) {
            if (p.getName().toString().contains(name)) {
                results.add(p);
            }
        }
        this.filteredParticipantList.setPredicate(
                Predicates.getPredicateFindEntityByName(name));
        return results;
    }

    /**
     * This method searches for all teams whose name matches the param.
     *
     * @param name
     * @return {@code List<Team>}
     */
    public List<Team> findTeamByName(String name) {
        List<Team> results = new ArrayList<>();
        for (Team t: this.teamList.getSpecificTypedList()) {
            if (t.getName().toString().contains(name)) {
                results.add(t);
            }
        }
        this.filteredTeamList.setPredicate(
                Predicates.getPredicateFindEntityByName(name));
        return results;
    }

    /**
     * This method searches for all mentors whose name matches the param.
     *
     * @param name
     * @return {@code List<Mentor>}
     */
    public List<Mentor> findMentorByName(String name) {
        List<Mentor> results = new ArrayList<>();
        for (Mentor m: this.mentorList.getSpecificTypedList()) {
            if (m.getName().toString().contains(name)) {
                results.add(m);
            }
        }
        this.filteredMentorList.setPredicate(
                Predicates.getPredicateFindEntityByName(name));
        return results;
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        addressBook.removePerson(target);
    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }


    // =========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && userPrefs.equals(other.userPrefs)
                && filteredPersons.equals(other.filteredPersons);
    }

    //========== ModelHistory Methods ===============
    /**
     * This method will update the ModelHistoryManager object with the current state of the model.
     * This method is expected to be called during the `execute()` method of each Command, right after
     * any transformations/mutations have been made to the data in Model.
     */
    public void updateHistory() {
        try {
            this.history.updateHistory(this.participantList, ParticipantList.getLastUsedId(),
                    this.mentorList, MentorList.getLastUsedId(),
                    this.teamList, TeamList.getLastUsedId());
        } catch (AlfredModelHistoryException e) {
            logger.warning("Problem encountered updating model state history.");
        }
    }

    /**
     * This method will undo the effects of the previous command executed and return the state of
     * the ModelManager to the state where the previous command executed is undone.
     * @throws AlfredModelHistoryException
     */
    public void undo() throws AlfredModelHistoryException {
        if (this.history.canUndo()) {
            ModelHistoryRecord hr = this.history.undo();

            //Set Last Used IDs for each of the EntityLists
            ParticipantList.setLastUsedId(hr.getParticipantListLastUsedId());
            MentorList.setLastUsedId(hr.getMentorListLastUsedId());
            TeamList.setLastUsedId(hr.getTeamListLastUsedId());

            //Update each of the EntityLists to the state in the ModelHistoryRecord hr
            this.participantList = hr.getParticipantList();
            this.mentorList = hr.getMentorList();
            this.teamList = hr.getTeamList();

            //Update each of the filteredEntityLists
            this.filteredParticipantList =
                    new FilteredList<>(this.participantList.getSpecificTypedList());
            this.filteredMentorList =
                    new FilteredList<>(this.mentorList.getSpecificTypedList());
            this.filteredTeamList =
                    new FilteredList<>(this.teamList.getSpecificTypedList());
        } else {
            throw new AlfredModelHistoryException("Unable to undo.");
        }
    }
}
