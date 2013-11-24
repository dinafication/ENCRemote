package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.ApplicationConstants;
import hr.hackweek.encchecker.MainActivity;
import hr.hackweek.encchecker.R;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class PasswordFrag extends Fragment implements OnClickListener {

	private View view;
	private EditText username;
	private EditText password;
	private ImageButton postavi;
	private String errorMessage;

	private SharedPreferences appSettings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		appSettings = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);

		view = inflater.inflate(R.layout.password_frag, container, false);

		username = (EditText) view.findViewById(R.id.username);
		password = (EditText) view.findViewById(R.id.password);

		// addTouchLsn();

		postavi = (ImageButton) view.findViewById(R.id.button1);
		postavi.setOnClickListener(this);

		return view;
	}

	@Override
	public void onStart() {
		super.onStart();

		checkErrorMessage();

		initUsername();

		initPassword();
		
		setKeyboardListeners();
	}

	private void checkErrorMessage() {
		TextView errorTextView = (TextView) view.findViewById(R.id.errorTextView);
		Bundle b = getArguments();
		if (b != null) {
			errorMessage = b.getString(ApplicationConstants.ERROR_MESSAGE);

			errorTextView.setText(errorMessage);
			errorTextView.setVisibility(TextView.VISIBLE);
		} else {
			errorTextView.setVisibility(TextView.INVISIBLE);
		}
	}

	private void initPassword() {
		if (appSettings.contains(ApplicationConstants.PASSWORD_PREFERENCES)) {
			password.setText(appSettings.getString(ApplicationConstants.PASSWORD_PREFERENCES, ""));
		}
	}

	private void initUsername() {
		if (appSettings.contains(ApplicationConstants.USERNAME_PREFERENCES)) {
			username.setText(appSettings.getString(ApplicationConstants.USERNAME_PREFERENCES, ""));
		}
	}

	public void onClick(View v) {
		savePrefferences();
	
		((MainActivity) getActivity()).setStateFrag();
	}

	private void savePrefferences() {
		Editor editor = appSettings.edit();

		String uname = username.getText().toString();
		String pass = password.getText().toString();

		editor.putString(ApplicationConstants.USERNAME_PREFERENCES, uname);
		editor.putString(ApplicationConstants.PASSWORD_PREFERENCES, pass);

		editor.commit();

	}
	
public void setKeyboardListeners(){
		
		// remove keyboard na view click
		View mainView = getActivity().findViewById(R.id.scrollView);
		
		mainView.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View arg0, MotionEvent arg1) {
				((MainActivity) getActivity()).hideSoftKeyboard();
				return false;
			}

        });
		
		
		View usernameView = getActivity().findViewById(R.id.username);

		usernameView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (!hasFocus) {
	            	((MainActivity) getActivity()).hideSoftKeyboard();
	            }
	        }
	    });
		
		View passwordView = getActivity().findViewById(R.id.password);

		passwordView.setOnFocusChangeListener(new View.OnFocusChangeListener() {
	        public void onFocusChange(View v, boolean hasFocus) {
	            if (!hasFocus)  {
	            	((MainActivity) getActivity()).hideSoftKeyboard();
	            }
	        }
	    });
	}
	
}
