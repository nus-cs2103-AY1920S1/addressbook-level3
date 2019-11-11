package seedu.address.calendar.model.event;

import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.model.util.DateUtil;
import seedu.address.calendar.model.util.IntervalSearchTree;
import seedu.address.calendar.model.util.exceptions.NoVacationException;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Manages all events.
 */
public class EventManager implements EventViewer {
    private IntervalSearchTree<Date, Event> engagedSchedule = new IntervalSearchTree<>();
    private IntervalSearchTree<Date, Event> vacationSchedule = new IntervalSearchTree<>();

    private HashMap<Event, List<Event>> engagements = new HashMap<>();
    private HashMap<Event, List<Event>> vacations = new HashMap<>();

    /**
     * Adds a new event to {@code this}.
     *
     * @param event The new event to be added
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical event (i.e. of the same type, same start and end dates, and same
     *                                  name) already exists
     * @throws ClashException if the operation may result in clashing commitments
     */
    public boolean add(Event event) throws DuplicateEventException, ClashException {
        if (event.isBusy()) {
            addEngagement(event);
        } else {
            addVacation(event);
        }
        return true;
    }

    /**
     * Adds a vacation to {@code this}.
     *
     * @param event The vacation to be added
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical event already exists
     */
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

    /**
     * Adds a vacation to the required list.
     *
     * @param event Vacation to be added
     * @param requiredList List which stores vacations that have the same time frame
     * @throws DuplicateEventException if an identical vacation already exists
     */
    private void addVacation(Event event, List<Event> requiredList) throws DuplicateEventException {
        if (isDuplicateEvent(event, requiredList)) {
            throw new DuplicateEventException();
        }

        requiredList.add(event);
    }

    /**
     * Adds an engagement to {@code this}.
     *
     * @param event The engagement to be added
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical event already exists
     * @throws ClashException if the operation can result in clashes between commitments
     */
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

    /**
     * Adds the engagement to the required list.
     *
     * @param event The engagement to be added
     * @param requiredList The list which contains all engagements that happen over the same period of time
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical engagement already exists
     * @throws ClashException if the operation can result in clashes in commitments
     */
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

