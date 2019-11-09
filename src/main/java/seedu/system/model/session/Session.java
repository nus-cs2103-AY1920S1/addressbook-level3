package seedu.system.model.session;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.system.model.attempt.Attempt;
import seedu.system.model.attempt.exceptions.AttemptHasBeenAttemptedException;
import seedu.system.model.competition.Competition;
import seedu.system.model.participation.Participation;
import seedu.system.model.person.Name;
import seedu.system.model.session.exceptions.AttemptsSubmittedException;
import seedu.system.model.session.exceptions.CompetitionEndedException;
import seedu.system.model.session.exceptions.IncompleteAttemptSubmissionException;
import seedu.system.model.session.exceptions.NoOngoingSessionException;
import seedu.system.model.session.exceptions.OngoingSessionException;
import seedu.system.model.session.exceptions.PreviousAttemptNotDoneException;

/**
 * Handles the competition session for a particular competition,
 * and the participation athletes associated with it.
 */
public class Session {
    private static Session session = null;

    private ObservableList<Participation> participationList;
    private ObservableList<ParticipationAttempt> attemptList;
    private List<Participation> loadedParticipations; // list of participations who have submitted their attempts

    private Competition competition; // the competition of the session
    private boolean isPrepared; // whether competition and attempts are ongoing
    private boolean isLifterReady; // whether the next lifter is ready to make his attempt

    private Session() {
        this.participationList = FXCollections.observableArrayList();
        this.attemptList = FXCollections.observableArrayList();
        this.loadedParticipations = new ArrayList<>();

        this.competition = null;
        this.isPrepared = false;
        this.isLifterReady = false;
    }

    public static Session getInstance() {
        if (session == null) {
            session = new Session();
        }
        return session;
    }

    /**
     * Starts a new session by loading the participations of the ongoing competition.
     *
     * @param competition the current competition of the session
     * @param participations the list of participations in the competition session
     * @throws OngoingSessionException if there is already an ongoing session
     */
    public void start(Competition competition, ObservableList<Participation> participations)
            throws OngoingSessionException {
        if (isOngoing()) {
            throw new OngoingSessionException();
        }

        this.competition = competition;
        this.participationList = participations;
        this.attemptList = FXCollections.observableArrayList();
        for (Participation p : participations) {
            if (p.getAreAttemptsSubmitted()) {
                loadAttempts(p, p.getAttempts());
            }
        }
    }

    /**
     * Loads all 9 attempts that the participation athlete is going to make.
     *
     * @param participation the participation whose just submitted all attempts
     * @param attempts a list of the participation's 9 attempts for the different lifts
     *
     * @throws AttemptsSubmittedException when a participant has submitted his/her attempts
     * @throws NoOngoingSessionException if there is no ongoing session
     */
    private void loadAttempts(Participation participation, List<Attempt> attempts)
            throws AttemptsSubmittedException, NoOngoingSessionException {
        if (!isOngoing()) {
            throw new NoOngoingSessionException();
        }

        if (loadedParticipations.contains(participation)) {
            throw new AttemptsSubmittedException(participation.getPerson());
        }

        for (int i = 0; i < attempts.size(); i++) {
            Attempt attempt = attempts.get(i);
            if (!attempt.getHasAttempted()) {
                // index param: 1,2,3 are squats attempts in order; 4,5,6 for bench; 7,8,9 deadlift
                ParticipationAttempt partAttempt = new ParticipationAttempt(participation, attempt, i + 1);
                attemptList.add(partAttempt);
            }
        }
        loadedParticipations.add(participation);
    }

