/**
 * Copyright (C) 2007 Laurent GRANIE.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General
 * Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301, USA.
 */
package org.ambiance.calendar;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Uid;

import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.util.FileUtils;

/**
 * @plexus.component role="org.ambiance.calendar.AmbianceCalendarUpdater" role-hint="fr-football-tv"
 */
public class TvFootUpdater extends AbstractLogEnabled implements AmbianceCalendarUpdater {

	public static Pattern DATE_PATTERN = Pattern.compile(".*<b>.*\\s(\\d{1,2}\\s+[^<]+)</b><ul>$");

	public static Pattern RENC_PATTERN = Pattern.compile("^<li><b>(.*)</b>$");

	public static Pattern HOR_PATTERN = Pattern.compile("(.*)\\s+&agrave;\\s+(\\d{2}h\\d{2}).*");

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	/**
	 * @plexus.requirement role="org.ambiance.calendar.ICalCalendar"
	 */
	private ICalCalendar calendar;

	public void update() throws CalendarException {
		calendar.retrieve();

		GregorianCalendar today = new GregorianCalendar(calendar.getTz(), Locale.FRENCH);
		today.setTimeZone(calendar.getTz());

		String journee = null;
		String heure = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH'h'mmdd MMMMyyyy", Locale.FRENCH);

		File fffFile = FileUtils.createTempFile("ambiance-calendar-updater-", ".html", null);
		try {
			transporter.get("http://www.fff.fr/actu/559.shtml", fffFile);
			BufferedReader br = new BufferedReader(new FileReader(fffFile));

			String line;
			String rencontre = null;
			String comment = new String();
			while ((line = br.readLine()) != null) {
				Matcher dateMatcher = DATE_PATTERN.matcher(line);
				if (dateMatcher.matches()) {
					journee = dateMatcher.group(1).replaceAll("&eacute;", "é");
					journee = journee.replaceAll("&ucirc;", "û");
					continue;
				}

				Matcher rencMatcher = RENC_PATTERN.matcher(line);
				if (rencMatcher.matches()) {
					rencontre = rencMatcher.group(1) + "\n";
					comment = new String();
					continue;
				}

				if (rencontre != null) {
					Matcher horMatcher = HOR_PATTERN.matcher(line);
					if (horMatcher.matches()) {
						comment += horMatcher.group(1);

						heure = horMatcher.group(2) + journee + today.get(Calendar.YEAR);
						DateTime dt = new DateTime(sdf.parse(heure));
						dt.setTimeZone(calendar.getTz());

						// If YEAR increase from december to january
						if (0 < today.getTime().compareTo(dt)) {
							today.add(Calendar.YEAR, 1);
							today.set(Calendar.DAY_OF_YEAR, 1);
							heure = horMatcher.group(2) + journee + today.get(Calendar.YEAR);
							dt = new DateTime(sdf.parse(heure));
						}

						VEvent event = new VEvent(dt, new Dur(0, 2, 0, 0), rencontre);
						event.getProperties().add(new Uid(rencontre.hashCode() + "@ambiance-calendar-tvfoot"));
						event.getProperties().add(new Description(comment));
						calendar.add(event);
						rencontre = null;
					} else {
						comment += line;
					}
				}
			}
			calendar.save();
			calendar.publish();
		} catch (TransporterException e) {
			getLogger().error("Enable to get file : " + e.getMessage());
		} catch (IOException e) {
			getLogger().error("Error while reading file : " + e.getMessage());
		} catch (ValidationException e) {
			getLogger().error("Error while validating calendar : " + e.getMessage());
		} catch (ParseException e) {
			getLogger().error("Error while parsing date from fff : " + e.getMessage());
		}
	}

}
