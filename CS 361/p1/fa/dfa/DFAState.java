package fa.dfa;

import fa.State;
/**
 * This class implements the State interface to model a Deterministic finite automaton state.
 * @author @michaelkinsy@u.boisestate.edu
 */
public class DFAState extends State {
    public DFAState(String name){
        this.name = name;
    }

    @Override 
    /**
     * Indicates whether a object is the same as the current state
     * @param Object obj to compare.
     * @return boolean if states are same
     */
    public boolean equals(Object obj){
        if(obj instanceof DFAState){
            return this.name.equals(((DFAState)obj).name);
        }return false;
    }

    @Override
    /**
     * New hashcode method so that the state name is what is used when generating a hashcode value for the state.
     * @return Int of new hashcode
     */
    public int hashCode(){
        int prime = 31;
        return prime + ((name == null) ? 0 : name.hashCode());
    }
}
