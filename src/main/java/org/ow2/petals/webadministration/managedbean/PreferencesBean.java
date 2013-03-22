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
import java.util.Locale;
import java.util.prefs.BackingStoreException;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.pojo.ApplicationPreferences;
import org.ow2.petals.webadministration.service.PreferenceService;
import org.ow2.petals.webadministration.utils.jsf.Messages;

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
//@Scope("session")
public class PreferencesBean implements Serializable {

	/**
	 * The serial ID.
	 */
	private static final long serialVersionUID = -4019608968933184061L;

	/**
	 * The languages.
	 */
	private static List<Locale> locales;
	static{
		locales = new ArrayList<Locale> ();
		locales.add( Locale.FRENCH );
		locales.add( Locale.ENGLISH );
	}

	/**
	 * The service in charge of the preferences.
	 */
	@Inject
	PreferenceService service;

	/**
	 * The preferences.
	 */
	private ApplicationPreferences prefs;



	/**
	 * @return the preferences
	 */
	public ApplicationPreferences getPrefs() {
		return this.prefs;
	}


	/**
	 * @param prefs the preferences to set
	 */
	public void setPrefs( ApplicationPreferences prefs ) {
		this.prefs = prefs;
	}



	/**
	 * Initializes the preferences.
	 */
    public void preRenderView() {
        if( this.prefs == null )
        	this.prefs = this.service.get();
    }


    /**
     * Updates the preferences.
     * @throws BackingStoreException
     */
	public void update() throws BackingStoreException{
        if( this.prefs != null )
        	 this.service.save( this.prefs );
	}


	/**
	 * Tests the connection for these preferences.
	 * @throws BackingStoreException
	 */
	public void testConnection() throws BackingStoreException{

        if( this.prefs == null )
        	return;

        // Server?
        boolean ok;
        if( this.prefs.isServerConnection()) {
        	ok = this.service.checkServerJMXConnection( this.prefs.getHost(), this.prefs.getPort(), this.prefs.getUser(), this.prefs.getPassword());
        	if( ok )
           		Messages.addInfoMessage( Messages.buildMessage( "preferences.msg.server" ));
           	else
           		Messages.addErrorMessage( Messages.buildMessage( "preferences.error.server" ));
        }

        // Topology?
        else {
        	ok = this.service.checkFileTopologyConnection( this.prefs.getTopologyURL());
        	if( ok )
           		Messages.addInfoMessage( Messages.buildMessage( "preferences.msg.topology-xml" ));
           	else
           		Messages.addErrorMessage( Messages.buildMessage( "preferences.error.topology-xml" ));
        }
	}


	/**
	 * Reacts to a change event in the topology preferences.
	 * @param e the change event
	 */
	public void switchConnection( ValueChangeEvent e ) {
		String s = String.valueOf( e.getNewValue());
		if( this.prefs != null )
			this.prefs.setConnection( s );
	}


	/**
	 * @return a map associating a language name (key) with a locale (value).
	 */
	public List<SelectItem> getLocalesToLabels() {

		List<SelectItem> result = new ArrayList<SelectItem> ();
		for( Locale locale : locales ) {
			String value = Messages.buildMessage( "preferences.lang." + locale.getLanguage().toLowerCase());
			result.add( new SelectItem( locale, value ));
		}

		return result;
	}


	/**
	 * Reacts to a change event in the language preferences.
	 * @param e the change event
	 */
	public void countryLocaleCodeChanged( ValueChangeEvent e ) {

		for( Locale locale : locales ) {
			if( locale.toString().equals( e.getNewValue()))
				continue;

			this.prefs.setLocale( locale.getCountry());
			FacesContext.getCurrentInstance().getViewRoot().setLocale( locale );
		}
	}
}