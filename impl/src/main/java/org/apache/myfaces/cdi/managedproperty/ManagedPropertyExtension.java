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
package org.apache.myfaces.cdi.managedproperty;

import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.inject.spi.AfterBeanDiscovery;
import jakarta.enterprise.inject.spi.AfterDeploymentValidation;
import jakarta.enterprise.inject.spi.Annotated;
import jakarta.enterprise.inject.spi.AnnotatedField;
import jakarta.enterprise.inject.spi.BeanManager;
import jakarta.enterprise.inject.spi.Extension;
import jakarta.enterprise.inject.spi.ProcessManagedBean;
import jakarta.faces.annotation.ManagedProperty;

public class ManagedPropertyExtension implements Extension
{
    private Set<ManagedPropertyInfo> types = new HashSet<>();

    public <T> void collect(@Observes ProcessManagedBean<T> event)
    {
        for (AnnotatedField<?> field : event.getAnnotatedBeanClass().getFields())
        {
            addAnnotatedTypeIfNecessary(field);
        }
    }
    
    private void addAnnotatedTypeIfNecessary(Annotated annotated)
    {
        if (annotated.isAnnotationPresent(ManagedProperty.class))
        {
            Type type = annotated.getBaseType();

            types.add(new ManagedPropertyInfo(type, annotated.getAnnotation(ManagedProperty.class).value()));
        }
    }
    
    public void afterBean(@Observes AfterBeanDiscovery afterBeanDiscovery, BeanManager beanManager)
    {
        for (ManagedPropertyInfo typeInfo : types)
        {
            afterBeanDiscovery.addBean(new ManagedPropertyProducer(beanManager, typeInfo));
        }
    }
    
    public void cleanup(@Observes AfterDeploymentValidation afterDeploymentValidation)
    {
        types.clear();
    }
}
