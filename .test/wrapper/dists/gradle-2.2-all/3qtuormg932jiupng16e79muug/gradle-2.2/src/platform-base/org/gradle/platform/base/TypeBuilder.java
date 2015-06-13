/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.gradle.platform.base;

import org.gradle.api.Incubating;

/**
 * A TypeBuilder to configure read the implementation class of a type.
 * @param <C> The component type.
 * */
@Incubating
public interface TypeBuilder<C> {

    /**
     * Allows the plugin to register the implementation type.
     * @param implementation the implementation class.
     */
    TypeBuilder<C> defaultImplementation(Class<? extends C> implementation);
}
