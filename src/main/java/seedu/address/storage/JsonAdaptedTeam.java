package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.entity.Id;
import seedu.address.model.entity.Location;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Name;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.PrefixType;
import seedu.address.model.entity.ProjectType;
import seedu.address.model.entity.Score;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Team's %s field is missing!";

    private final String teamName;
    private final JsonAdaptedMentor mentor;
    private final String subject;
    private final int score;
    private final String projectName;
    private final String projectType;
    private final int location;
    private final List<JsonAdaptedParticipant> pList = new ArrayList<>();
    private final String prefixTypeStr;
    private final int idNum;

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedTeam(@JsonProperty("teamName") String teamName, @JsonProperty("mentor") JsonAdaptedMentor mentor,
                           @JsonProperty("subject") String subject,
                           @JsonProperty("score") int score, @JsonProperty("projectName") String projectName,
                           @JsonProperty("projectType") String projectType, @JsonProperty("location") int location,
                           @JsonProperty("participants") List<JsonAdaptedParticipant> pList,
                           @JsonProperty("prefixTypeStr") String prefixTypeStr, @JsonProperty("idNum") int idNum) {

        this.teamName = teamName;
        this.mentor = mentor;
        this.subject = subject;
        this.score = score;
        this.projectName = projectName;
        this.projectType = projectType;
        this.location = location;
        this.prefixTypeStr = prefixTypeStr;
        this.idNum = idNum;

        if (pList != null) {
            this.pList.addAll(pList);
        }
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        if (pList != null) {
            this.pList.addAll(pList);
        }
        teamName = source.getName().toStorageValue();
        subject = source.getSubject().name();
        score = source.getScore().toStorageValue(); //Not implemented currently
        projectName = source.getProjectName().toStorageValue();
        projectType = source.getProjectType().name();
        location = source.getLocation().toStorageValue();
        mentor = new JsonAdaptedMentor(source.getMentor().get()); //Must deal with Optional
        prefixTypeStr = source.getId().getPrefix().name();
        idNum = source.getId().getNumber();
        pList.addAll(source.getParticipants().stream()
                .map(JsonAdaptedParticipant::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Team toModelType() throws IllegalValueException {
        final List<Participant> modelParticipants = new ArrayList<>();
        for (JsonAdaptedParticipant p : pList) {
            modelParticipants.add(p.toModelType());
        }

        if (teamName == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()) + "(teamName)");
        }
        if (!Name.isValidName(teamName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelTeamName = new Name(teamName);

        if (subject == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, SubjectName.class.getSimpleName()));
        }
        if (!SubjectName.isValidSubjectName(subject)) {
            throw new IllegalValueException(SubjectName.MESSAGE_CONSTRAINTS);
        }
        final SubjectName modelSubject = SubjectName.valueOf(subject);

        if (!Score.isValidScore(score)) {
            throw new IllegalValueException(Score.MESSAGE_CONSTRAINTS);
        }
        final Score modelScore = new Score(score);

        if (projectName == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()) + "(projectName)");
        }
        if (!Name.isValidName(projectName)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelProjectName = new Name(projectName);

        if (projectType == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, ProjectType.class.getSimpleName()));
        }
        if (!ProjectType.isValidProjectType(projectType)) {
            throw new IllegalValueException(ProjectType.MESSAGE_CONSTRAINTS);
        }
        final ProjectType modelProjectType = ProjectType.valueOf(projectType);

        if (!Location.isValidLocation(location)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Location modelLocation = new Location(location);

        if (mentor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Mentor.class.getSimpleName()));
        }
        //Todo: Check whether mentor validation is necessary
        //if (!Mentor.isValidLocation(location)) {
        //    throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        //}
        final Mentor modelMentor = mentor.toModelType();

        if (prefixTypeStr == null) {
            throw new IllegalValueException(String.format(
                    MISSING_FIELD_MESSAGE_FORMAT, PrefixType.class.getSimpleName()));
        }
        if (!PrefixType.isValidPrefixType(prefixTypeStr)) {
            throw new IllegalValueException(PrefixType.MESSAGE_CONSTRAINTS);
        }
        final PrefixType modelPrefixType = PrefixType.valueOf(prefixTypeStr);

        if (!Id.isValidNumber(idNum)) {
            throw new IllegalValueException(Id.MESSAGE_CONSTRAINTS_INVALID_NUMBER);
        }
        final int modelIdNum = idNum;
        final Id modelId = new Id(modelPrefixType, modelIdNum);

        return new Team(modelId, modelTeamName, modelParticipants, Optional.of(modelMentor),
                modelSubject, modelScore, modelProjectName, modelProjectType, modelLocation);
    }

}

