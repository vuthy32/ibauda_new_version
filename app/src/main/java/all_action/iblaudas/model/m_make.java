package all_action.iblaudas.model;

/**
 * Created by sunry on 9/1/2015.
 */
public class m_make {
    private String make_name;
    public m_make(){}
    public m_make(String countStrs) {
        this.make_name=countStrs;
    }
    public String getMake(){
        return make_name;
    }
    public void setMake(String countStrs) {
        this.make_name = countStrs;
    }
}
