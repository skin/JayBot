package it.nands.jaybot.util;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * @author a.pellegatta
 *
 */
public class DataUtils {

	/**
	 * @return
	 */
	public static String getTimestamp(){
		Calendar data =  new GregorianCalendar();
		String timestamp = 	data.get(Calendar.YEAR)+"-"+(data.get(Calendar.MONTH)+1)+"-"+data.get(Calendar.DAY_OF_MONTH) + "-" +
							data.getTimeInMillis();
		return timestamp;

	}
}
