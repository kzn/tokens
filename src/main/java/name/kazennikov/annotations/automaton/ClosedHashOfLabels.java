/*
 *  ClosedHashOfLabels.java
 *
 *  Copyright (c) 2010-2011, Ontotext (www.ontotext.com).
 *
 *  This file is part of GATE (see http://gate.ac.uk/), and is free
 *  software, licenced under the GNU Library General Public License,
 *  Version 2, June 1991 (in the distribution as file licence.html,
 *  and also available at http://gate.ac.uk/gate/licence.html).
 *
 *
 *  $Id$
 */
package name.kazennikov.annotations.automaton;

import name.kazennikov.annotations.fsm.JapePlusFSM.Transition;


/**
 * This class implements closed hash of elements of type Transition
 * 
 * @author petar.mitankin
 * 
 */
public class ClosedHashOfLabels extends ClosedHashOfObjects<Transition> {
	private boolean epsilon = false;

	/**
	 * Puts a transition in the closed hash.
	 * 
	 * @param t
	 * @return the number of the transition
	 */
	@Override
	public int put(Transition t) {
		return put(t);
	}

	/**
	 * Gets an array of all transitions that are stored in the closed hash. A
	 * change of one these transition will make the hash table inconsistent.
	 * 
	 * @return
	 */
	public Transition[] getCopyOfTransitions() {
		Transition[] t = new Transition[objectsStored];

		for (int i = 0; i < objectsStored; i++) {
			t[i] = (Transition) objects[i];
		}
		return t;
	}

	/**
	 * Gets the number of transitions that are stored in the closed hash.
	 * 
	 * @return
	 */
	public int getTransitionsStored() {
		if (epsilon) {
			return objectsStored + 1;
		}
		return objectsStored;
	}

	/**
	 * Marks that a transition with labeled with epsilon was put in the closed
	 * hash.
	 */
	public void addEpsilon() {
		epsilon = true;
	}

	@Override
	protected int getHashCode(Transition t) {
		return t.getLabel();

	}

	@Override
	protected boolean equal(Transition t1, Transition t2) {
		return t1.getLabel() == t2.getLabel();
	}
}
