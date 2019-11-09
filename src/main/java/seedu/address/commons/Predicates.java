package seedu.address.commons;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.model.entity.Entity;
import seedu.address.model.entity.Mentor;
import seedu.address.model.entity.Participant;
import seedu.address.model.entity.SubjectName;
import seedu.address.model.entity.Team;

/**
 * This is a helper class which contains all the Predicates that will be used.
 * by the find command.
 */
public class Predicates {
    public static Predicate<Participant> getPredicateFindParticipantByName(String name, boolean neg) {
        return neg
                ? (participant) -> !participant.getName().toString().contains(name)
                : (participant) -> participant.getName().toString().contains(name);
    }

    public static Predicate<Team> getPredicateFindTeamByName(String name, boolean neg) {
        return neg
                ? (team) -> !team.getName().toString().contains(name)
                : (team) -> team.getName().toString().contains(name);
    }

    public static Predicate<Mentor> getPredicateFindMentorByName(String name, boolean neg) {
        return neg
                ? (mentor) -> !mentor.getName().toString().contains(name)
                : (mentor) -> mentor.getName().toString().contains(name);
    }

    public static Predicate<Team> getPredicateFindTeamByProjectName(String name, boolean neg) {
        return neg
                ? (team) -> !team.getProjectName().toString().contains(name)
                : (team) -> team.getProjectName().toString().contains(name);
    }

    public static Predicate<Participant> getPredicateFindParticipantByEmail(String email, boolean neg) {
        return neg
                ? (participant) -> !participant.getEmail().toString().contains(email)
                : (participant) -> participant.getEmail().toString().contains(email);
    }

    public static Predicate<Mentor> getPredicateFindMentorByEmail(String email, boolean neg) {
        return neg
                ? (mentor) -> !mentor.getEmail().toString().contains(email)
                : (mentor) -> mentor.getEmail().toString().contains(email);
    }

    public static Predicate<Participant> getPredicateFindParticipantByPhone(String phone, boolean neg) {
        return neg
                ? (participant) -> !participant.getPhone().toString().contains(phone)
                : (participant) -> participant.getPhone().toString().contains(phone);
    }

    public static Predicate<Mentor> getPredicateFindMentorByPhone(String phone, boolean neg) {
        return neg
                ? (mentor) -> !mentor.getPhone().toString().contains(phone)
                : (mentor) -> mentor.getPhone().toString().contains(phone);
    }

    public static Predicate<Mentor> getPredicateFindMentorByOrganization(String org, boolean neg) {
        return neg
                ? (mentor) -> !mentor.getOrganization().toString().contains(org)
                : (mentor) -> mentor.getOrganization().toString().contains(org);
    }

    public static <T> Predicate<T> predicateReducer(List<Predicate<T>> predicates) {
        return predicates.stream().reduce(predicate -> true, Predicate::and);
    }

    public static <T> Predicate<T> predicateReducerOr(List<Predicate<T>> predicates) {
        return predicates.stream().reduce(predicate -> false, Predicate::or);
    }

    public static Predicate<Entity> viewSpecifiedEntity(Entity entityToView) {
        return (entity) -> entity.equals(entityToView);
    }

    public static Predicate<Team> getPredicateFilterTeamBySubject(SubjectName subjectName) {
        return Team -> !Team.getSubject().equals(subjectName);
    }
}
