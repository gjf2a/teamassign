package model;

import static org.junit.Assert.*;

import org.junit.Test;

public class AllPreferencesTest {

	@Test
	public void test() {
		// Use Case 2
		AllPreferences prefs = new AllPreferences();
		for (String name: new String[]{"Moe", "Larry", "Curly"}) {
			prefs.addPerson(name);
		}
		
		// Use Case 4
		prefs.addPreference("Curly", "Moe");
		prefs.addPreference("Larry", "Curly");
		prefs.addPreference("Moe", "Larry");
		prefs.addPreference("Moe", "Curly");
		
		assertEquals("{Curly:Moe;}{Larry:Curly;}{Moe:Curly;Larry;}", prefs.toString());
		assertEquals(prefs, AllPreferences.from(prefs.toString()));
		assertTrue(prefs.hasPerson("Moe"));
		assertTrue(prefs.hasPerson("Larry"));
		assertTrue(prefs.hasPerson("Curly"));
		assertFalse(prefs.hasPerson("Shemp"));
	}

	@Test(expected=IllegalArgumentException.class)
	public void guiBug1() {
		AllPreferences prefs = new AllPreferences();
		prefs.addPerson("");
	}
	
	@Test(expected=IllegalArgumentException.class) 
	public void guiBug2() {
		AllPreferences prefs = new AllPreferences();
		prefs.addPerson(null);
	}
}
