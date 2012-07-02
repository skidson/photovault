package com.skidson.json;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.stream.JsonReader;

/**
 * An implementation of Parser designed to fetch JSON encoded data, as per <code>RFC4627</code>.
 * @author Stephen Kidson
 */
public class JsonParser extends BaseParser {
	
	/**
	 * Instantiates a JSON Parser for the indicated URL.
	 * @param feedUrl the URL from which to parse data.
	 */
	public JsonParser(String feedUrl) {
		super(feedUrl);
	}
	
	/**
	 * Instantiates a JSON Parser around the provided InputStream.
	 * @param stream the input stream to parse data from.
	 */
	public JsonParser(InputStream stream) {
		super(stream);
	}
	
	/** 
	 * Parses all items available at this parser's URL. Top level items will be stored in the
	 * returned list. <p><p> 
	 * Note: It may be necessary for some web services to parse the top level item as a JsonItem
	 * and further items as JsonItems from it.
	 * @return a List containing JSON items fetched from this parser's URL.
	 */
	public List<JsonItem> parse() {
		// Determines whether we are fetching an array or just one item and parses accordingly
		try {
			JsonReader reader = new JsonReader(new InputStreamReader(getInputStream(), "UTF-8"));
			List<JsonItem> items = null;
			String nextToken = reader.peek().toString();
			if (nextToken.equals("BEGIN_ARRAY"))
				items = parseArray(reader);
			else if (nextToken.equals("BEGIN_OBJECT")) {
				items = new ArrayList<JsonItem>();
				items.add(parseItem(reader));
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/** 
	 * Parses all items available from the specified URL. Top level items will be stored in the
	 * returned list. <p><p> 
	 * Note: It may be necessary for some web services to parse the top level item as a JsonItem
	 * and further items as JsonItems from it.
	 * @return a List containing JSON items fetched from this parser's URL.
	 */
	public static List<JsonItem> parse(String url) {
		// Determines whether we are fetching an array or just one item and parses accordingly
		try {
			JsonReader reader = new JsonReader(new InputStreamReader((new URL(url)).openStream(), "UTF-8"));
			List<JsonItem> items = null;
			String nextToken = reader.peek().toString();
			if (nextToken.equals("BEGIN_ARRAY"))
				items = parseArray(reader);
			else if (nextToken.equals("BEGIN_OBJECT")) {
				items = new ArrayList<JsonItem>();
				items.add(parseItem(reader));
			}
			return items;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Parses a JSON Array into a List of JsonItems.
	 * @param reader
	 * @return a List of JsonItems.
	 * @throws IOException
	 */
	private static List<JsonItem> parseArray(JsonReader reader) throws IOException {
		List<JsonItem> items = new ArrayList<JsonItem>();
		reader.beginArray();
		while(reader.hasNext())
			items.add(parseItem(reader));
		reader.endArray();
		return items;
	}
	
	/**
	 * Parses a single JsonItem.
	 * @param reader
	 * @return a JsonItem.
	 * @throws IOException
	 */
	private static JsonItem parseItem(JsonReader reader) throws IOException {
		JsonItem item = new JsonItem();
		String key;
		reader.beginObject();
		while(reader.hasNext()) {
			key = reader.nextName();
			String nextToken = reader.peek().toString();
			if (nextToken.equals("BEGIN_ARRAY"))
				item.addAttribute(key, parseArray(reader));
			else if (nextToken.equals("BEGIN_OBJECT"))
				item.addAttribute(key, parseItem(reader));
			else {
				String value;
				try {
					value = reader.nextString().trim();
				} catch (IllegalStateException e) {
					reader.skipValue();
					continue;
				}
				item.addAttribute(key, value);
			}
		}
		reader.endObject();
		return item;
	}
}