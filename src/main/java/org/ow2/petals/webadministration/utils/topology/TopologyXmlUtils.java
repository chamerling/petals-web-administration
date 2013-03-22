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

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;

import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.exceptions.AlienTopologyException;
import org.ow2.petals.webadministration.service.exceptions.InvalidTopologyException;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;
import org.ow2.petals.webadministration.utils.MiscellaneousUtils;
import org.ow2.petals.webadministration.utils.NetworkUtils;


/**
 * A set of utilities to parse topology.xml files.
 * <p>
 * The structure of the topology.xml file had remained quite stable
 * over the years 2009-2012. However, changes are expected from 2013
 * with the work on the cloudification of Petals ESB.
 * </p>
 * <p>
 * The topology.xml file may then contain various parameters,
 * maybe not always defined a XML schema. Using JAX-B may also lead
 * to a more complex code when several definitions of a XML type will exist.
 * This is already the case of <b>Petals Admin</b> classes and those generated
 * by JAX-B with one XML schema. Apart from the package name, the class name is the
 * same and may confuse the developer and result in bugs.
 * </p>
 *
 * @author Vincent Zurczak - Linagora
 */
public class TopologyXmlUtils {

	/**
	 * Builds a list of Petals nodes from a topology.xml file.
	 * <p>
	 * This methods selects the right parser to extract the information.
	 * </p>
	 *
	 * @param url the URL of the topology.xml file
	 * @return a non-null list of Petals nodes
	 * @throws AlienTopologyException if no parser could be found for this topology
	 * @throws InvalidTopologyException if the parsing failed
	 * @throws WebAdministrationException if another error occurred
	 */
	public static List<PetalsNode> buildPetalsNodesFromTopologyXml( String url )
	throws AlienTopologyException, InvalidTopologyException, WebAdministrationException {

		InputStream is = null;
		try {
			URI uri = NetworkUtils.urlToUri( url );
			is = uri.toURL().openStream();
			return buildPetalsNodesFromTopologyXml( is );

		} catch( MalformedURLException e ) {
			throw new WebAdministrationException( "The topology could not be parsed.", e );

		} catch( IOException e ) {
			throw new WebAdministrationException( "The topology could not be parsed.", e );

		} finally {
			if( is != null )
				MiscellaneousUtils.closeStreamQuietly( is );
		}
	}


	/**
	 * Builds a list of Petals nodes from a topology.xml file.
	 * <p>
	 * This methods selects the right parser to extract the information.
	 * </p>
	 * <p>
	 * The input stream is not closed by this method. It is up to the invoking method
	 * to perform this task.
	 * </p>
	 *
	 * @param is an input stream for the topology.xml file
	 * @return a non-null list of Petals nodes
	 * @throws AlienTopologyException if no parser could be found for this topology
	 * @throws InvalidTopologyException if the parsing failed
	 */
	public static List<PetalsNode> buildPetalsNodesFromTopologyXml( InputStream is )
	throws AlienTopologyException, InvalidTopologyException {

		List<PetalsNode> result = null;
		try {
			XMLInputFactory factory = XMLInputFactory.newInstance();
			XMLStreamReader reader = factory.createXMLStreamReader( is );
			while( reader.hasNext()) {
				if( reader.next() == XMLStreamReader.START_ELEMENT
						&& "topology".equalsIgnoreCase(  reader.getLocalName())
						&& TopologyXmlParser1.NAMESPACE.equalsIgnoreCase(  reader.getNamespaceURI())) {
					result = new TopologyXmlParser1().parseTopology( reader );
					break;
				}
			}

		} catch( Exception e ) {
			throw new InvalidTopologyException( "The topology could not be parsed.", e );
		}

		// Result is null <=> No parser could be found
		if( result == null )
			throw new AlienTopologyException( "No parser could be found for the given topology file." );

		return result;
	}
}
