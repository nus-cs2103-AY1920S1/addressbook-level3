package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

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
        teams.addAll(source.list()
                           .stream()
                           .map((Entity t) -> new JsonAdaptedTeam((Team) t))
                           .collect(Collectors.toList()));
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
            //TODO: Check whether this checking of existing teams is necessary with the project team
            //if (teamList.hasTeam(team)) {
            //    throw new IllegalValueException(MESSAGE_DUPLICATE_ENTITY);
            //}
            teamList.addTeam(team);
        }
        return teamList;
    }

}
