package com.skidson.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.skidson.rss.RSSParser;

@Controller
public class HomeController {
	private static final String RSS_FEED = "http://skidson.wordpress.com/feed/";
	private static final String TAB = "&nbsp;&nbsp;&nbsp;&nbsp;";
	private static final int DAYS_PER_YEAR = 365;
	private static final int MAX_FETCH_COUNT = 9;
	
	@RequestMapping("/home/index")
	public ModelAndView index() {
		Map<String, Object> model = new HashMap<String, Object>();
		
		RSSParser rss = new RSSParser(
				Arrays.asList("title", "pubDate", "content:encoded", "wfw:commentRss", "slash:comments"));
		rss.setFetchCount(MAX_FETCH_COUNT);
		List<Map<String, Object>> rssItems = rss.parse(RSS_FEED);
		for (int i = 0; i < rssItems.size(); i++) {
			Map<String, Object> rssItem = rssItems.get(i);
			
			// Custom format for code sections
			String value = ((String)rssItem.get("content:encoded"))
				.replace("<pre", "<code")
				.replace("</pre", "<code")
				.replace("\t", TAB);
			int end = value.lastIndexOf("</p>")+4;
//			String metadata = value.substring(end);
			value = value.substring(0, end);
			String[] content = value.split("<code>");
			value = content[0];
			for (int j = 1; j < content.length-1; j+=2) {
				value += ("<code>" + content[j] + "</code>")
					.replace("\n", "<br/>") + content[j+1];
			}
			
			rssItem.put("content", value);
			rssItem.remove("content:encoded");
			
			try {
				rssItem.put("pubDate", formatDate((String)rssItem.remove("pubDate")));
			} catch (ParseException e) { }
			
			RSSParser rssComments = new RSSParser(Arrays.asList("dc:creator", "pubDate", "content:encoded"));
			List<Map<String, Object>> comments = rssComments.parse((String)rssItem.remove("wfw:commentRss"));
			for (int j = 0; j < comments.size(); j++) {
				Map<String, Object> rssComment = comments.get(j);
				rssComment.put("content", rssComment.remove("content:encoded"));
				rssComment.put("author", rssComment.remove("dc:creator"));
				try {
					rssComment.put("pubDate", formatDate((String)rssComment.remove("pubDate")));
				} catch (ParseException e) { }
			}
			rssItem.put("comments", comments);
			rssItem.put("numComments", rssItem.remove("slash:comments"));
		}
		model.put("rssItems", rssItems);
		return new ModelAndView("home", model);
	}
	
	private String formatDate(String date) throws ParseException {
		DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss Z");
		Calendar pubDate = Calendar.getInstance(TimeZone.getTimeZone("GMT-7"));
		pubDate.setTime(formatter.parse(date));
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("GMT-7"));
		int ago = (today.get(Calendar.YEAR)-pubDate.get(Calendar.YEAR))*365 + 
				today.get(Calendar.DAY_OF_YEAR)-pubDate.get(Calendar.DAY_OF_YEAR);
		date = "";
		switch (ago) {
		case 0:
			date = "Today "; break;
		case 1:
			date = "Yesterday "; break;
		default:
			if (ago < 7) {
				switch(pubDate.get(Calendar.DAY_OF_WEEK)) {
				case Calendar.MONDAY:
					date = "Monday"; break;
				case Calendar.TUESDAY:
					date = "Tuesday"; break;
				case Calendar.WEDNESDAY:
					date = "Wednesday"; break;
				case Calendar.THURSDAY:
					date = "Thursday"; break;
				case Calendar.FRIDAY:
					date = "Friday"; break;
				case Calendar.SATURDAY:
					date = "Saturday"; break;
				case Calendar.SUNDAY:
					date = "Sunday"; break;
				}
			} else {
				switch(pubDate.get(Calendar.MONTH)) {
				case Calendar.JANUARY:
					date = "January "; break;
				case Calendar.FEBRUARY:
					date = "February "; break;
				case Calendar.MARCH:
					date = "March "; break;
				case Calendar.APRIL:
					date = "April "; break;
				case Calendar.MAY:
					date = "May "; break;
				case Calendar.JUNE:
					date = "June "; break;
				case Calendar.JULY:
					date = "July "; break;
				case Calendar.AUGUST:
					date = "August "; break;
				case Calendar.SEPTEMBER:
					date = "September "; break;
				case Calendar.OCTOBER:
					date = "October "; break;
				case Calendar.NOVEMBER:
					date = "November "; break;
				case Calendar.DECEMBER:
					date = "December "; break;
				}
				date += pubDate.get(Calendar.DAY_OF_MONTH);
			}
		}
		if (ago > DAYS_PER_YEAR)
			date += ", "+pubDate.get(Calendar.YEAR);
		else {
			int min = pubDate.get(Calendar.MINUTE);
			int hour = pubDate.get(Calendar.HOUR);
			date += " at " + (hour==0?12:hour)+":"+(min<10?"0"+min:min)+(pubDate.get(Calendar.AM_PM)==Calendar.AM?"am":"pm");
		}
		return date;
	}
	
}
