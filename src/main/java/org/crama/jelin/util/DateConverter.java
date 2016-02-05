package org.crama.jelin.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateConverter {
	
	public static Date toDate(LocalDateTime localDate) { 
		Date date = Date.from(localDate.atZone(ZoneId.systemDefault()).toInstant());
		return date;
	}
	
	public static LocalDateTime toLocalDateTime(Date date) {
		LocalDateTime ldt = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
		return ldt;
	}

	public static LocalDate toLocalDate(Date date) {
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate;
	}
}
