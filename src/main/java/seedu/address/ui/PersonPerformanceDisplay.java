package seedu.address.ui;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;

public class PersonPerformanceDisplay extends UiPart<Region> {

	private static final String FXML = "PersonPerformanceDisplay.fxml";

	@FXML
	private Label bestPerformance;
	@FXML
	private Label date;
	@FXML
	private Label event;
	@FXML
	private Label mostRecent;

	public PersonPerformanceDisplay(String eventName, String recentDate, String performance, String recent) {
		super(FXML);
		event.setText(eventName);
		bestPerformance.setText(performance);
		mostRecent.setText(recent);
		date.setText(recentDate);
	}
}
