package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.MainActivity;
import android.support.v4.app.Fragment;



public class OneActivityFragment extends Fragment{
	
	public MainActivity getCustomActivity(){
		return (MainActivity) getActivity();
	}
	
	public void hideSoftKeyboard(){
		getCustomActivity().hideSoftKeyboard();
	}

}
