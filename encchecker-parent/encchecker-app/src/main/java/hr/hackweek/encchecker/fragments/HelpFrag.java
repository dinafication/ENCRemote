package hr.hackweek.encchecker.fragments;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import hr.hackweek.encchecker.MainActivity;
import hr.hackweek.encchecker.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HelpFrag extends Fragment {

	private View view;

	private final String TAG = "HELP_FRAG";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.help_frag, container, false);

		// Read raw file into string and populate TextView
		InputStream iFile = getResources().openRawResource(R.raw.help);
		try {
			TextView helpText = (TextView) view
					.findViewById(R.id.TextView_HelpText);
			String strFile = inputStreamToString(iFile);
			helpText.setText(strFile);
		} catch (Exception e) {
			Log.e(TAG, "InputStreamToString failure", e);
		}
		return view;
	}

	/**
	 * Converts an input stream to a string
	 * 
	 * @param is
	 *            The {@code InputStream} object to read from
	 * @return A {@code String} object representing the string for of the input
	 * @throws IOException
	 *             Thrown on read failure from the input
	 */
	public String inputStreamToString(InputStream is) throws IOException {
		StringBuffer sBuffer = new StringBuffer();

		BufferedReader dataIO = new BufferedReader(new InputStreamReader(is));

		String strLine = null;
		while ((strLine = dataIO.readLine()) != null) {
			sBuffer.append(strLine + "\n");
		}
		dataIO.close();
		is.close();
		return sBuffer.toString();
	}

	@Override
	public void onStart() {
		super.onStart();
		((MainActivity) getActivity()).hideSoftKeyboard();
	}

}
