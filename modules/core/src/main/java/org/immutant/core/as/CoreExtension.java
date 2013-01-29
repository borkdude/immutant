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

package org.immutant.core.as;

import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.ADD;
import static org.jboss.as.controller.descriptions.ModelDescriptionConstants.DESCRIBE;

import java.io.IOException;

import org.immutant.common.ClassLoaderUtils;
import org.immutant.core.Immutant;
import org.immutant.core.ImmutantClassLoader;
import org.immutant.core.TmpResourceMountMap;
import org.jboss.as.controller.ExtensionContext;
import org.jboss.as.controller.SubsystemRegistration;
import org.jboss.as.controller.parsing.ExtensionParsingContext;
import org.jboss.as.controller.registry.ManagementResourceRegistration;
import org.jboss.as.controller.registry.OperationEntry;
import org.jboss.logging.Logger;
import org.projectodd.polyglot.core.as.AbstractBootstrappableExtension;
import org.projectodd.polyglot.core.as.GenericSubsystemDescribeHandler;

public class CoreExtension extends AbstractBootstrappableExtension {

    @Override
    public void initialize(ExtensionContext context) {
        bootstrap();
        log.info( "Initializing Immutant Core Subsystem" );
        try {
            Immutant immutant = new Immutant();
            immutant.printVersionInfo( log );
        } catch (IOException e) {
            log.error( "Failed to load immutant.properties", e );
        }
        
        ClassLoaderUtils.init( ImmutantClassLoader.getFactory(), TmpResourceMountMap.ATTACHMENT_KEY );
        
        final SubsystemRegistration registration = context.registerSubsystem( SUBSYSTEM_NAME, 1, 0 );
        final ManagementResourceRegistration subsystem = registration.registerSubsystemModel( CoreSubsystemProviders.SUBSYSTEM );

        subsystem.registerOperationHandler( ADD,
                CoreSubsystemAdd.ADD_INSTANCE,
                CoreSubsystemProviders.SUBSYSTEM_ADD,
                false );
        subsystem.registerOperationHandler(DESCRIBE, 
                GenericSubsystemDescribeHandler.INSTANCE, 
                GenericSubsystemDescribeHandler.INSTANCE, 
                false, 
                OperationEntry.EntryType.PRIVATE);
        registration.registerXMLElementWriter(CoreSubsystemParser.getInstance());
    }

    @Override
    public void initializeParsers(ExtensionParsingContext context) {
        context.setSubsystemXmlMapping(SUBSYSTEM_NAME, Namespace.CURRENT.getUriString(), CoreSubsystemParser.getInstance());
    }
    
    
    public static final String SUBSYSTEM_NAME = "immutant-core";
    static final Logger log = Logger.getLogger( "org.immutant.core.as" );


}
