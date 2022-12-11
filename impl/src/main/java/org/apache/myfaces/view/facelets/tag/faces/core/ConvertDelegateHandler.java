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
package org.apache.myfaces.view.facelets.tag.faces.core;

import jakarta.el.ELException;
import jakarta.faces.FacesException;
import jakarta.faces.convert.Converter;
import jakarta.faces.view.facelets.ConverterConfig;
import jakarta.faces.view.facelets.ConverterHandler;
import jakarta.faces.view.facelets.FaceletContext;
import jakarta.faces.view.facelets.FaceletException;
import jakarta.faces.view.facelets.MetaRuleset;
import jakarta.faces.view.facelets.TagAttribute;

import org.apache.myfaces.buildtools.maven2.plugin.builder.annotation.JSFFaceletTag;

/**
 * Register a named Converter instance on the UIComponent associated with the closest parent UIComponent custom action.
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
@JSFFaceletTag(name = "f:converter", bodyContent = "empty")
public final class ConvertDelegateHandler extends ConverterHandler
{
    private final TagAttribute converterId;

    public ConvertDelegateHandler(ConverterConfig config)
    {
        super(config);
        this.converterId = this.getAttribute("converterId");
    }

    /**
     * Uses the specified "converterId" to pull an instance from the Application
     * 
     * See jakarta.faces.application.Application#createComponent(java.lang.String)
     * See org.apache.myfaces.view.facelets.tag.faces.ConverterHandler#createConverter(
     * jakarta.faces.view.facelets.FaceletContext)
     */
    protected Converter createConverter(FaceletContext ctx) throws FacesException, ELException, FaceletException
    {
        return ctx.getFacesContext().getApplication().createConverter(this.getConverterId(ctx));
    }

    @Override
    protected MetaRuleset createMetaRuleset(Class type)
    {
        return super.createMetaRuleset(type).ignoreAll();
    }

    @Override
    public String getConverterId(FaceletContext ctx)
    {
        if (converterId == null)
        {
            return null;
        }
        return converterId.getValue(ctx);
    }
}
