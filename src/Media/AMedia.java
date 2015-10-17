package Media;


/**
 *
 * @author Alex
 * Represents different media types.
 */
public abstract class AMedia {
	public static MediaType name;
	private static int duration;
	public static int getDuration() {
		return duration;
	}
	public static void setDuration(int duration) {
		AMedia.duration = duration;
	}
	
}
