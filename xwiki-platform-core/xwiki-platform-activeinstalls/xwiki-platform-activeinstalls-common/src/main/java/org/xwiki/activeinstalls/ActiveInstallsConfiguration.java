/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.xwiki.activeinstalls;

import org.xwiki.component.annotation.Role;
import org.xwiki.stability.Unstable;

/**
 * Configuration properties for the Active Installs module.
 *
 * @version $Id$
 * @since 5.2M2
 */
@Role
@Unstable
public interface ActiveInstallsConfiguration
{
    /**
     * @return the URL (as a string) of where the Active Installs module should connect to in order to send a ping of
     *         activity
     */
    String getPingInstanceURL();

    /**
     * @return the number of days after which an Install will no longer be considered active (eg 30 days means that if
     *         we haven't received a ping for the last 30 days the installation is no longer considered as an active
     *         installation)
     */
    int getActivityThreshold();
}