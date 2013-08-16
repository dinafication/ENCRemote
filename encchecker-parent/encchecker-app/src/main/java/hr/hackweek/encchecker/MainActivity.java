package hr.hackweek.encchecker;

import hr.hackweek.encchecker.fragments.HelpFrag;
import hr.hackweek.encchecker.fragments.PasswordFrag;
import hr.hackweek.encchecker.fragments.StateFrag;
import hr.hackweek.encchecker.fragments.StateFrag.OnAuthenticationExceptionListener;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends FragmentActivity implements OnAuthenticationExceptionListener {

	private FragmentTabHost mTabHost;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mTabHost = (FragmentTabHost) findViewById(R.id.fragmentViewGroup);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);

		mTabHost.addTab(mTabHost.newTabSpec("settings").setIndicator("Postavke", getResources().getDrawable(R.drawable.user_5)), PasswordFrag.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("state").setIndicator("Stanje", getResources().getDrawable(R.drawable.money3)), StateFrag.class, null);
		mTabHost.addTab(mTabHost.newTabSpec("help").setIndicator("Help", getResources().getDrawable(R.drawable.help_putokaz_2)), HelpFrag.class, null);	
	}

	private void setFragment(Fragment f, int id, boolean add) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (add)
			fragmentTransaction.add(id, f);// .addToBackStack(null);
		else
			fragmentTransaction.replace(id, f).addToBackStack(null);
		fragmentTransaction.commit();
	}

	public void postavkeClck(View view) {
		setFragment(new PasswordFrag(), R.id.fragmentViewGroup, false);
	}

	public void postavkeClck(View view, String message) {
		Fragment f = new PasswordFrag();
		Bundle b = new Bundle();
		b.putString(ApplicationConstants.ERROR_MESSAGE, message);

		f.setArguments(b);

		// TODO: provjeriti da li bi bolje bilo reusati fragmente
		setFragment(f, R.id.fragmentViewGroup, false);
	}

	public void setStateFrag(View view) {

		// TODO spremiti user i pass
		setFragment(new StateFrag(), R.id.fragmentViewGroup, false);
	}

	public void pomocClck(View view) {
		setFragment(new HelpFrag(), R.id.fragmentViewGroup, false);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		hideSoftKeyboard(this);

		return false;
	}

	public static void hideSoftKeyboard(Activity activity) {

		if (activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken() != null) {

			InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

			inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

		}
	}

	public void onAuthenticationException(String errorMessage) {
		postavkeClck(null, errorMessage);
	}

}
