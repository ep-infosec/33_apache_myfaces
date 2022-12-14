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
package org.apache.myfaces.context;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.faces.FacesException;
import jakarta.faces.FactoryFinder;
import jakarta.faces.application.ApplicationFactory;
import jakarta.faces.context.ExceptionHandlerFactory;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.ExternalContextFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.FacesContextFactory;
import jakarta.faces.context.PartialViewContextFactory;
import jakarta.faces.lifecycle.Lifecycle;
import jakarta.faces.render.RenderKitFactory;

import org.apache.myfaces.context.servlet.FacesContextImpl;
import org.apache.myfaces.core.api.shared.lang.Assert;
import org.apache.myfaces.util.lang.ClassUtils;

/**
 * DOCUMENT ME!
 * 
 * @author Manfred Geiler (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public class FacesContextFactoryImpl extends FacesContextFactory implements ReleasableFacesContextFactory
{
    private static final Logger log = Logger.getLogger(FacesContextFactoryImpl.class.getName());
    
    /**
     * Reference to factory to prevent unnecessary lookups
     */
    private final ExternalContextFactory _externalContextFactory;
    
    /**
     * Reference to factory to prevent unnecessary lookups
     */
    private final ExceptionHandlerFactory _exceptionHandlerFactory;
    
    private final ApplicationFactory _applicationFactory;
    
    private final RenderKitFactory _renderKitFactory;
    
    private final PartialViewContextFactory _partialViewContextFactory;
    
    /**
     * This var is assigned as the same as jakarta.faces.context.ExternalContext._firstInstance,
     * and since it is a static reference and does not change, we can cache it here safely.
     * 
     * We need
     */
    private final ThreadLocal<ExternalContext> _firstExternalContextInstance;
    
    @SuppressWarnings("unchecked")
    public FacesContextFactoryImpl()
    {
        super();
        ThreadLocal<ExternalContext> firstExternalContextInstance = null;
        try
        {
            Class clazz = ClassUtils.classForName("jakarta.faces.context._MyFacesExternalContextHelper");
            Field externalContextFirstInstance = clazz.getDeclaredField("firstInstance");
            externalContextFirstInstance.setAccessible(true);

            if (firstExternalContextInstance == null)
            {
                firstExternalContextInstance = 
                    (ThreadLocal<ExternalContext>) externalContextFirstInstance.get(null);
            }
        }
        catch (SecurityException e)
        {
            // It could happen, but we can ignore it.
            if (log.isLoggable(Level.FINE))
            {
                log.log(Level.FINE, "Cannot access field firstInstance "
                        + "from _MyFacesExternalContextHelper ", e);
            }
        }
        catch (Exception e)
        {
            if (log.isLoggable(Level.SEVERE))
            {
                log.log(Level.SEVERE, "Cannot find field firstInstance "
                        + "from _MyFacesExternalContextHelper ", e);
            }
        }
        
        _firstExternalContextInstance = firstExternalContextInstance;
        
        _externalContextFactory = (ExternalContextFactory)
            FactoryFinder.getFactory(FactoryFinder.EXTERNAL_CONTEXT_FACTORY);

        _exceptionHandlerFactory = (ExceptionHandlerFactory)
            FactoryFinder.getFactory(FactoryFinder.EXCEPTION_HANDLER_FACTORY);
        
        _applicationFactory = (ApplicationFactory)
            FactoryFinder.getFactory(FactoryFinder.APPLICATION_FACTORY);
        
        _renderKitFactory = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);

        _partialViewContextFactory = (PartialViewContextFactory) 
            FactoryFinder.getFactory(FactoryFinder.PARTIAL_VIEW_CONTEXT_FACTORY);
    }

    @Override
    public FacesContext getFacesContext(Object context, Object request, Object response, Lifecycle lifecycle)
            throws FacesException
    {
        Assert.notNull(context, "context");
        Assert.notNull(request, "request");
        Assert.notNull(response, "response");
        Assert.notNull(lifecycle, "lifecycle");
        
        ExternalContext externalContext = _externalContextFactory.getExternalContext(context, request, response);

        ExternalContext defaultExternalContext = null;
        
        if (_firstExternalContextInstance != null)
        {
            defaultExternalContext = (ExternalContext)
                externalContext.getRequestMap().remove(
                        ExternalContextFactoryImpl.EXTERNAL_CONTEXT_KEY);
            
            if (defaultExternalContext != null)
            {
                // Initialize the firstExternalContext that old jsf 1.2 or lower
                // implementations of ExternalContext should fall when call jsf 2.0
                // methods.
                _firstExternalContextInstance.set(defaultExternalContext);
            }
        }

        FacesContext facesContext = new FacesContextImpl(externalContext, defaultExternalContext, this,
            _applicationFactory, _renderKitFactory, _partialViewContextFactory, lifecycle);
        facesContext.setExceptionHandler(_exceptionHandlerFactory.getExceptionHandler());

        return facesContext;
    }

    @Override
    public void release()
    {
        if (_firstExternalContextInstance != null)
        {
            _firstExternalContextInstance.remove();
        }
    }
}
