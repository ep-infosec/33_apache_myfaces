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

import jakarta.faces.component.UIGraphic;
import jakarta.el.ValueExpression;

import org.easymock.classextension.EasyMock;
import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("deprecation")
public class UIGraphicTest
{

    @Test
    public void testUrlValue()
    {
        UIGraphic graphic = new UIGraphic();
        graphic.setValue("xxx");
        Assert.assertEquals(graphic.getUrl(), "xxx");
        graphic.setUrl("xyz");
        Assert.assertEquals(graphic.getValue(), "xyz");
    }

    @Test
    public void testUrlValueExpression()
    {
        UIGraphic graphic = new UIGraphic();
        ValueExpression expression = EasyMock.createMock(ValueExpression.class);
        graphic.setValueExpression("url", expression);
        Assert.assertSame(graphic.getValueExpression("value"), expression);

        expression = EasyMock.createMock(ValueExpression.class);
        graphic.setValueExpression("value", expression);
        Assert.assertSame(graphic.getValueExpression("url"), expression);
    }

}
