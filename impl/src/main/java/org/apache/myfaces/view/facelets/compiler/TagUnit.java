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
package org.apache.myfaces.view.facelets.compiler;

import jakarta.faces.view.facelets.FaceletHandler;
import jakarta.faces.view.facelets.Tag;
import jakarta.faces.view.facelets.TagConfig;

import org.apache.myfaces.view.facelets.tag.TagLibrary;

/**
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
class TagUnit extends CompilationUnit implements TagConfig
{

    private final TagLibrary library;
    private final String id;
    private final Tag tag;
    private final String namespace;
    private final String name;

    public TagUnit(TagLibrary library, String namespace, String name, Tag tag, String id)
    {
        this.library = library;
        this.tag = tag;
        this.namespace = namespace;
        this.name = name;
        this.id = id;
    }

    @Override
    public FaceletHandler createFaceletHandler()
    {
        return this.library.createTagHandler(this.namespace, this.name, this);
    }

    @Override
    public FaceletHandler getNextHandler()
    {
        return this.getNextFaceletHandler();
    }

    @Override
    public Tag getTag()
    {
        return this.tag;
    }

    @Override
    public String getTagId()
    {
        return this.id;
    }

    @Override
    public String toString()
    {
        return this.tag.toString();
    }

}
