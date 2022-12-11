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

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.WeakHashMap;
import jakarta.faces.context.ExternalContext;
import jakarta.faces.context.FacesContext;
import javax.naming.NamingException;

import org.apache.myfaces.util.lang.ClassUtils;
import org.apache.myfaces.spi.InjectionProvider;
import org.apache.myfaces.spi.InjectionProviderException;
import org.apache.myfaces.util.ExternalSpecifications;
import org.apache.tomcat.InstanceManager;

/**
 * An annotation lifecycle provider for Tomcat 7.
 */
public class Tomcat7AnnotationInjectionProvider extends InjectionProvider
{

    private WeakHashMap<ClassLoader, InstanceManager> instanceManagers = null;

    public Tomcat7AnnotationInjectionProvider(ExternalContext externalContext)
    {
        instanceManagers = new WeakHashMap<>();
    }

    @Override
    public Object inject(Object instance) throws InjectionProviderException
    {
        return null;
    }

    @Override
    public void preDestroy(Object instance, Object creationMetaData) throws InjectionProviderException
    {
        InstanceManager manager = instanceManagers.get(ClassUtils.getContextClassLoader());

        if (manager != null)
        {
            try
            {
                manager.destroyInstance(instance);
            }
            catch (IllegalAccessException | InvocationTargetException ex)
            {
                throw new InjectionProviderException(ex);
            }
        }
    }

    @Override
    public void postConstruct(Object instance, Object creationMetaData) throws InjectionProviderException
    {
        InstanceManager manager = instanceManagers.get(ClassUtils.getContextClassLoader());
        if (manager == null)
        {
            //Initialize manager
            manager = initManager();
        }

        //Is initialized
        if (manager != null)
        {
            //Inject resources
            try 
            {
                manager.newInstance(instance);
            }
            catch (IllegalAccessException | InvocationTargetException | NamingException ex)
            {
                throw new InjectionProviderException(ex);
            }
        }
    }

    @Override
    public boolean isAvailable()
    {
        try
        {
            Class c = ClassUtils.classForName("org.apache.tomcat.InstanceManager");
            if (c != null)
            {
                // Tomcat 7 Available, check CDI integration. If there is no CDI available,
                // things goes as usual just connect to the server. If CDI is available,
                // the injection provider should check if we can inject a bean through tomcat
                // otherwise, we need to prefer an integration against CDI, and from CDI to
                // the underlying server.
                FacesContext facesContext = FacesContext.getCurrentInstance();
                if (ExternalSpecifications.isCDIAvailable(facesContext.getExternalContext()))
                {
                    return false;
                }
            }
            return true;
        }
        catch (Exception e)
        {
            // ignore
        }
        return false;
    }

    private InstanceManager initManager()
    {
        FacesContext context = FacesContext.getCurrentInstance();
        if (context == null)
        {
            return null;
        }

        ExternalContext extCtx = context.getExternalContext();
        if (extCtx == null)
        {
            return null;
        }

        // get application map to access ServletContext attributes
        Map<String, Object> applicationMap = extCtx.getApplicationMap();

        InstanceManager instanceManager = (InstanceManager)
                applicationMap.get(InstanceManager.class.getName());
        if (instanceManager != null)
        {
            instanceManagers.put(ClassUtils.getContextClassLoader(), instanceManager);
        }

        return instanceManager;
    }

}
