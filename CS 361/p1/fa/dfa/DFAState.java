package fa.dfa;

import fa.State;

public class DFAState extends State {
    public DFAState(String name){
        this.name = name;
    }

    @Override 
    public boolean equals(Object obj){
        if(obj instanceof DFAState){
            return this.name.equals(((DFAState)obj).name);
        }return false;
    }

    @Override
    public int hashCode(){
        int prime = 31;
        return prime + ((name == null) ? 0 : name.hashCode());
    }
}
