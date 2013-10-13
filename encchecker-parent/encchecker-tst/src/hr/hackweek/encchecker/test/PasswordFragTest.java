package hr.hackweek.encchecker.test;

import com.jayway.android.robotium.solo.Solo;

import hr.hackweek.encchecker.MainActivity;
import android.support.v4.app.FragmentTabHost;
import android.test.ActivityInstrumentationTestCase2;

public class PasswordFragTest extends ActivityInstrumentationTestCase2<MainActivity>{

	private FragmentTabHost tabHost;
	private Solo robotium;
	
	public PasswordFragTest() {
		super(MainActivity.class);		
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		robotium = new Solo(getInstrumentation(), getActivity());
	}
	
	public void testCorrectUsernamePassword(){
//		robotium.clickOnEditText(R.)
	}
}
