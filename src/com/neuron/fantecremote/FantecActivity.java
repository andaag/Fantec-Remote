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

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

//TODO FUTURE : Multiple servers
//TODO FUTURE : auto detect if server is up?


public class FantecActivity extends Activity {
	private static final String TAG = "FantecRemote";
	
	//TODO : totalTasks/completedTasks can be merged into one again, "runningTasks"
	public static int totalTasks = 0;
	public static int completedTasks = 0;
	private SharedPreferences prefs;
	private ActionBar actionBar;
	//private final static int SETTINGS_ID = Menu.FIRST;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.navigate);
        prefs = getSharedPreferences("fantecremote", 0);
        
        /*
         * Setup toolbar
         */
        actionBar = (ActionBar) findViewById(R.id.actionbar);
        // You can also assign the title programmatically by passing a
        // CharSequence or resource id.
        //actionBar.setTitle(R.string.some_title);
        //actionBar.setHomeAction(new IntentAction(this, HomeActivity.createIntent(this), R.drawable.ic_title_home_default));
        actionBar.setTitle("Fantec Remote");
        actionBar.addAction(new SettingsAction());
        
        // Look up the AdView as a resource and load a request.
        /*AdView adView = (AdView)this.findViewById(R.id.adView);
        adView.loadAd(new AdRequest());*/
        // Create the adView
        AdView adView = new AdView(this, AdSize.BANNER, "a14dd8d8827bacc");
        // Lookup your LinearLayout assuming it’s been given
        // the attribute android:id="@+id/mainLayout"
        LinearLayout layout = (LinearLayout)findViewById(R.id.mainLayout);
        // Add the adView to it
        //layout.addView(adView, new LayoutParams())
        layout.addView(adView);
        // Initiate a generic request to load it with an ad
        AdRequest adRequest = new AdRequest();
        //adRequest.addTestDevice("F7C9180F318B9F36622B5A61C79AD7BF");
        adView.loadAd(adRequest);
        //adView.setGravity(Gravity.BOTTOM | Gravity.CENTER);
        
        /*if (Settings.GetApiUrl(prefs) == null) {
    		Intent i = new Intent(this, Settings.class);
    		startActivityForResult(i, Intent.FLAG_ACTIVITY_CLEAR_TOP);
        } */
        
        ImageButton button = (ImageButton) findViewById(R.id.imageButton1);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_GUIDE");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton2);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_UP");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton3);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_RETURN");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton4);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_LEFT");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton5);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_SELECT");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton6);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_RIGHT");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton7);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_EDIT");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton8);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_DOWN");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton9);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_SETUP");
			}
        });
        
        
        /** The playback controls */
        
        button = (ImageButton) findViewById(R.id.imageButton10);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_STOP");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton11);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_MELE_PLAYPAUSE");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton12);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_DISPLAY");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton13);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_FRWD");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton14);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_FFWD");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton15);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_STITLE");
			}
        });
        
        
        
        button = (ImageButton) findViewById(R.id.imageButton16);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_PREV");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton17);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_NEXT");
			}
        });
        
        button = (ImageButton) findViewById(R.id.imageButton18);
        button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				send("CMD_MUTE");
			}
        });
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_VOLUME_UP:
			send("CMD_VOL_UP");
			return true;
		case KeyEvent.KEYCODE_VOLUME_DOWN:
			send("CMD_VOL_DOWN");
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
	
	

	@Override
	protected void onResume() {
		super.onResume();
        /**
         * Check if the API server has been set.
         */
    	TextView infoText = (TextView) findViewById(R.id.infotext);
    	TableLayout tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        if (Settings.GetApiUrl(prefs) == null) {
        	infoText.setVisibility(View.VISIBLE);
        	tableLayout.setVisibility(View.GONE);
        } else {
        	infoText.setVisibility(View.GONE);
        	tableLayout.setVisibility(View.VISIBLE);
        }
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if  (keyCode == KeyEvent.KEYCODE_VOLUME_UP || keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}
	
	private void updateProgress() {
		if (completedTasks == totalTasks) {
			actionBar.setProgressBarVisibility(View.GONE);
			//actionBar.setProgressBarIndeterminateVisibility(View.GONE);
			totalTasks = 0;
			completedTasks = 0;
		} else {
			//actionBar.setProgress(completedTasks / totalTasks * 100);
			actionBar.setProgressBarVisibility(View.VISIBLE);
			//actionBar.setProgressBarIndeterminateVisibility(View.VISIBLE);
		}
	}
	
	public void send(String command) {
		totalTasks++;
		updateProgress();
		String apiurl = Settings.GetApiUrl(prefs); 
		if (apiurl == null) {
			Toast.makeText(this, "You must configure a server before using the remote", Toast.LENGTH_LONG).show();
			return;
		}
		new SendCommandTask().execute(apiurl + command);
	}

	private class SendCommandTask extends AsyncTask<String, Void, Boolean> {
	     protected Boolean doInBackground(String... cmd) {
	    	 try {
				new DefaultHttpClient().execute(new HttpGet(cmd[0]));
				//HttpResponse response = 
				//TODO : Handle exceptions with popups.
			} catch (ClientProtocolException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
	    	 //InputStream content = response.getEntity().getContent();
	    	 return true;
	     }

	     protected void onProgressUpdate(Void... progress) {
	     }

	     protected void onPostExecute(Boolean result) {
	    	 if (!result) {
	    		 Toast.makeText(FantecActivity.this, "Error comunicating with server.\n\n *Is it on?\n* Is the right address in settings?", Toast.LENGTH_LONG).show();
	    	 }
	    	 completedTasks++;
	    	 updateProgress();
	     }
	 }

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		final MenuItem settings = menu.add(0, SETTINGS_ID, 0, "Preferences");
		settings.setIcon(android.R.drawable.ic_menu_preferences);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i = new Intent(this, Settings.class);
		startActivityForResult(i, Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return false;
	}*/

	
	private class SettingsAction implements Action {

	    @Override
	    public int getDrawable() {
	        return android.R.drawable.ic_menu_preferences;
	    }

	    @Override
	    public void performAction(View view) {
	        /*Toast.makeText(FantecActivity.this,
	                "Example action", Toast.LENGTH_SHORT).show();*/
	    	Intent i = new Intent(FantecActivity.this, Settings.class);
    		startActivityForResult(i, Intent.FLAG_ACTIVITY_CLEAR_TOP);
	    }

	}
	
    
}