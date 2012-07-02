package com.skidson.services;

import java.util.ArrayList;
import java.util.List;

import com.skidson.json.JsonItem;
import com.skidson.json.JsonParser;

// TODO html encode styling for certain triggers (@, http:, etc)

public class SocialService {
	private static final String TWITTER_FEED = "http://api.twitter.com/1/statuses/user_timeline.json?screen_name=stephenkidson&count=5&trim_user=true";
	
	public static List<String> getTweets() {
		List<JsonItem> items = JsonParser.parse(TWITTER_FEED);
		List<String> tweets = new ArrayList<String>();
		for (JsonItem item : items) {
			try {
				StringBuilder tweet = new StringBuilder(item.getAsString("text"));
				String[] words = tweet.toString().split("\\s");
				tweet = new StringBuilder();
				for (String word : words) {
					if (word.startsWith("@"))
						word = "<i>" + word + "</i>";
					else if (word.startsWith("http://"))
						word = "<a href=\"" + word + "\">" + word + "</a>";
					tweet.append(word + " ");
				}
				tweets.add(tweet.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		for (String tweet : tweets)
			tweet = tweet.substring(0, tweet.length()-1);
		return tweets;	
	}
	
}
