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
package org.apache.myfaces.renderkit.html.base;

import org.apache.myfaces.renderkit.html.util.HtmlRendererUtils;
import org.apache.myfaces.renderkit.html.util.ClientBehaviorRendererUtils;
import org.apache.myfaces.renderkit.html.util.CommonHtmlAttributesUtil;
import org.apache.myfaces.renderkit.html.util.CommonHtmlEventsUtil;
import org.apache.myfaces.renderkit.RendererUtils;

import jakarta.faces.component.UIComponent;
import jakarta.faces.component.html.HtmlPanelGroup;
import jakarta.faces.context.FacesContext;
import jakarta.faces.context.ResponseWriter;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import jakarta.faces.component.behavior.ClientBehavior;
import org.apache.myfaces.renderkit.html.util.ResourceUtils;
import org.apache.myfaces.renderkit.html.util.HTML;

public class HtmlGroupRendererBase extends HtmlRenderer 
{
    private static final String LAYOUT_BLOCK_VALUE = "block";

    @Override
    public boolean getRendersChildren()
    {
        return true;
    }

    @Override
    public void decode(FacesContext context, UIComponent component)
    {
        // Check for npe
        super.decode(context, component);
        
        ClientBehaviorRendererUtils.decodeClientBehaviors(context, component);
    }

    @Override
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException
    {
    }

    @Override
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException
    {
    }

    @Override
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException
    {
        ResponseWriter writer = context.getResponseWriter();
        boolean span = false;

        // will be SPAN or DIV, depending on the layout attribute value
        String layoutElement = HTML.SPAN_ELEM;

        HtmlPanelGroup panelGroup = (HtmlPanelGroup) component;

        // if layout is 'block', render DIV instead SPAN
        String layout = panelGroup.getLayout();
        if (layout != null && layout.equals(LAYOUT_BLOCK_VALUE))
        {
            layoutElement = HTML.DIV_ELEM;
        }
        
        Map<String, List<ClientBehavior>> behaviors = panelGroup.getClientBehaviors();
        if (behaviors != null && !behaviors.isEmpty())
        {
            ResourceUtils.renderDefaultJsfJsInlineIfNecessary(context, writer);
        }

        if (!behaviors.isEmpty() || shouldRenderId(context, component))
        {
            span = true;

            writer.startElement(layoutElement, component);

            //HtmlRendererUtils.writeIdIfNecessary(writer, component, context);
            writer.writeAttribute(HTML.ID_ATTR, component.getClientId(context), null);

            long commonPropertiesMarked = 0L;
            if (isCommonPropertiesOptimizationEnabled(context))
            {
                commonPropertiesMarked = CommonHtmlAttributesUtil.getMarkedAttributes(component);
                CommonHtmlAttributesUtil.renderCommonPassthroughPropertiesWithoutEvents(writer, 
                        commonPropertiesMarked, component);
            }
            else
            {
                HtmlRendererUtils.renderHTMLAttributes(writer, component, HTML.UNIVERSAL_ATTRIBUTES);
            }
            if (behaviors.isEmpty() && isCommonPropertiesOptimizationEnabled(context))
            {
                CommonHtmlAttributesUtil.renderEventProperties(writer, 
                        commonPropertiesMarked, component);
            }
            else
            {
                if (isCommonEventsOptimizationEnabled(context))
                {
                    CommonHtmlEventsUtil.renderBehaviorizedEventHandlers(context, writer, 
                           commonPropertiesMarked,
                           CommonHtmlEventsUtil.getMarkedEvents(component), component, behaviors);
                }
                else
                {
                    HtmlRendererUtils.renderBehaviorizedEventHandlers(context, writer, component, behaviors);
                }
            }
        }
        else
        {
            if (isCommonPropertiesOptimizationEnabled(context))
            {
                long commonAttributesMarked = CommonHtmlAttributesUtil.getMarkedAttributes(component);
                if (commonAttributesMarked > 0)
                {
                    span = true;
                    writer.startElement(layoutElement, component);
                    HtmlRendererUtils.writeIdIfNecessary(writer, component, context);

                    CommonHtmlAttributesUtil.renderCommonPassthroughProperties(writer,
                            commonAttributesMarked, component);
                }
            }
            else
            {
                span = HtmlRendererUtils.renderHTMLAttributesWithOptionalStartElement(writer,
                                                                                 component,
                                                                                 layoutElement,
                                                                                 HTML.COMMON_PASSTROUGH_ATTRIBUTES);
            }
        }

        RendererUtils.renderChildren(context, component);
        if (span)
        {
            writer.endElement(layoutElement);
        }
    }

}
