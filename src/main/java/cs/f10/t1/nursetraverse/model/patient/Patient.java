package cs.f10.t1.nursetraverse.model.patient;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.model.Model;
import cs.f10.t1.nursetraverse.model.tag.Tag;
import cs.f10.t1.nursetraverse.model.visit.Visit;
import cs.f10.t1.nursetraverse.model.visit.exceptions.VisitNotFoundException;
import cs.f10.t1.nursetraverse.model.visittodo.VisitTodo;

/**
 * Represents a Patient in the patient book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Patient {

    public static final List<Visit> PLACEHOLDER_NO_VISITS = Collections.unmodifiableList(new ArrayList<>());

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Collection<VisitTodo> visitTodos = new LinkedHashSet<>();
    private final List<Visit> visits = new ArrayList<>();

    /**
     * Every field must be present and not null.
     */
    public Patient(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Collection<VisitTodo> visitTodos, List<Visit> visits) {
        CollectionUtil.requireAllNonNull(name, phone, email, address, tags, visitTodos);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.visitTodos.addAll(visitTodos);
        this.visits.addAll(visits);
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns an immutable visitTodo collection, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Collection<VisitTodo> getVisitTodos() {
        return Collections.unmodifiableCollection(visitTodos);
    }

    /**
     * Returns an immutable visit list, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public List<Visit> getVisits() {
        return Collections.unmodifiableList(visits);
    }

    /**
     * Returns true if both patients of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two patients.
     */
    public boolean isSamePatient(Patient otherPatient) {
        if (otherPatient == this) {
            return true;
        }

        return otherPatient != null
                && otherPatient.getName().equals(getName())
                && (otherPatient.getPhone().equals(getPhone()) || otherPatient.getEmail().equals(getEmail()));
    }

    /**
     * Returns true if both patients have the same identity and data fields.
     * This defines a stronger notion of equality between two patients.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Patient)) {
            return false;
        }

        Patient otherPatient = (Patient) other;

        return otherPatient.getName().equals(getName())
                && otherPatient.getPhone().equals(getPhone())
                && otherPatient.getEmail().equals(getEmail())
                && otherPatient.getAddress().equals(getAddress())
                && otherPatient.getTags().equals(getTags())
                && CollectionUtil.checkEqual(getVisitTodos(), otherPatient.getVisitTodos())
                && CollectionUtil.checkEqual(getVisits(), otherPatient.getVisits());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, visitTodos, visits);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(getName())
                .append(" Phone: ")
                .append(getPhone())
                .append(" Email: ")
                .append(getEmail())
                .append(" Address: ")
                .append(getAddress())
                .append(" Tags: ");
        getTags().forEach(builder::append);
        builder.append(" Visit Todos: ");
        getVisitTodos().forEach(builder::append);
        builder.append(" Visits: ");
        getVisits().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Return index of visit from the visit list.
     */
    public int indexOfVisit(Visit visit) {
        requireNonNull(visit);
        return visits.indexOf(visit);
    }

    /**
     * Add visit to patient's visit list.
     */
    public void addVisit(Visit visit) {
        requireNonNull(visit);
        visits.add(visit);
    }

    /**
     * Add a list of visits to patient's visit list.
     */
    public void addVisits(List<Visit> visits) {
        requireNonNull(visits);
        this.visits.addAll(visits);
    }

    /**
     * Update a visit in the patient's list of visits.
     */
    public void updateVisit(Visit target, Visit updatedVisit) {
        CollectionUtil.requireAllNonNull(target, updatedVisit);
        int indexOf = this.visits.indexOf(target);
        if (indexOf > -1) {
            this.visits.set(indexOf, updatedVisit);
        } else {
            //This should not happen under normal circumstances (code error)
            throw new VisitNotFoundException();
        }
    }

    /**
     * Remove a visit object. Model is passed in to ensure visit is not ongoing.
     * Throws IllegalArgumentException if index is not within visit range.
     * Throws IllegalStateException if visit is ongoing.
     */
    public void removeVisit(Visit visitToRemove, Model model) {
        CollectionUtil.requireAllNonNull(visitToRemove, model);
        if (!visits.contains(visitToRemove)) {
            throw new IllegalArgumentException();
        }
        //If visit is ongoing
        boolean updateOngoingVisitAfterRemoval = false;
        Optional<Visit> optionalVisit = model.getOngoingVisit();
        if (optionalVisit.isPresent()) {
            Visit ongoingVisit = optionalVisit.get();
            //Unset before removal
            model.unsetOngoingVisit();
            //If it's another visit being deleted, ensure indexes are the same
            if (!ongoingVisit.equals(visitToRemove)) {
                updateOngoingVisitAfterRemoval = true;
            }
        }

        visits.remove(visitToRemove);

        //Update ongoing visit (if there is) if not removing ongoing visit
        if (optionalVisit.isPresent() && updateOngoingVisitAfterRemoval) {
            model.setNewOngoingVisit(optionalVisit.get());
        }
    }

    /**
     * Get visit by index from the visit list (Optional object).
     */
    public Optional<Visit> getVisitByIndex(int value) {
        if (value >= 0 && value < visits.size()) {
            return Optional.of(visits.get(value));
        }
        return Optional.empty();
    }
}
