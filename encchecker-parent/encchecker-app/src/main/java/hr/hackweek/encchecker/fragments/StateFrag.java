package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.R;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

}
