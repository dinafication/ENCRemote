package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class PasswordFrag extends Fragment{
	
	private View view;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.password_frag, container, false);
		return view;
	}

}
