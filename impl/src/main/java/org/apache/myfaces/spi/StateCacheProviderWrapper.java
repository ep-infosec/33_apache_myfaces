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

package org.apache.myfaces.spi;

import jakarta.faces.FacesWrapper;
import jakarta.faces.context.FacesContext;
import org.apache.myfaces.application.viewstate.StateCache;

/**
 *
 */
public class StateCacheProviderWrapper extends StateCacheProvider implements FacesWrapper<StateCacheProvider>
{
    
    private StateCacheProvider delegate;

    public StateCacheProviderWrapper()
    {
    }

    public StateCacheProviderWrapper(StateCacheProvider delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public StateCache getStateCache(FacesContext facesContext)
    {
        return getWrapped().getStateCache(facesContext);
    }

    @Override
    public StateCacheProvider getWrapped()
    {
        return delegate;
    }
    
}
