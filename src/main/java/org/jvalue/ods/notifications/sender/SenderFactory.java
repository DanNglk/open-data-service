package org.jvalue.ods.notifications.sender;

import org.jvalue.ods.notifications.clients.GcmClient;
import org.jvalue.ods.notifications.clients.HttpClient;


public final class SenderFactory {

	private SenderFactory() { }


	private static GcmSender gcmSender;
	private static HttpSender restSender;


	public static Sender<GcmClient> getGcmSender() {
		if (gcmSender == null) gcmSender = new GcmSender("/googleApi.key");
		return gcmSender;
	}


	public static Sender<HttpClient> getRestSender() {
		if (restSender == null) restSender = new HttpSender();
		return restSender;
	}

}
