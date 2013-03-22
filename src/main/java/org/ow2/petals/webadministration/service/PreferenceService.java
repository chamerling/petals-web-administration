/****************************************************************************
 *
 * Copyright (c) 2012, Linagora
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 *
 *****************************************************************************/

package org.ow2.petals.webadministration.service;

import java.util.prefs.BackingStoreException;

import org.ow2.petals.webadministration.pojo.ApplicationPreferences;

/**
 * The preferences service API.
 * @author Sebastien Levesque - Linagora
 */
public interface PreferenceService {

	/**
	 * Gets the application preferences.
	 * @return the application preferences
	 */
	public ApplicationPreferences get();

	/**
	 * Saves the application preferences.
	 * @param prefs the preferences to save
	 * @throws BackingStoreException if the save operation failed
	 */
	public void save( ApplicationPreferences prefs ) throws BackingStoreException;

	/**
	 * Checks a JMX connection.
	 * @param host the host
	 * @param port the port
	 * @param user the user
	 * @param password the password
	 * @return true if the connection was successfully tested, false otherwise
	 */
	boolean checkServerJMXConnection( String host, int port, String user, String password );

	/**
	 * Checks the existence of a topology file at a given location.
	 * @param topologyURL the URL of the topology.xml file
	 * @return true if the connection was successfully tested, false otherwise
	 */
	public boolean checkFileTopologyConnection( String topologyURL );
}