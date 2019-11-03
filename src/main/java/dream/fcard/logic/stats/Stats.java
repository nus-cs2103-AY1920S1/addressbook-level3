package dream.fcard.logic.stats;

import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public abstract class Stats {
    /**
     * Represents the user's statistics.
     */

    /** List of Sessions the user has engaged in. */
    SessionList sessionList;

    /** The current Session the user is engaging in. */
    Session currentSession;

    public Stats() {

    }

    /** Sets the sessionList of the current Stats object to the given newSessionList. */
    public void setSessionList(SessionList newSessionList) {
        this.sessionList = newSessionList;
    }

    /** Returns the number of sessions in the sessionList contained inside this Stats object. */
    public int getNumberOfSessions() {
        return this.sessionList.numberOfSessions();
    }

    /** Adds the given Session to the sessionList contained inside this Stats object. */
    public void addSession(Session session) {
        this.sessionList.addSession(session);
    }

    /** Starts a new Session, representing the current Session the user is engaging in. */
    public void startCurrentSession() {
        if (currentSession != null) {
            endCurrentSession(); // should not occur, but should terminate just in case
            System.out.println("Existing current session detected. Terminating it first...");
        }

        // debug (change to Logger when implemented)
        System.out.println("Starting a session...");

        currentSession = new Session(); // currentSession should be null
    }

    /** Ends the current Session the user is engaging in and saves it to the list of Sessions. */
    public void endCurrentSession() {
        // assert current session is not null?
        try {
            // ensure that new UserStats() is called
            //getUserStats(); // temporary

            this.currentSession.endSession();
            this.sessionList.addSession(currentSession);

            // reset currentSession to null since this is terminated
            this.currentSession = null;

            // debug (change to Logger when implemented)
            System.out.println("Ending the current session...");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Current session not found?");
        }
    }

    /** Gets the list of sessions. */
    public SessionList getSessionList() {
        return this.sessionList;
    }

    /** Gets the list of sessions, as the underlying ArrayList, to display in the GUI. */
    public ArrayList<Session> getSessionsAsArrayList() {
        return this.sessionList.getSessionArrayList();
    }

    /** Gets the current session. */
    public Session getCurrentSession() {
        return this.currentSession;
    }

    /** Gets the total length of time spent in sessions, as a String. */
    public String getTotalDurationOfSessionsAsString() {
        return this.sessionList.getTotalDurationAsString();
    }

    /** Creates the TableView object from the list of login sessions. */
    public TableView<Session> getSessionsTableView() {
        ArrayList<Session> sessionsList = this.getSessionsAsArrayList();
        TableView<Session> sessionsTableView = new TableView<>();

        // temporary debug
        //for (Session session : sessionsList) {
        //    System.out.println("Start: " + session.getSessionStartString());
        //    System.out.println("End: " + session.getSessionEndString());
        //    System.out.println("Duration: " + session.getDurationString());
        //}

        sessionsTableView.setItems(FXCollections.observableArrayList(sessionsList));
        sessionsTableView.setPlaceholder(new Label("There are no recorded sessions yet!"));

        TableColumn<Session, String> startColumn = new TableColumn<>("Start");
        startColumn.setCellValueFactory(new PropertyValueFactory<>("sessionStartString"));

        TableColumn<Session, String> endColumn = new TableColumn<>("End");
        endColumn.setCellValueFactory(new PropertyValueFactory<>("sessionEndString"));

        TableColumn<Session, String> durationColumn = new TableColumn<>("Duration");
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));

        sessionsTableView.getColumns().add(startColumn);
        sessionsTableView.getColumns().add(endColumn);
        sessionsTableView.getColumns().add(durationColumn);

        return sessionsTableView;
    }
}
