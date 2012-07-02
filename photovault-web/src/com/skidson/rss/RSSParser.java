package com.skidson.rss;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

public class RSSParser extends DefaultHandler {
	private int fetchCount = 0;
	private String parsed = "";
	private List<Map<String, Object>> rssList = 
			new ArrayList<Map<String, Object>>(fetchCount);
	private Map<String, Object> rssItem;
	private Set<String> rssKeys;
	
	public RSSParser() {
		this.rssKeys = new HashSet<String>();
	}
	
	public RSSParser(Collection<String> rssKeys) {
		this.rssKeys = new HashSet<String>(rssKeys);
	}
	
	public synchronized List<Map<String, Object>> parse(String url) {
		XMLReader reader;
		try {
			reader = XMLReaderFactory.createXMLReader();
			reader.setContentHandler(this);
			reader.setErrorHandler(this);
			reader.parse(new InputSource(
					new InputStreamReader((
					new URL(url)).openStream())));
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return rssList;
	}
	
	// ******** XMLReader Event Handlers ********

	@Override
	public void characters(char[] in, int start, int length) 
			throws SAXException {
		for (int i = start; i < start+length; i++)
			parsed += in[i];
	}
	
	@Override
	public void startElement(String uri, String name, String tag,
			Attributes attributes) throws SAXException {
		if (fetchCount > 0 && rssList.size() >= fetchCount)
			throw new SAXException("Fetch count reached");
		if (tag.equals("item")) {
			if (rssItem != null && !rssItem.isEmpty())
				rssList.add(rssItem);
			rssItem = new HashMap<String, Object>();
		}
	}

	@Override
	public void endElement(String uri, String name, String tag)
			throws SAXException {
		if (rssKeys.contains(tag) && rssItem != null)
			rssItem.put(tag, parsed.trim());
		parsed = "";
	}
	
	@Override
	public void endDocument() throws SAXException {
		if (rssItem != null && !rssItem.isEmpty())
			rssList.add(rssItem);
		super.endDocument();
	}
	
	public void addField(String name) {
		rssKeys.add(name);
	}
	
	public void removeField(String name) {
		rssKeys.remove(name);
	}
	
	public void setFetchCount(int count) {
		this.fetchCount = count;
	}
	
}
