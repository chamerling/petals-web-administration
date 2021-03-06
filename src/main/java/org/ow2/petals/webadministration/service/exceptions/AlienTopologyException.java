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

package org.ow2.petals.webadministration.service.exceptions;

/**
 * An exception that indicates a topology could not be understood.
 * @author Vincent Zurczak - Linagora
 */
public class AlienTopologyException extends Exception {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = 6053189704553501720L;


	/**
	 * Constructor.
	 */
	public AlienTopologyException() {
		super();
	}

	/**
	 * Constructor.
	 * @param message
	 * @param cause
	 */
	public AlienTopologyException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor.
	 * @param message
	 */
	public AlienTopologyException(String message) {
		super(message);
	}

	/**
	 * Constructor.
	 * @param cause
	 */
	public AlienTopologyException(Throwable cause) {
		super(cause);
	}
}
