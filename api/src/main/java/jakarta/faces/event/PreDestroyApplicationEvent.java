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
package jakarta.faces.event;

import jakarta.faces.application.Application;
import jakarta.faces.context.FacesContext;

/**
 * @since 2.0
 */
public class PreDestroyApplicationEvent extends SystemEvent
{
    /**
     * @param application
     */
    public PreDestroyApplicationEvent(Application application)
    {
        super(application);
    }

    /**
     * @since 2.3
     */
    public PreDestroyApplicationEvent(FacesContext facesContext, Application application)
    {
        super(facesContext, application);
    }
    
    /**
     * @return the application object tied to this event
     */
    public Application getApplication()
    {
        return (Application) getSource();
    }
}
