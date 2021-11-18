package fa.nfa;

import java.util.HashMap;
import java.util.Set;
import java.util.LinkedHashSet;

import fa.State;

/**
 * This class represents a state within a NFA
 * @author MichaelKinsy
 */
public class NFAState extends State{

    private boolean isFinal;
    private HashMap<Character,LinkedHashSet<NFAState>> delta;
    /**
     * 
     * @param name
     */
    public NFAState(String name){
        initDefault(name);
        this.isFinal = false;
    }
    /**
     * 
     * @param name
     * @param isFinal
     */
    public NFAState(String name, boolean isFinal){
        initDefault(name);
        this.isFinal = isFinal;
    }

    /**
     * Intializes the default values for state
     * @param name
     */
    public void initDefault(String name){
         this.name = name;
         delta =new HashMap<Character, LinkedHashSet<NFAState>>();
    }

    /**
     * @return boolean indicating final state
     */
    public boolean isFinal() { return isFinal; }
    
    /**
     * add a transistion to delta for state
     * @param onSymb
     * @param toState
     */
    public void addTransition(char onSymb, NFAState toState){
        if (delta.containsKey(onSymb) == false){
            delta.put(onSymb, new LinkedHashSet<NFAState>());
        }
        delta.get(onSymb).add(toState);
    }

    /**
     *  returns the state transition given a symbol
     * @param symb
     * @return
     */
    public Set<NFAState> getTo(char symb){
        LinkedHashSet<NFAState> ret = this.delta.get(symb);
        if(ret == null){
            return new LinkedHashSet<NFAState>();
        }
        return ret;

    }

}
