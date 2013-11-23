package jp.co.aclox.android.treasuresearch;

public class YahooMapApiException extends Exception {

	/**
	 *
	 */
	private static final long serialVersionUID = -5494380500716291161L;

	public YahooMapApiException() {
		super("Yahoo Map API error: ");
	}

	public YahooMapApiException(String detailMessage) {
		super("Yahoo Map API error: " + detailMessage);
	}

	public YahooMapApiException(Throwable throwable) {
		super(throwable);

	}

	public YahooMapApiException(String detailMessage, Throwable throwable) {
		super("Yahoo Map API error: " + detailMessage, throwable);
	}

}
