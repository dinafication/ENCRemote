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
import hr.hackweek.encchecker.MainActivity;
import hr.hackweek.encchecker.R;
import hr.hackweek.encchecker.lib.AuthenticationException;
import hr.hackweek.encchecker.lib.EncPageParser;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class StateFrag extends Fragment implements OnClickListener {

	private final String TAG = "STATE_FRAG";
	private View view;
	private ProgressBar pb;

	private TextView title;
	private String username;
	private String password;
	private Button refresher;

	private SharedPreferences appSettings;

	private OnAuthenticationExceptionListener mListener;

	public interface OnAuthenticationExceptionListener {
		public void onAuthenticationException(String errorMessage);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		try {
			mListener = (OnAuthenticationExceptionListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString() + " must implement onAuthenticationException");
		}
	}
	
	public void onClick(View v) {
		loadPreferences();

		fetchUrl();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		appSettings = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);
		view = inflater.inflate(R.layout.state_frag, container, false);

		pb = (ProgressBar) view.findViewById(R.id.progress_bar);
		title = (TextView) view.findViewById(R.id.enc_stanje_text);
		refresher = (Button) view.findViewById(R.id.refresher);
		refresher.setOnClickListener(this);

		// setAnimation();
		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		((MainActivity) getActivity()).hideSoftKeyboard();

		loadPreferences();

		fetchUrl();
	}

	public void fetchUrl() {
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
		private boolean online;

		@Override
		protected void onPreExecute() {
			refresher.setVisibility(View.INVISIBLE);

			online = isOnline();
			if (online) {
				toggleOfflineMessage(false);
			} else {
				toggleOfflineMessage(true);
			}

			// Pokrenuti Progress Bar
			pb.setEnabled(true);
			pb.setIndeterminate(true);
			pb.setVisibility(ProgressBar.VISIBLE);
		}

		@Override
		protected String doInBackground(String... params) {
			httpclient = AndroidHttpClient.newInstance("AndroidHttpClient");

			String response;

			if (online) {
				response = postLoginData(params[0]);
			} else {
				response = fetchStoredState();
			}

			httpclient.close();
			return response;
		}

		/**
		 * Parametar treba biti da li je uređaj online ili offline
		 * 
		 * @param state
		 */
		private void toggleOfflineMessage(boolean workingOffline) {
			if (workingOffline) {
				title.setText(R.string.enc_offline_stanje_text);
			} else {
				title.setText(R.string.enc_online_stanje_text);
			}
		}

		@Override
		protected void onPostExecute(String result) {
			pb.setEnabled(false);
			pb.setVisibility(ProgressBar.INVISIBLE);

			// getResources()!=null provjerava da je fragment attachan
			if (result != null && getResources() != null) {
				String errorNoDataMessage = getResources().getString(R.string.error_no_data_message);
				String errorWrongDataMessage = getResources().getString(R.string.error_wrong_data_message);

				if (result.length() == 0) {
					title.setText(R.string.enc_unknown_stanje_text);
				} else if (result.equals(errorNoDataMessage) || result.equals(errorWrongDataMessage)) {
					mListener.onAuthenticationException(result);
				} else {
					TextView encState = (TextView) view.findViewById(R.id.enc_stanje_iznos);
					encState.setText(result);

					saveEncState(result);
				}
			}

			// spinin.cancel();
			refresher.setVisibility(View.VISIBLE);
		}

		private void saveEncState(String result) {
			Editor editor = appSettings.edit();
			editor.putString(ApplicationConstants.ENC_STAT_PREFERENCES, result);

			editor.commit();
		}

		/**
		 * Vraća lokalno storani podatak
		 * 
		 * @return
		 */
		private String fetchStoredState() {
			String ret = null;
			if (appSettings.contains(ApplicationConstants.ENC_STAT_PREFERENCES)) {
				ret = appSettings.getString(ApplicationConstants.ENC_STAT_PREFERENCES, "");
			}

			return ret;
		}

		/**
		 * Provjerava da li je moguće uspostaviti HTTPS vezu
		 * 
		 * @return
		 */
		private boolean isOnline() {
			ConnectivityManager conMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo netInfo = conMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
			if (netInfo == null)
				netInfo = conMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
			/**
			 * Vratiti true samo ako je moguće uspostaviti vezu i ako uređaj
			 * nije u roamingu
			 */
			return netInfo != null && netInfo.isAvailable() && !netInfo.isRoaming();
		}

		/**
		 * Šalje korisnikov username i password i dohvaća stranicu na kojoj je
		 * stanje
		 * 
		 * @param url
		 * @return ENC state
		 */
		public String postLoginData(String url) {
			String ret = null;

			HttpPost httpPost = new HttpPost(url);

			try {

				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("username", username));
				nameValuePairs.add(new BasicNameValuePair("password", password));
				nameValuePairs.add(new BasicNameValuePair("login", "Prijava"));

				httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				httpPost.setHeader("User-Agent", "EncChecker Android application. email: enc.checker@gmail.com");

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httpPost);

				EncPageParser parser = new EncPageParser(response.getEntity().getContent());

				ret = parser.getEncState();

			} catch (ClientProtocolException e) {
				Log.e(TAG, e.getLocalizedMessage());
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			} catch (AuthenticationException e) {

				// u slučaju da je došlo do greške vraća se string o grešci
				// greška se mora obraditi u glavnoj dretvi
				ret = e.getMessage();
			}

			return ret;
		}
	}
}
