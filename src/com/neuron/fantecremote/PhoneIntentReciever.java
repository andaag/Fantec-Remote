package com.neuron.fantecremote;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

public class PhoneIntentReciever extends BroadcastReceiver {
	@Override
	public void onReceive(Context context, Intent intent) {
		MyPhoneStateListener phoneListener = new MyPhoneStateListener(context.getSharedPreferences("fantecremote", 0));
		TelephonyManager telephony = (TelephonyManager) 
		context.getSystemService(Context.TELEPHONY_SERVICE);
		telephony.listen(phoneListener,PhoneStateListener.LISTEN_CALL_STATE);
	}
}

class MyPhoneStateListener extends PhoneStateListener {
	private SharedPreferences prefs;
	public MyPhoneStateListener(SharedPreferences prefs) {
		super();
		this.prefs = prefs;
	}
	
	public void onCallStateChanged(int state,String incomingNumber) {
		switch(state){
		case TelephonyManager.CALL_STATE_IDLE:
			//Log.d("DEBUG", "IDLE");
			break;
		case TelephonyManager.CALL_STATE_OFFHOOK:
			//Log.d("DEBUG", "OFFHOOK");
			break;
		case TelephonyManager.CALL_STATE_RINGING:
			//Log.d("DEBUG", "RINGING");
			String apiurl = Settings.GetApiUrl(prefs);
			if (apiurl != null && prefs.getBoolean("autopause", true)) {
				new SendCommandTask().execute(apiurl + "CMD_PAUSE");
			}
			break;
		}
	} 
	
	private class SendCommandTask extends AsyncTask<String, Void, Void> {
	     protected Void doInBackground(String... cmd) {
	    	 try {
				new DefaultHttpClient().execute(new HttpGet(cmd[0]));
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	    	 //InputStream content = response.getEntity().getContent();
	    	 return null;
	     }

	     protected void onProgressUpdate(Void... progress) {
	     }

	     protected void onPostExecute(Void result) {
	     }
	 }
}