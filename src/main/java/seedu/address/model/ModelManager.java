package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;

import seedu.address.commons.Comparators;
import seedu.address.commons.Predicates;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.exceptions.AlfredModelException;
import seedu.address.commons.exceptions.AlfredModelHistoryException;
import seedu.address.commons.exceptions.AlfredRuntimeException;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.MissingEntityException;
import seedu.address.commons.exceptions.ModelValidationException;
import seedu.address.commons.util.LeaderboardUtil;
import seedu.address.logic.commands.Command;
import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.Score;
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

    protected SortedList<Team> sortedTeam;
    protected SortedList<Team> topKTeams;

    // TODO: Remove the null values which are a placeholder due to the multiple
    // constructors.
    // Also will have to change the relevant attributes to final.
    private AlfredStorage storage = null;
    private ModelHistory history = null;
    private CommandHistory commandHistory = null;
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
        this.commandHistory = new CommandHistoryManager();
    }

    /**
     * Initializes the various lists used. If storage contains no data, empty lists
     * are initialized.
     */
    public void initialize() {
        // Try loading the 3 lists into memory.
        try {
            Optional<ParticipantList> storageParticipantList = this.storage.readParticipantList();
            if (storageParticipantList.isEmpty()) {
                this.participantList = new ParticipantList();
            } else {
                this.participantList = storageParticipantList.get();
                int largestIdUsed = participantList.list().stream().map(participant -> participant.getId().getNumber())
                        .max(Integer::compare).orElse(0);
                ParticipantList.setLastUsedId(largestIdUsed);
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
                int largestIdUsed = mentorList.list().stream().map(mentor -> mentor.getId().getNumber())
                        .max(Integer::compare).orElse(0);
                MentorList.setLastUsedId(largestIdUsed);
            }
        } catch (AlfredException e) {
            logger.warning("Initialising new MentorList. " + "Problem encountered reading MentorList from storage: "
                    + e.getMessage());
            this.mentorList = new MentorList();
        }

        try {
            Optional<TeamList> storageTeamList = this.storage.readTeamList();
            if (storageTeamList.isEmpty()) {
                this.teamList = new TeamList();
            } else {
                this.teamList = storageTeamList.get();
                int largestIdUsed = teamList.list().stream().map(team -> team.getId().getNumber()).max(Integer::compare)
                        .orElse(0);
                TeamList.setLastUsedId(largestIdUsed);
            }
        } catch (AlfredException e) {
            logger.warning("Initialising new TeamList. " + "Problem encountered reading TeamList from storage: "
                    + e.getMessage());
            this.teamList = new TeamList();
        }

        // The following try-catch block is necessary to ensure that the teamList loaded
        // is valid
        // and the data has not been tampered with.
        try {
            for (Team t : this.teamList.getSpecificTypedList()) {
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
                    this.mentorList, MentorList.getLastUsedId(), this.teamList, TeamList.getLastUsedId());
        } catch (AlfredModelHistoryException e) {
            logger.severe("Unable to initialise ModelHistoryManager.");
        }

        this.filteredParticipantList = new FilteredList<>(this.participantList.getSpecificTypedList());
        this.filteredMentorList = new FilteredList<>(this.mentorList.getSpecificTypedList());
        this.filteredTeamList = new FilteredList<>(this.teamList.getSpecificTypedList());
        this.sortedTeam = new SortedList<>(this.teamList.getSpecificTypedList());

        // Optional TODO: reimplement this logic here.
        // Optional<ReadOnlyAddressBook> addressBookOptional;
        // ReadOnlyAddressBook initialData;
        // try {
        // addressBookOptional = storage.readAddressBook();
        // if (!addressBookOptional.isPresent()) {
        // logger.info("Data file not found. Will be starting with a sample
        // AddressBook");
        // }
        // initialData =
        // addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        // } catch (DataConversionException e) {
        // logger.warning("Data file not in the correct format. Will be starting with an
        // empty AddressBook");
        // initialData = new AddressBook();
        // } catch (IOException e) {
        // logger.warning("Problem while reading from the file. Will be starting with an
        // empty AddressBook");
        // initialData = new AddressBook();
        // }
    }

    // =========== UserPrefs
    // ==================================================================================

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

    // ========== EntityListMethods ===============

    /**
     * Checks if there exists any {@code Entity} in this {@code ModelManager}.
     */
    public boolean isEmpty() {
        return this.participantList.isEmpty() && this.mentorList.isEmpty() && this.teamList.isEmpty();
    }

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

    public SortedList<Team> getSortedTeamList() {
        return this.sortedTeam;
    }

    /**
     * Resets the filtered lists to display all entities in the list.
     */
    public void resetFilteredLists() {
        this.filteredTeamList.setPredicate(team -> true);
        this.filteredMentorList.setPredicate(mentor -> true);
        this.filteredParticipantList.setPredicate(participant -> true);
    }

    // ========== Entity Methods =============================

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
        Participant participantToDelete = this.participantList.delete(id); // May throw MissingEntityException here
        this.saveList(PrefixType.P);

        Team targetTeam;
        try {
            targetTeam = this.getTeamByParticipantId(id);
        } catch (MissingEntityException e) {
            return participantToDelete;
        }

        boolean isSuccessful = targetTeam.deleteParticipant(participantToDelete);
        if (!isSuccessful) {
            logger.warning("Participant does not exist");
            throw new ModelValidationException("Participant does not exist");
        }

        this.saveList(PrefixType.T);
        return participantToDelete;
    }

    /* Team Methods */

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
        for (Team t : teams) {
            for (Participant p : t.getParticipants()) {
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
        for (Team t : teams) {
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
     * Updates the given team's score with the given score.
     *
     * @param team  the team who's score is to be updated.
     * @param score the score to which the team's score will be updated.
     * @throws AlfredException if the update fails.
     */
    public void updateTeamScore(Team team, Score score) throws AlfredException {
        team.setScore(score);
        updateTeam(team.getId(), team);
    }

    /**
     * Adds to the given team's score the given score.
     *
     * @param team  the team who's score is to be added to.
     * @param score the score by which the team's score will be increased.
     * @throws AlfredException if the update fails.
     */
    public void addTeamScore(Team team, Score score) throws AlfredException {
        int currentScore = Integer.parseInt(team.getScore().toString());
        int scoreToAdd = Integer.parseInt(score.toString());

        if (currentScore == Score.MAX_SCORE) {
            throw new IllegalValueException(Score.MAX_SCORE_MESSAGE);
        } else if (currentScore + scoreToAdd > 100) {
            team.setScore(new Score(100));
        } else {
            Score newScore = new Score(currentScore + scoreToAdd);
            team.setScore(newScore);
        }
        updateTeam(team.getId(), team);
    }

    /**
     * Subtracts the given score from the given team's current score.
     *
     * @param team  the team who's score is to be subtracted from.
     * @param score the score which will be subtracted from the team's current
     *              score.
     * @throws AlfredException if the update fails.
     */
    @Override
    public void subtractTeamScore(Team team, Score score) throws AlfredException {
        int currentScore = Integer.parseInt(team.getScore().toString());
        int scoreToSub = Integer.parseInt(score.toString());

        if (currentScore == Score.MIN_SCORE) {
            throw new IllegalValueException(Score.MIN_SCORE_MESSAGE);
        } else if (currentScore - scoreToSub < 0) {
            team.setScore(new Score(0));
        } else {
            Score newScore = new Score(currentScore - scoreToSub);
            team.setScore(newScore);
        }
        updateTeam(team.getId(), team);
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
        try {
            boolean isSuccessful = targetTeam.addParticipant(participant);
            if (!isSuccessful) {
                logger.severe("Participant is already present in team");
                throw new AlfredModelException("Participant is already present in team");
            }
            this.saveList(PrefixType.T);
        } catch (Exception e) {
            throw new AlfredModelException(e.getMessage());
        }
    }

    /**
     * Removes the participant from the given team.
     *
     * @param teamId
     * @param participant
     * @throws AlfredException if the team does not exist.
     */
    public void removeParticipantFromTeam(Id teamId, Participant participant) throws AlfredException {
        if (!this.participantList.contains(participant.getId())) {
            throw new ModelValidationException("Participant does not exist in participantList");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.deleteParticipant(participant);

        if (!isSuccessful) {
            logger.severe("Team does not have this Participant");
            throw new AlfredModelException("Team does not have this Participant");
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
     * Removes the mentor from the given team.
     *
     * @param teamId
     * @param mentor
     * @throws AlfredException if the team does not exist.
     */
    public void removeMentorFromTeam(Id teamId, Mentor mentor) throws AlfredException {
        if (!this.mentorList.contains(mentor.getId())) {
            throw new ModelValidationException("Mentor does not exist in mentorList.");
        }

        Team targetTeam;
        try {
            targetTeam = this.getTeam(teamId);
        } catch (MissingEntityException e) {
            throw e;
        }
        boolean isSuccessful = targetTeam.deleteMentor(mentor);
        if (!isSuccessful) {
            logger.severe("Team does not have this Mentor");
            throw new AlfredModelException("Team does not have this Mentorr");
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

        boolean allParticipantsDeleted = true;
        for (Participant p : teamToDelete.getParticipants()) {
            try {
                this.participantList.delete(p.getId());
            } catch (MissingEntityException e) {
                allParticipantsDeleted = false;
            }
        }
        this.saveList(PrefixType.T);
        this.saveList(PrefixType.P);

        if (!allParticipantsDeleted) {
            throw new AlfredRuntimeException("Duplicate assigning of teams for certain participants.");
        }

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
            logger.severe("Unable to update the mentor in team as it is not the " + "same id");
            throw new ModelValidationException("Unable to update the mentor in team as it is not the " + "same id");
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
        Mentor mentorToDelete = this.mentorList.delete(id); // May throw MissingEntityException here
        this.saveList(PrefixType.M);

        Team targetTeam;
        try {
            targetTeam = this.getTeamByMentorId(id);
        } catch (MissingEntityException e) {
            return mentorToDelete;
        }

        boolean isSuccessful = targetTeam.deleteMentor(mentorToDelete);
        if (!isSuccessful) {
            logger.severe("Unable to delete the mentor from the team");
            throw new AlfredModelException("Update to delete the mentor from the team");
        }

        this.saveList(PrefixType.T);
        return mentorToDelete;
    }

    // =========== Utils
    // ==============================================================

    /**
     * Helper function to save the lists.
     *
     * @param type
     */
    private void saveList(PrefixType type) {
        try {
            switch (type) {
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

    // =========== Utils
    // ==================================================================

    /**
     * Validates the Participant and Mentor attributes of a CRUD team object.
     *
     * @param team
     * @throws ModelValidationException
     */
    private void validateNewTeamObject(Team team) throws ModelValidationException {
        // Check if the participants are valid, then if mentor is valid.
        for (Participant p : team.getParticipants()) {
            if (!this.participantList.contains(p.getId())) {
                throw new ModelValidationException("Participant in team does not exist in ParticipantList");
            }
        }
        Optional<Mentor> mentor = team.getMentor();
        if (mentor.isPresent()) {
            if (!this.mentorList.contains(mentor.get().getId())) {
                throw new ModelValidationException("Mentor in team does not exist in mentorList");
            }
        }
    }

    // =========== Leader Board methods
    // ==================================================================

    /**
     * Resets the {@code sortedTeam} list to its original order without any sorting,
     * then arranges it to sort the current teams stored in Alfred in descending
     * order of their score. Implements additional Comparators {@code comparators}
     * for tie-breaking if specified by the user.
     */
    public final void setSimpleLeaderboard(ArrayList<Comparator<Team>> comparators) {
        this.sortedTeam = new SortedList<>(this.teamList.getSpecificTypedList());
        for (Comparator<Team> comparator : comparators) {
            this.sortedTeam.setComparator(comparator);
        }
        // Set the comparator to rank by score last as in-place sorting is taking place,
        // so ranking by score
        // in the end will rank teams by their score and retain the tie-breaks obtained
        // from the previously applied
        // comparators.
        this.sortedTeam.setComparator(Comparators.rankByScore());
    }

    /**
     * Sorts the sortedTeam list by the value of the team's score and additional
     * Comparators {@code comparators} if specified by the user, and filters the top
     * {@code k} teams, inclusive of ties, into {@code topKTeams} list.
     */
    public final void setTopK(int k, ArrayList<Comparator<Team>> comparators) {
        setSimpleLeaderboard(comparators);

        // Create a copy of the sorted teams from which teams can be removed without
        // damaging the original sorted teams list.
        ObservableList<Team> teams = FXCollections.observableArrayList(sortedTeam);
        teams = LeaderboardUtil.topKWithTie(teams, k, comparators);
        this.sortedTeam = new SortedList<>(teams);
    }

    /**
     * Sorts the sortedTeam list by the value of the team's score and additional
     * Comparators {@code comparators} if specified by the user, and filters the top
     * {@code k} teams into {@code topKTeams} list, resolving ties on a random
     * basis.
     */
    public final void setTopKRandom(int k, ArrayList<Comparator<Team>> comparators) {
        setSimpleLeaderboard(comparators);
        ObservableList<Team> teams = FXCollections.observableArrayList(sortedTeam);
        teams = LeaderboardUtil.randomWinnersGenerator(teams, k, comparators);
        this.sortedTeam = new SortedList<>(teams);
    }

    // =========== Find methods
    // ==================================================================

    /**
     * This method searches for all participants whose name matches the param.
     *
     * @param predicate
     * @return {@code List<Participant>}
     */
    public List<Participant> findParticipant(Predicate<Participant> predicate) {
        this.filteredParticipantList.setPredicate(predicate);
        return this.participantList.getSpecificTypedList().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * This method searches for all teams whose name matches the param.
     *
     * @param predicate
     * @return {@code List<Team>}
     */
    public List<Team> findTeam(Predicate<Team> predicate) {
        this.filteredTeamList.setPredicate(predicate);
        return this.teamList.getSpecificTypedList().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * This method searches for all mentors whose name matches the param.
     *
     * @param predicate
     * @return {@code List<Mentor>}
     */
    public List<Mentor> findMentor(Predicate<Mentor> predicate) {
        this.filteredMentorList.setPredicate(predicate);
        return this.mentorList.getSpecificTypedList().stream().filter(predicate).collect(Collectors.toList());
    }

    /**
     * Sets the predicate to show detailed information of {@code entity}.
     *
     * @param entity {@code Entity} to view.
     */
    public void viewEntity(Entity entity) {
        PrefixType entityType = entity.getPrefix();
        Predicate<Entity> predicate = Predicates.viewSpecifiedEntity(entity);
        switch (entityType) {
        case M:
            this.filteredMentorList.setPredicate(predicate);
            return;
        case P:
            this.filteredParticipantList.setPredicate(predicate);
            return;
        case T:
            this.filteredTeamList.setPredicate(predicate);
            return;
        default:
            // should never reach here
            throw new RuntimeException();
        }
    }

    // ========== ModelHistory Methods ===============

    /**
     * This method will update the ModelHistoryManager object with the current state
     * of the model. This method is expected to be called during the `execute()`
     * method of each Command, right after any transformations/mutations have been
     * made to the data in Model.
     */
    public void updateHistory(Command c) {
        try {
            this.history.updateHistory(this.participantList, ParticipantList.getLastUsedId(), this.mentorList,
                    MentorList.getLastUsedId(), this.teamList, TeamList.getLastUsedId(), c);
        } catch (AlfredModelHistoryException e) {
            logger.warning("Problem encountered updating model state history.");
        }
    }

    /**
     * This method will undo the effects of the previous {@code numToUndo}
     * command(s) executed and return the state of the ModelManager to the state
     * where these previous command(s) executed is undone.
     *
     * @param numToUndo number of commands in ModelHistory to undo.
     * @throws AlfredModelHistoryException
     */
    public void undo(int numToUndo) throws AlfredModelHistoryException {
        if (this.history.canUndo(numToUndo)) {
            ModelHistoryRecord hr = this.history.undo(numToUndo);
            updateModelState(hr);
        } else {
            throw new AlfredModelHistoryException("Unable to undo.");
        }
        // Saves the 3 EntityLists to Storage
        this.saveList(PrefixType.P);
        this.saveList(PrefixType.M);
        this.saveList(PrefixType.T);
    }

    /**
     * This method will return the ModelManager to the state where the next
     * {@code numToRedo} command(s) executed is/are redone.
     *
     * @param numToRedo number of commands in ModelHistory to redo.
     * @throws AlfredModelHistoryException
     */
    public void redo(int numToRedo) throws AlfredModelHistoryException {
        if (this.history.canRedo(numToRedo)) {
            ModelHistoryRecord hr = this.history.redo(numToRedo);
            updateModelState(hr);
        } else {
            throw new AlfredModelHistoryException("Unable to redo.");
        }
        // Saves the 3 EntityLists to Storage
        this.saveList(PrefixType.P);
        this.saveList(PrefixType.M);
        this.saveList(PrefixType.T);
    }

    /**
     * Updates the current Model state (for each of the EntityLists and their
     * lastUsedIDs) using a ModelHistoryRecord.
     *
     * @param hr ModelHistoryRecord containing the state of each of the EntityLists
     *           and their lastUsedIDs.
     * @throws AlfredModelHistoryException
     */
    private void updateModelState(ModelHistoryRecord hr) throws AlfredModelHistoryException {
        // Set Last Used IDs for each of the EntityLists
        ParticipantList.setLastUsedId(hr.getParticipantListLastUsedId());
        MentorList.setLastUsedId(hr.getMentorListLastUsedId());
        TeamList.setLastUsedId(hr.getTeamListLastUsedId());

        // Update each of the EntityLists to the state in the ModelHistoryRecord hr
        try {
            this.participantList = hr.getParticipantList().copy();
            this.mentorList = hr.getMentorList().copy();
            this.teamList = hr.getTeamList().copy();
        } catch (AlfredModelException e) {
            throw new AlfredModelHistoryException("Unable to copy EntityLists from ModelHistoryRecord");
        }

        // Update each of the filteredEntityLists
        this.filteredParticipantList = new FilteredList<>(this.participantList.getSpecificTypedList());
        this.filteredMentorList = new FilteredList<>(this.mentorList.getSpecificTypedList());
        this.filteredTeamList = new FilteredList<>(this.teamList.getSpecificTypedList());
    }

    /**
     * Gets a String detailing the previously executed commands that can be undone
     * by the user.
     */
    public String getCommandHistoryString() {
        return this.history.getCommandHistoryString();
    }

    /**
     * Returns a List of Strings describing the commands that can be undone.
     */
    public List<String> getUndoCommandHistory() {
        return this.history.getUndoCommandHistory();
    }

    /**
     * Returns a List of Strings describing the commands that can be redone.
     */
    public List<String> getRedoCommandHistory() {
        return this.history.getRedoCommandHistory();
    }

    /**
     * Returns a List of CommandRecords describing the commands that can be
     * undone/redone
     */
    public ArrayList<CommandRecord> getCommandHistory() {
        return this.history.getCommandHistory();
    }

    public void recordCommandExecution(String commandInputString) {
        this.commandHistory.saveCommandExecutionString(commandInputString);
    }

    public String getPrevCommandString() {
        return this.commandHistory.getPrevCommandString();
    }

    public String getNextCommandString() {
        return this.commandHistory.getNextCommandString();
    }

}
