/*  Open Data Service
    Copyright (C) 2013  Tsysin Konstantin, Reischl Patrick

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
    
 */
package org.jvalue.ods.server;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Set;

import org.jvalue.ods.grabber.DataGrabberMain;
import org.jvalue.ods.logger.Logging;
import org.jvalue.ods.server.restlet.DefaultRestlet;
import org.jvalue.ods.server.router.RouterFactory;
import org.restlet.Application;
import org.restlet.Restlet;
import org.restlet.routing.Router;

/**
 * The Class ContainerRestletApp.
 */
public class ContainerRestletApp extends Application implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.restlet.Application#createInboundRoot()
	 */
	@Override
	public Restlet createInboundRoot() {
		(new Thread(this)).start();

		org.jvalue.ods.server.router.Router<Restlet> odsRouter = RouterFactory
				.createOdsRouter();
		org.jvalue.ods.server.router.Router<Restlet> poRouter = RouterFactory
				.createPegelOnlineRouter();
		org.jvalue.ods.server.router.Router<Restlet> ppRouter = RouterFactory
				.createPegelPortalMvRouter();
		org.jvalue.ods.server.router.Router<Restlet> noRouter = RouterFactory
				.createNominatimRouter();
		org.jvalue.ods.server.router.Router<Restlet> ovRouter = RouterFactory
				.createOverpassRouter();
		org.jvalue.ods.server.router.Router<Restlet> osmRouter = RouterFactory
				.createOsmRouter();
		org.jvalue.ods.server.router.Router<Restlet> notificationRouter = RouterFactory
				.createNotificationRouter();
		org.jvalue.ods.server.router.Router<Restlet> routesRouter = RouterFactory
				.createRoutesRouter();
		org.jvalue.ods.server.router.Router<Restlet> administrationRouter = RouterFactory
				.createAdministrationRouter();

		HashMap<String, Restlet> combinedRouter = new LinkedHashMap<String, Restlet>();
		combinedRouter.putAll(odsRouter.getRoutes());
		combinedRouter.putAll(poRouter.getRoutes());
		combinedRouter.putAll(ppRouter.getRoutes());
		combinedRouter.putAll(noRouter.getRoutes());
		combinedRouter.putAll(ovRouter.getRoutes());
		combinedRouter.putAll(osmRouter.getRoutes());
		combinedRouter.putAll(notificationRouter.getRoutes());
		combinedRouter.putAll(routesRouter.getRoutes());
		combinedRouter.putAll(administrationRouter.getRoutes());

		// must be last router, generates api output
		org.jvalue.ods.server.router.Router<Restlet> apiRouter = RouterFactory
				.createApiRouter(combinedRouter);
		combinedRouter.putAll(apiRouter.getRoutes());

		if (combinedRouter.isEmpty()) {
			String errorMessage = "routes are empty";
			Logging.error(this.getClass(), errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		// Create a root router
		Router router = new Router(getContext());

		router.attachDefault(new DefaultRestlet());

		// Get Map in Set interface to get key and value
		Set<Entry<String, Restlet>> s = combinedRouter.entrySet();

		// Move next key and value of Map by iterator
		Iterator<Entry<String, Restlet>> it = s.iterator();

		while (it.hasNext()) {
			Entry<String, Restlet> m = it.next();
			router.attach(m.getKey(), m.getValue());
		}

		return router;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		while (true) {
			try {
				DataGrabberMain.initialize();
				DataGrabberMain.updateData();
			} catch (Exception ex) {
				Logging.error(DataGrabberMain.class, ex.getMessage());
				ex.printStackTrace();
			} finally {
				try {
					Thread.sleep(2000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
