package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.model.person.Person;


public class InformationDisplay extends UiPart<Region> {

	private static final String FXML = "InformationDisplay.fxml";

	public final Person person;

	@FXML
	private AnchorPane displayPane;
	@FXML
	private Label name;
	@FXML
	private Label phone;
	@FXML
	private Label address;
	@FXML
	private Label email;
	@FXML
	private ImageView photo;
	@FXML
	private FlowPane tags;
	@FXML
	private Label attendanceRate;
	@FXML
	private Label performance;

	public InformationDisplay(Person person) {
		super(FXML);
		this.person = person;
		name.setText(person.getName().fullName);
		phone.setText(person.getPhone().value);
		address.setText(person.getAddress().value);
		email.setText(person.getEmail().value);
		//setText of attendance and performance. Should attendance and performance be tag to a person?
		attendanceRate.setText("0%");
		performance.setText("superb");
	}
}
