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
package org.apache.myfaces.view.facelets.pss.acid.managed;

import java.io.Serializable;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.faces.event.ActionEvent;
import jakarta.inject.Named;

/**
 *
 * @author Leonardo Uribe
 */
@Named("checkActionEventBean")
@SessionScoped
public class CheckActionEventBean implements Serializable
{
    private int actionListenerCount;
    
    @PostConstruct
    public void init()
    {
        actionListenerCount = 0;
    }
    
    public void increment(ActionEvent evt)
    {
        actionListenerCount++;
    }
    
    public int getActionListenerCount()
    {
        return actionListenerCount;
    }
}
