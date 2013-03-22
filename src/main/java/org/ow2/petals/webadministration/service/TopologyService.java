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

import java.util.List;

import org.ow2.petals.webadministration.pojo.PetalsNode;

/**
 * The topology service API.
 * @author Sebastien Levesque - Linagora
 * @author Vincent Zurczak - Linagora
 */
public interface TopologyService {

	/**
	 * Retrieves all the Petals node by using the way selected in the preferences.
	 * @return a non-null list of Petals nodes
	 */
	public List<PetalsNode> retrievePetalsNodes();

	/**
	 * Checks the JMX connection with a Petals server.
	 * @param host the host (not null)
	 * @param port the port (not null)
	 * @param user the user name (not null)
	 * @param password the password (not null)
	 * @return true if the connection was successfully established, false otherwise
	 */
	public boolean checkServerConnection( String host, int port, String user,String password );

	/**
	 * Checks that the given topology.xml file is accessible.
	 * @param url the URL of the topology.xml file (not null)
	 * @return true if the connection was successfully established, false otherwise
	 */
	public boolean checkTopologyFileConnection( String url );

	/**
	 * Finds a Petals server by its name in the topology.
	 * @param nodeName the node name (not null)
	 * @return a Petals node (may be null)
	 */
	public PetalsNode findPetalsNode( String nodeName );
}