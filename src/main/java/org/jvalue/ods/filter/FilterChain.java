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
package org.jvalue.ods.filter;

import org.jvalue.ods.data.DataSource;
import org.jvalue.ods.utils.Assert;


public final class FilterChain<P,R> {

	public static <P,R> FilterChain<P,R> instance(Filter<P,R> filter) {
		return new FilterChain<P,R>(filter);
	}


	private final Filter<P,R> filter;
	private FilterChain<R,?> nextChain;


	private FilterChain(Filter<P,R> filter) {
		Assert.assertNotNull(filter);
		this.filter = filter;
	}


	public <T> FilterChain<R,T> setNextFilter(Filter<R,T> filter) {
		Assert.assertNotNull(filter);
		FilterChain<R,T> nextChain = new FilterChain<R,T>(filter);
		this.nextChain = nextChain;
		return nextChain;
	}


	public void filter(DataSource source, P param) {
		Assert.assertNotNull(source);
		R ret = filter.filter(source, param);
		if (nextChain != null) nextChain.filter(source, ret);
	}


}