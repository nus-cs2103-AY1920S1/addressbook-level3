package seedu.address.logic.commands.editcommand;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.exceptions.AlfredException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.CliSyntax;
import seedu.address.model.Model;
import seedu.address.model.entity.CommandType;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Edits a {@link Team} in Alfred.
 */
public class EditTeamCommand extends EditCommand {

    public static final String MESSAGE_EDIT_TEAM_SUCCESS = "Edited Team: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_TEAM_DISPLAYED_INDEX =
            "The team index provided is invalid";
    public static final String MESSAGE_USAGE = COMMAND_WORD + " team"
            + ": Edits the details of the team by ID.\n"
            + "Existing values will be overwritten by the input values.\n"
            + "Format: " + "edit team [team ID]"
            + "[" + CliSyntax.PREFIX_NAME + "NAME] "
            + "[" + CliSyntax.PREFIX_SUBJECT_NAME + "SUBJECT_NAME] "
            + "[" + CliSyntax.PREFIX_PROJECT_NAME + "PROJECT_NAME] "
            + "[" + CliSyntax.PREFIX_PROJECT_TYPE + "PROJECT_TYPE] "
            + "[" + CliSyntax.PREFIX_LOCATION + "TABLE_NUMBER] \n"
            + "Example: " + COMMAND_WORD + " T-1 "
            + CliSyntax.PREFIX_PROJECT_NAME + "Saving Gotham "
            + CliSyntax.PREFIX_PROJECT_TYPE + "Public welfare";

    private EditTeamDescriptor editTeamDescriptor;

    public EditTeamCommand(Id id, EditTeamDescriptor editTeamDescriptor) {
        super(id);
        requireNonNull(editTeamDescriptor);
        this.editTeamDescriptor = editTeamDescriptor;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Team teamToEdit;
        try {
            teamToEdit = model.getTeam(this.id);
        } catch (AlfredException e) {
            throw new CommandException(MESSAGE_INVALID_TEAM_DISPLAYED_INDEX);
        }
        Team editedTeam = this.createEditedTeam(teamToEdit,
                this.editTeamDescriptor);

        try {
            model.updateTeam(this.id, editedTeam);
            model.updateHistory(this);
            return new CommandResult(String.format(MESSAGE_EDIT_TEAM_SUCCESS, editedTeam.toString()), CommandType.T);
        } catch (AlfredException e) {
            throw new CommandException(e.getMessage());
        }
    }

    /**
     * Creates and returns a new {@code Team} with the details {@code teamToEdit}
     * edited with {@code editTeamDescriptor}.
     *
     * @param teamToEdit {@code Team} that will be updated.
     * @param editTeamDescriptor Descriptor with the details to edit {@code teamToEdit}.
     * @return Updated {@code Team}.
     */
    private Team createEditedTeam(Team teamToEdit, EditTeamDescriptor editTeamDescriptor) {
        assert teamToEdit != null;

        Name updatedName = editTeamDescriptor.getName().orElse(teamToEdit.getName());
        Id id = teamToEdit.getId();
        List<Participant> participants = teamToEdit.getParticipants();
        Optional<Mentor> mentor = teamToEdit.getMentor();
        SubjectName updatedSubject = editTeamDescriptor.getSubject().orElse(teamToEdit.getSubject());
        Score updatedScore = editTeamDescriptor.getScore().orElse(teamToEdit.getScore());
        Name updatedProjectName = editTeamDescriptor.getProjectName().orElse(teamToEdit.getProjectName());
        ProjectType updatedProjectType = editTeamDescriptor.getProjectType().orElse(teamToEdit.getProjectType());
        Location updatedLocation = editTeamDescriptor.getLocation().orElse(teamToEdit.getLocation());

        // ID, NAME, PARTICIPANTS, MENTOR (OPTIONAL), SUBJECT, SCORE, PROJECT_NAME, PROJECT_TYPE, LOCATION
        return new Team(
                id,
                updatedName,
                participants,
                mentor,
                updatedSubject,
                updatedScore,
                updatedProjectName,
                updatedProjectType,
                updatedLocation
        );
    }

    /**
     * Stores the details to edit the {@code Team} with. Each non-empty field value will replace the
     * corresponding field value of the {@code Team}.
     */
    public static class EditTeamDescriptor extends EditEntityDescriptor {

        // Don't allow user to edit List<Participant> and Optional<Mentor> through Edit command
        // Do the above through addToTeam/deleteFromTeam command (something like that)
        private SubjectName subject;
        private Score score;
        private Name projectName;
        private ProjectType projectType;
        private Location location;

        public EditTeamDescriptor() {}

        public EditTeamDescriptor(EditTeamDescriptor toCopy) {
            super(toCopy);
            this.setSubject(toCopy.subject);
            this.setScore(toCopy.score);
            this.setProjectName(toCopy.projectName);
            this.setProjectType(toCopy.projectType);
            this.setLocation(toCopy.location);
        }

        /**
         * Returns true if at least one field is edited.
         */
        @Override
        public boolean isAnyFieldEdited() {
            return super.isAnyFieldEdited()
                    || CollectionUtil.isAnyNonNull(
                            this.subject,
                            this.score,
                            this.projectName,
                            this.projectType,
                            this.location
                    );
        }

        /* ======== Getters ======== */

        public Optional<SubjectName> getSubject() {
            return Optional.ofNullable(subject);
        }

        public Optional<Score> getScore() {
            return Optional.ofNullable(score);
        }

        public Optional<Name> getProjectName() {
            return Optional.ofNullable(projectName);
        }

        public Optional<ProjectType> getProjectType() {
            return Optional.ofNullable(projectType);
        }

        public Optional<Location> getLocation() {
            return Optional.ofNullable(location);
        }

        /* ======== Setters ======== */

        public void setSubject(SubjectName subject) {
            this.subject = subject;
        }

        public void setScore(Score score) {
            this.score = score;
        }

        public void setProjectName(Name projectName) {
            this.projectName = projectName;
        }

        public void setProjectType(ProjectType projectType) {
            this.projectType = projectType;
        }

        public void setLocation(Location location) {
            this.location = location;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditTeamDescriptor)) {
                return false;
            }

            // state check
            EditTeamDescriptor e = (EditTeamDescriptor) other;
            return super.equals(other)
                    && getSubject().equals(e.getSubject())
                    && getScore().equals(e.getScore())
                    && getProjectName().equals(e.getProjectName())
                    && getProjectType().equals(e.getProjectType())
                    && getLocation().equals(e.getLocation());
        }
    }
}
