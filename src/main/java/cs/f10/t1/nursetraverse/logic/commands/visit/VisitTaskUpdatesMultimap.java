package cs.f10.t1.nursetraverse.logic.commands.visit;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cs.f10.t1.nursetraverse.commons.core.index.Index;
import cs.f10.t1.nursetraverse.commons.util.CollectionUtil;
import cs.f10.t1.nursetraverse.logic.commands.exceptions.CommandException;
import cs.f10.t1.nursetraverse.model.visittask.Detail;
import cs.f10.t1.nursetraverse.model.visittask.VisitTask;
import javafx.util.Pair;

/**
 * Package private class that
 * RecordsVisitTaskUpdatesMultimap the visit task updates into a multimap so that all changes can be made in one go.
 */
class VisitTaskUpdatesMultimap {
    private final Map<Integer, Set<Object>> map = new HashMap<>();

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key,
     * the new value is appended to the list of existing values unless it already exists.
     *
     * @param index Index key with which the specified argument value is to be associated
     * @param detail Argument value to be associated with the specified Index key
     */
    private void put(Index index, String detail) {
        Set<Object> values = getAllValues(index);
        values.add(detail);
        map.put(indexToInt(index), values);
    }

    /**
     * Associates the specified argument value with {@code prefix} key in this map.
     * If the map previously contained a mapping for the key,
     * the new value is appended to the list of existing values unless it already exists.
     * If a FINISH and UNFINISH exist, UNFINISH will take priority.
     *
     * @param index Index key with which the specified argument value is to be associated
     * @param key Argument value to be associated with the specified Index key
     */
    private void put(Index index, VisitTaskUpdatesMultimapKeys key) {
        Set<Object> values = getAllValues(index);
        if (!values.contains(VisitTaskUpdatesMultimapKeys.UNFINISH)) {
            values.add(key);
            map.put(indexToInt(index), values);
        }
    }

    /**
     * Returns all values of {@code index}.
     * If the prefix does not exist or has no values, this will return an empty list.
     * Modifying the returned list will not affect the underlying data structure of the ArgumentMultimap.
     */
    private Set<Object> getAllValues(Index index) {
        if (!map.containsKey(indexToInt(index))) {
            return new HashSet<>();
        }
        return new HashSet<>(map.get(indexToInt(index)));
    }

    /**
     * Helper function to ensure index.getZeroBased() and not index.getOneBased() is used
     */
    private int indexToInt(Index index) {
        return index.getZeroBased();
    }

    /**
     * Record in a multimap which Visit Tasks finished / unfinished using a list of indexes.
     */
    public void saveTasksFinishedByIndexes(
            List<VisitTask> taskList,
            List<Index> indexList,
            VisitTaskUpdatesMultimapKeys isMarkFinish) throws CommandException {
        CollectionUtil.requireAllNonNull(taskList, indexList, isMarkFinish);
        for (Index targetIndex : indexList) {
            //Throw an exception if the index is larger than the size
            if (indexToInt(targetIndex) >= taskList.size()) {
                throw new CommandException(String.format(
                        UpdateOngoingVisitCommand.MESSAGE_INVALID_VISIT_TASK_INDEX,
                        targetIndex.getOneBased()));
            }
            this.put(targetIndex, isMarkFinish);
        }
    }

    /**
     * Record in a multimap the details of a VisitTask using a list of indexes.
     */
    public void saveTaskDetailsByIndexes(
            List<VisitTask> taskList,
            List<Pair<Index, String>> indexDetailPairList) throws CommandException {
        CollectionUtil.requireAllNonNull(taskList, indexDetailPairList);
        for (Pair<Index, String> indexStringPair : indexDetailPairList) {
            //Throw an exception if the index is larger than the size
            if (indexToInt(indexStringPair.getKey()) >= taskList.size()) {
                throw new CommandException(String.format(
                        UpdateOngoingVisitCommand.MESSAGE_INVALID_VISIT_TASK_INDEX,
                        indexStringPair.getKey()));
            }
            this.put(indexStringPair.getKey(), indexStringPair.getValue());
        }
    }

    /**
     * Builds and returns an updated visit task list using changes recorded in the multimap.
     */
    public List<VisitTask> buildUpdatedVisitTasks(final List<VisitTask> visitTaskList) {
        requireNonNull(visitTaskList);
        List<VisitTask> updatedVisitTaskList = new ArrayList<>();
        for (int i = 0; i < visitTaskList.size(); i++) {
            updatedVisitTaskList.add(updateVisitTask(visitTaskList.get(i), i));
        }
        return updatedVisitTaskList;
    }

    /**
     * Returns an updated visit task using changes recorded in the multimap.
     */
    private VisitTask updateVisitTask(VisitTask visitTask, int index) {
        requireNonNull(visitTask);
        VisitTask result = visitTask;
        if (map.containsKey(index)) {
            Detail detail = visitTask.getDetail();
            boolean isDone = visitTask.isDone();
            for (Object change : map.get(index)) {
                //Detail
                if (change instanceof String) {
                    detail = new Detail((String) change);
                } else if (change == VisitTaskUpdatesMultimapKeys.FINISH) {
                    isDone = true;
                } else if (change == VisitTaskUpdatesMultimapKeys.UNFINISH) {
                    isDone = false;
                } else {
                    throw new IllegalArgumentException();
                }
            }
            result = new VisitTask(visitTask.getVisitTodo(), detail, isDone);
        }
        return result;
    }
}
