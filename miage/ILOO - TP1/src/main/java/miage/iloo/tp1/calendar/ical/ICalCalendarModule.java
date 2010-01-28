package miage.iloo.tp1.calendar.ical;

import miage.iloo.tp1.calendar.CalendarProcessor;

import com.google.inject.AbstractModule;

public class ICalCalendarModule extends AbstractModule {

    @Override
    protected void configure() {
	bind(CalendarProcessor.class).to(ICalCalendarProcessor.class);

    }

}
