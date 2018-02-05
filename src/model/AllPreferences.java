package model;

import java.util.Collections;
import java.util.Map.Entry;

import edu.hendrix.util.Util;

import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class AllPreferences {
	private TreeMap<String,TreeSet<String>> person2prefs = new TreeMap<>();
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		for (Entry<String, TreeSet<String>> personList: person2prefs.entrySet()) {
			result.append('{');
			result.append(personList.getKey());
			result.append(':');
			for (String pref: personList.getValue()) {
				result.append(pref);
				result.append(';');
			}
			result.append('}');
		}
		return result.toString();
	}
	
	public static AllPreferences from(String s) {
		AllPreferences result = new AllPreferences();
		for (String part: Util.debrace(s)) {
			String[] personList = part.split(":");
			for (String target: personList[1].split(";")) {
				if (target.trim().length() > 0) {
					result.addPreference(personList[0], target);
				}
			}
		}
		return result;
	}
	
	@Override
	public boolean equals(Object other) {
		if (other instanceof AllPreferences) {
			AllPreferences that = (AllPreferences)other;
			if (this.allPersons().size() != that.allPersons().size()) {
				return false;
			}
			for (String person: allPersons()) {
				if (!that.allPersons().contains(person)) {
					return false;
				}
				Set<String> prefs = prefsFor(person);
				if (prefs.size() != that.prefsFor(person).size()) {
					return false;
				}
				for (String pref: prefs) {
					if (!that.hasPreference(person, pref)) {
						return false;
					}
				}
			}
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasPerson(String person) {
		return person != null && person2prefs.containsKey(person);
	}
	
	public boolean hasPreference(String person, String preference) {
		return !hasPerson(person) || person2prefs.get(person).contains(preference);
	}
	
	public void removePerson(String person) {
		person2prefs.remove(person);
	}
	
	public Set<String> allPersons() {
		return Collections.unmodifiableSet(person2prefs.keySet());
	}
	
	public Set<String> prefsFor(String person) {
		return Collections.unmodifiableSet(person2prefs.get(person));
	}
	
	public void addPerson(String person) {
		if (!hasPerson(person)) {
			Util.assertArgument(person != null && person.length() > 0, "No null persons allowed");
			person2prefs.put(person, new TreeSet<>());
		}
	}
	
	private void guarantee(String person) {
		if (!hasPerson(person)) {
			addPerson(person);
		}
	}
	
	public void addPreference(String person, String preference) {
		guarantee(person);
		guarantee(preference);
		person2prefs.get(person).add(preference);
	}
	
	public void removePreference(String person, String preference) {
		if (hasPerson(person)) {
			if (person2prefs.get(person).remove(preference));
		}
	}
}
