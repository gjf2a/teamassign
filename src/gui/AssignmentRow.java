package gui;

import java.util.List;

import model.Assignment;

public class AssignmentRow {
	private String person, team;
	
	public static void resetListTo(Assignment a, List<AssignmentRow> target) {
		target.clear();
		for (String person: a.allAssignedPersons()) {
			target.add(new AssignmentRow(person, a.getLeaderFor(person)));
		}
	}
	
	public AssignmentRow(String p, String t) {
		person = p;
		team = t;
	}
	
	public String getPerson() {
		return person;
	}
	
	public String getTeam() {
		return team;
	}
}
