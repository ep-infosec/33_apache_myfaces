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
package org.apache.myfaces.view.facelets.tag.faces.html;

import jakarta.faces.component.UIOutput;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * <p>
 * This tag is backed using a jakarta.faces.component.UIOutput component instance.
 * In other words, instances of this component class are created when it is resolved
 * a Resource annotation, so there is no concrete class or specific tag handler for it,
 * but there exists a concrete renderer for it.
 * </p>
 */
@JSFComponent(configExcluded = true, defaultRendererType = "jakarta.faces.resource.Stylesheet")
abstract class _HtmlOutputStylesheet extends UIOutput
{

    @JSFProperty
    public abstract String getLibrary();

    @JSFProperty(required = true)
    public abstract String getName();

}
