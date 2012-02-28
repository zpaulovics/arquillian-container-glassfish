/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.container.glassfish.managed_3_1;

import java.io.File;

import org.jboss.arquillian.container.glassfish.CommonGlassFishConfiguration;
import org.jboss.arquillian.container.spi.ConfigurationException;
import org.jboss.arquillian.container.spi.client.deployment.Validate;

/**
 * @author <a href="http://community.jboss.org/people/dan.j.allen">Dan Allen</a>
 * @author <a href="http://community.jboss.org/people/LightGuard">Jason Porter</a>
 */
public class GlassFishManagedContainerConfiguration extends CommonGlassFishConfiguration {

    /**
     * The local GlassFish installation directory
     */
    private String glassfishHome = System.getenv("GLASSFISH_HOME");
    
    /**
     * The GlassFish domain to use or the default domain if not specified
     */
    private String domain = null;

    /**
     * Show the output of the admin commands on the console
     */
    private boolean outputToConsole = false;
    
    /**
     * Flag to start the server in debug mode using standard GlassFish debug port
     */
    private boolean debug = false;
    
    /**
     * Http port for application urls.
     * Used to build the URL for the REST request.
     */
    private int remoteServerHttpPort = 8080;
    
    public String getGlassfishHome() {
        return glassfishHome;
    }

    public void setGlassfishHome(String glassfishHome) {
        this.glassfishHome = glassfishHome;
    }
    
    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public boolean isOutputToConsole() {
        return outputToConsole;
    }

    public void setOutputToConsole(boolean outputToConsole) {
        this.outputToConsole = outputToConsole;
    }

    public boolean isDebug() {
        return debug;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public int getRemoteServerHttpPort() {
        return remoteServerHttpPort;
    }

    public void setRemoteServerHttpPort(int remoteServerHttpPort) {
        this.remoteServerHttpPort = remoteServerHttpPort;
    }

    public File getAdminCliJar() {
        return new File(getGlassfishHome() + "/glassfish/modules/admin-cli.jar");
    }

    /**
     * Validates if current configuration is valid, that is if all required
     * properties are set and have correct values
     */
    public void validate() throws ConfigurationException {
        Validate.notNull(getGlassfishHome(), "glassfishHome must be specified or the GLASSFISH_HOME environment variable must be set");
        // FIXME this should be a more robust check
        if (!getAdminCliJar().exists()) {
            throw new IllegalArgumentException(getGlassfishHome() + " is not a valid GlassFish installation");
        }
        
        if (getDomain() != null) {
            Validate.configurationDirectoryExists(getGlassfishHome() + "/glassfish/domains/" + getDomain(), "Invalid domain: " + getDomain());
        }
        
       super.validate();
    }
}
