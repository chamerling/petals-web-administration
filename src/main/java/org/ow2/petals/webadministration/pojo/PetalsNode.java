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

package org.ow2.petals.webadministration.pojo;

import java.util.HashMap;

import org.ow2.petals.admin.topology.Container.PortType;

/**
 * A bean with the properties of a Petals node.
 * @author Sebastien Levesque - Linagora
 */
public class PetalsNode {

	private String containerName;
	private String host;
	private boolean master;
	private boolean online;
	private HashMap<PortType,Integer> ports;
	private String user;
	private String password;
	private String description;


	/**
	 * Constructor.
	 */
	public PetalsNode() {
		// nothing
	}

	/**
	 * Constructor.
	 * @param containerName
	 * @param host
	 * @param master
	 * @param online
	 * @param ports
	 * @param user
	 * @param password
	 */
	public PetalsNode(
			String containerName,
			String host,
			boolean master,
			boolean online,
			HashMap<PortType,Integer> ports,
			String user,
			String password ) {

		this.containerName = containerName;
		this.host = host;
		this.master = master;
		this.online = online;
		this.ports = ports;
		this.user = user;
		this.password = password;
	}

	/**
	 * @return the containerName
	 */
	public String getContainerName() {
		return this.containerName;
	}

	/**
	 * @param containerName the containerName to set
	 */
	public void setContainerName( String containerName ) {
		this.containerName = containerName;
	}

	/**
	 * @return the host
	 */
	public String getHost() {
		return this.host;
	}

	/**
	 * @param host the host to set
	 */
	public void setHost( String host ) {
		this.host = host;
	}

	/**
	 * @return the master
	 */
	public boolean isMaster() {
		return this.master;
	}

	/**
	 * @param master the master to set
	 */
	public void setMaster( boolean master ) {
		this.master = master;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return this.online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline( boolean online ) {
		this.online = online;
	}

	/**
	 * @return the ports
	 */
	public HashMap<PortType, Integer> getPorts() {
		return this.ports;
	}

	/**
	 * @param ports the ports to set
	 */
	public void setPorts( HashMap<PortType, Integer> ports ) {
		this.ports = ports;
	}

	/**
	 * @return the user
	 */
	public String getUser() {
		return this.user;
	}

	/**
	 * @param user the user to set
	 */
	public void setUser( String user ) {
		this.user = user;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return this.password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword( String password ) {
		this.password = password;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription( String description ) {
		this.description = description;
	}
}
