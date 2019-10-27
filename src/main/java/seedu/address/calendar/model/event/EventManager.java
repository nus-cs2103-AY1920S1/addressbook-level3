package seedu.address.calendar.model.event;

import seedu.address.calendar.model.date.Date;
import seedu.address.calendar.model.event.exceptions.ClashException;
import seedu.address.calendar.model.event.exceptions.DuplicateEventException;
import seedu.address.calendar.model.util.IntervalSearchTree;

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventManager {
    private IntervalSearchTree<Date, Event> engagedSchedule = new IntervalSearchTree<>();
    private IntervalSearchTree<Date, Event> breakSchedule = new IntervalSearchTree<>();

    private HashMap<Event, List<Event>> engagements = new HashMap<>();
    private HashMap<Event, List<Event>> breaks = new HashMap<>();

    public boolean addBreak(Event event) throws DuplicateEventException {
        if (breaks.containsKey(event)) {
            List<Event> requiredList = breaks.get(event);
            addBreak(event, requiredList);
        } else {
            List<Event> newList = new ArrayList<>();
            newList.add(event);
        }

        breakSchedule.insert(event);
        return true;
    }

    private void addBreak(Event event, List<Event> requiredList) throws DuplicateEventException {
        if (isDuplicateEvent(event, requiredList)) {
            throw new DuplicateEventException();
        }

        requiredList.add(event);
    }

    public boolean addCommitment(Event event) throws DuplicateEventException, ClashException {
        if (engagements.containsKey(event)) {
            List<Event> requiredList = engagements.get(event);
            addCommitment(event, requiredList);
        } else {
            if (engagedSchedule.hasCollision(event)) {
                List<String> collisions = getCollisionsAsStr(event);
                throw new ClashException(collisions);
            }
            List<Event> newList = new ArrayList<>();
            newList.add(event);
        }

        engagedSchedule.insert(event);
        return true;
    }

    private boolean addCommitment(Event event, List<Event> requiredList) throws DuplicateEventException,
            ClashException {
        boolean isDuplicate = requiredList.stream()
                .anyMatch(item -> item.equals(event));
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

    public boolean addCommitmentIgnoreClash(Event event) throws DuplicateEventException {
        if (engagements.containsKey(event)) {
            List<Event> requiredList = engagements.get(event);
            addCommitmentIgnoreClash(event, requiredList);
        } else {
            List<Event> newList = new ArrayList<>();
            newList.add(event);
        }

        engagedSchedule.insert(event);
        return true;
    }

    private boolean addCommitmentIgnoreClash(Event event, List<Event> requiredList) throws DuplicateEventException {
        boolean isDuplicate = requiredList.stream()
                .anyMatch(item -> item.equals(event));
        if (isDuplicate) {
            throw new DuplicateEventException();
        }
        requiredList.add(event);
        return true;
    }

    private List<String> getCollisionsAsStr(Event event) {
        // todo: limit the number of collisions shown
        return engagedSchedule.getCollisions(event)
                .stream()
                .map(Object::toString)
                .collect(Collectors.toList());
    }

    private boolean isDuplicateEvent(Event event, List<Event> eventList) {
        return eventList.stream()
                .anyMatch(item -> item.equals(event));
    }

    public String suggest(EventQuery eventQuery) {
        Stream<Event> availableSlots = breakSchedule.getCollisions(eventQuery).stream();
        Deque<EventQuery> availableBlocks = findBlocks(availableSlots);
        Stream<Event> engagedSlots = engagedSchedule.getCollisions(eventQuery).stream();
        Deque<EventQuery> engagedBlocks = findBlocks(engagedSlots);
        return findSuitableBlocks(availableBlocks, engagedBlocks).stream()
                .map(Object::toString)
                .reduce("", (prev, curr) -> {
                    return prev + curr + "\n";
                })
                .trim();
    }

    private Deque<EventQuery> findBlocks(Stream<Event> originalSlots) {
        LinkedList<EventQuery> availableBlocks = new LinkedList<>();
        originalSlots.sorted()
                .forEach(current -> {
                    EventQuery previous = availableBlocks.getLast();
                    Date currentStart = current.getStart();
                    Date currentEnd = current.getEnd();
                    if (previous.isOverlap(current)) {
                        availableBlocks.add(new EventQuery(currentStart, currentEnd));
                        return;
                    }
                    Date previousStart = previous.getStart();
                    EventQuery joinedBlock = new EventQuery(previousStart, currentEnd);
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
}

