package co.snagapp.android;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import android.content.Context;
import android.database.Cursor;
import android.text.TextUtils;

public class Classifier {

	private static final int STRENGTH = 1;
	private static final int HAM = 0;
	private static final int SPAM = 1;
	private static final double PrC = 0.5;
	Context context;
	DBHelper mDb;
	
	public Classifier(Context context){
		this.context = context;
		mDb = new DBHelper(context);
	}

	public boolean isSpam(String sms) {
				
        float [] categoryWordProbability = {1, 1};
        String [] words = splitWord(sms);
		int[] allWords = {mDb.allWordCount(HAM), mDb.allWordCount(SPAM)};
        
        for(int c=0;c<words.length;c++) {
        	if (words[c].length() < 3)
        		continue;
        	
			for (int i=0;i<2;i++) {
		        // Get count of all words in categories
				int allWordsInCategory = allWords[i];
		        // Get occurence of this word in category
				int wordInCategory = mDb.thisWordCount(words[c], i);
		        // Get occurence of this word both categories
		        int n = mDb.wordCount(words[c]);

		        // Corrected probability
		        float probability = (float) wordInCategory/allWordsInCategory;
		        categoryWordProbability[i] *= (float) (STRENGTH*PrC + n*probability)/(STRENGTH + n);
			}
			
        }
        
        return (categoryWordProbability[SPAM] > categoryWordProbability[HAM]) ?
                    true : false;
	}
	
	public Cursor getLikelySpamMessages(){
		return mDb.getLikelySpamMessages();
	}
	
	public void addSpamSMS(String from, String sms){
		mDb.addSpamSMS(from, sms);
	}
	
	public void removeSpamSMS(int id){
		// Remove words from spam
		// Add to ham
	}
	
	public void train(String sender, String sms){
		// Train
	}
    
    public String[] splitWord(String sms) {
        // Split words 
        String [] words = TextUtils.split(sms, "\\W+");
        // unique
        Set<String> temp = new HashSet<String>(Arrays.asList(words));
        String[] unique = temp.toArray(new String[temp.size()]);
		
		return unique;
    }

}
