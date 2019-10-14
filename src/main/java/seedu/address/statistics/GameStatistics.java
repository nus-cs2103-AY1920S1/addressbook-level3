package seedu.address.statistics;

import seedu.address.model.card.Card;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class GameStatistics {
    private Map<Card, List<GameDataPoint>> data;

    public GameStatistics() {
        data = new LinkedHashMap<>();
    }

    public void addDataPoint(GameDataPoint gameDataPoint, Card card) {
        if (data.containsKey(card)) {
            data.get(card).add(gameDataPoint);
        } else {
            List<GameDataPoint> gameDataPointList = new ArrayList<>();
            gameDataPointList.add(gameDataPoint);
            data.put(card, gameDataPointList);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Card, List<GameDataPoint>> entry : data.entrySet()) {
            sb.append("-------").append(entry.getKey()).append("-------\n");
            for (GameDataPoint gameDataPoint : entry.getValue()) {
                sb.append("   ").append(gameDataPoint).append("\n");
            }
        }
        return sb.toString();
    }
}
