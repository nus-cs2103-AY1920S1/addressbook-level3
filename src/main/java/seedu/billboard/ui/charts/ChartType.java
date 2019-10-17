package seedu.billboard.ui.charts;

import java.util.function.Supplier;

public enum ChartType {
    TIMELINE(ExpenseTimelineChart::new);

    private final Supplier<ExpenseChart> chartProducer;

    ChartType(Supplier<ExpenseChart> chartProducer) {
        this.chartProducer = chartProducer;
    }

    public ExpenseChart getChart() {
        return chartProducer.get();
    }
}
