package com.skidson.services;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;

public final class PersistenceService {
	private static final PersistenceManagerFactory pmf = JDOHelper.getPersistenceManagerFactory("transactions-optional");
	
	private PersistenceService() {}
	
	public static PersistenceManagerFactory getFactory() {
		return pmf;
	}
	
	public static void cleanup(PersistenceManager pm, Query query) {
		query.closeAll();
		pm.close();
	}
}
