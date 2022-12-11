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
package org.apache.myfaces.context.servlet;

import java.util.Enumeration;
import java.util.Map;

import jakarta.servlet.ServletContext;

import org.apache.myfaces.util.lang.AbstractThreadSafeAttributeMap;

/**
 * ServletContext attributes as a Map.
 *
 * @author Anton Koinov (latest modification by $Author$)
 * @version $Revision$ $Date$
 */
public final class ApplicationMap extends AbstractThreadSafeAttributeMap<Object>
{
    final ServletContext servletContext;

    ApplicationMap(ServletContext servletContext)
    {
        this.servletContext = servletContext;
    }

    @Override
    protected Object getAttribute(final String key)
    {
        return servletContext.getAttribute(key);
    }

    @Override
    protected void setAttribute(final String key, final Object value)
    {
        servletContext.setAttribute(key, value);
    }

    @Override
    protected void removeAttribute(final String key)
    {
        servletContext.removeAttribute(key);
    }

    @Override
    protected Enumeration<String> getAttributeNames()
    {
        return servletContext.getAttributeNames();
    }

    @Override
    public void putAll(final Map<? extends String, ? extends Object> t)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear()
    {
        throw new UnsupportedOperationException();
    }
}
