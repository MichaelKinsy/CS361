package re;

public class RE implements REInterface{
    private string regEx;
    public RE(String regEx){
        this.regEx = regEx;
    }

    public NFA getNFA(){
        NFA nfa = null;
    }
}
