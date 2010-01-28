package miage.iloo.tp1.calendar.ical;

import miage.iloo.tp1.calendar.CalendarException;
import miage.iloo.tp1.calendar.CalendarProcessor;
import miage.iloo.tp1.calendar.CalendarService;

import com.google.inject.Inject;

public class ICalCalendarService implements CalendarService {
    
    private final CalendarProcessor processor;
    
    @Inject
    public ICalCalendarService(CalendarProcessor cp) {
	this.processor = cp;
    }

    public void get() throws CalendarException {
	
    }

    public void put() throws CalendarException {

    }

}
