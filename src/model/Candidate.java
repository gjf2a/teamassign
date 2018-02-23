package model;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.stream.IntStream;

import edu.hendrix.util.Util;

public class Candidate {
	private Assignment tentative;
	private AllPreferences prefs;
	private int min, max;
	
	public Candidate(AllPreferences prefs, int min, int max) {
		this(prefs, min, max, new Assignment());
	}
	
	public Assignment getAssignment() {
		return tentative;
	}
	
	private Candidate(AllPreferences prefs, int min, int max, Assignment a) {
		Util.assertArgument(min <= max, "min " + min + " > max " + max);
		this.prefs = prefs;
		this.min = min;
		this.max = max;
		tentative = a;
	}
	
	public Candidate with(String person, String leader) {
		Candidate result = new Candidate(prefs, min, max);
		result.tentative = this.tentative.with(person, leader);
		return result;
	}
	
	public ArrayList<Candidate> successors() {
		TreeSet<String> unassigned = new TreeSet<>(prefs.allPersons());
		unassigned.removeAll(tentative.allAssignedPersons());
		
		ArrayList<Candidate> result = new ArrayList<>();
		for (String person: unassigned) {
			for (String leader: prefs.prefsFor(person)) {
				result.add(with(person, leader));
			}
		}
		return result;
	}
	
	public boolean atGoal() {
		return allPersonsAssigned() && allTeamSizesOk();
	}
	
	public boolean allPersonsAssigned() {
		return prefs.allPersons().stream().allMatch(person -> tentative.isAssigned(person));
	}
	
	public IntStream allTeamSizes() {
		return tentative.allLeaders().stream().mapToInt(s -> tentative.teamSizeFor(s));
	}
	
	public boolean allTeamSizesOk() {
		return allTeamSizes().allMatch(size -> size >= min && size <= max);
	}
	
	public boolean canPurge() {
		return allTeamSizes().anyMatch(size -> size > max);
	}
}
