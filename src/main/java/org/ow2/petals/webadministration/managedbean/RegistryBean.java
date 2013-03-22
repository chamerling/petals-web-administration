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

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.admin.registry.Endpoint;
import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.RegistryService;
import org.ow2.petals.webadministration.service.exceptions.WebAdministrationException;
import org.ow2.petals.webadministration.utils.jsf.JSFUtils;

/**
 * The presentation bean for the service registry.
 * <p>
 * By convention, all our session-scoped beans are serializable.
 * This avoids errors when the servlet container tries to save them.
 * </p>
 *
 * @author Sebastien Levesque - Linagora
 * @author Vincent Zurczak - Linagora
 */
@Named
//@Scope("session")
public class RegistryBean {

	@Inject
	RegistryService registryService;

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
	 * @return the name of the node, as defined in the request
	 */
	public String getRequestParameter() {
		return JSFUtils.getRequestParameter( "name" );
	}


	public List<Endpoint> getRegistryData() {

		List<Endpoint> result;
		try {
			result = this.registryService.getRegistryContent().getAllEndpoints();

		} catch( WebAdministrationException e ) {
			result = Collections.emptyList();
			e.printStackTrace();
		}

		return result;
	}


	public String formatQNameCandidate( String s ) {

		String result;
		int index = s.indexOf( '}' );
		if( index < 0 || index + 1 >= s.length())
			result = s;
		else
			result = s.substring( index + 1 ) + " - " + s.substring( 1, index );

		return result;
	}


	public String formatInterfaceNames( List<String> names ) {

		StringBuilder sb = new StringBuilder();
		boolean first = true;
		for( String s : names ) {
			if( first )
				first = false;
			else
				sb.append( "<br />" );

			sb.append( formatQNameCandidate( s ));
		}

		// Hack:
		String result = sb.toString().replace( "UNRESOLVED_INTERFACE", "" );

		return result;
	}


	public int getMaxServicePerPage(){
		return this.registryService.getMaxServicePerPage();
	}
}