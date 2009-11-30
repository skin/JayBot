package it.nands.jaybot.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;


/***
 * Classe di utilità per la gestione del gruppo di utenti
 * 
 * @author a.franzi
 *
 */
public class GroupUtils {
	private static Logger logger = Logger.getRootLogger();
	
	/***
	 * Metodo per recuperare la lista di entries appartenenti al Roster
	 * 
	 * @param connection	: connessione
	 * @return				: lista di RosterEntry
//	 */
//	public static List<RosterEntry> getRosterEntries (XMPPConnection connection){
//		List<RosterEntry> retList = new ArrayList<RosterEntry>();
//		
//		Roster roster = connection.getRoster();
//
//		Collection<RosterEntry> coll = roster.getEntries();
//		Iterator<RosterEntry> iter = coll.iterator();
//	    while (iter.hasNext()) {
//	        RosterEntry entry = iter.next();
//	        logger.debug(entry.getName() + " (" + entry.getUser() + ")");
//	        retList.add(entry);
//	    }
//		logger.debug("Ci sono : "+retList.size()+" nel Roster");
//		return retList;
//	}
		
}
