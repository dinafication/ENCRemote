package hr.hackweek.encchecker.fragments;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import hr.hackweek.encchecker.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class StateFrag extends Fragment {

	private View view;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.state_frag, container, false);
		// getActivity().setContentView(R.layout.activity_main);

		chechState();

		postData();
		return view;
	}

	/**
	 * Provjerava stanje mreže, ako je uređaj online napravi provjeru stanja
	 * inače postavlja zadnju spremljenu vrijednost
	 */
	private void chechState() {
		if (isOnline()) {
			TextView encState = (TextView) view.findViewById(R.id.textView1);
			encState.setText(fetchENCState().toString());
		} else {
			TextView encState = (TextView) view.findViewById(R.id.textView1);
			encState.setText(fetchStoredState().toString());
		}
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
		 * Vtariti true samo ako je moguće uspostaviti vezu i ako uređaj nije u
		 * roamingu
		 */
		return netInfo.isAvailable() && !netInfo.isRoaming();
	}

	/**
	 * Dohvaća podatak o stanju enca i vraća ga u FDloat objektu
	 * 
	 * @return
	 */
	private Float fetchENCState() {
		// TODO: dodati parsiranje HTTPS requesta

		return 103.75f;
	}

	/**
	 * TODO: Maknuti
	 */
	public void postData() {
	    // Create a new HttpClient and Post Header
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("https://prodaja.hac.hr");

	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("formPost_username", "mladen.cikara"));
	        nameValuePairs.add(new BasicNameValuePair("formPost_password", "22563585"));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        
	        Log.d("HTTP_RESPONSE", response.toString());
	        
	    } catch (ClientProtocolException e) {
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	        // TODO Auto-generated catch block
	    }
	} 
}
