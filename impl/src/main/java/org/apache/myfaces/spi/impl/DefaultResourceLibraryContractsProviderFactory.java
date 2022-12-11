/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.myfaces.spi.impl;

import org.apache.myfaces.resource.DefaultResourceLibraryContractsProvider;
import org.apache.myfaces.util.lang.ClassUtils;
import org.apache.myfaces.spi.ResourceLibraryContractsProvider;
import org.apache.myfaces.spi.ResourceLibraryContractsProviderFactory;
import org.apache.myfaces.spi.ServiceProviderFinderFactory;

import jakarta.faces.FacesException;
import jakarta.faces.context.ExternalContext;
import java.lang.reflect.InvocationTargetException;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * @since 2.0.2
 * @author Leonardo Uribe
 */
public class DefaultResourceLibraryContractsProviderFactory extends ResourceLibraryContractsProviderFactory
{
    public static final String CONTRACTS_PROVIDER = ResourceLibraryContractsProvider.class.getName();
    
    public static final String CONTRACTS_PROVIDER_LIST = ResourceLibraryContractsProvider.class.getName()+".LIST";

    private Logger getLogger()
    {
        return Logger.getLogger(DefaultResourceLibraryContractsProviderFactory.class.getName());
    }
    
    @Override
    public ResourceLibraryContractsProvider createResourceLibraryContractsProvider(ExternalContext externalContext)
    {
        ResourceLibraryContractsProvider returnValue = null;
        final ExternalContext extContext = externalContext;
        try
        {
            if (System.getSecurityManager() != null)
            {
                returnValue = (ResourceLibraryContractsProvider) AccessController.doPrivileged(
                        (PrivilegedExceptionAction) () ->
                                resolveResourceLibraryContractsProviderFromService(extContext));
            }
            else
            {
                returnValue = resolveResourceLibraryContractsProviderFromService(extContext);
            }
        }
        catch (ClassNotFoundException | NoClassDefFoundError e)
        {
            // ignore
        }
        catch (InstantiationException | IllegalAccessException | InvocationTargetException e)
        {
            getLogger().log(Level.SEVERE, "", e);
        }
        catch (PrivilegedActionException e)
        {
            throw new FacesException(e);
        }
        return returnValue;
    }
    
    private ResourceLibraryContractsProvider resolveResourceLibraryContractsProviderFromService(
            ExternalContext externalContext) throws ClassNotFoundException,
            NoClassDefFoundError,
            InstantiationException,
            IllegalAccessException,
            InvocationTargetException,
            PrivilegedActionException
    {
        List<String> classList = (List<String>) externalContext.getApplicationMap().get(CONTRACTS_PROVIDER_LIST);
        if (classList == null)
        {
            classList = ServiceProviderFinderFactory.getServiceProviderFinder(externalContext).
                    getServiceProviderList(CONTRACTS_PROVIDER);
            externalContext.getApplicationMap().put(CONTRACTS_PROVIDER_LIST, classList);
        }
        
        return ClassUtils.buildApplicationObject(ResourceLibraryContractsProvider.class, classList,
                                                 new DefaultResourceLibraryContractsProvider());
    }

}
