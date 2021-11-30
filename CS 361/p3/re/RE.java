package re;
import fa.dfa.DFA;
import fa.nfa.NFA;

public class RE implements REInterface{
    private String regEx;
    public RE(String regEx){
        this.regEx = regEx;
    }

    public NFA getNFA(){
        NFA nfa = base();
        return nfa;
    }

    private NFA base(){
        switch(peek()){
            case '('{
                eat('(');

            }
        }
    }

    private String regex(){
        String term = term();

        if (more() && peek() == '|'){
            eat('|');
            String regex = regex();
            return new Choice(term, regex);
        }else{
            return term;
        }
    }
    private char peek() {
        return regEx.charAt(0);
    }

    private void  eat(char c){
        if(peek() == c){
            this.regEx = this.regEx.substring(1);
        }else{
            throw new RuntimeException("Expected: " + c + "; got: " + peek());
        }
    }

    private char next() {
        char c = peek();
        eat(c);
        return c;
    }

    private boolean more(){
        return regEx.length() > 0;
    }

    private class Choice {
        private String thisOne;
        private String thatOne;
        
        public Choice(String thisOne, String thatOne){
            this.thisOne = thisOne;
            this.thatOne = thatOne;
        }
    } 
}
