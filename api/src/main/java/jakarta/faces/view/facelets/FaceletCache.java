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

package jakarta.faces.view.facelets;

import java.io.IOException;
import java.net.URL;
import org.apache.myfaces.core.api.shared.lang.Assert;

/**
 * @since 2.1
 */
public abstract class FaceletCache<V>
{
    private FaceletCache.MemberFactory<V> _faceletFactory;
    private FaceletCache.MemberFactory<V> _viewMetadataFaceletFactory;
    
    public abstract V getFacelet(URL url) throws IOException;
    
    public abstract boolean isFaceletCached(URL url);
    
    public abstract V getViewMetadataFacelet(URL url) throws IOException;
    
    public abstract boolean isViewMetadataFaceletCached(URL url);

    protected FaceletCache.MemberFactory<V> getMemberFactory()
    {
        return _faceletFactory;
    }
    
    protected FaceletCache.MemberFactory<V> getMetadataMemberFactory()
    {
        return _viewMetadataFaceletFactory;
    }
    
    public static interface MemberFactory<V>
    {
        V newInstance(URL key) throws IOException;
    }
    
    /**
     * @since 2.3
     * @param faceletFactory
     * @param viewMetadataFaceletFactory 
     */
    public void setCacheFactories(FaceletCache.MemberFactory<V> faceletFactory, 
            FaceletCache.MemberFactory<V> viewMetadataFaceletFactory)
    {
        Assert.notNull(faceletFactory, "faceletFactory");
        Assert.notNull(viewMetadataFaceletFactory, "viewMetadataFaceletFactory");

        _faceletFactory = faceletFactory;
        _viewMetadataFaceletFactory = viewMetadataFaceletFactory;
    }
}
