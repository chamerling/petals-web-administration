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

package org.ow2.petals.webadministration.utils.jsf;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;

/**
 * The class in charge of all the messages (internationalization and flash).
 * @author Sebastien Levesque - Linagora
 */
public class Messages {

	/**
	 * The bundle ID for i18n.
	 */
    private static final String BUNDLE_ID = "org.ow2.petals.webadministration.messages";



	/**
	 * Shows an infiormation message.
	 * @param message
	 */
	public static void addInfoMessage(String message) {
		addFlashMessage(null, FacesMessage.SEVERITY_INFO, message);
	}


	/**
	 * Shows an error message.
	 * @param message
	 */
	public static void addErrorMessage( String message ) {
		addFlashMessage( null, FacesMessage.SEVERITY_ERROR, message );
	}


	/**
	 * Resets the messages.
	 */
	public static void resetMessages() {
		FacesContext context = FacesContext.getCurrentInstance();
		Iterator<FacesMessage> iter = context.getMessages();
		while( iter.hasNext()) {
			iter.next();
			iter.remove();
		}
	}


	/**
	 * Builds a message with potential parameters.
	 * @param key the key
	 * @param params the parameters
	 * @return an internationalized string
	 */
	public static String buildMessage( String key, Object... params ) {

		String message = null;
		try {
			ResourceBundle bundle = ResourceBundle.getBundle( BUNDLE_ID );
			message = bundle.getString( key );
			message = MessageFormat.format( message, params );

		} catch( MissingResourceException e ) {
			return "Unkown message " + key;

		} catch( IllegalArgumentException e ) {
			return "Bad parameters for " + message;
		}

		return message;
	}


	/**
	 * Displays a "flash" message.
	 * <p>
	 * Flash in the JSF sense.
	 * </p>
	 *
	 * @param component the UI component (can be null)
	 * @param severity the severity (@see {@link FacesMessage})
	 * @param message the message to display (not null)
	 */
	private static void addFlashMessage( UIComponent component, Severity severity, String message ) {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext() .getFlash();
		flash.setKeepMessages( true );
		flash.setRedirect( true );

		FacesMessage msg = new FacesMessage( severity, message, null );
		String id = component != null ? component.getClientId( FacesContext.getCurrentInstance()) : null;
		FacesContext.getCurrentInstance().addMessage( id, msg );
	}
}
