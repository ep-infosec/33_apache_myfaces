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
package jakarta.faces.component;

import java.io.Serializable;
import java.util.function.Supplier;

/**
 * @since 2.0
 */
public interface StateHelper extends StateHolder
{
    public void add(Serializable key, Object value);

    public Object eval(Serializable key);

    public Object eval(Serializable key, Object defaultValue);

    /**
     * 
     * @param key
     * @param defaultValueSupplier
     * @return 
     * 
     * @since 4.0
     */
    public Object eval(Serializable key, Supplier<Object> defaultValueSupplier);

    public Object get(Serializable key);

    public Object put(Serializable key, Object value);

    public Object put(Serializable key, String mapKey, Object value);

    public Object remove(Serializable key);

    public Object remove(Serializable key, Object valueOrKey);
}
