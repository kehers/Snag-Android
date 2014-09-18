package co.snagapp.android.ui;

import co.snagapp.android.R;
import android.support.v7.app.ActionBarActivity;
//import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class HomeActivity extends ActionBarActivity {
	
	HomeFragment mHomeFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		mHomeFragment = new HomeFragment();
		getSupportActionBar().setTitle("Snagged");
	}

    @Override
    public void onResume(){
    	super.onResume();
    	// Execute pending fragments before checking isAdded
    	getSupportFragmentManager().executePendingTransactions();

    	if (!mHomeFragment.isAdded()) {
	        getSupportFragmentManager().beginTransaction()
	        	.add(R.id.content_frame, mHomeFragment).commit();
    	}
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.home, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		//Intent i;
		if (id == R.id.action_new) {
			//i = new Intent(this, PostActivity.class);
		}
		else if (id == R.id.action_feedback) {
			//i = new Intent(this, AccountActivity.class);
		}
		else if (id == R.id.action_about) {
			//i = new Intent(this, AccountActivity.class);
		}
		//startActivity(i);

		return super.onOptionsItemSelected(item);
	}
	
	public void onDestroy(){
		super.onDestroy();
	}

}