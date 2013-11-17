package hr.hackweek.encchecker.fragments;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

import hr.hackweek.encchecker.MainActivity;
import hr.hackweek.encchecker.R;
import android.support.v4.app.Fragment;



public class OneActivityFragment extends Fragment{
	
	public MainActivity getCustomActivity(){
		return (MainActivity) getActivity();
	}
	
	public void hideSoftKeyboard(){
		getCustomActivity().hideSoftKeyboard();
	}

	protected void setDebug() {
//		AdRequest request = new AdRequest();
//		request.addTestDevice("params[0]");
//		request.addTestDevice(AdRequest.TEST_EMULATOR);
//		request.addTestDevice("BF4DAC3E5979DCD3F8AB61D55AC33C8F");
//		AdView adView = (AdView) getActivity().findViewById(R.id.adView); 
//		adView.loadAd(request);		
	}

}
