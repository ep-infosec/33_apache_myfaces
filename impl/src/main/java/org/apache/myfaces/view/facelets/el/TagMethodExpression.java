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
package org.apache.myfaces.view.facelets.el;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import jakarta.el.ELContext;
import jakarta.el.ELException;
import jakarta.el.MethodExpression;
import jakarta.el.MethodInfo;
import jakarta.el.MethodNotFoundException;
import jakarta.el.PropertyNotFoundException;
import jakarta.faces.FacesWrapper;
import jakarta.faces.view.facelets.TagAttribute;

/**
 * 
 * 
 * @author Jacob Hookom
 * @version $Id$
 */
public final class TagMethodExpression extends MethodExpression
        implements Externalizable, FacesWrapper<MethodExpression>
{

    private static final long serialVersionUID = 1L;

    private String attr;
    private MethodExpression orig;
    
    public TagMethodExpression()
    {
        super();
    }

    public TagMethodExpression(TagAttribute attr, MethodExpression orig)
    {
        this.attr = attr.toString();
        this.orig = orig;
    }

    @Override
    public MethodInfo getMethodInfo(ELContext context)
    {
        try
        {
            return this.orig.getMethodInfo(context);
        }
        catch (PropertyNotFoundException pnfe)
        {
            throw new PropertyNotFoundException(this.attr + ": " + pnfe.getMessage(), pnfe.getCause());
        }
        catch (MethodNotFoundException mnfe)
        {
            throw new MethodNotFoundException(this.attr + ": " + mnfe.getMessage(), mnfe.getCause());
        }
        catch (ELException e)
        {
            throw new ELException(this.attr + ": " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public Object invoke(ELContext context, Object[] params)
    {
        try
        {
            return this.orig.invoke(context, params);
        }
        catch (PropertyNotFoundException pnfe)
        {
            throw new PropertyNotFoundException(this.attr + ": " + pnfe.getMessage(), pnfe.getCause());
        }
        catch (MethodNotFoundException mnfe)
        {
            throw new MethodNotFoundException(this.attr + ": " + mnfe.getMessage(), mnfe.getCause());
        }
        catch (ELException e)
        {
            throw new ELException(this.attr + ": " + e.getMessage(), e.getCause());
        }
    }

    @Override
    public String getExpressionString()
    {
        return this.orig.getExpressionString();
    }

    @Override
    public boolean equals(Object obj)
    {
        return this.orig.equals(obj);
    }

    @Override
    public int hashCode()
    {
        return this.orig.hashCode();
    }

    @Override
    public boolean isLiteralText()
    {
        return this.orig.isLiteralText();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException
    {
        out.writeObject(this.orig);
        out.writeUTF(this.attr);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException
    {
        this.orig = (MethodExpression) in.readObject();
        this.attr = in.readUTF();
    }

    @Override
    public String toString()
    {
        return this.attr + ": " + this.orig;
    }

    @Override
    public MethodExpression getWrapped()
    {
        return this.orig;
    }
}
