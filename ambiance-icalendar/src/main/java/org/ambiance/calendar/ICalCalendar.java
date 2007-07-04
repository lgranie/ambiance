package org.ambiance.calendar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.Property;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.ValidationException;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Version;

import org.ambiance.transport.Transporter;
import org.ambiance.transport.TransporterException;
import org.codehaus.plexus.logging.AbstractLogEnabled;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.Initializable;
import org.codehaus.plexus.personality.plexus.lifecycle.phase.InitializationException;
import org.codehaus.plexus.util.FileUtils;

/**
 * @plexus.component role="org.ambiance.calendar.AmbianceCalendar" role-hint="ical"
 */
public class ICalCalendar extends AbstractLogEnabled implements AmbianceCalendar, Initializable {

	/**
	 * @plexus.requirement role="org.ambiance.transport.Transporter" role-hint="default"
	 */
	private Transporter transporter;

	private Calendar calendar = null;

	private CalendarOutputter outputter;

	private CalendarBuilder builder;

	private File tmpFile;

	/**
	 * @plexus.configuration default-value="the id of your calendar"
	 */
	private String id;

	/**
	 * @plexus.configuration default-value="the timezone of your calendar"
	 */
	private String timezone;

	private TimeZone tz;

	/**
	 * @plexus.configuration default-value="http://your-ical-url"
	 */
	private String inputUrl;

	/**
	 * @plexus.configuration default-value="ftp://your-ical-url"
	 */
	private String outputUrl;

	public void initialize() throws InitializationException {
		// Initialize tmp file into system.tmpdir
		tmpFile = FileUtils.createTempFile("ambiance-calendar-", ".ics", null);

		// Initialize outputter
		outputter = new CalendarOutputter();

		// Initialize builder
		builder = new CalendarBuilder();

		// Initialize TimeZone
		tz = TimeZoneRegistryFactory.getInstance().createRegistry().getTimeZone(timezone);
	}

	public void retrieve() throws CalendarException {
		try {
			// Get calendar from input-url
			InputStream is = transporter.getAsStream(inputUrl);

			// Build calendar in memory
			calendar = builder.build(is);
		} catch (Exception e) {
			getLogger().info("Enable to get calendar from input-url");
			// If file not exist on input-url create an empty calendar
			calendar = new Calendar();
			calendar.getProperties().add(new ProdId("-//ambiance-calendar//ical 1.0-SNAPSHOT//EN"));
			calendar.getProperties().add(Version.VERSION_2_0);
			calendar.getProperties().add(CalScale.GREGORIAN);
		}
	}

	public void publish() throws CalendarException {
		try {
			transporter.put(tmpFile, outputUrl);
			getLogger().info("Calendar published");
		} catch (TransporterException e) {
			getLogger().error("Enable to publish calendar");
		}
	}

	public void save() throws FileNotFoundException, IOException, ValidationException {
		outputter.output(calendar, new FileOutputStream(tmpFile));
	}

	/**
	 * Add a VEvent instance to the iCalendar if no other VEvent has the same UID.
	 * 
	 * @param event
	 *            The VEvent to add
	 * @throws CalendarException
	 */
	public void add(VEvent event) throws CalendarException {
		boolean present = false;
		for (Iterator i = calendar.getComponents().iterator(); i.hasNext();) {
			Component component = (Component) i.next();
			if (component.getProperties().getProperty(Property.UID).equals(
					event.getProperties().getProperty(Property.UID))) {
				present = true;
				break;
			}
		}

		if (!present)
			calendar.getComponents().add(event);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TimeZone getTz() {
		return tz;
	}

	public void setTz(TimeZone tz) {
		this.tz = tz;
	}
}
