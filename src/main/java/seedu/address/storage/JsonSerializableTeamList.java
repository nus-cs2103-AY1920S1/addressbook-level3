package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;

/**
 * An Immutable TeamList that is serializable to JSON format.
 */
@JsonRootName(value = "teamlist")
class JsonSerializableTeamList {

    public static final String MESSAGE_DUPLICATE_ENTITY = "Team list contains duplicate team(s).";

    private final List<JsonAdaptedTeam> teams = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableTeamList} with the given teams.
     */
    @JsonCreator
    public JsonSerializableTeamList(@JsonProperty("teams") List<JsonAdaptedTeam> teams) {
        this.teams.addAll(teams);
    }

    /**
     * Converts a given {@code TeamList} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableTeamList}.
     */
    public JsonSerializableTeamList(TeamList source) {
        teams.addAll(source.list().stream().map(JsonAdaptedTeam::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code TeamList} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public TeamList toModelType() throws IllegalValueException {
        TeamList teamList = new TeamList();
        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType();
            if (teamList.hasTeam(team)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            }
            teamList.addTeam(team);
        }
        return teamList;
    }

}
