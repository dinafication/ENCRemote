package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class StateFrag extends Fragment{
	
	private View view;

	 @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	      Bundle savedInstanceState) {
		  
		 
		  
		  
		 view = inflater.inflate(R.layout.state_frag,
	        container, false);
	    //getActivity().setContentView(R.layout.activity_main);
	    
	    
	    return view;
	  }
	 
}
