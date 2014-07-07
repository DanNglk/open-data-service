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
package org.jvalue.ods.data.sources;

import java.util.LinkedList;

import org.jvalue.ods.data.DataSource;
import org.jvalue.ods.data.OdsView;
import org.jvalue.ods.data.metadata.JacksonMetaData;
import org.jvalue.ods.data.objecttypes.MapObjectType;


public final class DummyDataSource extends DataSource {

	public DummyDataSource(
			String id, 
			String url) {

		super(
				id, 
				url,
				new MapObjectType("dummy", null, null), 
				new MapObjectType("dummy", null, null), 
				new JacksonMetaData("dummy", "dummy", "dummy", "dummy", "dummy", "dummy", "dummy"),
				new LinkedList<OdsView>());
	}

}