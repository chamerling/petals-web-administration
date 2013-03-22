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

package org.ow2.petals.webadministration.utils.topology;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.ow2.petals.admin.topology.Container.PortType;
import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.exceptions.InvalidTopologyException;

/**
 * A parser for the topologies used in Petals 3.x, 4.0 and 4.1.
 * <p>
 * This implementation only supports the <i>master-slave</i> mode.
 * </p>
 *
 * @author Vincent Zurczak - Linagora
 */
public class TopologyXmlParser1 implements TopologyXmlParser {

	public static final String NAMESPACE = "http://petals.ow2.org/topology";

	private static final String CONTAINER = "container";
	private static final String HOST = "host";
	private static final String USER = "user";
	private static final String PASSWORD = "password";
	private static final String DESCRIPTION = "description";
	private static final String PORT_WS = "webservice-service";
	private static final String PORT_JMX = "jmx-service";
	private static final String PORT_TRANSPORT = "transport-service";
	private static final String PORT_REGISTRY = "registry-service";
	private static final String NAME = "name";
	private static final String TYPE = "type";
	private static final String MASTER = "master";


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.utils.topology.TopologyXmlParser
	 * #parseTopology(javax.xml.stream.XMLStreamReader)
	 */
	@Override
	public List<PetalsNode> parseTopology( XMLStreamReader reader )
	throws InvalidTopologyException {

		List<PetalsNode> result = new ArrayList<PetalsNode> ();
		try {
			String currentStartElement = null;
			PortType currentPort = null;

			PetalsNode currentNode = null;
			HashMap<PortType,Integer> ports = null;
			while( reader.hasNext()) {
				int currentEvent = reader.next();

				// New element
				if( currentEvent == XMLStreamReader.START_ELEMENT ) {
					currentStartElement = reader.getLocalName();
					if( ! NAMESPACE.equalsIgnoreCase( reader.getNamespaceURI()))
						continue;

					// The container itself
					if( CONTAINER.equalsIgnoreCase( currentStartElement )) {
						currentNode = new PetalsNode();
						ports = new HashMap<PortType,Integer> ();

						String name = reader.getAttributeValue( null, NAME );
						currentNode.setContainerName( name );
						String type = reader.getAttributeValue( null, TYPE );
						currentNode.setMaster( MASTER.equalsIgnoreCase( type ));
					}

					// Other possibilities are properties of the current node
					if( currentNode == null )
						continue;

					// Deal with complex types
					if( PORT_WS.equalsIgnoreCase( currentStartElement ))
						currentPort = PortType.HTTP_WEBSERVICE;
					else if( PORT_JMX.equalsIgnoreCase( currentStartElement ))
						currentPort = PortType.JMX;
					else if( PORT_TRANSPORT.equalsIgnoreCase( currentStartElement ))
						currentPort = PortType.TCP_TRANSPORT;
					else if( PORT_REGISTRY.equalsIgnoreCase( currentStartElement ))
						currentPort = PortType.REGISTRY;

					// Deal with simple types
					else {
						if( HOST.equalsIgnoreCase( currentStartElement ))
							currentNode.setHost( getFormattedValue( reader ));
						else if( DESCRIPTION.equalsIgnoreCase( currentStartElement ))
							currentNode.setDescription( getFormattedValue( reader ));
						else if( USER.equalsIgnoreCase( currentStartElement ))
							currentNode.setUser( getFormattedValue( reader ));
						else if( PASSWORD.equalsIgnoreCase( currentStartElement ))
							currentNode.setPassword( getFormattedValue( reader ));
					}

					// The port is always the first sub-element
					if( currentPort != null
							&& reader.hasNext()) {

						if( reader.nextTag() == XMLStreamReader.START_ELEMENT ) {
							String value = getFormattedValue( reader );
							int port = value != null ? Integer.parseInt( value.trim()) : -1;
							ports.put( currentPort, port );
						}

						currentPort = null;
					}
				}

				// End element
				else if( currentEvent == XMLStreamReader.END_ELEMENT ) {
					if( ! NAMESPACE.equalsIgnoreCase( reader.getNamespaceURI())
							|| ! CONTAINER.equalsIgnoreCase( reader.getLocalName()))
						continue;

					if( currentNode != null ) {
						currentNode.setPorts( ports );
						result.add( currentNode );
					}
				}
			}

		} catch( XMLStreamException e ) {
			throw new InvalidTopologyException( e );
		}

		return result;
	}


	/**
	 * Gets the formatted value for an element.
	 * @param reader the XML reader
	 * @return a formatted value (may be null)
	 */
	private String getFormattedValue( XMLStreamReader reader ) {

		String value = null;
		try {
			value = reader.getElementText();
			value = value == null ? null : value.trim();
		} catch( XMLStreamException e ) {
			// Ignore elements with children
		}

		return value;
	}
}
