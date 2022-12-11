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
package org.apache.myfaces.config.impl.element.facelets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.myfaces.config.element.facelets.FaceletFunction;
import org.apache.myfaces.config.element.facelets.FaceletTag;
import org.apache.myfaces.config.element.facelets.FaceletTagLibrary;

public class FaceletTagLibraryImpl extends FaceletTagLibrary implements Serializable
{
    private String namespace;
    private String shortName;
    private String compositeLibraryName;
    private String libraryClass;
    private List<FaceletTag> tags;
    private List<FaceletFunction> functions;

    @Override
    public String getNamespace()
    {
        return namespace;
    }

    public void setNamespace(String namespace)
    {
        this.namespace = namespace;
    }

    @Override
    public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

    @Override
    public String getCompositeLibraryName()
    {
        return compositeLibraryName;
    }

    public void setCompositeLibraryName(String compositeLibraryName)
    {
        this.compositeLibraryName = compositeLibraryName;
    }

    @Override
    public String getLibraryClass()
    {
        return libraryClass;
    }

    public void setLibraryClass(String libraryClass)
    {
        this.libraryClass = libraryClass;
    }

    @Override
    public List<FaceletTag> getTags()
    {
        if (tags == null)
        {
            return Collections.emptyList();
        }
        return tags;
    }

    public void addTag(FaceletTag tag)
    {
        if (tags == null)
        {
            tags = new ArrayList<>();
        }
        tags.add(tag);
    }

    @Override
    public List<FaceletFunction> getFunctions()
    {
        if (functions == null)
        {
            return Collections.emptyList();
        }
        return functions;
    }
    
    public void addFunction(FaceletFunction function)
    {
        if (functions == null)
        {
            functions = new ArrayList<>();
        }
        functions.add(function);
    }
    
}
