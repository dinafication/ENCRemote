package hr.hackweek.encchecker;

import hr.hackweek.encchecker.fragments.HelpFrag;
import hr.hackweek.encchecker.fragments.PasswordFrag;
import hr.hackweek.encchecker.fragments.StateFrag;
import hr.hackweek.encchecker.fragments.StateFrag.OnAuthenticationExceptionListener;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableContainer;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends ActionBarActivity implements
		OnAuthenticationExceptionListener {

	private FragmentTabHost mTabHost;

	public static final String tab1 = "settings";
	public static final String tab2 = "state";
	public static final String tab3 = "help";

	public final static int sdk = android.os.Build.VERSION.SDK_INT;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createTabs();	
	}
	
	
	private void createTabs(){
		
		mTabHost = (FragmentTabHost) findViewById(R.id.fragmentViewGroup);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.tabFrameLayout);

		mTabHost.addTab(
				mTabHost.newTabSpec(tab1).setIndicator("Postavke",
						getResources().getDrawable(R.drawable.user_5_light)),
				PasswordFrag.class, new Bundle());
		mTabHost.addTab(
				mTabHost.newTabSpec(tab2).setIndicator("Stanje",
						getResources().getDrawable(R.drawable.money3_light)),
				StateFrag.class, null);
		mTabHost.addTab(
				mTabHost.newTabSpec(tab3).setIndicator(
						"Help",
						getResources().getDrawable(
								R.drawable.help_putokaz_2_light)),
				HelpFrag.class, null);

		final GradientDrawable gd_selected = new GradientDrawable(
				GradientDrawable.Orientation.TOP_BOTTOM, new int[] {
						getResources()
								.getInteger(R.integer.selected_tab_color1),
						getResources()
								.getInteger(R.integer.selected_tab_color2) });// 0188CC
																				// 0B2DD6
		gd_selected.setCornerRadius(0f);

		final Drawable gd = mTabHost.getTabWidget().getChildAt(1)
				.getBackground();
		final Drawable gd_unselected = ((DrawableContainer) gd).getCurrent();
		
		addDrawableToLay(mTabHost.getTabWidget().getChildAt(0), gd_selected);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			public void onTabChanged(String str) {

				View tab = mTabHost.getTabWidget().getChildAt(
						mTabHost.getCurrentTab());

				addDrawableToLay(tab, gd_selected);

				for (int i = 0; i <= mTabHost.getChildCount(); i++) {
					if (i == mTabHost.getCurrentTab())
						continue;

					tab = mTabHost.getTabWidget().getChildAt(i);
					addDrawableToLay(tab, gd_unselected);
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private void addDrawableToLay(View tab, Drawable d) {
		if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
			tab.setBackgroundDrawable(d);
		} else {
			tab.setBackground(d);
		}
	}

	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		Window window = getWindow();
		window.setFormat(PixelFormat.RGBA_8888);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		hideSoftKeyboard();
		return false;
	}

	public void hideSoftKeyboard() {

		if (getCurrentFocus() != null
				&& getCurrentFocus().getWindowToken() != null) {

			InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
			inputMethodManager.hideSoftInputFromWindow(getCurrentFocus()
					.getWindowToken(), 0);

		}
	}

	public void onAuthenticationException(String errorMessage) {
		Fragment f = getSupportFragmentManager().findFragmentByTag("settings");
		Bundle b = f.getArguments();
		b.putString(ApplicationConstants.ERROR_MESSAGE, errorMessage);

		mTabHost.setCurrentTabByTag(tab1);

	}

	public void setStateFrag() {
		mTabHost.setCurrentTabByTag(tab2);
	}
	
	

}
