package co.snagapp.android.listeners;

import co.snagapp.android.Classifier;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class SmsListener extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

    	Log.e("DEBUG", "New SMS");
        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
        	Log.e("DEBUG", "Correct Intent");
            Bundle bundle = intent.getExtras();
            SmsMessage [] msgs = null;
            String msgFrom;
            String msgBody;
            if (bundle != null) {
                try {
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    msgs = new SmsMessage[pdus.length];
                    for(int i=0; i<msgs.length; i++){
                        msgs[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
                        msgFrom = msgs[i].getOriginatingAddress();
                        msgBody = msgs[i].getMessageBody();
                    	Log.e("DEBUG", "From: "+msgFrom);
                    	Log.e("DEBUG", "Body: "+msgBody);

                        // If spam,
                        Classifier snag = new Classifier(context);
                        if (snag.isSpam(msgFrom+" "+msgBody)) {
                        	Log.e("DEBUG", "Is spam");
                        	abortBroadcast();
                        	// Add to db
                        	snag.addSpamSMS(msgFrom, msgBody);
                        }
                    }
                } catch(Exception e) {}
            }
        }
    	
    }

}
