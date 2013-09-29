package hr.hackweek.encchecker;

import hr.hackweek.encchecker.fragments.HelpFrag;
import hr.hackweek.encchecker.fragments.PasswordFrag;
import hr.hackweek.encchecker.fragments.StateFrag;
import hr.hackweek.encchecker.fragments.StateFrag.OnAuthenticationExceptionListener;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost.TabSpec;

public class MainActivity extends FragmentActivity implements OnAuthenticationExceptionListener {

	private FragmentTabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost) findViewById(R.id.fragmentViewGroup);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);

		mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator("Postavke", getResources().getDrawable(R.drawable.user_5_light)), PasswordFrag.class, new Bundle());
		mTabHost.addTab(mTabHost.newTabSpec("state").setIndicator("Stanje", getResources().getDrawable(R.drawable.money3_light)), StateFrag.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("help").setIndicator("Help", getResources().getDrawable(R.drawable.help_putokaz_2_light)), HelpFrag.class, null);

		
		Drawable kaj = mTabHost.getTabWidget().getChildAt(0).getBackground();
		Log.d("Nekaj", kaj.toString());
	}
	

	public void refresherClck(View view) {
		StateFrag sf = (StateFrag) getSupportFragmentManager().findFragmentByTag("state");
//        .findFragmentById(R.id.st));
//		mTabHost.getCurrentTabView().getf;
//		mTabHost.getCurrentView();
//		sf.fetchUrl();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		hideSoftKeyboard();

		return false;
	}

	public  void hideSoftKeyboard() {

		if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {

			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);

		}
	}

	public void onAuthenticationException(String errorMessage) {
		Fragment f = getSupportFragmentManager().findFragmentByTag("settings");
		Bundle b = f.getArguments();
		b.putString(ApplicationConstants.ERROR_MESSAGE, errorMessage);

		mTabHost.setCurrentTabByTag("settings");

	}

	public void setStateFrag() {
		mTabHost.setCurrentTabByTag("state");
	}

}
