package fa.dfa;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;

import fa.State;
public class DFA implements DFAInterface {
    private DFAState q0;
    private LinkedHashSet<DFAState> q;
    private LinkedHashSet<DFAState> f;
    private HashMap<DFAState, HashMap<Character, DFAState>> delta;
    private LinkedHashSet<Character> sigma;

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
        HashMap<Character,DFAState> transition = new HashMap<Character,DFAState>();
        this.sigma.add(onSymb);
        transition.put(onSymb, new DFAState(toState));
        if(this.delta.containsKey(new DFAState(fromState))){
            this.delta.get(new DFAState(fromState)).put(onSymb, new DFAState(toState));
        }else{
            this.delta.put(new DFAState(fromState),transition);
        }
        
    }

    @Override
    public Set<DFAState> getStates() {
        LinkedHashSet<DFAState> states = new LinkedHashSet<DFAState>();
        
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
            if (state == null){
                return accept;
            }
        }
        if(this.f.contains(state)){
            accept = true;
        }

        return accept;
    }

    @Override
    public DFAState getToState(fa.dfa.DFAState from, char onSymb) {
        if( this.delta.get(from) == null || this.delta.get(from).get(onSymb) == null){
            return null;
        }
        return this.delta.get(from).get(onSymb);
    }

    @Override
    public String toString(){
        String result = "Q = { ";
        Iterator itr = this.f.iterator();
        while(itr.hasNext()){
            result += itr.next() + " ";
        }
        if (!this.f.contains(this.q0)){
            result += this.q0 + " ";
        }

        itr = this.q.iterator();
        while(itr.hasNext()){
            result += itr.next() + " ";
        }

        result += "}\nSigma = { ";

        itr = this.sigma.iterator();
        while(itr.hasNext()){
            result+= itr.next() + " ";
        }
        result += "}\ndelta = \n\t\t";
        itr = this.sigma.iterator();
        while(itr.hasNext()){
            result += itr.next() + "\t";
        }
        result += "\n\t";
        itr =getStates().iterator();
        while(itr.hasNext()){
            Iterator itr2 = this.sigma.iterator();
            DFAState state = (DFAState) itr.next();
            result += state + "\t";
            while(itr2.hasNext()){
                Character symb = (Character) itr2.next();
                result += getToState(state,symb) + "\t";
            }
            result += "\n\t";
            
        }
        result += "\nq0 = " + this.q0 + "\nF = { ";
        itr = this.f.iterator();
        while(itr.hasNext()){
            result += itr.next() + " ";
        }
        
        result += "}\n";
        return result;
    }
 
}
