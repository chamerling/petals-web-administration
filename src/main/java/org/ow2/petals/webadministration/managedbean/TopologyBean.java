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

package org.ow2.petals.webadministration.managedbean;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.TopologyService;
import org.ow2.petals.webadministration.utils.jsf.Messages;

/**
 * The presentation bean for the topology.
 * <p>
 * By convention, all our session-scoped beans are serializable.
 * This avoids errors when the servlet container tries to save them.
 * </p>
 *
 * @author Sebastien Levesque - Linagora
 * @author Vincent Zurczak - Linagora
 */
@Named
// FIXME: session scoped => make all the services serializable or find a way to inject them from the application context
//@Scope("session")
public class TopologyBean implements Serializable {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 3921690733632809657L;

	/**
	 * The topology service.
	 */
	@Inject
	transient TopologyService topologyService;

	/**
	 * Cache the list of nodes.
	 */
	private transient List<PetalsNode> nodes;



	/**
	 * Refreshes the list of Petals nodes.
	 */
	public void refreshPetalsNodes() {
		this.nodes = this.topologyService.retrievePetalsNodes();
	}


	/**
	 * Lists the Petals nodes by invoking the topology service.
	 * @return a non-null list of Petals nodes
	 */
	public List<PetalsNode> getPetalsNodes() {
		return this.nodes;
	}


	/**
	 * @param refresh true to refresh
	 * @return true if the topology is empty (no node), false otherwise
	 */
	public boolean isEmptyTopology( boolean refresh ) {
		if( refresh )
			refreshPetalsNodes();

		return this.nodes == null || this.nodes.isEmpty();
	}


	/**
	 * @return the number of Petals nodes
	 */
	public int getNodesCount() {
		return this.nodes == null ? 0 : this.nodes.size();
	}


	/**
	 * Gets a formatted description.
	 * <p>
	 * We use this solution because there is no simple way to pass parameters to i18n with JSF templates.
	 * </p>
	 *
	 * @return a formatted string (not null)
	 */
	public String getFormattedDescription() {

		String result;
		int cpt = getNodesCount();
		switch( cpt ) {
		case 0:
			result = Messages.buildMessage( "topology.meta0" );
			break;
		case 1:
			result = Messages.buildMessage( "topology.meta1" );
			break;
		default:
			result = Messages.buildMessage( "topology.meta2", cpt );
		}

		return result;
	}
}
