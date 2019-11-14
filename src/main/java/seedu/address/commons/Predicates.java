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
        Predicate<Participant> predicate = (participant) -> participant.getName().toString().toLowerCase()
                        .contains(name.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Team> getPredicateFindTeamByName(String name, boolean neg) {
        Predicate<Team> predicate = (participant) -> participant.getName().toString()
                .toLowerCase().contains(name.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Mentor> getPredicateFindMentorByName(String name, boolean neg) {
        Predicate<Mentor> predicate = (mentor) -> mentor.getName().toString()
                .toLowerCase().contains(name.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Team> getPredicateFindTeamByProjectName(String name, boolean neg) {
        Predicate<Team> predicate = (team) -> team.getProjectName().toString()
                .toLowerCase().contains(name.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Participant> getPredicateFindParticipantByEmail(String email, boolean neg) {
        Predicate<Participant> predicate = (participant) -> participant.getEmail().toString()
                .toLowerCase().contains(email.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Mentor> getPredicateFindMentorByEmail(String email, boolean neg) {
        Predicate<Mentor> predicate = (mentor) -> mentor.getEmail().toString()
                .toLowerCase().contains(email.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Participant> getPredicateFindParticipantByPhone(String phone, boolean neg) {
        Predicate<Participant> predicate = (participant) -> participant.getPhone().toString()
                .toLowerCase().contains(phone.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Mentor> getPredicateFindMentorByPhone(String phone, boolean neg) {
        Predicate<Mentor> predicate = (mentor) -> mentor.getPhone().toString()
                .toLowerCase().contains(phone.toLowerCase());
        return neg ? predicate.negate() : predicate;
    }

    public static Predicate<Mentor> getPredicateFindMentorByOrganization(String org, boolean neg) {
        Predicate<Mentor> predicate = (mentor) -> mentor.getOrganization().toString()
                .toLowerCase().contains(org.toLowerCase());
        return neg ? predicate.negate() : predicate;
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
