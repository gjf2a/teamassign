package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import edu.hendrix.util.Util;

public class Assignment {
	private TreeMap<String,String> person2leader = new TreeMap<>();
	private TreeMap<String,ArrayList<String>> leader2team = new TreeMap<>();
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Entry<String, String> entry: person2leader.entrySet()) {
			result.append("{" + entry.getKey() + ":" + entry.getValue() + "}");
		}
		return result.toString();
	}
	
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof Assignment) {
			Assignment that = (Assignment)other;
			return this.person2leader.equals(that.person2leader) && this.leader2team.equals(that.leader2team);
		} else {
			return false;
		}
	}
	
	public boolean isAssigned(String person) {
		return person2leader.containsKey(person);
	}
	
	public String getLeaderFor(String person) {
		return person2leader.get(person);
	}
	
	public Assignment with(String person, String leader) {
		Assignment updated = new Assignment();
		updated.person2leader.putAll(this.person2leader);
		for (String ld: allLeaders()) {
			updated.leader2team.put(ld, new ArrayList<>());
			updated.leader2team.get(ld).addAll(this.leader2team.get(ld));
		}
		updated.assign(person, leader);
		return updated;
	}
	
	private void assign(String person, String leader) {
		Util.assertArgument(!isAssigned(person), person + " is already assigned");
		person2leader.put(person, leader);
		if (!leader2team.containsKey(leader)) {
			leader2team.put(leader, new ArrayList<>());
		}
		leader2team.get(leader).add(person);
	}
	
	public Set<String> allAssignedPersons() {
		return Collections.unmodifiableSet(person2leader.keySet());
	}
	
	public Set<String> allLeaders() {
		return Collections.unmodifiableSet(leader2team.keySet());
	}
	
	public int teamSizeFor(String leader) {
		return leader2team.get(leader).size();
	}
}
