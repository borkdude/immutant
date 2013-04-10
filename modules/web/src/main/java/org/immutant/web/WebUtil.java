/*
 * Copyright 2008-2013 Red Hat, Inc, and individual contributors.
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

package org.immutant.web;

import java.lang.reflect.Method;

import org.jboss.as.server.deployment.DeploymentUnit;
import org.jboss.as.undertow.deployment.UndertowDeploymentProcessor;
import org.jboss.as.web.common.WarMetaData;
import org.jboss.metadata.web.jboss.JBossWebMetaData;

public class WebUtil {

    public static String hostNameOfDeployment(final WarMetaData metaData, final String defaultHost) {
        try {
            Method method = UndertowDeploymentProcessor.class.getDeclaredMethod("hostNameOfDeployment", WarMetaData.class, String.class);
            method.setAccessible(true);
            return (String)method.invoke(null, metaData, defaultHost);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static String pathNameOfDeployment(final DeploymentUnit deploymentUnit, final JBossWebMetaData metaData) {
        try {
            Method method = UndertowDeploymentProcessor.class.getDeclaredMethod("pathNameOfDeployment", DeploymentUnit.class, JBossWebMetaData.class);
            method.setAccessible(true);
            return (String)method.invoke(null, deploymentUnit, metaData);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
