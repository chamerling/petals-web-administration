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

import java.io.Serializable;

/**
 * A bean with the properties for the preferences.
 * @author Sebastien Levesque - Linagora
 */
public class ApplicationPreferences implements Serializable {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = -4929971564855815086L;

	public static final String CONNECTION_SERVER = "SERVER";
	public static final String CONNECTION_TOPOLOGY = "TOPOLOGY";

	private int port, maxPerPageArtifactList, maxPerPageServiceList;
	private String connection, host, user, password, topologyURL, locale;



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
	 * @return the port
	 */
	public int getPort() {
		return this.port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort( int port ) {
		this.port = port;
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
	 * @return the topologyURL
	 */
	public String getTopologyURL() {
		return this.topologyURL;
	}

	/**
	 * @param topologyURL the topologyURL to set
	 */
	public void setTopologyURL( String topologyURL ) {
		this.topologyURL = topologyURL;
	}

	/**
	 * @return the maxPerPageArtifactList
	 */
	public int getMaxPerPageArtifactList() {
		return this.maxPerPageArtifactList;
	}

	/**
	 * @param maxPerPageArtifactList the maxPerPageArtifactList to set
	 */
	public void setMaxPerPageArtifactList( int maxPerPageArtifactList ) {
		this.maxPerPageArtifactList = maxPerPageArtifactList;
	}

	/**
	 * @return the maxPerPageServiceList
	 */
	public int getMaxPerPageServiceList() {
		return this.maxPerPageServiceList;
	}

	/**
	 * @param maxPerPageServiceList the maxPerPageServiceList to set
	 */
	public void setMaxPerPageServiceList( int maxPerPageServiceList ) {
		this.maxPerPageServiceList = maxPerPageServiceList;
	}

	/**
	 * @return true if the topology is defined through a Petals server
	 */
	public boolean isServerConnection(){
		return CONNECTION_SERVER.equals( this.connection );
	}

	/**
	 * @return true if the topology is defined through a topology.xml file
	 */
	public boolean isTopologyConnection(){
		return CONNECTION_TOPOLOGY.equals( this.connection );
	}

	/**
	 * @param connection the connection to set
	 */
	public void setConnection( String connection ) {
		this.connection = connection;
	}

	/**
	 * @return the connection
	 */
	public String getConnection() {
		return this.connection != null ? this.connection : CONNECTION_SERVER;
	}

	/**
	 * @return the locale
	 */
	public String getLocale() {
		return this.locale != null ? this.locale : "fr";
	}

	/**
	 * @param locale the locale to set
	 */
	public void setLocale( String locale ) {
		this.locale = locale;
	}
}
