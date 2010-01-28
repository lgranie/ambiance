package miage.iloo.tp1.calendar.ical;

import java.io.File;

import miage.iloo.tp1.calendar.CalendarProcessor;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.data.CalendarOutputter;
import net.fortuna.ical4j.model.Calendar;

public class ICalCalendarProcessor implements CalendarProcessor {

    private Calendar calendar = null;

    private CalendarOutputter outputter;

    private CalendarBuilder builder;

    private File tmpFile;

    public ICalCalendarProcessor() {
	// Initialize tmp file into system.tmpdir
	//tmpFile = new File();

	// Initialize outputter
	outputter = new CalendarOutputter();

	// Initialize builder
	builder = new CalendarBuilder();
    }
}
