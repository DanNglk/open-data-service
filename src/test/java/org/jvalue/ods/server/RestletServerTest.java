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

import java.net.BindException;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.jvalue.ods.db.DbFactory;
import org.jvalue.ods.main.RouterFactory;
import org.jvalue.ods.server.pegelonline.PegelOnlineRouter;
import org.restlet.Restlet;

/**
 * The Class RestletServerTest.
 */
@RunWith(value = Parameterized.class)
public class RestletServerTest {

	/**
	 * Restlet adapter with null routes.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void RestletServerWithNullRoutes() throws Exception {
		new RestletServer(null, 8182);
	}

	/**
	 * Restlet adapter with empty routes.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void RestletServerWithEmptyRoutes() throws Exception {
		new RestletServer(new HashMap<String, Restlet>(), 8182);
	}

	/** The port. */
	private int port;

	/**
	 * Instantiates a new restlet adapter test.
	 * 
	 * @param port
	 *            the port
	 */
	public RestletServerTest(int port) {
		this.port = port;
	}

	/**
	 * Data.
	 * 
	 * @return the collection
	 */
	@Parameters
	public static Collection<Object[]> data() {
		Object[][] data = new Object[][] { { -50 }, { 0 }, { 1000 }, { 1023 },
				{ 49152 }, { 50000 } };
		return Arrays.asList(data);
	}

	/**
	 * Restlet adapter with illegal ports.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = IllegalArgumentException.class)
	public void RestletServerWithIllegalPorts() throws Exception {

		PegelOnlineRouter poRouter = RouterFactory.createPegelOnlineRouter();
		poRouter.setDbAccessor(DbFactory.createMockDbAccessor("DbAccessorTest"));

		HashMap<String, Restlet> combinedRouter = new HashMap<String, Restlet>();
		combinedRouter.putAll(poRouter.getRoutes());

		new RestletServer(combinedRouter, port);
	}

	/**
	 * Test initialize.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testInitialize() throws Exception {

		PegelOnlineRouter poRouter = RouterFactory.createPegelOnlineRouter();
		poRouter.setDbAccessor(DbFactory.createMockDbAccessor("DbAccessorTest"));

		HashMap<String, Restlet> combinedRouter = new HashMap<String, Restlet>();
		combinedRouter.putAll(poRouter.getRoutes());

		RestletServer ra = new RestletServer(combinedRouter, 8800);
		ra.initialize();
		ra.disconnect();

	}

	/**
	 * Test double initialize.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test(expected = BindException.class)
	public void testDoubleInitialize() throws Exception {

		PegelOnlineRouter poRouter = RouterFactory.createPegelOnlineRouter();
		poRouter.setDbAccessor(DbFactory.createMockDbAccessor("DbAccessorTest"));

		HashMap<String, Restlet> combinedRouter = new HashMap<String, Restlet>();
		combinedRouter.putAll(poRouter.getRoutes());

		new RestletServer(combinedRouter, 8900).initialize();
		new RestletServer(combinedRouter, 8900).initialize();
	}

	/**
	 * Test stop without init.
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testStopWithoutInit() throws Exception {

		PegelOnlineRouter poRouter = RouterFactory.createPegelOnlineRouter();
		poRouter.setDbAccessor(DbFactory.createMockDbAccessor("DbAccessorTest"));

		HashMap<String, Restlet> combinedRouter = new HashMap<String, Restlet>();
		combinedRouter.putAll(poRouter.getRoutes());

		RestletServer ra = new RestletServer(combinedRouter, 8900);
		ra.disconnect();

	}

}
