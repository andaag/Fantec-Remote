package com.neuron.fantecremote;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.util.Log;

public class Settings extends PreferenceActivity {
	private static final String TAG = "FantecRemote-Settings";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPreferenceManager().setSharedPreferencesName("fantecremote");
        addPreferencesFromResource(R.xml.preferances);
    }
    
    
	public static String GetApiUrl(SharedPreferences prefs) {
        String server = prefs.getString("server", null);
        String port = prefs.getString("port", "1024");
        if (server == null || server.length() < 1) {
    		return null;
        } else {
        	if (port.length() < 1) {
        		port = "1024";
        	}
        	server = server.replace("\n","").replace(" ","");
        	port = port.replace("\n","").replace(" ","");
        	String url = "http://" + server + ":" + port + "/cgi-bin/cubermctrl.cgi?id=1&amp;cmd=";
        	Log.i(TAG,"Url is : " + url);
        	return url;
        }
	}
}
