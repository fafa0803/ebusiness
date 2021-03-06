package ebusiness.myapp;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.parse.ParseUser;

import java.util.Locale;

import ebusiness.myapp.Facebook.UserDetailsActivity;
import ebusiness.myapp.GoogleMaps.MapActivity;
import ebusiness.myapp.LoginReg.LoginActivity;
import ebusiness.myapp.NewsFeed.NewsFeedFragment;
import ebusiness.myapp.NewsFeed.UpdateStatusActivity;
import ebusiness.myapp.PlacesPackage.AddPlaceActivity;
import ebusiness.myapp.Rating.PlaceListFragment;
import ebusiness.myapp.Profil.ProfilDatenActivity;
import ebusiness.myapp.Util.StaticKlasse;

public class MainActivity extends Activity implements ActionBar.TabListener, NewsFeedFragment.OnNewsFeedInteractionListener, PlaceListFragment.OnPlaceListInteractionListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v13.app.FragmentStatePagerAdapter}.
     */
    SectionsPagerAdapter mSectionsPagerAdapter;
    public static final String TAG = "SightSee";
    private static long back_pressed;


    /**
     * The {@link ViewPager} that will host the section contents.
     */
    ViewPager mViewPager;

    //überschreibt Back-Button
    //TODO bei Doubleclick logout siehe Loginactivity
    @Override
    public void onBackPressed() {
        //  if (back_pressed + 2000 > System.currentTimeMillis()) super.onBackPressed();
        //  else Toast.makeText(getBaseContext(), "Nocheinmal um App zu schließen!", Toast.LENGTH_SHORT).show();
        //  logout User
        //  ParseUser.logOut();
        //  MainActivity.status = 0;
        //  take User Back to the login screen
        //  Intent takeUsertoLogin = new Intent(MainActivity.this,LoginActivity.class);
        //  startActivity(takeUsertoLogin);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            // do stuff with the user
        } else {
            // show the login screen
            Intent takeUserToLogin = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(takeUserToLogin);
        }


        // Set up the action bar.
        final ActionBar actionBar = getActionBar();
        assert actionBar != null;
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });

        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by
            // the adapter. Also specify this Activity object, which implements
            // the TabListener interface, as the callback (listener) for when
            // this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(mSectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (StaticKlasse.status == 0) {
            getMenuInflater().inflate(R.menu.allgemein, menu);
        }
        if (StaticKlasse.status == 1) {
            getMenuInflater().inflate(R.menu.facebook, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.updateStatus:
                //takeUser to Update Status Activity
                Intent intent = new Intent(MainActivity.this, UpdateStatusActivity.class);
                startActivity(intent);
                break;
            case R.id.profil:
                //takeUser to Profil Activity
                Intent takeUsertoProfil = new Intent(MainActivity.this, ProfilDatenActivity.class);
                startActivity(takeUsertoProfil);
                break;
            case R.id.action_status_update:
                Intent takeStatusUp = new Intent(MainActivity.this, UpdateStatusActivity.class);
                startActivity(takeStatusUp);
                break;
            case R.id.AddPlace:
                Intent place = new Intent(MainActivity.this, AddPlaceActivity.class);
                startActivity(place);
                break;
            case R.id.Home:
                break;
            case R.id.action_map:
                Intent map = new Intent(MainActivity.this, MapActivity.class);
                startActivity(map);
                break;
            case R.id.action_fb_profil:
                Intent fb = new Intent(MainActivity.this, UserDetailsActivity.class);
                startActivity(fb);
                break;
            case R.id.action_settings:

                break;
            case R.id.logoutUser:
                //logout User
                ParseUser.logOut();
                StaticKlasse.status = 0;
                //take User Back to the login screen
                Intent takeUsertoLogin = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(takeUsertoLogin);
                break;
        }

        return super.onOptionsItemSelected(item);

    }


    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
        // When the given tab is selected, switch to the corresponding page in
        // the ViewPager.
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction fragmentTransaction) {
    }

    @Override
    public void onPlaceListInteraction(String id) {

    }

    @Override
    public void onNewsFeedInteraction(String id) {

    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).

            switch (position) {
                case 0:
                    return PlaceListFragment.newInstance("a", "b");//return new ListOfPlacesFragment();
                case 1:
                     return NewsFeedFragment.newInstance("c", "d"); //return NewsFragment.newInstance("a","b");
                default:
                   // return PlaceholderFragment.newInstance(position + 1);
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            Locale l = Locale.getDefault();
            switch (position) {
                case 0:
                    return getString(R.string.title_section1).toUpperCase(l);
                case 1:
                    return getString(R.string.title_section2).toUpperCase(l);
            }
            return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }

    }


}