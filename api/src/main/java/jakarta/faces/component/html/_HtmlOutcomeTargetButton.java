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
package jakarta.faces.component.html;

import jakarta.faces.component.UIOutcomeTarget;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFComponent;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFProperty;

/**
 * 
 * @since 2.0
 */
@JSFComponent(name="h:button",
        clazz = "jakarta.faces.component.html.HtmlOutcomeTargetButton",template=true,
        implementz = "jakarta.faces.component.behavior.ClientBehaviorHolder",
        defaultRendererType = "jakarta.faces.Button")
abstract class _HtmlOutcomeTargetButton extends UIOutcomeTarget implements _FocusBlurProperties,
_EventProperties, _StyleProperties, _UniversalProperties, _AccesskeyProperty,
_TabindexProperty, _AltProperty, _RoleProperty
{

  static public final String COMPONENT_FAMILY =
    "jakarta.faces.OutcomeTarget";
  static public final String COMPONENT_TYPE =
    "jakarta.faces.HtmlOutcomeTargetButton";

  /**
   * HTML: the value to put as image attribute
   * 
   * @return
   */
  @JSFProperty
  public abstract String getImage(); 
  
  /**
   * When true, this element cannot receive focus.
   *
   * @since 2.3
   * @return  the new disabled value
   */
  @JSFProperty
  (defaultValue = "false")
  public abstract boolean isDisabled();

}
