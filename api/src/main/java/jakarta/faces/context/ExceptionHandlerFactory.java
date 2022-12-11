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
package jakarta.faces.context;

import jakarta.faces.FacesWrapper;

/**
 * @since 2.0
 */
public abstract class ExceptionHandlerFactory implements FacesWrapper<ExceptionHandlerFactory>
{
    private ExceptionHandlerFactory delegate;

    @Deprecated
    public ExceptionHandlerFactory()
    {
    }

    public ExceptionHandlerFactory(ExceptionHandlerFactory delegate)
    {
        this.delegate = delegate;
    }
    
    public abstract ExceptionHandler getExceptionHandler();

    /**
     * If this factory has been decorated, the implementation doing the decorating may override this method to provide
     * access to the implementation being wrapped. A default implementation is provided that returns <code>null</code>.
     * 
     * @return the decorated <code>DiscoveryHandlerFactory</code> if this factory decorates another, or
     *         <code>null</code> otherwise
     * 
     * @since 2.0
     */
    @Override
    public ExceptionHandlerFactory getWrapped()
    {
        return delegate;
    }
}