    /**
     * Adds an engagement without checking for whether there might be clashes.
     * Guarantees: only engagements would use this operation
     *
     * @param event The engagement to be added
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical engagement already exists
     */
    public boolean addIgnoreClash(Event event) throws DuplicateEventException {
        if (!event.isBusy()) {
            assert false : "Add without clash command is only available for commitments and trips";
        }

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

    /**
     * Adds the engagement to the required list. Potential conflicts in schedule will be ignored.
     *
     * @param event The engagement to be added
     * @param requiredList The list which contains all engagements that happen over the same period of time
     * @return {@code true} if the operation is successful
     * @throws DuplicateEventException if an identical engagement already exists
     */
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
                .flatMap(e -> engagements.get(e).stream())
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    /**
     * Checks whether there are duplicate events.
     * @param event The event that is about to be added
     * @param eventList The list containing similar events
     * @return {@code true} if {@code event} does not exist
     */
    private boolean isDuplicateEvent(Event event, List<Event> eventList) {
        return eventList.stream()
                .anyMatch(item -> item.isIdentical(event));
    }

    /**
     * Removes an event from {@code this}.
     *
     * @param event The event to be removed
     * @return {@code true} if the operation is successful
     * @throws NoSuchElementException if the event to be removed does not exist
     */
    public boolean remove(Event event) throws NoSuchElementException {
        if (event.isBusy()) {
            return removeEngagement(event);
        } else {
            return removeVacation(event);
        }
    }

    private boolean removeEngagement(Event event) throws NoSuchElementException {
        return remove(event, engagements, engagedSchedule);
    }

    private boolean removeVacation(Event event) throws NoSuchElementException {
        return remove(event, vacations, vacationSchedule);
    }

    /**
     * Helps to remove the event completely.
     *
     * @param event The event to be removed
     * @param hashMap The hashMap which contains a reference to the event
     * @param schedule The schedule which contains a copy of the event
     * @return {@code true} if the operation is successful
     * @throws NoSuchElementException if the event to be removed does not exist
     */
    private boolean remove(Event event, HashMap<Event, List<Event>> hashMap,
                           IntervalSearchTree<Date, Event> schedule) throws NoSuchElementException {
        if (!hashMap.containsKey(event)) {
            throw new NoSuchElementException("There is no event with the same start and end dates.");
        }

        List<Event> requiredList = hashMap.get(event);

        if (!isDuplicateEvent(event, requiredList)) {
            String exceptionMessage = getRelevantEventsAsString(event);
            throw new NoSuchElementException(exceptionMessage);
        }

        removeFromList(event, requiredList);
        if (requiredList.isEmpty()) {
            hashMap.remove(event);
        }

        try {
            schedule.remove(event);
        } catch (NoSuchElementException e) {
            assert false : "This event should exist in schedule";
        }
        return true;
    }

    /**
     * Gets relevant events (events that happen at the same time) as a formatted {@code String}.
     *
     * @param event The event which user wants to remove
     * @return A formatted {@code String} containing similar events
     */
    public String getRelevantEventsAsString(Event event) {
        List<Event> relevantList = getEventsAtSpecificTime(event)
                .collect(Collectors.toCollection(ArrayList::new));
        String relevantListStr = IntStream.range(0, relevantList.size())
                .mapToObj(i -> String.format("%d. %s", i + 1, relevantList.get(i)))
                .reduce("", (prev, curr) -> prev + curr + "\n")
                .trim();
        return  String.format("There is no such event with the same start and end dates. "
                + "Event(s) with the same start and end dates:\n%s\n"
                + "If you would like to select an option, enter the relevant index. "
                + "Otherwise, type 'no' or other commands.",
                relevantListStr);
    }

    /**
     * Gets all events that have the same time frame as {@code eventQuery}.
     *
     * @param eventQuery {@code EventQuery} which has the desired start and end dates
     * @return A stream of relevant events
     */
    public Stream<Event> getEventsAtSpecificTime(EventQuery eventQuery) {
        Event event = Event.getEventPlaceHolder(eventQuery);
        return getEventsAtSpecificTime(event);
    }

    /**
     * Gets all events that have the same time frame as {@code event}.
     *
     * @param event {@code Event} which has the desired start and end dates
     * @return A stream of relevant events
     */
    public Stream<Event> getEventsAtSpecificTime(Event event) {
        List<Event> relevantEvents = new ArrayList<>();

        if (engagements.containsKey(event)) {
            relevantEvents.addAll(engagements.get(event));
        }

        if (vacations.containsKey(event)) {
            relevantEvents.addAll(vacations.get(event));
        }

        return relevantEvents.stream();
    }

    private void removeFromList(Event eventToRemove, List<Event> requiredList) {
        for (int i = 0; i < requiredList.size(); i++) {
            Event event = requiredList.get(i);
            if (event.isIdentical(eventToRemove)) {
                requiredList.remove(i);
                break;
            }
        }
    }

    /**
     * Checks whether the user is available from the specified start date to the specified end date.
     *
     * @param eventQuery {@code EventQuery} has the desired start and end date
     * @return {@code true} if the user is available on all days between the start and end date
     */
    public boolean isAvailable(EventQuery eventQuery) {
        Event placeHolderEvent = Event.getEventPlaceHolder(eventQuery);
        boolean hasNoEventsPlanned = !engagedSchedule.hasCollision(placeHolderEvent);
        List<Event> availableSlots = vacationSchedule.getCollisions(eventQuery);
        Stream<EventQuery> availableConstrainedSlots = getConstrainedSlots(availableSlots, eventQuery).stream();
        Deque<EventQuery> availableBlocks = findBlocks(availableConstrainedSlots);
        boolean hasVacation = availableBlocks.stream()
                .anyMatch(processedEventQuery -> processedEventQuery.compareTo(eventQuery) == 0);
        return hasNoEventsPlanned && hasVacation;
    }

    /**
     * Suggests possible blocks of time to travel.
     *
     * @param eventQuery The time period for which the user is considering to travel
     * @return The possible time blocks, if any
     */
    public String suggest(EventQuery eventQuery) {
        return suggestBlocks(eventQuery)
                .map(Object::toString)
                .reduce("", (prev, curr) ->  prev + curr + "\n")
                .trim();
    }

    /**
     * Suggests possible blocks of time to travel that meet the {@code minPeriod} requirement.
     * Guarantees: {@code minPeriod} is positive
     *
     * @param eventQuery The time period for which the user is considering to travel
     * @param minPeriod The minimum number of days for which the user wants to travel
     * @return The possible time blocks, if any
     */
    public String suggest(EventQuery eventQuery, int minPeriod) {
        checkArgument(minPeriod > 0, "Min period must be at least 1");
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

    /**
     * Suggests blocks of time when the user can travel.
     *
     * @param eventQuery The relevant period of time
     * @return Possible blocks of time when the user can travel, if any
     */
    private Stream<EventQuery> suggestBlocks(EventQuery eventQuery) {
        List<Event> availableSlots = vacationSchedule.getCollisions(eventQuery);
        Stream<EventQuery> availableConstrainedSlots = getConstrainedSlots(availableSlots, eventQuery).stream();
        Deque<EventQuery> availableBlocks = findBlocks(availableConstrainedSlots);

        List<Event> engagedSlots = engagedSchedule.getCollisions(eventQuery);
        Stream<EventQuery> engagedConstrainedSlots = getConstrainedSlots(engagedSlots, eventQuery).stream();
        Deque<EventQuery> engagedBlocks = findBlocks(engagedConstrainedSlots);

        return findSuitableBlocks(availableBlocks, engagedBlocks).stream();
    }

    /**
     * Gets the relevant blocks of time.
     *
     * @param eventList List of events which happen during the relevant period of time
     * @param eventQuery The relevant period of time
     * @return Relevant blocks of time
     */
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
            EventQuery currentEngagedBlock = engagedBlocks.peek();

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

    /**
     * Gets all events that happen sometime when {@code eventQuery} happens.
     *
     * @param eventQuery The {@code EventQuery} instance which has the desired start and end dates
     * @return All events that happen while {@code eventQuery} does, if any
     */
    public Stream<Event> getEvents(EventQuery eventQuery) {
        Event placeHolderEvent = Event.getEventPlaceHolder(eventQuery);
        Stream<Event> requiredVacations = vacationSchedule.getCollisions(placeHolderEvent)
                .stream()
                .flatMap(eventIdentifier -> vacations.get(eventIdentifier).stream());
        Stream<Event> requiredEngagements = engagedSchedule.getCollisions(placeHolderEvent)
                .stream()
                .flatMap(eventIdentifier -> engagements.get(eventIdentifier).stream());
        return Stream.concat(requiredVacations, requiredEngagements);
    }

    /**
     * Lists all events of {@code this} as a formatted {@code String}.
     *
     * @return A formatted {@code String} of all events
     */
    public List<String> listAllAsString() {
        return asList()
                .stream()
                .map(Event::toString)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Lists all events that happen while {@code eventQuery} does.
     *
     * @param eventQuery The {@code EventQuery} instance which has the desired start and end dates
     * @return A formatted {@code String} of all relevant events
     */
    public List<String> listRelevantAsString(EventQuery eventQuery) {
        return asListRelevant(eventQuery)
                .stream()
                .map(Event::toString)
                .collect(Collectors.toCollection(ArrayList::new));
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

    private List<Event> asListRelevant(EventQuery eventQuery) {
        Stream<Event> relevantVacations = vacationSchedule.getCollisions(eventQuery)
                .stream()
                .flatMap(event -> vacations.get(event).stream());
        Stream<Event> relevantEngagements = engagedSchedule.getCollisions(eventQuery)
                .stream()
                .flatMap(event -> engagements.get(event).stream());

        return Stream.concat(relevantVacations, relevantEngagements)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Clears all events from {@code this}.
     */
    public void clear() {
        engagedSchedule = new IntervalSearchTree<>();
        vacationSchedule = new IntervalSearchTree<>();
        engagements = new HashMap<>();
        vacations = new HashMap<>();
    }

    /**
     * Gets the number of days of vacation (i.e. school breaks and holidays)
     *
     * @return Number of days of vacation (i.e. school breaks and holidays)
     */
    public long getNumDaysVacation() {
        return vacations.values()
                .stream()
                .flatMap(events ->  events.stream()
                        .map(event -> DateUtil.daysBetween(event.getStart(), event.getEnd()) + 1))
                .reduce((long) 0, Long::sum);

    }

    /**
     * Gets the number of days spent on trips
     *
     * @return Number of days spent on trips
     */
    public long getNumDaysTrip() {
        return engagements.values()
                .stream()
                .flatMap(events ->  events.stream()
                        .filter(event -> event.getEventType().equals(EventType.TRIP))
                        .map(event -> DateUtil.daysBetween(event.getStart(), event.getEnd()) + 1))
                .reduce((long) 0, Long::sum);
    }

    /**
     * Gets the number of trips.
     *
     * @return Absolute number of trips
     */
    public long getNumTrip() {
        return engagements.values()
                .stream()
                .map(events -> events.stream()
                        .filter(event -> event.getEventType().equals(EventType.TRIP))
                        .count())
                .reduce((long) 0, Long::sum);
    }

    /**
     * Gets the percentage of vacation that is spent on trips.
     *
     * @return Percentage of vacation that is spent on trips
     * @throws NoVacationException If there is no vacation
     */
    public double getPercentageTrip() throws NoVacationException {
        if (getNumDaysVacation() == 0) {
            throw new NoVacationException();
        }
        return (double) getNumDaysTrip() / getNumDaysVacation();
    }
}

