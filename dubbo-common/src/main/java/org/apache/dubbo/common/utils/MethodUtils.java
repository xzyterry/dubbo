/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.dubbo.common.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class MethodUtils {

    /**
     * set开头 set不全等 public权限 参数等于一 方法参数为原始类型
     */
    public static boolean isSetter(Method method) {
        return method.getName().startsWith("set")
                && !"set".equals(method.getName())
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterCount() == 1
                && ClassUtils.isPrimitive(method.getParameterTypes()[0]);
    }

    //判断是否是get or is方法

    /**
     * get is 开头 不等于 get is getClass getObject
     *
     * 访问权限为public 方法参数列表size = 0 方法返回类型是原始类型
     */
    public static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith("get") || name.startsWith("is"))
                && !"get".equals(name) && !"is".equals(name)
                && !"getClass".equals(name) && !"getObject".equals(name)
                && Modifier.isPublic(method.getModifiers())
                && method.getParameterTypes().length == 0
                && ClassUtils.isPrimitive(method.getReturnType());
    }

    /**
     * 是否是元方法
     * 返回false:
     * 方法名:
     *      1.不以get is开头
     *      2.get全等
     *      3.getClass
     *      4.非public
     *      5.入参个数不为0
     *      6.返回类型不为元类型
     *
     */
    public static boolean isMetaMethod(Method method) {
        String name = method.getName();
        if (!(name.startsWith("get") || name.startsWith("is"))) {
            return false;
        }
        if ("get".equals(name)) {
            return false;
        }
        if ("getClass".equals(name)) {
            return false;
        }
        if (!Modifier.isPublic(method.getModifiers())) {
            return false;
        }
        if (method.getParameterTypes().length != 0) {
            return false;
        }
        if (!ClassUtils.isPrimitive(method.getReturnType())) {
            return false;
        }
        return true;
    }
}
