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
    private HashMap<Character, Set<NFAState>> delta;
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
         delta =new HashMap<>();
    }

    /**
     * Sets a no-final state to a final state.
     */
    public void setFinal() {
        this.isFinal = true;
      }

    /**
     * Sets a final state to a non-final state.
     */
    public void setNonFinal() {
        this.isFinal = false;
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
        if (delta.containsKey(Character.valueOf(onSymb)) == false){
            Set<NFAState> tran = new LinkedHashSet<>();
            delta.put(Character.valueOf(onSymb), tran);
            tran.add(toState);
        }else{
            Set<NFAState> tran = delta.get(Character.valueOf(onSymb));
            tran.add(toState);
        }
    }

    /**
     *  returns the state transition given a symbol
     * @param symb
     * @return
     */
    public Set<NFAState> getTo(char symb){
        Set<NFAState> ret = this.delta.get(Character.valueOf(symb));
        if(ret == null){
            return new LinkedHashSet<NFAState>();
        }
        return ret;

    }

}
