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
package org.apache.myfaces.component.search;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import jakarta.faces.FacesException;
import jakarta.faces.component.UIComponent;
import jakarta.faces.component.search.UntargetableComponent;
import jakarta.faces.component.search.SearchExpressionContext;
import jakarta.faces.component.search.SearchKeywordContext;
import jakarta.faces.component.search.SearchKeywordResolver;

/**
 *
 */
public class ChildSearchKeywordResolver extends SearchKeywordResolver
{

    public static final String CHILD_KEYWORD = "child";

    private static final Pattern PATTERN = Pattern.compile("child\\((\\d+)\\)");

    @Override
    public void resolve(SearchKeywordContext expressionContext, UIComponent current, String keyword)
    {
        Matcher matcher = PATTERN.matcher(keyword);

        if (matcher.matches())
        {
            int childNumber = Integer.parseInt(matcher.group(1));

            if (childNumber + 1 > current.getChildCount())
            {
                throw new FacesException("Component with clientId \""
                        + current.getClientId(expressionContext.getSearchExpressionContext().getFacesContext()) 
                        + "\" has fewer children as \"" + 
                          childNumber + "\". Expression: \"" + keyword + '"');
            }

            int count = 0;
            for (int i = 0; i < current.getChildCount(); i++)
            {
                if (!(current.getChildren().get(i) instanceof UntargetableComponent))
                {
                    count++;
                }
                if (count == childNumber + 1)
                {
                    expressionContext.invokeContextCallback(current.getChildren().get(childNumber));
                    break;
                }
            }
            if (count < childNumber)
            {
                throw new FacesException("Component with clientId \""
                        + current.getClientId(expressionContext.getSearchExpressionContext().getFacesContext()) 
                        + "\" has fewer children as \"" + 
                          childNumber + "\". Expression: \"" + keyword + '"');
            }
        }
        else
        {
            throw new FacesException(
                    "Expression does not match following pattern @child(n). Expression: \"" + keyword + '"');
        }
    }

    @Override
    public boolean isResolverForKeyword(SearchExpressionContext searchExpressionContext, String command)
    {
        if (command.length() > 6 && command.substring(0, CHILD_KEYWORD.length()).equalsIgnoreCase(CHILD_KEYWORD))
        {
            Matcher matcher = PATTERN.matcher(command);

            if (matcher.matches())
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;
    }

    @Override
    public boolean isPassthrough(SearchExpressionContext searchExpressionContext, String keyword)
    {
        return false;
    }
    
    @Override
    public boolean isLeaf(SearchExpressionContext searchExpressionContext, String keyword)
    {
        return false;
    }

}
