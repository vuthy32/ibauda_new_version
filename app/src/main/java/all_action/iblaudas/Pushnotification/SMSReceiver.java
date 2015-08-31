package all_action.iblaudas.Pushnotification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

/**
 * Created by softbooms on 4/2/2015.
 */
public class SMSReceiver extends BroadcastReceiver {
    private final String DEBUG_TAG = getClass().getSimpleName().toString();
    private static final String ACTION_SMS_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private Context mContext;
    private Intent mIntent;

    // Retrieve SMS
    public void onReceive(Context context, Intent intent) {
        mContext = context;
        mIntent = intent;

        String action = intent.getAction();

        if(action.equals(ACTION_SMS_RECEIVED)){

            String address, str = "";
            int contactId = -1;

            SmsMessage[] msgs = getMessagesFromIntent(mIntent);
            if (msgs != null) {
                for (int i = 0; i < msgs.length; i++) {
                    address = msgs[i].getOriginatingAddress();
                 //   contactId = ContactsUtils.getContactId(mContext, address, "address");
                    str += msgs[i].getMessageBody().toString();
                    str += "\n";
                }
            }

            if(contactId != -1){
                showNotification(contactId, str);
            }

            // ---send a broadcast intent to update the SMS received in the
            // activity---
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction("SMS_RECEIVED_ACTION");
            broadcastIntent.putExtra("sms", str);
            context.sendBroadcast(broadcastIntent);
        }

    }

    public static SmsMessage[] getMessagesFromIntent(Intent intent) {
        Object[] messages = (Object[]) intent.getSerializableExtra("pdus");
        byte[][] pduObjs = new byte[messages.length][];

        for (int i = 0; i < messages.length; i++) {
            pduObjs[i] = (byte[]) messages[i];
        }
        byte[][] pdus = new byte[pduObjs.length][];
        int pduCount = pdus.length;
        SmsMessage[] msgs = new SmsMessage[pduCount];
        for (int i = 0; i < pduCount; i++) {
            pdus[i] = pduObjs[i];
            msgs[i] = SmsMessage.createFromPdu(pdus[i]);
        }
        return msgs;
    }

    /**
     * The notification is the icon and associated expanded entry in the status
     * bar.
     */
    protected void showNotification(int contactId, String message) {
        //Display notification...
    }
}
