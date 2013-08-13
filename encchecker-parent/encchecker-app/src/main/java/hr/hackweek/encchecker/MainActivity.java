package hr.hackweek.encchecker;

import hr.hackweek.encchecker.fragments.PasswordFrag;
import hr.hackweek.encchecker.fragments.StateFrag;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.ViewGroup;

public class MainActivity extends FragmentActivity {
	
	private StateFrag stateFrag;
	private PasswordFrag passwordFrag;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		stateFrag = new StateFrag();
		setFragment(stateFrag, R.id.fragmentViewGroup, true);
		
		passwordFrag = new PasswordFrag();
		
	}

	private void setFragment(Fragment f, int id, boolean add){
		FragmentManager fragmentManager = getSupportFragmentManager();
		FragmentTransaction fragmentTransaction = fragmentManager
				.beginTransaction();

		if(add)fragmentTransaction.add(id, f);
		else fragmentTransaction.replace(id, f);
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
			
			//getLayoutInflater().inflate(R.layout.password_frag, (ViewGroup) findViewById(R.id.fragmentViewGroup), false);
			break;
		case R.id.settings2_stanje:
			setFragment(new StateFrag(), R.id.fragmentViewGroup, false);
			
			//getLayoutInflater().inflate(R.layout.state_frag, (ViewGroup) findViewById(R.id.fragmentViewGroup), false);
			break;
		case R.id.settings3_pomoc:
			//setFragment(stateFrag, R.id.fragmentViewGroup, false); // TODO
			break;
		}	
		return false;
		
	}

}
