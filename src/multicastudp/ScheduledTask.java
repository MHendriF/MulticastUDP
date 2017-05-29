package multicastudp;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

    public class ScheduledTask extends TimerTask {

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	Date now; // to display current time

	// Add your task here
	public void run() {
            now = new Date(); // initialize date
            System.out.println("Time is :" + dateFormat.format(now)); // Display current time
	}
    }