package all_action.iblaudas.function_api;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class UserFunctions {

    private JSONParser jsonParser;


    //URL of the PHP API
 /* private static String loginURL = "http://192.168.0.14/mobile/postlogin.php";
  private static String registerURL = "http://192.168.0.14/mobile/postregister.php";
   private static String posOrder = "http://192.168.0.14/mobile/postOrderCar.php";
   private static String reservedCar = "http://iblauda.com/mobile/testOrderedCarList.php";*/
    //private static String ServerURL = "http://192.168.0.210";
    private static String ServerURL = "http://iblauda.com";
    private static String loginURL = ServerURL+"/mobile/postlogin.php";
   //===========test login========
   // private static String loginURL = ServerURL+"/mobile/login.php";
   // private static String registerURL = "https://iblauda.com/mobile/postregister.php";
    private static String registerURL = ServerURL+"/mobile/postregister.php";
 
  //private static String postOrder = "http://iblauda.com/mobile/testOrderCar.php";
    private static String postOrder = ServerURL+"/mobile/postOrderCar.php";
    private static String orderlist_url = ServerURL+"/mobile/getOrderedCarList.php";
    private static String cancel_order_url = ServerURL+"/mobile/postCancelCarOrder.php";

    private static String user_chat_url = ServerURL+"/mobile/getUserList.php";
    private static String getConversationMessage =  ServerURL+"/mobile/getConversationMessages.php";
    private static String setupMessagePosting_url =  ServerURL+"/mobile/postCreateConversationMessage.php";
    //==for test  private static String getNewMsgUrl = ServerURL+"/mobile/testCountNewConversationMessages.php";====
    private static String getNewMsgUrl = ServerURL+"/mobile/getCountNewConversationMessages.php";
    private static String reserveCar_tag = "reserveCar";
    private static String reserved_tag = "reserved_tag";


    // constructor
    public UserFunctions(){
        jsonParser = new JSONParser();

    }
    /**
     * Function to getNew Message
     **/
    @SuppressWarnings("deprecation")
    public JSONObject GetNewMsg(String member_no,String remember_token,String reviever_no){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("member_no", member_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        params.add(new BasicNameValuePair("receiver_no", reviever_no));
        JSONObject json = jsonParser.getJSONFromUrl(getNewMsgUrl, params);
        return json;
    }


    /**
     * Function to Login
     **/

    public JSONObject CancelOrder(String cancelmember_no,String cancelremember_token ,String cancelidexId){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("member_no", cancelmember_no));
        params.add(new BasicNameValuePair("remember_token", cancelremember_token));
        params.add(new BasicNameValuePair("idx", cancelidexId));
        JSONObject json = jsonParser.getJSONFromUrl(cancel_order_url, params);
        return json;
    }
    /**
     * Function to Login
     **/

    public JSONObject loginUser(String member_id, String member_pwd){
        // Building Parameters		
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("member_id", member_id));
        params.add(new BasicNameValuePair("member_pwd", member_pwd));
        JSONObject json = jsonParser.getJSONFromUrl(loginURL, params);
        return json;
    }

    /**
     * Function to ReservedCar
     **/

    public JSONObject ReservedCar(String memeber_no, String remember_token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", reserved_tag));

        params.add(new BasicNameValuePair("member_no", memeber_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        JSONObject json = jsonParser.getJSONFromUrl(orderlist_url, params);
        return json;
    }
    /**
     * Function to reset the password
     *
*/
    public JSONObject ReVersCar(String member_no,String remember_token,String getIdx){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("tag", reserveCar_tag));
        params.add(new BasicNameValuePair("member_no", member_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        params.add(new BasicNameValuePair("idx", getIdx));
        JSONObject json = jsonParser.getJSONFromUrl(postOrder, params);
        return json;
    }
    /**
     * Function to  Register
     **/
    public JSONObject registerUser(String member_id, String member_pwd, String email, String member_first_name, String member_last_name, String member_country, String mobile_no1, String mobile_no2){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("member_id", member_id ));
        params.add(new BasicNameValuePair("member_pwd", member_pwd ));
        params.add(new BasicNameValuePair("email", email ));
        params.add(new BasicNameValuePair("member_first_name", member_first_name ));
        params.add(new BasicNameValuePair("member_last_name", member_last_name ));
       // pairs.add(new BasicNameValuePair("sex", sex ));
        params.add(new BasicNameValuePair("member_country", member_country ));
        params.add(new BasicNameValuePair("mobile_no1", mobile_no1 ));
        params.add(new BasicNameValuePair("mobile_no2", mobile_no2 ));
       // pairs.add(new BasicNameValuePair("mobile_no3", mobile_no3 ));
        JSONObject json = jsonParser.getJSONFromUrl(registerURL,params);
        //JSONObject json = jsonParser.getJSONFromUrl(registerURL, params);
        return json;
    }
    /**
     * Function to UserChat
     **/

    public JSONObject UserChat(String memeber_no, String remember_token){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("tag", user_chat));

        params.add(new BasicNameValuePair("member_no", memeber_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        JSONObject json = jsonParser.getJSONFromUrl(user_chat_url, params);
        return json;
    }

    public JSONObject ReceiveMessage(String memeber_no, String remember_token, String receiver_no){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("tag", user_chat));

        params.add(new BasicNameValuePair("member_no", memeber_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        params.add(new BasicNameValuePair("receiver_no", receiver_no));
        JSONObject json = jsonParser.getJSONFromUrl(getConversationMessage, params);
        return json;
    }

    public JSONObject setupMessagePosting(String memeber_no, String remember_token, String receiver_no, String conversation_text){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        //params.add(new BasicNameValuePair("tag", user_chat));

        params.add(new BasicNameValuePair("member_no", memeber_no));
        params.add(new BasicNameValuePair("remember_token", remember_token));
        params.add(new BasicNameValuePair("receiver_no", receiver_no));
        params.add(new BasicNameValuePair("conversation_text", conversation_text));
        JSONObject json = jsonParser.getJSONFromUrl(setupMessagePosting_url, params);
        return json;
    }
}

