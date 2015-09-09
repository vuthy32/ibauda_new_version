package all_action.iblaudas.model;

/**
 * Created by sunry on 9/1/2015.
 */
public class m_country {
    private String countStr;
    public m_country(){}
    public m_country(String countStrs) {
        this.countStr=countStrs;
    }
    public String getCountry(){
        return countStr;
    }
    public void setCountStr(String countStrs) {
        this.countStr = countStrs;
    }
}
