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
package org.apache.myfaces.view.facelets.pool.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import jakarta.faces.component.UIViewRoot;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.config.RuntimeConfig;
import org.apache.myfaces.config.element.ViewPoolMapping;
import org.apache.myfaces.config.element.ViewPoolParameter;
import org.apache.myfaces.util.UrlPatternMatcher;
import org.apache.myfaces.view.facelets.ViewPoolProcessor;
import org.apache.myfaces.view.facelets.pool.ViewPool;
import org.apache.myfaces.view.facelets.pool.ViewPoolFactory;

/**
 *
 */
public class ViewPoolFactoryImpl extends ViewPoolFactory
{
    private List<String> urlPatterns;
    private List<ViewPool> viewPoolList;
    private ViewPool defaultViewPool;
    
    public ViewPoolFactoryImpl(FacesContext context)
    {
        RuntimeConfig runtimeConfig = RuntimeConfig.getCurrentInstance(context.getExternalContext());
        // If no view pool mappings set, apply to all views.
        if (runtimeConfig.getViewPoolMappings().isEmpty())
        {
            defaultViewPool = new ViewPoolImpl(context, new HashMap<>());
        }
        urlPatterns = new ArrayList<>();
        viewPoolList = new ArrayList<>();
        for (ViewPoolMapping vpm : runtimeConfig.getViewPoolMappings())
        {
            urlPatterns.add(vpm.getUrlPattern());
            Map<String,String> parameters = new HashMap<>();
            for (ViewPoolParameter param : vpm.getParameterList())
            {
                parameters.put(param.getName(), param.getValue());
            }
            viewPoolList.add(new ViewPoolImpl(context, parameters));
        }
    }
    
    @Override
    public ViewPool getViewPool(FacesContext context, UIViewRoot root)
    {
        for (int i = 0; i < urlPatterns.size(); i++)
        {
            String urlPattern = urlPatterns.get(i);
            if (UrlPatternMatcher.match(root.getViewId(), urlPattern))
            {
                return viewPoolList.get(i);
            }
        }
        if (defaultViewPool != null)
        {
            // The default view pool applies to all views that does not have any view pool mapping,
            // but only when oamEnableViewPool is set, so we need a check here when it is not to avoid
            // use view pool on all views when only alwaysRecompile is set to true.
            Boolean enableViewPool = (Boolean) root.getAttributes().get(ViewPoolProcessor.ENABLE_VIEW_POOL);
            if (enableViewPool == null)
            {
                return null;
            }
            return defaultViewPool;
        }
        return null;
    }
}
