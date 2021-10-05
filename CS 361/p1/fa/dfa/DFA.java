package fa.dfa;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import fa.State;
public class DFA implements DFAInterface {
    private DFAState q0;
    private HashSet<DFAState> q;
    private HashSet<DFAState> f;
    private HashMap<DFAState, HashMap<Character, DFAState>> sigma;

    public DFA(){
        this.q = new HashSet<DFAState>();
        this.q0 = null;
        this.f =  new HashSet<DFAState>();
        this.sigma =  new HashMap<DFAState,HashMap<Character,DFAState>>();
    }

    @Override
    public void addStartState(String name) {
        this.q0 = new DFAState(name);
    }

    @Override
    public void addState(String name) {
        q.add(new DFAState(name));
    }

    @Override
    public void addFinalState(String name) {
        f.add(new DFAState(name));
    }

    @Override
    public void addTransition(String fromState, char onSymb, String toState) {
        HashMap<Character,DFAState> transition = new HashMap<Character,DFAState>();
        transition.put(onSymb, new DFAState(toState));
        this.sigma.put(new DFAState(fromState),transition);
    }

    @Override
    public Set<DFAState> getStates() {
        HashSet<DFAState> states = new HashSet<DFAState>();
        states.add(this.q0);
        for (DFAState state : this.q){
            states.add(state);
        }
        for (DFAState state : this.f){
            states.add(state);
        }
        return states;
    }

    @Override
    public Set<DFAState> getFinalStates() {
        return f;
    }

    @Override
    public State getStartState() {
        return q0;
    }

    @Override
    public Set<Character> getABC() {
        return sigma;
    }

    @Override
    public boolean accepts(String s) {
        boolean accept = true;
        for (char sym: s.toCharArray()){
            
        }
        return accept;
    }

    @Override
    public State getToState(fa.dfa.DFAState from, char onSymb) {
        // TODO Auto-generated method stub
        return null;
    }
 
}
