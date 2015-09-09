package all_action.iblaudas.model;

/**
 * Created by sunry on 9/1/2015.
 */
public class m_model {
    private String make_name;int mode_id;
    public m_model(){}
    public m_model(String countStrs,int id) {
        this.make_name=countStrs;
        this.mode_id = id;
    }
    public int getModelID(){
        return mode_id;
    }
    public void setModelID(int ID) {
        this.mode_id = ID;
    }

    public String getModel(){
        return make_name;
    }
    public void setModel(String countStrs) {
        this.make_name = countStrs;
    }
}
