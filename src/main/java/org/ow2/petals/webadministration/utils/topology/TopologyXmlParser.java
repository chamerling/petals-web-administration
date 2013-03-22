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

import java.util.List;

import javax.xml.stream.XMLStreamReader;

import org.ow2.petals.webadministration.pojo.PetalsNode;
import org.ow2.petals.webadministration.service.exceptions.InvalidTopologyException;

/**
 * An interface that defines a topology parser.
 * @author Vincent Zurczak - Linagora
 */
public interface TopologyXmlParser {

	/**
	 * Parses a topology by using StaX.
	 * @param reader a reader, already initialized
	 * @return a non-null list of Petals nodes
	 * @throws InvalidTopologyException if the topology could not be parsed
	 */
	List<PetalsNode> parseTopology( XMLStreamReader reader ) throws InvalidTopologyException;
}
