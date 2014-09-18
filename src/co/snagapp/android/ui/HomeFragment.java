package co.snagapp.android.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import co.snagapp.android.Constants;
import co.snagapp.android.R;
import co.snagapp.android.Classifier;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

public class HomeFragment extends ListFragment {
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);

    	// Add adapter
		Classifier snag = new Classifier(getActivity());
		//snag.isSpam("Body: Girl : Are u sure??u love me??and no one else? Boy: Dead sure,I have checked the whole list again yesterday.To Get Jokes FREE for 5 Days,send JOKES to 38500");
		
		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
    			getActivity(), R.layout.view_item, snag.getLikelySpamMessages(), 
    			new String []{Constants.KEY_FROM, Constants.KEY_BODY, Constants.KEY_DATE}, 
    			new int[]{R.id.sms_from, R.id.sms_body, R.id.sms_date},
    			SimpleCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    	adapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
    		
			@Override
			public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
				if (columnIndex == cursor.getColumnIndex(Constants.KEY_DATE)){
					
					String date = cursor.getString(columnIndex);
					TextView tv = (TextView) view;
                    tv.setText(relativeDate(date));
                    
					return true;
				}
				
				return false;
			}
		});
    	
        setListAdapter(adapter);
	}
	
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
        Bundle savedInstanceState) {
        if (container == null) {
            return null;
        }
        
        return inflater.inflate(R.layout.home_fragment, container, false);
    }
    
    @Override
    public void onResume() {
    	super.onResume();    	
    }
	
	public String relativeDate(String date){
        Calendar calendar = Calendar.getInstance();
        long deviceTime = calendar.getTimeInMillis();
        
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
		Date d = new Date();
		try {
			d = dateFormat.parse(date);
		} catch (ParseException e) {
			Log.e("DEBUG", "Parse error "+e.getMessage());
			return "";
		}
		
		long serverTime = d.getTime() + calendar.getTimeZone().getOffset(deviceTime);
        CharSequence relativeDate = DateUtils.getRelativeTimeSpanString(
        								serverTime, deviceTime, 0, DateUtils.FORMAT_ABBREV_RELATIVE);
        
        return relativeDate.toString();
	}
    
    @Override 
    public void onListItemClick(ListView l, View v, int position, long id) {
    	/*HashMap<String, String> post = mAdapter.getItem(position);

		Intent i = new Intent(getActivity(), PostActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("body", post.get("body"));
		bundle.putString("title", post.get("title"));
		bundle.putString("draft", post.get("draft"));
		bundle.putString("sha", post.get("sha"));
		bundle.putString("path", post.get("path"));
		bundle.putInt("position", position);
		
		i.putExtras(bundle);
		startActivity(i);//*/
    }

}
