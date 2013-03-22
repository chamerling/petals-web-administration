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

package org.ow2.petals.webadministration.pojo.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.TopologyService;

/**
 * A converter for Petals nodes.
 * @author Sebastien Levesque - Linagora
 */
@Named
public class NodeConvertor implements Converter {

    @Inject
	TopologyService topologyService;


    /*
     * (non-Javadoc)
     * @see javax.faces.convert.Converter
     * #getAsObject(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    @Override
    public Object getAsObject( FacesContext context, UIComponent component, String value ) {
    	return value != null ? this.topologyService.findPetalsNode( value ) : null;
    }


    /*
     * (non-Javadoc)
     * @see javax.faces.convert.Converter
     * #getAsString(javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    @Override
    public String getAsString( FacesContext context, UIComponent component, Object value ) {

    	String result = null;
    	if( value != null )
    		result = ((PetalsNode) value).getContainerName();

    	return result;
    }
}
