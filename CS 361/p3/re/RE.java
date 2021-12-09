package re;
import java.util.*;
import fa.dfa.DFA;
import fa.nfa.NFA;
import fa.State;
import fa.nfa.NFAState;

/**
 * This class implements a regular expression that can be converted to an NFA
 * @author Michael
 */
public class RE implements REInterface{
	//private variables
    private int nameNum = 0; 
    private String regEx;
    
    /**
     * Constructor for a regex obj
     * 
     * @param regEx to be converted
     */
    public RE(String regEx){
        this.regEx = regEx;
    }
    
    /**
     * The top level function for our recursive decent parser which returns the NFA form of a parsed Regex
     * Grammar:
     * 	nfaRE: <nfaTerm> '|' <nfaRE>
     * 	  | <nfaTerm>
     * @return NFA object of given a parsed Regex
     */
    public NFA getNFA(){
    	//parse the required term from the RE
        NFA nfaTerm = parseTerm();
        //if there is a union we must parse another RE
        if(more() && peek() == '|'){
            next();
            NFA nfaRE = getNFA(); //Parse the next RE which will come back in the form of a term eventually
            //After succesfully parsing the second RE to a nfa union the two seperate nfaTerms
            //By first remembering the orginal start states for the two seperate term nfa's
            String start1 = nfaTerm.getStartState().getName();
            String start2 = nfaRE.getStartState().getName();
            //Next add all of the states and alphabet from the newly parsed nfa to the first nfaterm one
            nfaTerm.addNFAStates(nfaRE.getStates());
            nfaTerm.addAbc(nfaRE.getABC());
            //Create the new start and final states for the combined nfa
            //Then add a e transition from the new start state to each of the previously saved start states
            nfaTerm.addStartState('q'+Integer.toString(nameNum++));
            nfaTerm.addFinalState('q'+Integer.toString(nameNum++));
            nfaTerm.addTransition('q'+Integer.toString(nameNum-2),'e', start1);
            nfaTerm.addTransition('q'+Integer.toString(nameNum-2),'e', start2);
            
            //finally turn all original final states from the now unioned nfa to non final and e transition them to 
            //the new final state created.
            for(State state : nfaRE.getFinalStates()){
                NFAState fState = (NFAState) state;
                fState.setNonFinal();
                nfaTerm.addTransition(fState.getName(), 'e', 'q'+Integer.toString(nameNum-1));
            }
            //unioned nfa
            return nfaTerm;
        }
        	//single nfa term
            return nfaTerm;
    }
    
    /**
     * Parses a nfaterm into a nfa fact by either returning a parsed fact nfa or fact nfa concated with one or more fact nfas
     * Grammar:
     * 	nfaterm: <fact>
     * 		   | <fact><fact>^+
     * @return a nfa representing a factor 
     */
    private NFA parseTerm(){
    	//create our new base fact nfa that must be returned
        NFA fact = new NFA();   
        // create a new start and final state and make a e transition from this start to final.
        fact.addStartState('q'+String.valueOf(nameNum++));
        fact.addFinalState('q'+String.valueOf(nameNum++));
        fact.addTransition(fact.getStartState().getName(),'e','q'+Integer.toString(nameNum-1));
        
        //if we must concatanate more nfa facts to the return nfa fact
        while(more() && peek() != ')'  && peek()!= '|'){
        	//parse next fact
            NFA newFact = parseFact();
            //add next facts alphabet to the return nfa fact
            fact.addAbc(newFact.getABC());
            //turn all original final states to nonfinal and add a tranistion to them on e to our new facts start state
            for(State state : fact.getFinalStates()){
                NFAState tmp = (NFAState)state;
                tmp.setNonFinal();
                tmp.addTransition('e',(NFAState)newFact.getStartState());
            }
            //add all new factor states to our return fact nfa
            fact.addNFAStates(newFact.getStates());
        }
        //the nfa fact
        return fact;
    }
    
    /**
     * Parses a fact nfa to a atom or a atom that is repeated by a kleen star
     * * Grammar:
     * 	fact: <atom>
     * 		   | <atom>'*'
     * @return
     */
    private NFA parseFact(){
    	//parse the required nfa from the atom
        NFA atom = parseAtom();
        while (more() && peek() == '*'){//incase there are multiple kleene stars back to back
            next();
            //add e transitions from all final states to our start state
            for(State state : atom.getFinalStates()){
                atom.addTransition(state.getName(),'e',atom.getStartState().getName());
            }
            //e transition from a new start state to our atoms original start state and make it final
            atom.addTransition('q'+Integer.toString(nameNum++), 'e', atom.getStartState().getName());
            atom.addStartState('q'+Integer.toString(nameNum-1));
            atom.addFinalState('q'+Integer.toString(nameNum-1));

            
        }
        //return atom
        return atom;
    }
    
   
    /**
     * Constructs the smallest nfa we can have which represents a atom (lang symbol)
     * * Grammar:
     * 	atom: '(' <nfaRE>' ')'
     * 		   | <atom>
     * @return a new nfa with two states and a one symbol alphabet or a parsed nfaRE
     */
    private NFA parseAtom(){
    	//if parenthisis return RE
    	if(peek() == '('){
            next();
            NFA re = getNFA();
            next();
            return re;
    	}
    	// no parenthisis just make a new nfa with a start and final state transitioning on the atom parsed 
        NFA atom = new NFA();
        atom.addStartState('q'+Integer.toString(nameNum++));
        atom.addFinalState('q'+Integer.toString(nameNum++));
        atom.addTransition('q'+Integer.toString(nameNum-2),next(),'q'+String.valueOf(nameNum-1));
        return atom;
    }
    
    /**
     * returns next first char in regex
     * @author https://matt.might.net/articles/parsing-regex-with-recursive-descent/
     * @return c
     */
    private char peek() {
        return regEx.charAt(0);
    }
    
    /**
     * removes first symbol from regex
     * @author https://matt.might.net/articles/parsing-regex-with-recursive-descent/
     * @param c
     */
    private void  eat(char c){
        if(peek() == c){
            this.regEx = this.regEx.substring(1);
        }else{
            throw new RuntimeException("Expected: " + c + "; got: " + peek());
        }
    }
    
    /**
     * eats and returns symbol
     * @author https://matt.might.net/articles/parsing-regex-with-recursive-descent/
     * @return c
     */
    private char next() {
        char c = peek();
        eat(c);
        return c;
    }
    
    /**
     * checks if regex is empty
     * @author https://matt.might.net/articles/parsing-regex-with-recursive-descent/
     * @return boolean
     */
    private boolean more(){
        return regEx.length() > 0;
    }
}
