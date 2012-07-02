package com.skidson.json;

import java.io.IOException;
import java.util.List;

/**
 * Interface for a standard web parser. Implementations must implement the parse() method.
 * @author Stephen Kidson
 */
public interface Parser {
	/**
	 * Parses data from a URL associated with this parser.
	 * @return a list of FeedItems parsed from the URL.
	 * @throws IOException
	 */
	List<? extends FeedItem> parse() throws IOException;
}