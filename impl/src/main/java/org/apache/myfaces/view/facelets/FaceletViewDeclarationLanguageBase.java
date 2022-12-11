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
package org.apache.myfaces.view.facelets;

import jakarta.faces.FactoryFinder;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.component.visit.VisitContextFactory;
import jakarta.faces.context.FacesContext;
import jakarta.faces.render.RenderKitFactory;
import org.apache.myfaces.view.ViewDeclarationLanguageBase;

/**
 *
 * @author Leonardo Uribe
 */
public abstract class FaceletViewDeclarationLanguageBase extends ViewDeclarationLanguageBase
{
    /**
     * UIViewRoot attribute to check if the dynamic components in a view should be refreshed under a transient build
     */
    private static final String DYNAMIC_COMPONENT_REFRESH_TRANSIENT_BUILD = "oam.vf.DCRTB";
    
    private static final String DYNAMIC_COMPONENT_NEEDS_REFRESH = "oam.vf.DC_NEEDS_REFRESH";
    
    private VisitContextFactory _visitContextFactory = null;
    
    private RenderKitFactory _renderKitFactory = null;

    protected VisitContextFactory getVisitContextFactory()
    {
        if (_visitContextFactory == null)
        {
            _visitContextFactory = (VisitContextFactory)FactoryFinder.getFactory(FactoryFinder.VISIT_CONTEXT_FACTORY);
        }
        return _visitContextFactory;
    }
    
    public static boolean isDynamicComponentRefreshTransientBuildActive(FacesContext facesContext)
    {
        if (facesContext.getViewRoot() == null)
        {
            return false;
        }
        return Boolean.TRUE.equals(
                facesContext.getViewRoot().getAttributes().get(DYNAMIC_COMPONENT_REFRESH_TRANSIENT_BUILD));
    }
    
    public static boolean isDynamicComponentRefreshTransientBuildActive(FacesContext facesContext, UIViewRoot view)
    {
        return Boolean.TRUE.equals(
                view.getAttributes().get(DYNAMIC_COMPONENT_REFRESH_TRANSIENT_BUILD));
    }
    
    public static void activateDynamicComponentRefreshTransientBuild(FacesContext facesContext)
    {
        if (!isDynamicComponentRefreshTransientBuildActive(facesContext))
        {
            facesContext.getViewRoot().getAttributes().put(
                    DYNAMIC_COMPONENT_REFRESH_TRANSIENT_BUILD, Boolean.TRUE);
        }
    }
    
    public static boolean isDynamicComponentNeedsRefresh(FacesContext context)
    {
        return Boolean.TRUE.equals(context.getAttributes().get(DYNAMIC_COMPONENT_NEEDS_REFRESH));
    }

    public static void dynamicComponentNeedsRefresh(FacesContext context)
    {
        context.getAttributes().put(DYNAMIC_COMPONENT_NEEDS_REFRESH, Boolean.TRUE);
    }
    
    public static void resetDynamicComponentNeedsRefreshFlag(FacesContext context)
    {
        context.getAttributes().put(DYNAMIC_COMPONENT_NEEDS_REFRESH, Boolean.FALSE);
    }
 
    protected RenderKitFactory getRenderKitFactory()
    {
        if (_renderKitFactory == null)
        {
            _renderKitFactory = (RenderKitFactory)FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        }
        return _renderKitFactory;
    }

}
