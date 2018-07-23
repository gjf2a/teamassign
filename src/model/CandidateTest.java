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
		assertEquals("{A:C}{B:A}{C:D}{D:B}", result2.get().toString());
		
		// Use cases 3, 7, and 10
		prefs.addPreference("A", "A");
		prefs.addPreference("B", "B");
		prefs.addPreference("C", "C");
		prefs.addPreference("D", "D");
		
		Candidate t1 = new Candidate(prefs, 2, 3);
		Optional<Assignment> result3 = DepthFirstSearch.getSolution(t1);
		assertTrue(result3.isPresent());
		assertEquals("{A:A}{B:A}{C:D}{D:D}", result3.get().toString());
		
		Candidate t2 = new Candidate(prefs, 1, 3);
		Optional<Assignment> result4 = DepthFirstSearch.getSolution(t2);
		assertTrue(result4.isPresent());
		assertEquals("{A:A}{B:A}{C:C}{D:D}", result4.get().toString());
	}
}
