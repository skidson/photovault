package com.skidson;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.skidson.rss.RSSParser;

public class TestDriver {
	private static final String RSS_FEED = "http://skidson.wordpress.com/feed/";
	
	public static void main(String[] args) {
		RSSParser rss = new RSSParser(Arrays.asList("title", "pubDate", "content:encoded"));
		rss.setFetchCount(2);
		List<Map<String, Object>> rssItems = rss.parse(RSS_FEED);
		for (int i = 0; i < rssItems.size(); i++) {
			Iterator<Map.Entry<String, Object>> rssIterator = rssItems.get(i).entrySet().iterator();
			while (rssIterator.hasNext()) {
				Map.Entry<String, Object> entry = rssIterator.next();
				System.out.println(entry.getKey() + ": " + entry.getValue());
			}
		}
	}
}
