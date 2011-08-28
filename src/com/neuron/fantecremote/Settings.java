/**
 *     Copyright (C) 2011 Anders Aagaard <aagaande@gmail.com>
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
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
