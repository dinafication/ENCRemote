package hr.hackweek.encchecker.fragments;

import hr.hackweek.encchecker.ApplicationConstants;
import hr.hackweek.encchecker.MainActivity;
import hr.hackweek.encchecker.R;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class PasswordFrag extends Fragment implements OnClickListener{

	private View view;
	private EditText username;
	private EditText password;
	private Button postavi;

	private SharedPreferences appSettings;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		appSettings = getActivity().getSharedPreferences(ApplicationConstants.PREFERENCES, Context.MODE_PRIVATE);

		view = inflater.inflate(R.layout.password_frag, container, false);

		username = (EditText) view.findViewById(R.id.username);
		password = (EditText) view.findViewById(R.id.password);
		
		//addTouchLsn();
		
		postavi = (Button) view.findViewById(R.id.button1);
		postavi.setOnClickListener(this);

		return view;
	}


	@Override
	public void onStart() {
		super.onStart();

		initUsername();

		initPassword();		
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
		
		((MainActivity) getActivity()).setStateFrag(view);
	}

	private void savePrefferences() {
		Editor editor = appSettings.edit();
		
		String uname = username.getText().toString();
		String pass = password.getText().toString();
		
		editor.putString(ApplicationConstants.USERNAME_PREFERENCES, uname);
		editor.putString(ApplicationConstants.PASSWORD_PREFERENCES, pass);
		
		editor.commit();
		
	}
}
