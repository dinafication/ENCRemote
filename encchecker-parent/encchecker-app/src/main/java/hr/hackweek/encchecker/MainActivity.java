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
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends FragmentActivity implements OnAuthenticationExceptionListener {

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		setFragment(new StateFrag(), R.id.fragmentViewGroup, true);

	}

	private void setFragment(Fragment f, int id, boolean add) {
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

		if (add)
			fragmentTransaction.add(id, f);//.addToBackStack(null);
		else
			fragmentTransaction.replace(id, f).addToBackStack(null);
		fragmentTransaction.commit();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(hr.hackweek.encchecker.R.menu.main, menu);
		return true;
	}

	public boolean onOptionsItemSelected(android.view.MenuItem item) {

		switch (item.getItemId()) {
		case R.id.settings1_postavke:
			setFragment(new PasswordFrag(), R.id.fragmentViewGroup, false);
			break;
		case R.id.settings2_stanje:
			setFragment(new StateFrag(), R.id.fragmentViewGroup, false);

			break;
		case R.id.settings3_pomoc:
			setFragment(new HelpFrag(), R.id.fragmentViewGroup, false); // TODO
			break;
		}
		return false;
	}
	
	public void postavkeClck(View view) {
		setFragment(new PasswordFrag(), R.id.fragmentViewGroup, false);
	}
	
	public void postavkeClck(View view, String message) {
		Fragment f = new PasswordFrag();		
		Bundle b = new Bundle();
		b.putString(ApplicationConstants.ERROR_MESSAGE, message);
		
		f.setArguments(b);
		
		//TODO: provjeriti da li bi bolje bilo reusati fragmente
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

    	if(activity.getCurrentFocus() != null && activity.getCurrentFocus().getWindowToken()!=null){
        	
        InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        
        inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);

        }
    }

	public void onAuthenticationException(String errorMessage) {
		postavkeClck(null, errorMessage);
	}

}
