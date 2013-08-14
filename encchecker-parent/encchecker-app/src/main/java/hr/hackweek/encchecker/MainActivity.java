package hr.hackweek.encchecker;

import hr.hackweek.encchecker.fragments.HelpFrag;
import hr.hackweek.encchecker.fragments.PasswordFrag;
import hr.hackweek.encchecker.fragments.StateFrag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {

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
			fragmentTransaction.add(id, f);
		else
			fragmentTransaction.replace(id, f);
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

	public void setStateFrag(View view) {

		// TODO spremiti user i pass
		setFragment(new StateFrag(), R.id.fragmentViewGroup, false);
	}

	public void pomocClck(View view) {
		setFragment(new HelpFrag(), R.id.fragmentViewGroup, false);
	}

}
