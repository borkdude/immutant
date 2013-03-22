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

package org.immutant.web.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.GenericServlet;

import org.immutant.runtime.ClojureRuntime;
import org.jboss.logging.Logger;

public class RingServlet extends GenericServlet {
    public static final String CLOJURE_RUNTIME = "clojure.runtime";
    
    public RingServlet() {
        log.info("JC: servlet constructed");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        ServletConfig config = getServletConfig();
        this.runtime = (ClojureRuntime)getServletContext().getAttribute( CLOJURE_RUNTIME );
        this.handlerName = config.getServletName();
        log.info("JC: servlet initialized");
    }
    
    @Override
    public void service(ServletRequest request, ServletResponse response) {
        this.runtime.invoke( "immutant.web.ring/handle-request", this.handlerName, request, response );
    }

    private ClojureRuntime runtime;
    private String handlerName;

    private static final Logger log = Logger.getLogger( RingServlet.class );
    private static final long serialVersionUID = 1L;
}
