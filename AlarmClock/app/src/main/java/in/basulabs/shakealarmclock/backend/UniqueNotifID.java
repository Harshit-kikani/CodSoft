package in.basulabs.shakealarmclock.backend;

import java.util.Date;

/**
 * A class to generate unique Notification IDs.
 */
public class UniqueNotifID {

	/**
	 * Get a unique notification ID.
	 * <p>
	 * <a href="https://stackoverflow.com/questions/12978184/android-get-unique-id-of-notification#comment51322954_28251192">Courtesy</a>
	 *
	 * @return A unique notification ID.
	 */
	public static int getID(){
		return (int)((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
	}

}
