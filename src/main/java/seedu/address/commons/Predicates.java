package seedu.address.commons;

import java.util.function.Predicate;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Team;

/**
 * This is a helper class which contains all the Predicates that will be used.
 * by the find command.
 */
public class Predicates {
    public static Predicate<Entity> getPredicateFindEntityByName(String name) {
        return (entity) -> entity.getName().toString().contains(name);
    }

    public static Predicate<Team> getPredicateFindTeamByProjectName(String name) {
        return (team) -> team.getName().toString().contains(name);
    }
}
