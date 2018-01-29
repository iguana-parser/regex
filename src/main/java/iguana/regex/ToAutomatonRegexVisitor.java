package iguana.regex;

import iguana.regex.automaton.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ToAutomatonRegexVisitor implements RegularExpressionVisitor<Automaton> {

	@Override
	public Automaton visit(Char c) {
		State startState = new State();
		State finalState = new State(StateType.FINAL);
		startState.addTransition(new Transition(c.getValue(), finalState));
		return Automaton.builder(startState).build();
	}

	@Override
	public Automaton visit(CharRange r) {
		State startState = new State();
		State finalState = new State(StateType.FINAL);
		startState.addTransition(new Transition(r.getStart(), r.getEnd(), finalState));
		return Automaton.builder(startState).build();
	}

	@Override
	public Automaton visit(EOF eof) {
    	State startState = new State();
    	State endState = new State(StateType.FINAL);
    	startState.addTransition(new Transition(EOF.VALUE, endState));
        return Automaton.builder(startState).build();		
	}

	@Override
	public Automaton visit(Epsilon e) {
    	State state = new State(StateType.FINAL);
        return Automaton.builder(state).build();
	}

	@Override
	public Automaton visit(Star star) {
		//TODO: add separators to the DFA
		State startState = new State();
		State finalState = new State(StateType.FINAL);
		
		Automaton automaton = star.getSymbol().accept(this).copy();
		
		startState.addEpsilonTransition(automaton.getStartState());
		
		Set<State> finalStates = automaton.getFinalStates();
		
		for(State s : finalStates) {
			s.setStateType(StateType.NORMAL);
			s.addEpsilonTransition(finalState);
			s.addEpsilonTransition(automaton.getStartState());
		}
		
		startState.addEpsilonTransition(finalState);
		
		return Automaton.builder(startState).build();
	}

	@Override
	public Automaton visit(Plus p) {
		return Seq.from(p.getSymbol(), Star.from(p.getSymbol())).accept(this);
	}

	@Override
	public Automaton visit(Opt o) {
		State startState = new State();
		
		State finalState = new State(StateType.FINAL);

		Automaton automaton = o.getSymbol().accept(this).copy();
		startState.addEpsilonTransition(automaton.getStartState());
		
		Set<State> finalStates = automaton.getFinalStates();
		for(State s : finalStates) {
			s.setStateType(StateType.NORMAL);
			s.addEpsilonTransition(finalState);			
		}
		
		startState.addEpsilonTransition(finalState);
		
		return Automaton.builder(startState).build();
	}

	@Override
	public <E extends RegularExpression> Automaton visit(Alt<E> alt) {
		List<E> symbols = alt.getSymbols();
		
		if (symbols.size() == 1)
			return symbols.get(0).accept(this);
		
		List<Automaton> automatons = new ArrayList<>();
				
		for (RegularExpression e : symbols) {
			automatons.add(e.accept(this).copy());
		}
		
		State startState = new State();
		State finalState = new State(StateType.FINAL);
		
		for (Automaton automaton : automatons) {
			startState.addEpsilonTransition(automaton.getStartState());
			
			Set<State> finalStates = automaton.getFinalStates();
			for (State s : finalStates) {
				s.setStateType(StateType.NORMAL);
				s.addEpsilonTransition(finalState);				
			}
		}
		
		return new AutomatonBuilder(startState).build(); 
	}

	@Override
	public <E extends RegularExpression> Automaton visit(Seq<E> seq) {
		List<Automaton> automatons = new ArrayList<>();
		
		List<E> symbols = seq.getSymbols();
		for (int i = 0; i < symbols.size(); i++) {
			automatons.add(symbols.get(i).accept(this).copy());
		}
		
		Automaton current = automatons.get(0);
		State startState = current.getStartState();
		
		for (int i = 1; i < automatons.size(); i++) {
			Automaton next = automatons.get(i);
			
			for (State s : current.getFinalStates()) {
				s.setStateType(StateType.NORMAL);
				// Merge the end state with the start state of the next automaton
				for (Transition t : next.getStartState().getTransitions()) {
					s.addTransition(new Transition(t.getRange(), t.getDestination()));
				}
			}
			
			current = next;
		}
		
		return Automaton.builder(startState).build();

	}

}