    /**
     * Prepares the session by sorting the participants and their attempts accordingly.
     *
     * @throws NoOngoingSessionException if there is no ongoing session to prepare for
     * @throws IncompleteAttemptSubmissionException if there exists athletes who have not submitted their attempts
     */
    private void prepare() throws NoOngoingSessionException, IncompleteAttemptSubmissionException {
        if (!isOngoing()) {
            throw new NoOngoingSessionException();
        }

        // create list of participations names who have not submitted their attempts
        List<Name> nonSubmissionNames = new ArrayList<>();
        for (Participation p : participationList) {
            if (!loadedParticipations.contains(p)) {
                nonSubmissionNames.add(p.getName());
            }
        }

        if (!nonSubmissionNames.isEmpty()) {
            throw new IncompleteAttemptSubmissionException(nonSubmissionNames);
        }

        attemptList.sort(new ParticipationAttemptComparator());
        isPrepared = true;
        isLifterReady = true;
    }

    /**
     * Retrieves the next lifter in the queue to attempt his weight.
     *
     * @return the Participation of the next lifter to be attempting his weight
     *
     * @throws NoOngoingSessionException if there is no ongoing session to get the next lifter
     * @throws IncompleteAttemptSubmissionException if there exists athletes who have not submitted their attempts
     * @throws PreviousAttemptNotDoneException if the previous lifter has not completed his attempt,
     *                                         and the next lifter is not ready to be called
     * @throws CompetitionEndedException if the last attempt has been made, and the competition has come to an end
     */
    public ParticipationAttempt nextLifter() throws NoOngoingSessionException, IncompleteAttemptSubmissionException,
            PreviousAttemptNotDoneException, CompetitionEndedException {
        if (!isOngoing()) {
            throw new NoOngoingSessionException();
        }

        if (!isPrepared) {
            prepare();
        }

        if (!isLifterReady) {
            throw new PreviousAttemptNotDoneException();
        }

        if (attemptList.isEmpty()) {
            throw new CompetitionEndedException(participationList.get(0).getCompetition());
        }

        ParticipationAttempt nextParticipationAttempt = attemptList.get(0);
        isLifterReady = false;
        return nextParticipationAttempt;
    }

    /**
     * Records the attempt made by the lifter.
     *
     * @throws NoOngoingSessionException if there is no ongoing competition session
     * @throws AttemptHasBeenAttemptedException if an attempt has already been made
     * @throws IncompleteAttemptSubmissionException if there exists athletes who have not submitted their attempts
     */
    public ParticipationAttempt attemptMade() throws NoOngoingSessionException,
            AttemptHasBeenAttemptedException, IncompleteAttemptSubmissionException {
        if (!isOngoing()) {
            throw new NoOngoingSessionException();
        }

        if (isLifterReady) {
            throw new AttemptHasBeenAttemptedException();
        }

        isLifterReady = true;
        return attemptList.remove(0);
    }

    /**
     * Ends the session, and resets all the data stored by the ended session.
     *
     * @throws NoOngoingSessionException if there is no ongoing session
     */
    public void end() throws NoOngoingSessionException {
        if (!isOngoing()) {
            throw new NoOngoingSessionException();
        }

        competition = null;
        isPrepared = false;
        loadedParticipations = new ArrayList<>();
    }

    public Participation getParticipationByName(Name name) {
        Participation participation = null;
        for (Participation p : participationList) {
            if (p.getName().equals(name)) {
                participation = p;
            }
        }
        return participation;
    }

    public ObservableList<Participation> getParticipationList() {
        return participationList;
    }

    public ObservableList<ParticipationAttempt> getAttemptList() {
        return attemptList;
    }

    public Competition getCompetition() {
        return competition;
    }

    public boolean isOngoing() {
        return competition != null;
    }

    public boolean isPrepared() {
        return isPrepared;
    }

    public boolean isLifterReady() {
        return isLifterReady;
    }

    /**
     * Gets the participation attempt instance of the following lifter for the session.
     *
     * @return the next participation attempt in the competition
     */
    public ParticipationAttempt getFollowingLifter() {
        try {
            return attemptList.get(1);
        } catch (IndexOutOfBoundsException ioe) {
            return null;
        }
    }
}
