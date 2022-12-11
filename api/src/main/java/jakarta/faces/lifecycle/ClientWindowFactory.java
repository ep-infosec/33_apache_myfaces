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
package jakarta.faces.lifecycle;

import jakarta.faces.FacesWrapper;
import jakarta.faces.context.FacesContext;

/**
 *
 * @since 2.2
 */
public abstract class ClientWindowFactory implements FacesWrapper<ClientWindowFactory>
{
    private ClientWindowFactory delegate;

    @Deprecated
    public ClientWindowFactory()
    {
    }

    public ClientWindowFactory(ClientWindowFactory delegate)
    {
        this.delegate = delegate;
    }

    @Override
    public ClientWindowFactory getWrapped()
    {
        return delegate;
    }
    
    public abstract ClientWindow getClientWindow(FacesContext context);
}
