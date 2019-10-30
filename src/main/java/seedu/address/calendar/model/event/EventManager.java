package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.IntervalSearchTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventManager {
    private IntervalSearchTree<Date, Event> engagedSchedule = new IntervalSearchTree<>();
    private IntervalSearchTree<Date, Event> vacationSchedule = new IntervalSearchTree<>();

    private HashMap<Event, List<Event>> engagements = new HashMap<>();
    private HashMap<Event, List<Event>> vacations = new HashMap<>();

    public boolean add(Event event) throws DuplicateEventException, ClashException {
        if (event.isBusy()) {
            addEngagement(event);
        } else {
            addVacation(event);
        }
        return true;
    }

    private boolean addVacation(Event event) throws DuplicateEventException {
        if (vacations.containsKey(event)) {
            List<Event> requiredList = vacations.get(event);
            addVacation(event, requiredList);
        } else {
            List<Event> newList = new ArrayList<>();
            newList.add(event);
            vacations.put(event, newList);
        }

        vacationSchedule.insert(event);
        return true;
    }

    private void addVacation(Event event, List<Event> requiredList) throws DuplicateEventException {
        if (isDuplicateEvent(event, requiredList)) {
            throw new DuplicateEventException();
        }

        requiredList.add(event);
    }

    private boolean addEngagement(Event event) throws DuplicateEventException, ClashException {
        if (engagements.containsKey(event)) {
            List<Event> requiredList = engagements.get(event);
            addEngagement(event, requiredList);
        } else {
            if (engagedSchedule.hasCollision(event)) {
                List<String> collisions = getCollisionsAsStr(event);
                throw new ClashException(collisions);
            }
            List<Event> newList = new ArrayList<>();
            newList.add(event);
            engagements.put(event, newList);
        }

        engagedSchedule.insert(event);
        return true;
    }

    private boolean addEngagement(Event event, List<Event> requiredList) throws DuplicateEventException,
            ClashException {
        boolean isDuplicate = isDuplicateEvent(event, requiredList);
        if (isDuplicate) {
            throw new DuplicateEventException();
        }
        if (engagedSchedule.hasCollision(event)) {
            List<String> collisions = getCollisionsAsStr(event);
            throw new ClashException(collisions);
        }
        requiredList.add(event);
        return true;
    }

    public boolean addIgnoreClash(Event event) throws DuplicateEventException {
        if (!event.isBusy()) {
            assert false : "Add without clash command is only available for commitments and trips";
        }
        return addEngagementIgnoreClash(event);
    }

    private boolean addEngagementIgnoreClash(Event event) throws DuplicateEventException {
        if (engagements.containsKey(event)) {
            List<Event> requiredList = engagements.get(event);
            addEngagementIgnoreClash(event, requiredList);
        } else {
            List<Event> newList = new ArrayList<>();
            newList.add(event);
            engagements.put(event, newList);
        }

        engagedSchedule.insert(event);
        return true;
    }

    private boolean addEngagementIgnoreClash(Event event, List<Event> requiredList) throws DuplicateEventException {
        boolean isDuplicate = isDuplicateEvent(event, requiredList);
        if (isDuplicate) {
            throw new DuplicateEventException();
        }
        requiredList.add(event);
        return true;
    }

    private List<String> getCollisionsAsStr(Event event) {
        return engagedSchedule.getCollisions(event)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private boolean isDuplicateEvent(Event event, List<Event> eventList) {
        return eventList.stream()
                .anyMatch(item -> item.isIdentical(event));
    }

    public boolean remove(Event event) throws NoSuchElementException {
        if (event.isBusy()) {
            return removeEngagement(event);
        } else {
            return removeVacation(event);
        }
    }

    private boolean removeEngagement(Event event) throws NoSuchElementException {
        if (!engagements.containsKey(event)) {
            throw new NoSuchElementException("There is no event at this time.");
        }

        List<Event> requiredList = engagements.get(event);

        if (!isDuplicateEvent(event, requiredList)) {
            String requiredListStr = requiredList.stream()
                    .map(Event::toString)
                    .reduce("", (prev, curr) -> prev + "- " + curr + "\n")
                    .trim();
            String exceptionMessage = String.format("There is no such commitment/trip at this time. "
                    + "Commitments/trips at this time:\n%s",
                    requiredListStr);
            throw new NoSuchElementException(exceptionMessage);
        }

        requiredList.remove(event);
        if (requiredList.isEmpty()) {
            engagements.remove(event);
        }

        try {
            engagedSchedule.remove(event);
        } catch (NoSuchElementException e) {
            assert false : "This event should exist in engagedSchedule";
        }
        return true;
    }

    private boolean removeVacation(Event event) throws NoSuchElementException {
        if (!vacations.containsKey(event)) {
            throw new NoSuchElementException("There is no vacation at this time.");
        }

        List<Event> requiredList = vacations.get(event);

        if (!isDuplicateEvent(event, requiredList)) {
            String requiredListStr = requiredList.stream()
                    .map(Event::toString)
                    .reduce("", (prev, curr) -> prev + "- " + curr + "\n")
                    .trim();
            String exceptionMessage = String.format("There is no such vacation at this time. "
                    + "Vacations at this time:\n%s",
                    requiredListStr);
            throw new NoSuchElementException(exceptionMessage);
        }

        requiredList.remove(event);
        if (requiredList.isEmpty()) {
            vacations.remove(event);
        }

        try {
            vacationSchedule.remove(event);
        } catch (NoSuchElementException e) {
            assert false : "This event should exist in vacationSchedule";
        }
        return true;
    }

    public boolean isAvailable(EventQuery eventQuery) {
        Event placeHolderEvent = Event.getEventPlaceHolder(eventQuery);
        boolean hasNoEventsPlanned = !engagedSchedule.hasCollision(placeHolderEvent);
        boolean hasVacation = vacationSchedule.hasCollision(placeHolderEvent);
        return hasNoEventsPlanned && hasVacation;
    }

    public String suggest(EventQuery eventQuery) {
        return suggestBlocks(eventQuery)
                .map(Object::toString)
                .reduce("", (prev, curr) ->  prev + curr + "\n")
                .trim();
    }

    public String suggest(EventQuery eventQuery, int minPeriod) {
        return suggestBlocks(eventQuery)
                .filter(block -> {
                    Date startDate = block.getStart();
                    Date endDate = block.getEnd();
                    long period = DateUtil.daysBetween(startDate, endDate) + 1;
                    return period >= minPeriod;
                })
                .map(Object::toString)
                .reduce("", (prev, curr) -> {
                    return prev + curr + "\n";
                })
                .trim();
    }

    public Stream<EventQuery> suggestBlocks(EventQuery eventQuery) {
        List<Event> availableSlots = vacationSchedule.getCollisions(eventQuery);
        Stream<EventQuery> availableConstrainedSlots = getConstrainedSlots(availableSlots, eventQuery).stream();
        Deque<EventQuery> availableBlocks = findBlocks(availableConstrainedSlots);

        List<Event> engagedSlots = engagedSchedule.getCollisions(eventQuery);
        Stream<EventQuery> engagedConstrainedSlots = getConstrainedSlots(engagedSlots, eventQuery).stream();
        Deque<EventQuery> engagedBlocks = findBlocks(engagedConstrainedSlots);

        return findSuitableBlocks(availableBlocks, engagedBlocks).stream();
    }

    private List<EventQuery> getConstrainedSlots(List<Event> eventList, EventQuery eventQuery) {
        List<EventQuery> constrainedEventList = new LinkedList<>();
        Date earliestStartDate = eventQuery.getStart();
        Date latestEndDate = eventQuery.getEnd();
        for (Event event: eventList) {
            Date startDate;
            Date endDate;
            Date eventStartDate = event.getStart();
            Date eventEndDate = event.getEnd();
            if (eventQuery.isStartsAfter(eventStartDate)) {
                startDate = earliestStartDate;
            } else {
                startDate = eventStartDate;
            }

            if (event.isEndsAfter(latestEndDate)) {
                endDate = latestEndDate;
            } else {
                endDate = eventEndDate;
            }
            boolean isValidTime = EventQuery.isValidEventTime(startDate, endDate);

            if (!isValidTime) {
                continue;
            }

            EventQuery constrainedSlot = new EventQuery(startDate, endDate);
            constrainedEventList.add(constrainedSlot);
        }
        return constrainedEventList;
    }

    private Deque<EventQuery> findBlocks(Stream<EventQuery> originalSlots) {
        LinkedList<EventQuery> availableBlocks = new LinkedList<>();
        originalSlots.sorted()
                .forEach(current -> {
                    if (availableBlocks.isEmpty()) {
                        Date currentStart = current.getStart();
                        Date currentEnd = current.getEnd();
                        availableBlocks.offer(new EventQuery(currentStart, currentEnd));
                        return;
                    }
                    EventQuery previous = availableBlocks.getLast();
                    Date previousStart = previous.getStart();
                    Date currentStart = current.getStart();
                    Date currentEnd = current.getEnd();

                    boolean currentStartsDayAfter = currentStart.equals(previous.getEnd().getNextDate());

                    if (currentStartsDayAfter) {
                        EventQuery joinedBlock = new EventQuery(previousStart, currentEnd);
                        availableBlocks.removeLast();
                        availableBlocks.add(joinedBlock);
                        return;
                    }

                    if (!previous.isOverlap(current)) {
                        availableBlocks.add(new EventQuery(currentStart, currentEnd));
                        return;
                    }

                    boolean isPrevEndsLater = previous.isEndsAfter(currentEnd);
                    if (isPrevEndsLater) {
                        return;
                    }

                    EventQuery joinedBlock = new EventQuery(previousStart, currentEnd);
                    availableBlocks.removeLast();
                    availableBlocks.add(joinedBlock);
                });
        return availableBlocks;
    }

    private List<EventQuery> findSuitableBlocks(Deque<EventQuery> availableBlocks, Deque<EventQuery> engagedBlocks) {
        List<EventQuery> suitableBlocks = new ArrayList<>();
        while (!availableBlocks.isEmpty() || !engagedBlocks.isEmpty()) {
            if (availableBlocks.isEmpty()) {
                break;
            } else if (engagedBlocks.isEmpty()) {
                suitableBlocks.addAll(availableBlocks);
                break;
            }

            EventQuery currentAvailableBlock = availableBlocks.peek();
            assert false : "AvailableBlock should be non-empty";
            EventQuery currentEngagedBlock = engagedBlocks.peek();
            assert false : "EngagedBlock should be non-empty";

            if (!currentAvailableBlock.isOverlap(currentEngagedBlock)) {
                // the block before can be removed
                removeFirstBlock(availableBlocks, engagedBlocks, suitableBlocks);
                continue;
            } else if (currentAvailableBlock.isSameInterval(currentEngagedBlock)) {
                availableBlocks.poll();
                continue;
            }
            segmentCurrentAvailableBlock(currentAvailableBlock, currentEngagedBlock, availableBlocks);
        }

        return suitableBlocks;
    }

    private void removeFirstBlock(Deque<EventQuery> availableBlocks, Deque<EventQuery> engagedBlocks,
                                  List<EventQuery> suitableBlocks) {
        EventQuery availableBlock = availableBlocks.peek();
        EventQuery engagedBlock = engagedBlocks.peek();

        if (availableBlock.isStartsAfter(engagedBlock.startDate)) {
            engagedBlocks.poll();
        } else {
            suitableBlocks.add(availableBlock);
            availableBlocks.poll();
        }
    }

    private void segmentCurrentAvailableBlock(EventQuery availableBlock, EventQuery engagedBlock,
                                              Deque<EventQuery> availableBlocks) {
        availableBlocks.poll();
        if (availableBlock.isStartsAfter(engagedBlock.startDate) && availableBlock.isEndsAfter(engagedBlock.endDate)) {
            Date newStart = engagedBlock.endDate.getNextDate();
            Date newEnd = availableBlock.endDate;
            boolean isValid = EventQuery.isValidEventTime(newStart, newEnd);
            if (!isValid) {
                return;
            }
            EventQuery newAvailableBlock = new EventQuery(newStart, newEnd);
            availableBlocks.offerFirst(newAvailableBlock);
        } else if (availableBlock.isStartsAfter(engagedBlock.startDate)) {
            return;
        } else if (availableBlock.isEndsAfter((engagedBlock.endDate))) {
            Date newStart2 = engagedBlock.endDate.getNextDate();
            Date newEnd2 = availableBlock.endDate;
            boolean isValid2 = EventQuery.isValidEventTime(newStart2, newEnd2);
            if (isValid2) {
                EventQuery newAvailableBlock2 = new EventQuery(newStart2, newEnd2);
                availableBlocks.offerFirst(newAvailableBlock2);
            }

            Date newStart1 = availableBlock.startDate;
            Date newEnd1 = engagedBlock.startDate.getPreviousDate();
            boolean isValid1 = EventQuery.isValidEventTime(newStart1, newEnd1);
            if (isValid1) {
                EventQuery newAvailableBlock1 = new EventQuery(newStart1, newEnd1);
                availableBlocks.offerFirst(newAvailableBlock1);
            }
        } else {
            Date newStart = availableBlock.startDate;
            Date newEnd = engagedBlock.startDate.getPreviousDate();
            boolean isValid = EventQuery.isValidEventTime(newStart, newEnd);
            if (!isValid) {
                return;
            }
            EventQuery newAvailableBlock = new EventQuery(newStart, newEnd);
            availableBlocks.offerFirst(newAvailableBlock);
        }
    }

    public Stream<Event> getEvents(EventQuery eventQuery) {
        Event placeHolderEvent = Event.getEventPlaceHolder(eventQuery);
        Stream<Event> requiredVacations = vacationSchedule.getCollisions(placeHolderEvent).stream();
        Stream<Event> requiredEngagements = engagedSchedule.getCollisions(placeHolderEvent).stream();
        return Stream.concat(requiredVacations, requiredEngagements);
    }

    public List<Event> asList() {
        List<Event> eventList = new ArrayList<>();
        engagements.values()
                .stream()
                .flatMap(listOfEvents -> listOfEvents.stream())
                .forEach(event -> eventList.add(event));
        vacations.values()
                .stream()
                .flatMap(listOfEvents -> listOfEvents.stream())
                .forEach(event -> eventList.add(event));
        return eventList;
    }

    public void clear() {
        engagedSchedule = new IntervalSearchTree<>();
        vacationSchedule = new IntervalSearchTree<>();
        engagements = new HashMap<>();
        vacations = new HashMap<>();
    }
}

