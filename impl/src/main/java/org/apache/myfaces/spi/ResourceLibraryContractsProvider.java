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

import java.io.IOException;
import java.util.Set;

import jakarta.faces.context.ExternalContext;

/**
 * Locate resource library contracts, implementing the behavior described in 
 * Faces 2.2 section 11.4.2.1 related to discover the available resource library
 * contracts.
 * 
 * @since 2.0.2
 * @author Leonardo Uribe 
 */
public abstract class ResourceLibraryContractsProvider
{
    
    public abstract Set<String> getExternalContextResourceLibraryContracts(
        ExternalContext context) throws IOException;
    
    public abstract Set<String> getClassloaderResourceLibraryContracts(
        ExternalContext context) throws IOException;
}