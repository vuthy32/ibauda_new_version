package info.androidhive.materialdesign.JsonModel;

/**
 * Created by DON on 3/30/2015.
 */
public class User {
    private String userName;
    private String msg;
    private String receiver,newCount,lastname;


    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getlastname(){return lastname;}

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public String getNewCount(String newCount){return newCount;}

    public void setNewCount(String newCount) {
        this.newCount = newCount;
    }
}
