package fa.dfa;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
/**
 * This class implements the DFAInterface to model a Deterministic finite automaton.
 * @author @michaelkinsy@u.boisestate.edu
 */
import fa.State;
public class DFA implements DFAInterface {
    // Instance variables for DFA 5-tuple
    private DFAState q0;
    private LinkedHashSet<DFAState> q;
    private LinkedHashSet<DFAState> f;
    private HashMap<DFAState, HashMap<Character, DFAState>> delta;
    private LinkedHashSet<Character> sigma;

    /*
     * Constructor for dfa
     */
    public DFA(){
        this.q = new LinkedHashSet<DFAState>();
        this.q0 = null;
        this.f =  new LinkedHashSet<DFAState>();
        this.sigma = new LinkedHashSet<Character>();
        this.delta =  new HashMap<DFAState,HashMap<Character,DFAState>>();
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
        // transition variable maps a symbol to a to state where the key is the symbol and the value is the to state.
        HashMap<Character,DFAState> transition = new HashMap<Character,DFAState>();
        this.sigma.add(onSymb);
        transition.put(onSymb, new DFAState(toState));
        // if this from state contains a transition already we must append the new transition to the existing transition
        if(this.delta.containsKey(new DFAState(fromState))){
            this.delta.get(new DFAState(fromState)).put(onSymb, new DFAState(toState));
        }else{ //else we simply add the new transition
            this.delta.put(new DFAState(fromState),transition);
        }
        
    }

    @Override
    public Set<DFAState> getStates() {
        LinkedHashSet<DFAState> states = new LinkedHashSet<DFAState>();
        // adds states in the order they are parsed
        for (DFAState state : this.f){
            states.add(state);
        }
        states.add(this.q0);
        for (DFAState state : this.q){
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
    public LinkedHashSet<Character> getABC() {
        return sigma;
    }

    @Override
    public boolean accepts(String s) {
        boolean accept = false;
        DFAState state = this.q0;
        for (char sym: s.toCharArray()){
            state = getToState(state, sym);
            //if there is no state given the symbol return
            if (state == null){
                return accept;
            }
        }
        // ensure that the string ends in an accept state
        if(this.f.contains(state)){
            accept = true;
        }

        return accept;
    }

    @Override
    public DFAState getToState(fa.dfa.DFAState from, char onSymb) {
        // ensure that the there is a valid state and transition
        if( this.delta.get(from) == null || this.delta.get(from).get(onSymb) == null){
            return null;
        }
        return this.delta.get(from).get(onSymb);
    }

    @Override
    public String toString(){
        // Format string for printing Q in the order states where parsed.
        String result = "Q = { ";
        Iterator itr = this.f.iterator();
        //iterate through f
        while(itr.hasNext()){
            result += itr.next() + " ";
        }
        // add q0 if not in f
        if (!this.f.contains(this.q0)){
            result += this.q0 + " ";
        }
        // add the rest of the states
        itr = this.q.iterator();
        while(itr.hasNext()){
            result += itr.next() + " ";
        }
        // Begin formating for Sigma
        result += "}\nSigma = { ";
        //iterate through Sigma
        itr = this.sigma.iterator();
        while(itr.hasNext()){
            result+= itr.next() + " ";
        }
        // Begin formatting for delta
        result += "}\ndelta = \n\t\t";
        itr = this.sigma.iterator();
        // Add alphabet to top of table
        while(itr.hasNext()){
            result += itr.next() + "\t";
        }
        result += "\n\t";
        itr =getStates().iterator();
        // iterate through all states and add themselves and their transitions
        while(itr.hasNext()){
            Iterator itr2 = this.sigma.iterator();
            DFAState state = (DFAState) itr.next();
            result += state + "\t";
            // after adding each state add to state from each symbol in alphabet
            while(itr2.hasNext()){
                Character symb = (Character) itr2.next();
                result += getToState(state,symb) + "\t";
            }
            result += "\n\t";
            
        }
        // format q0
        result += "\nq0 = " + this.q0 + "\nF = { ";
        itr = this.f.iterator();
        while(itr.hasNext()){
            result += itr.next() + " ";
        }
        
        result += "}\n";
        return result;
    }
 
}
