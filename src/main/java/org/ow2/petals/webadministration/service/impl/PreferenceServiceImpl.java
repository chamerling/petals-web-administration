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

package org.ow2.petals.webadministration.service.impl;

import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

import javax.inject.Inject;
import javax.inject.Named;

import org.ow2.petals.webadministration.pojo.ApplicationPreferences;
import org.ow2.petals.webadministration.service.PreferenceService;
import org.ow2.petals.webadministration.service.TopologyService;

/**
 * An implementation of the topology service.
 * @see PreferenceService
 *
 * @author Sebastien Levesque - Linagora
 * @author Vincent Zurczak - Linagora
 */
@Named
public class PreferenceServiceImpl implements PreferenceService {

	/**
	 * The service in charge of the topology.
	 */
	@Inject
	TopologyService topologyService;


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.PreferenceService
	 * #get()
	 */
	@Override
	public ApplicationPreferences get() {

			Preferences root = Preferences.userRoot();
			ApplicationPreferences pf = new ApplicationPreferences();
			pf.setHost(root.get("host", "localhost"));
			pf.setPort(Integer.parseInt(root.get("port", "7700")));
			pf.setUser(root.get("user", "petals"));
			pf.setPassword(root.get("password", "petals"));

			pf.setTopologyURL(root.get("topologyURL", ""));
			pf.setMaxPerPageArtifactList(Integer.parseInt(root.get("maxPerPageArtifactList", "100")));
			pf.setMaxPerPageServiceList(Integer.parseInt(root.get("maxPerPageServiceList", "100")));
			pf.setConnection(root.get("selectConnection", ApplicationPreferences.CONNECTION_SERVER));

			return pf;
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.PreferenceService
	 * #save(org.ow2.petals.webadministration.pojo.ApplicationPreferences)
	 */
	@Override
	public void save(ApplicationPreferences prefs) throws BackingStoreException {
		Preferences root = Preferences.userRoot();
		root.put("host", prefs.getHost());
		root.putInt("port", prefs.getPort());
		root.put("user", prefs.getUser());
		root.put("password", prefs.getPassword());
		root.put("topologyURL", prefs.getTopologyURL());
		root.putInt("maxPerPageArtifactList", prefs.getMaxPerPageArtifactList());
		root.putInt("maxPerPageServiceList", prefs.getMaxPerPageServiceList());
		root.put("selectConnection", prefs.getConnection());
		root.flush();
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.PreferenceService
	 * #checkServerJMXConnection(java.lang.String, int, java.lang.String, java.lang.String)
	 */
	@Override
	public boolean checkServerJMXConnection( String host, int port, String user, String password ) {
		return this.topologyService.checkServerConnection( host, port, user, password );
	}


	/*
	 * (non-Javadoc)
	 * @see org.ow2.petals.webadministration.service.PreferenceService
	 * #checkFileTopologyConnection(java.lang.String)
	 */
	@Override
	public boolean checkFileTopologyConnection( String url ) {
		return this.topologyService.checkTopologyFileConnection( url );
	}
}