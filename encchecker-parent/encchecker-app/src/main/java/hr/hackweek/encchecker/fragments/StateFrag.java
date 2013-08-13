package hr.hackweek.encchecker.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import hr.hackweek.encchecker.R;
import android.app.ProgressDialog;
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
import android.widget.TextView;

public class StateFrag extends Fragment {

	public static final String PREFERENCES = "ENCPrefs";
	
	private final String TAG = "STATE_FRAG";
	private View view;
	private ProgressDialog pd;

	private SharedPreferences mGameSettings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		mGameSettings = getActivity().getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE);
		view = inflater.inflate(R.layout.state_frag, container, false);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		DownloadEncStateTask dest = new DownloadEncStateTask();

		StringBuilder url = new StringBuilder();
		url.append(getResources().getString(R.string.base_url));
		url.append(getResources().getString(R.string.auth_login));

		dest.execute(new String[] { url.toString() });
	}

	private class DownloadEncStateTask extends AsyncTask<String, Void, Float> {

		// Create a new HttpClient and Post Header
		private AndroidHttpClient httpclient;

		@Override
		protected void onPreExecute() {
			httpclient = AndroidHttpClient.newInstance("AndroidHttpClient");

			pd = new ProgressDialog(view.getContext());
			pd.setTitle("Processing...");
			pd.setMessage("Please wait.");
			pd.setCancelable(false);
			pd.setIndeterminate(true);
			pd.show();
		}

		@Override
		protected Float doInBackground(String... params) {
			Float response;

			if (isOnline()) {
				String page = postLoginData(params[0]);

				response = fetchENCState(page);
			} else {
				response = fetchStoredState();
			}

			return response;
		}

		@Override
		protected void onPostExecute(Float result) {
			pd.dismiss();
			httpclient.close();

			TextView encState = (TextView) view.findViewById(R.id.enc_stanje_iznos);
			encState.setText(result.toString());
		}

		/**
		 * Vraća lokalno storani podatak
		 * 
		 * @return
		 */
		private Float fetchStoredState() {
			// TODO napraviti pravo učitavanje iz lokalnog stora

			return 99.9f;
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
		 * Dohvaća podatak o stanju enca i vraća ga u FDloat objektu
		 * 
		 * @return
		 */
		private Float fetchENCState(String page) {

			return 103.75f;
		}

		private String createPageFromStream(BufferedReader reader) {
			StringBuilder str = new StringBuilder();
			String line = null;

			try {
				while ((line = reader.readLine()) != null) {
					str.append(line + "\n");
				}
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			}

			return str.toString();
		}

		/**
		 * Šalje korisnikov username i password i dogvaća stranicu na kojoj je
		 * stanje
		 * 
		 * @param url
		 * @return html page
		 */
		public String postLoginData(String url) {
			String ret = null;

			HttpPost httppost = new HttpPost(url);

			try {

				// Add your data
				List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(3);
				nameValuePairs.add(new BasicNameValuePair("username", "mladen.cikara"));
				nameValuePairs.add(new BasicNameValuePair("password", "22563585"));
				nameValuePairs.add(new BasicNameValuePair("login", "Prijava"));

				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

				// Execute HTTP Post Request
				HttpResponse response = httpclient.execute(httppost);

				BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
				ret = createPageFromStream(reader);

			} catch (ClientProtocolException e) {
				Log.e(TAG, e.getLocalizedMessage());
			} catch (IOException e) {
				Log.e(TAG, e.getLocalizedMessage());
			}

			return ret;
		}
	}
}
