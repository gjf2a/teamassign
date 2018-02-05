package gui;

import java.util.Collection;
import java.util.List;

import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import model.AllPreferences;
import model.Candidate;
import model.DepthFirstSearch;

public class Controller {
	@FXML
	TextField personName;
	@FXML
	ChoiceBox<String> persons;
	@FXML
	ChoiceBox<String> potentialPreferences;
	
	@FXML
	ListView<String> personPreferences;
	
	@FXML
	TextField min;
	@FXML
	TextField max;
	
	@FXML
	TableView<AssignmentRow> assignmentView;
	@FXML
	TableColumn<AssignmentRow,String> person;
	@FXML
	TableColumn<AssignmentRow,String> team;
	
	AllPreferences prefs = new AllPreferences();
	
	@FXML
	void initialize() {
		persons.getSelectionModel().selectedItemProperty().addListener((obs, oldV, newV) -> {
			if (newV != null) {
				refreshPersonPrefs(newV);
			}
		});
		
		person.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getPerson()));
		team.setCellValueFactory(cdf -> new SimpleStringProperty(cdf.getValue().getTeam()));
	}
	
	private String getPerson() {
		String person = personName.getText();
		personName.clear();
		return person;
	}
	
	@FXML
	void addPerson() {
		String person = getPerson();
		if (!prefs.hasPerson(person)) {
			prefs.addPerson(person);
			updatePersons();
		}
	}
	
	@FXML
	void removePerson() {
		String person = getPerson();
		if (prefs.hasPerson(person)) {
			prefs.removePerson(person);
			updatePersons();
		}
	}
	
	private void updatePersons() {
		update(persons.getItems(), prefs.allPersons());
		persons.getSelectionModel().select(0);
		update(potentialPreferences.getItems(), prefs.allPersons());
		potentialPreferences.getSelectionModel().select(0);
	}
	
	private void update(List<String> items, Collection<String> data) {
		items.clear();
		items.addAll(data);
	}
	
	@FXML
	void addPreference() {
		String person = persons.getValue();
		prefs.addPreference(person, potentialPreferences.getValue());
		refreshPersonPrefs(person);
	}
	
	@FXML
	void removePreference() {
		String person = persons.getValue();
		prefs.removePreference(person, potentialPreferences.getValue());
		refreshPersonPrefs(person);
	}
	
	private void refreshPersonPrefs(String person) {
		if (person != null) {
			update(personPreferences.getItems(), prefs.prefsFor(person));
		} 
	}
	
	@FXML
	void findSolution() {
		Candidate start = new Candidate(prefs, Integer.parseInt(min.getText()), Integer.parseInt(max.getText()));
		DepthFirstSearch.getSolution(start).ifPresent(a -> {
			AssignmentRow.resetListTo(a, assignmentView.getItems());
		});
	}
}
