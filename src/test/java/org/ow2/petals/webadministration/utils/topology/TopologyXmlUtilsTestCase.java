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

import static org.junit.Assert.fail;

import java.io.InputStream;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;
import org.ow2.petals.admin.topology.Container.PortType;
import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.exceptions.AlienTopologyException;
import org.ow2.petals.webadministration.service.exceptions.InvalidTopologyException;

/**
 * @author Vincent Zurczak - Linagora
 */
public class TopologyXmlUtilsTestCase {

	/**
	 * Tests an invalid topology file (any XML).
	 */
	@Test
	public void testInvalidTopology() {

		try {
			retrievePetalsNodes( "./topologies/invalid-topology.xml" );
			fail( "An exception was expected." );

		} catch( AlienTopologyException e ) {
			// Good!

		} catch( Exception e ) {
			fail( "This exception was not expected." );
		}
	}


	/**
	 * Tests empty topologies.
	 */
	@Test
	public void testEmptyTopology() {

		for( int i=1; i<5; i++ ) {
			try {
				List<PetalsNode> result = retrievePetalsNodes( "./topologies/empty-topology-" + i + ".xml" );
				Assert.assertNotNull( result );
				Assert.assertEquals( result.size(), 0 );

			} catch( Exception e ) {
				fail( "An exception was not expected (topology " + i + ")." );
			}
		}
	}


	/**
	 * Tests empty topologies.
	 */
	@Test
	public void testOneNodeTopology() {

		try {
			List<PetalsNode> result = retrievePetalsNodes( "./topologies/one-node-topology.xml" );
			Assert.assertNotNull( result );
			Assert.assertEquals( result.size(), 1 );

			PetalsNode node = result.get( 0 );
			Assert.assertEquals( "0", node.getContainerName());
			Assert.assertEquals( "localhost", node.getHost());
			Assert.assertEquals( "petals", node.getUser());
			Assert.assertEquals( "petals", node.getPassword());

			Assert.assertNotNull( node.getPorts());
			Assert.assertEquals((Integer) 7600, node.getPorts().get( PortType.HTTP_WEBSERVICE ));
			Assert.assertEquals((Integer) 7700, node.getPorts().get( PortType.JMX ));
			Assert.assertEquals((Integer) 7900, node.getPorts().get( PortType.REGISTRY ));
			Assert.assertEquals((Integer) 7800, node.getPorts().get( PortType.TCP_TRANSPORT ));

			Assert.assertEquals( true, node.isMaster());
			Assert.assertEquals( false, node.isOnline());

		} catch( Exception e ) {
			fail( "An exception was not expected." );
		}
	}


	/**
	 * Tests empty topologies.
	 */
	@Test
	public void testSeveralNodeTopology() {

		try {
			List<PetalsNode> result = retrievePetalsNodes( "./topologies/several-node-topology.xml" );
			Assert.assertNotNull( result );
			Assert.assertEquals( result.size(), 3 );

			// First node
			PetalsNode node = result.get( 0 );
			Assert.assertEquals( "0", node.getContainerName());
			Assert.assertEquals( "localhost", node.getHost());
			Assert.assertEquals( "petals", node.getUser());
			Assert.assertEquals( "petals", node.getPassword());
			Assert.assertEquals( "my container 0", node.getDescription());

			Assert.assertNotNull( node.getPorts());
			Assert.assertEquals((Integer) 7600, node.getPorts().get( PortType.HTTP_WEBSERVICE ));
			Assert.assertEquals((Integer) 7700, node.getPorts().get( PortType.JMX ));
			Assert.assertEquals((Integer) 7900, node.getPorts().get( PortType.REGISTRY ));
			Assert.assertEquals((Integer) 7800, node.getPorts().get( PortType.TCP_TRANSPORT ));

			Assert.assertEquals( true, node.isMaster());
			Assert.assertEquals( false, node.isOnline());

			// Second node
			node = result.get( 1 );
			Assert.assertEquals( "2", node.getContainerName());
			Assert.assertEquals( "localhost", node.getHost());
			Assert.assertEquals( "petals", node.getUser());
			Assert.assertEquals( "petals", node.getPassword());
			Assert.assertEquals( "my container 2", node.getDescription());

			Assert.assertNotNull( node.getPorts());
			Assert.assertEquals((Integer) 7602, node.getPorts().get( PortType.HTTP_WEBSERVICE ));
			Assert.assertEquals((Integer) 7702, node.getPorts().get( PortType.JMX ));
			Assert.assertEquals((Integer) 7902, node.getPorts().get( PortType.REGISTRY ));
			Assert.assertEquals((Integer) 7802, node.getPorts().get( PortType.TCP_TRANSPORT ));

			Assert.assertEquals( false, node.isMaster());
			Assert.assertEquals( false, node.isOnline());


			// Third node
			node = result.get( 2 );
			Assert.assertEquals( "3", node.getContainerName());
			Assert.assertEquals( "localhost", node.getHost());
			Assert.assertEquals( "petals", node.getUser());
			Assert.assertEquals( "petals", node.getPassword());
			Assert.assertEquals( "my container 3", node.getDescription());

			Assert.assertNotNull( node.getPorts());
			Assert.assertNull( node.getPorts().get( PortType.HTTP_WEBSERVICE ));
			Assert.assertEquals((Integer) 7703, node.getPorts().get( PortType.JMX ));
			Assert.assertEquals((Integer) 7903, node.getPorts().get( PortType.REGISTRY ));
			Assert.assertEquals((Integer) 7803, node.getPorts().get( PortType.TCP_TRANSPORT ));

			Assert.assertEquals( false, node.isMaster());
			Assert.assertEquals( false, node.isOnline());

		} catch( Exception e ) {
			fail( "An exception was not expected." );
		}
	}


	/**
	 * Retrieves the Petals nodes from a topology.xml file.
	 * @param localPath
	 * @return
	 */
	private List<PetalsNode> retrievePetalsNodes( String localPath )
	throws AlienTopologyException, InvalidTopologyException {
		InputStream is = getClass().getClassLoader().getResourceAsStream( localPath );
		return TopologyXmlUtils.buildPetalsNodesFromTopologyXml( is );
	}
}
