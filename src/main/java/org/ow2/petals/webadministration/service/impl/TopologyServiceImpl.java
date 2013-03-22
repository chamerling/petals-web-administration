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

package org.ow2.petals.webadministration.service.impl;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.admin.api.ContainerAdministration;
import org.ow2.petals.admin.api.exception.ContainerAdministrationException;
import org.ow2.petals.admin.jmx.JMXPetalsAdministrationFactory;
import org.ow2.petals.admin.topology.Container.PortType;
import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.PreferenceService;
import org.ow2.petals.webadministration.service.TopologyService;
import org.ow2.petals.webadministration.utils.NetworkUtils;
import org.ow2.petals.webadministration.utils.topology.TopologyXmlUtils;

/**
 * A JMX implementation of the topology service.
 * @see TopologyService
 *
 * @author Sebastien Levesque - Linagora
 * @author Vincent Zurczak - Linagora
 */
@Named
public class TopologyServiceImpl implements TopologyService {

	/**
	 * The service in charge of the preferences.
	 */
	@Inject
	private PreferenceService preferenceService;



	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.TopologyService
	 * #checkServerConnection(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkServerConnection( String host, int port, String user, String password ) {

		boolean success = false;
    	try {
    		JMXPetalsAdministrationFactory jmxfactory = new JMXPetalsAdministrationFactory();
			ContainerAdministration containerAdministration = jmxfactory.newContainerAdministration();
			containerAdministration.connect( host,port, user, password );
			containerAdministration.disconnect();
			success = true;

		} catch( ContainerAdministrationException e ) {
			// nothing
		}

    	return success;
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.TopologyService
	 * #checkTopologyFileConnection(java.lang.String)
	 */
	@Override
	public boolean checkTopologyFileConnection( String url ) {

		boolean success = false;
		try {
			List<PetalsNode> nodes = TopologyXmlUtils.buildPetalsNodesFromTopologyXml( url );
			success = nodes.size() > 0;

		} catch( Exception e ) {
			// nothing
		}

    	return success;
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.TopologyService
	 * #retrievePetalsNodes()
	 */
	@Override
	public List<PetalsNode> retrievePetalsNodes() {

		List<PetalsNode> result;
		if( this.preferenceService.get().isServerConnection())
			result = retrievePetalsNodesFromServer();
		else
			result = retrievePetalsNodesFromTopologyFile();

		return result;
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.TopologyService
	 * #findPetalsNode(java.lang.String)
	 */
	@Override
	public PetalsNode findPetalsNode( String nodeName ) {

		PetalsNode result = null;
		if( nodeName != null ) {
			for( PetalsNode node : retrievePetalsNodes()) {
				if( nodeName.equalsIgnoreCase( node.getContainerName())) {
					result = node;
					break;
				}
			}
		}

		return result;
	}


	/**
	 * Retrieves the Petals nodes from the Petals server given in the preferences.
	 * @return a non-null list of Petals nodes
	 * FIXME: for the moment, this implementation is not fully functional. The result only contain one node.
	 */
	private List<PetalsNode> retrievePetalsNodesFromServer() {

		List <PetalsNode> result = new ArrayList<PetalsNode> ();
		ContainerAdministration containerAdministration = null;
        try {
        	/*
        	PetalsAdministrationFactory jmxfactory = PetalsAdministrationFactory.newInstance();
        	containerAdministration = jmxfactory.newContainerAdministration();
            containerAdministration.connect(
            		this.preferenceService.get().getHost(),
            		this.preferenceService.get().getPort(),
            		this.preferenceService.get().getUser(),
            		this.preferenceService.get().getPassword());

            Domain domain = containerAdministration.getTopology( "passphrase to get in the preferences" );
            for( Subdomain subdomain : domain.getSubdomains()) {
                for( Container container : subdomain.getContainers()) {

                	boolean online = false;
					HashMap<PortType, Integer> ports = container.getPorts();
					for( PortType portType : ports.keySet()) {
						if( portType.equals( PortType.TCP_TRANSPORT )) {
							int givenport = ports.get(portType);
							URL petalsUrl = new URL( "http", container.getHost(), givenport, "" );
							online = NetworkUtils.pingServer( petalsUrl.toURI(), givenport, 1, 0 );
							break;
						}
					}

                	result.add( new PetalsNode(
                			container.getContainerName(),
                			container.getHost(),
                			container.getNodeType().equals( NodeType.MASTER ),
                			container.getPorts(),
                			online,
                			"petals",
                			"petals" ));
                }
            }
             */

        	// FIXME: this is a temporary workaround
        	// The API to get the full topology from a server has changed.
        	// We will have to wait for a new Petals kernel to be released to use the code above
			HashMap<PortType,Integer> allPorts = new HashMap<PortType,Integer> ();
			allPorts.put( PortType.TCP_TRANSPORT, this.preferenceService.get().getPort());

        	PetalsNode petalsNode = new PetalsNode(
        			"Default",
        			this.preferenceService.get().getHost(),
        			true,
        			false,
        			allPorts,
        			this.preferenceService.get().getUser(),
        			this.preferenceService.get().getPassword());

        	updateOnlineStatus( petalsNode );
        	if( petalsNode.isOnline())
        		result.add( petalsNode );


//        	result.add( new PetalsNode(
//        			"Providers 1",
//        			"192.168.1.7",
//        			true,
//        			true,
//        			allPorts,
//        			"",
//        			"" ));
//        	result.add( new PetalsNode(
//        			"Providers 2",
//        			"192.168.1.8",
//        			true,
//        			false,
//        			allPorts,
//        			"",
//        			"" ));
//        	result.add( new PetalsNode(
//        			"Front End 1",
//        			"192.168.1.4",
//        			true,
//        			true,
//        			allPorts,
//        			"",
//        			"" ));
//        	result.add( new PetalsNode(
//        			"Front End 2",
//        			"192.168.1.4",
//        			true,
//        			true,
//        			allPorts,
//        			"",
//        			"" ));
        	// End of workaround

        } catch( Exception e ) {
        	e.printStackTrace();

        } finally {
        	if( containerAdministration != null ) {
        		try {
					containerAdministration.disconnect();
				} catch( ContainerAdministrationException e ) {
					e.printStackTrace();
				}
        	}
        }

        return result;
	}


	/**
	 * Retrieves the Petals nodes from the topology file given in the preferences.
	 * @return a non-null list of Petals nodes
	 */
	private List<PetalsNode> retrievePetalsNodesFromTopologyFile() {

		List <PetalsNode> result = new ArrayList<PetalsNode> ();
		try {
			String url = this.preferenceService.get().getTopologyURL();
			result.addAll( TopologyXmlUtils.buildPetalsNodesFromTopologyXml( url ));
			for( PetalsNode node : result )
				updateOnlineStatus( node );

		} catch( Exception e ) {
			// nothing
		}

		return result;
	}


	/**
	 * Updates the <i>online</i> status of a Petals node.
	 * @param petalsNode a Petals node (not null)
	 */
	private void updateOnlineStatus( PetalsNode petalsNode ) {

		try {
			URL petalsUrl = new URL( "http", petalsNode.getHost(), petalsNode.getPorts().get( PortType.TCP_TRANSPORT ), "" );
			boolean online = NetworkUtils.pingServer( petalsUrl.toURI(), this.preferenceService.get().getPort(), 1, 0 );
			petalsNode.setOnline( online );

		} catch( MalformedURLException e ) {
			// FIXME: log

		} catch( URISyntaxException e ) {
			// FIXME: log
		}
	}
}
