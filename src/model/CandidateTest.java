package model;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;

public class CandidateTest {

	@Test
	// Use case 7
	public void test() {
		AllPreferences prefs = new AllPreferences();
		for (String name: new String[]{"A", "B", "C", "D"}) {
			prefs.addPerson(name);
		}
		prefs.addPreference("A", "B");
		prefs.addPreference("B", "D");
		prefs.addPreference("C", "D");
		prefs.addPreference("D", "B");
		prefs.addPreference("B", "A");
		Candidate c = new Candidate(prefs, 1, 1);
		Optional<Assignment> result1 = DepthFirstSearch.getSolution(c);
		assertFalse(result1.isPresent());
		
		prefs.addPreference("A", "C");
		Optional<Assignment> result2 = DepthFirstSearch.getSolution(c);
		assertTrue(result2.isPresent());
		
		// Use cases 3 and 7
		Candidate t1 = new Candidate(prefs, 2, 3);
		Optional<Assignment> result3 = DepthFirstSearch.getSolution(t1);
		assertTrue(result3.isPresent());
		
		// Use cases 3 and 7
		Candidate t2 = new Candidate(prefs, 1, 3);
		Optional<Assignment> result4 = DepthFirstSearch.getSolution(t2);
		assertTrue(result4.isPresent());
	}
	
	
}
