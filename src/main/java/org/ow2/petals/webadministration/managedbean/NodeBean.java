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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Named;

import org.ow2.petals.admin.topology.Container.PortType;
import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.utils.jsf.JSFUtils;
import org.ow2.petals.webadministration.utils.jsf.Messages;
import org.springframework.context.annotation.Scope;

/**
 * The presentation bean for the node properties.
 * <p>
 * By convention, all our session-scoped beans are serializable.
 * This avoids errors when the servlet container tries to save them.
 * </p>
 *
 * @author Sebastien Levesque - Linagora
 */
@Named
@Scope("session")
public class NodeBean implements Serializable {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 8386286241583727332L;

	/**
	 * The Petals node (set from the template).
	 */
	private transient PetalsNode petalsNode;



	/**
	 * @return the Petals node
	 */
	public PetalsNode getPetalsNode() {
		return this.petalsNode;
	}


	/**
	 * @param petalsNode the Petals node to set
	 */
	public void setPetalsNode( PetalsNode petalsNode ) {
		this.petalsNode = petalsNode;
	}


	/**
	 * @return true if there is a Petals node, false otherwise
	 */
	public boolean exist() {
		return this.petalsNode != null;
	}


	/**
	 * @return true if there is a Petals node and if it has a description
	 */
	public boolean hasDescription() {
		return this.petalsNode != null
				&& this.petalsNode.getDescription() != null
				&& this.petalsNode.getDescription().trim().length() > 0;
	}


	/**
	 * Gets a property name.
	 * <p>
	 * We use this solution because there is no simple way to pass parameters to i18n with JSF templates.
	 * </p>
	 *
	 * @param propertyName the property name
	 * @return a formatted string (not null)
	 */
	public String getFormattedPropertyName( PortType property ) {

		String result;
		if( property == null )
			result = Messages.buildMessage( "node.ports.unknown" );
		else if( PortType.HTTP_WEBSERVICE == property )
			result = Messages.buildMessage( "node.ports.ws" );
		else if( PortType.JMX == property )
			result = Messages.buildMessage( "node.ports.jmx" );
		else if( PortType.TCP_TRANSPORT == property )
			result = Messages.buildMessage( "node.ports.transporter" );
		else if( PortType.REGISTRY == property )
			result = Messages.buildMessage( "node.ports.registry" );
		else
			result = Messages.buildMessage( "node.ports.unknown" );

		return result;
	}


	/**
	 * Gets a description with the given parameter name.
	 * <p>
	 * We use this solution because there is no simple way to pass parameters to i18n with JSF templates.
	 * </p>
	 *
	 * @return a formatted string
	 */
	public String getDescription() {
		return Messages.buildMessage( "node.description", getRequestParameter());
	}


	/**
	 * Gets a title with the given parameter name.
	 * <p>
	 * We use this solution because there is no simple way to pass parameters to i18n with JSF templates.
	 * </p>
	 *
	 * @return a formatted string
	 */
	public String getTitle() {
		return Messages.buildMessage( "node.title", getRequestParameter());
	}


	/**
	 * @return the name of the node, as defined in the request
	 */
	public String getRequestParameter() {
		return JSFUtils.getRequestParameter( "name" );
	}


	/**
	 * @return
	 */
	public List<Map.Entry<PortType, Integer>> getPortsEntries(){

		if( this.petalsNode != null && this.petalsNode.getPorts()!=null ) {
			Set<Map.Entry<PortType, Integer>> productSet = this.petalsNode.getPorts().entrySet();
			return new ArrayList<Map.Entry<PortType, Integer>>(productSet);
		}
		else
			return new ArrayList<Map.Entry<PortType, Integer>>();
	}
}