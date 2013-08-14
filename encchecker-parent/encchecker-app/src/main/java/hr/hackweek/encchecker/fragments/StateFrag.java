package hr.hackweek.encchecker.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import hr.hackweek.encchecker.ApplicationConstants;
import hr.hackweek.encchecker.R;
import hr.hackweek.encchecker.lib.AuthenticationException;
import hr.hackweek.encchecker.lib.EncPageParser;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StateFrag extends Fragment {

	private final String TAG = "STATE_FRAG";
	private View view;
	private ProgressBar pb;

	private String username;
	private String password;

	private SharedPreferences appSettings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		appSettings = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
		view = inflater.inflate(R.layout.state_frag, container, false);
		
		pb = (ProgressBar) view.findViewById(R.id.progress_bar);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		loadPreferences();

		DownloadEncStateTask dest = new DownloadEncStateTask();

		StringBuilder url = new StringBuilder();
		url.append(getResources().getString(R.string.base_url));
		url.append(getResources().getString(R.string.auth_login));

		dest.execute(new String[] { url.toString() });
	}

	private void loadPreferences() {

		if (appSettings.contains(ApplicationConstants.USERNAME_PREFERENCES)) {
			username = appSettings.getString(ApplicationConstants.USERNAME_PREFERENCES, "");
		}

		if (appSettings.contains(ApplicationConstants.PASSWORD_PREFERENCES)) {
			password = appSettings.getString(ApplicationConstants.PASSWORD_PREFERENCES, "");
		}
	}

	private class DownloadEncStateTask extends AsyncTask<String, Void, String> {

		// Create a new HttpClient and Post Header
		private AndroidHttpClient httpclient;

		@Override
		protected void onPreExecute() {
			httpclient = AndroidHttpClient.newInstance("AndroidHttpClient");

			// Pokrenuti Progress Bar
			pb.setEnabled(true);
			pb.setIndeterminate(true);
			pb.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			String response;

			if (isOnline()) {
				response = postLoginData(params[0]);
			} else {
				response = fetchStoredState();
			}

			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			pb.setEnabled(false);
			pb.setVisibility(ProgressBar.INVISIBLE);
			
			
			httpclient.close();

			TextView encState = (TextView) view.findViewById(R.id.enc_stanje_iznos);
			encState.setText(result);

			// TODO: spremiti vrijednost u settinse
		}

		/**
		 * Vraća lokalno storani podatak
		 * 
		 * @return
		 */
		private String fetchStoredState() {
			// TODO napraviti pravo učitavanje iz lokalnog stora

			return "99.9";
		}

		/**
		 * Provjerava da li je moguće uspostaviti HTTPS vezu
		 * 
		 * @return
		 */
		private boolean isOnline() {
			ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

			/**
			 * Vtariti true samo ako je moguće uspostaviti vezu i ako uređaj
			 * nije u roamingu
			 */
			return netInfo.isAvailable() && !netInfo.isRoaming();
		}

		/**
		 * Šalje korisnikov username i password i dogvaća stranicu na kojoj je
		 * stanje
		 * 
		 * @param url
		 * @return ENC state
		 */
		public String postLoginData(String url) {
			String ret = null;

			HttpPost httppost = new HttpPost(url);

			try {

				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("login", "Prijava"));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				EncPageParser parser = new EncPageParser(response.getEntity().getContent());

				ret = parser.getEncState();

			} catch (ClientProtocolException e) {
				Log.e(TAG, e.getLocalizedMessage());
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			} catch (AuthenticationException e) {
				// TODO: treba korisnika obavijestiti da ima krivi username i
				// password
				ret = "NE!";
			}

			return ret;
		}
	}
}
