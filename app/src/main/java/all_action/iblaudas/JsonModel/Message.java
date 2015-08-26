package all_action.iblaudas.JsonModel;


public class Message {

    private String Text;
    private String senderId;
    private String recipientId;
    private String messageId ,setCm_id,
            created_dt,getUserID;


    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getCm_id() {
        return setCm_id;
    }

    public void setCm_id(String setCm_id) {
        this.setCm_id = setCm_id;
    }
    public void setCreated_dt(String created_dt){this.created_dt = created_dt;}
    public String getCreated_dt(){return created_dt;}

    public void setUserID(String getUserID){this.getUserID = getUserID;}
    public String getUserIDs(){return getUserID;}

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public  String getMessageId(){return messageId;}
}