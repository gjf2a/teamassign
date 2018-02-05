package model;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

public class DepthFirstSearch {
	public static Optional<Assignment> getSolution(Candidate current) {
		if (current.atGoal()) {
			return Optional.of(current.getAssignment());
		} else {
			Stream<Assignment> candidates = getSolutionStream(current);
			Iterator<Assignment> iter = candidates.iterator();
			return iter.hasNext() ? Optional.of(iter.next()) : Optional.empty();
		}
	}
	
	public static Stream<Assignment> getSolutionStream(Candidate current) {
		return current.successors().stream().map(c -> getSolution(c)).filter(Optional::isPresent).map(Optional::get);
	}
}
