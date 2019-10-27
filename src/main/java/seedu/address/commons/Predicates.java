package seedu.address.commons;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.Team;

/**
 * This is a helper class which contains all the Predicates that will be used.
 * by the find command.
 */
public class Predicates {
    public static Predicate<Participant> getPredicateFindParticipantByName(String name) {
        return (participant) -> participant.getName().toString().contains(name);
    }

    public static Predicate<Team> getPredicateFindTeamByName(String name) {
        return (team) -> team.getName().toString().contains(name);
    }

    public static Predicate<Mentor> getPredicateFindMentorByName(String name) {
        return (mentor) -> mentor.getName().toString().contains(name);
    }

    public static Predicate<Team> getPredicateFindTeamByProjectName(String name) {
        return (team) -> team.getName().toString().contains(name);
    }

    public static Predicate<Participant> getPredicateFindParticipantByEmail(String email) {
        return (participant) -> participant.getEmail().toString().contains(email);
    }

    public static Predicate<Mentor> getPredicateFindMentorByEmail(String email) {
        return (mentor) -> mentor.getEmail().toString().contains(email);
    }

    public static Predicate<Participant> getPredicateFindParticipantByPhone(String phone) {
        return (participant) -> participant.getPhone().toString().contains(phone);
    }

    public static Predicate<Mentor> getPredicateFindMentorByPhone(String phone) {
        return (mentor) -> mentor.getPhone().toString().contains(phone);
    }

    public static Predicate<Mentor> getPredicateFindMentorByOrganization(String org) {
        return (mentor) -> mentor.getOrganization().toString().contains(org);
    }

    public static <T> Predicate<T> predicateReducer(List<Predicate<T>> predicates) {
        return predicates.stream().reduce(predicate -> true, Predicate::and);
    }

    public static Predicate<Entity> viewSpecifiedEntity(Entity entityToView) {
        return (entity) -> entity.equals(entityToView);
    }
}
