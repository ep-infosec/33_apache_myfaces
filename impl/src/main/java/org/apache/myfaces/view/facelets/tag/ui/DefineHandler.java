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
package org.apache.myfaces.view.facelets.tag.ui;

import java.io.IOException;

import jakarta.el.ELException;
import jakarta.faces.FacesException;
import jakarta.faces.component.UIComponent;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.FaceletException;
import jakarta.faces.view.facelets.TagAttribute;
import jakarta.faces.view.facelets.TagAttributeException;
import jakarta.faces.view.facelets.TagConfig;
import jakarta.faces.view.facelets.TagHandler;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletAttribute;
import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

/**
 * The define tag can be used within tags that allow templating. 
 * This includes composition and decorate tags.
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
@JSFFaceletTag(name="ui:define")
public final class DefineHandler extends TagHandler
{

    /**
     * The literal name for this definition. This name will match up with 
     * a &lt;ui:insert/&gt; tag in a target template.
     */
    @JSFFaceletAttribute(
            className="jakarta.el.ValueExpression",
            deferredValueType="java.lang.String",
            required=true)
    private final String name;

    public DefineHandler(TagConfig config)
    {
        super(config);
        TagAttribute attr = this.getRequiredAttribute("name");
        if (!attr.isLiteral())
        {
            throw new TagAttributeException(this.tag, attr, "Must be Literal");
        }
        this.name = attr.getValue();
    }

    /*
     * (non-Javadoc)
     * 
     * @see jakarta.faces.view.facelets.FaceletHandler#apply(jakarta.faces.view.facelets.FaceletContext, jakarta.faces.component.UIComponent)
     */
    @Override
    public void apply(FaceletContext ctx, UIComponent parent) throws IOException, FacesException, FaceletException,
            ELException
    {
        // no-op
    }

    public void applyDefinition(FaceletContext ctx, UIComponent parent) throws IOException, FacesException,
            FaceletException, ELException
    {
        this.nextHandler.apply(ctx, parent);
    }

    public String getName()
    {
        return this.name;
    }
}
