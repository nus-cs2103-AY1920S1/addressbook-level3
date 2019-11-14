package seedu.address.logic;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AlfredParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.CommandRecord;
import seedu.address.model.Model;
import seedu.address.model.Statistics;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final AlfredParser alfredParser;

    public LogicManager(Model model) {
        this.model = model;
        alfredParser = new AlfredParser();
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = alfredParser.parseCommand(commandText);
        commandResult = command.execute(model);

        return commandResult;
    }

    @Override
    public ObservableList<Participant> getFilteredParticipantList() {
        return model.getFilteredParticipantList();
    }

    @Override
    public ObservableList<Team> getFilteredTeamList() {
        return model.getFilteredTeamList();
    }

    @Override
    public ObservableList<Team> getSortedTeamList() {
        return model.getSortedTeamList();
    }

    @Override
    public ObservableList<Mentor> getFilteredMentorList() {
        return model.getFilteredMentorList();
    }

    @Override
    public ArrayList<CommandRecord> getCommandHistory() {
        return model.getCommandHistory();
    }

    @Override
    public String getPrevCommandString() {
        return model.getPrevCommandString();
    }

    @Override
    public String getNextCommandString() {
        return model.getNextCommandString();
    }

    // TODO: May update the three methods below to get Alfred file path instead
    @Override
    public Path getParticipantListFilePath() {
        return model.getParticipantListFilePath();
    }

    @Override
    public Path getTeamListFilePath() {
        return model.getTeamListFilePath();
    }

    @Override
    public Path getMentorListFilePath() {
        return model.getMentorListFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public Statistics getStatistics() {
        Statistics result = new Statistics();

        setParticipantStatistics(result);
        setTeamStatistics(result);
        setMentorStatistics(result);
        return result;
    }

    private void setParticipantStatistics(Statistics statistics) {
        FilteredList<Participant> participantList = model.getFilteredParticipantList();
        int numParticipants = participantList.size();

        statistics.setTotalParticipants(numParticipants);
    }

    private void setMentorStatistics(Statistics statistics) {
        FilteredList<Mentor> mentorList = model.getFilteredMentorList();

        int numMentors = mentorList.size();
        long numHealthMentor = mentorList.stream().filter(m -> m.getSubject().equals(SubjectName.HEALTH)).count();
        long numEnvMentors = mentorList.stream().filter(m -> m.getSubject().equals(SubjectName.ENVIRONMENTAL)).count();
        long numSocialMentors = mentorList.stream().filter(m -> m.getSubject().equals(SubjectName.SOCIAL)).count();
        long numEduTeams = mentorList.stream().filter(m -> m.getSubject().equals(SubjectName.EDUCATION)).count();


        statistics.setTotalMentors(numMentors);
        statistics.setEduMentors(numEduTeams);
        statistics.setEnvMentors(numEnvMentors);
        statistics.setSocialMentors(numSocialMentors);
        statistics.setHealthMentors(numHealthMentor);
    }

    private void setTeamStatistics(Statistics statistics) {
        FilteredList<Team> teamList = model.getFilteredTeamList();

        int numTeams = teamList.size();
        long numHealthTeams = teamList.stream().filter(t -> t.getSubject().equals(SubjectName.HEALTH)).count();
        long numEnvTeams = teamList.stream().filter(t -> t.getSubject().equals(SubjectName.ENVIRONMENTAL)).count();
        long numSocialTeams = teamList.stream().filter(t -> t.getSubject().equals(SubjectName.SOCIAL)).count();
        long numEduTeams = teamList.stream().filter(t -> t.getSubject().equals(SubjectName.EDUCATION)).count();


        statistics.setTotalTeams(numTeams);
        statistics.setEduTeams(numEduTeams);
        statistics.setEnvTeams(numEnvTeams);
        statistics.setSocialTeams(numSocialTeams);
        statistics.setHealthTeams(numHealthTeams);
    }
}
