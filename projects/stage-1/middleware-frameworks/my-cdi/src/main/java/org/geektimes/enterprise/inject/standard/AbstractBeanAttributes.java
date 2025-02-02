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
package org.geektimes.enterprise.inject.standard;

import org.geektimes.enterprise.inject.util.Qualifiers;

import javax.enterprise.inject.Alternative;
import javax.enterprise.inject.spi.BeanAttributes;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Type;
import java.util.Set;
import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;
import static org.geektimes.commons.lang.util.AnnotationUtils.isAnnotationPresent;
import static org.geektimes.enterprise.inject.util.Beans.getBeanTypes;
import static org.geektimes.enterprise.inject.util.Scopes.getScopeType;
import static org.geektimes.enterprise.inject.util.Stereotypes.getStereotypeTypes;

/**
 * Abstract {@link BeanAttributes} implementation
 *
 * @param <T> the class of the bean instance
 * @param <A> the type of annotated element
 * @author <a href="mailto:mercyblitz@gmail.com">Mercy</a>
 * @since 1.0.0
 */
public abstract class AbstractBeanAttributes<A extends AnnotatedElement, T> implements BeanAttributes<T> {

    private final A annotatedElement;

    private final Class<?> beanClass;

    private final Set<Type> beanTypes;

    private final Set<Annotation> qualifiers;

    private final String beanName;

    private final Class<? extends Annotation> scopeType;

    private final Set<Class<? extends Annotation>> stereotypeTypes;

    private final boolean alternative;

    public AbstractBeanAttributes(A annotatedElement, Class<?> beanClass) {
        requireNonNull(annotatedElement, "The 'annotatedElement' argument must not be null!");
        requireNonNull(beanClass, "The 'beanClass' argument must not be null!");
        validateAnnotatedElement(annotatedElement);
        this.annotatedElement = annotatedElement;
        this.beanClass = beanClass;
        this.beanTypes = getBeanTypes(beanClass);
        this.qualifiers = Qualifiers.getQualifiers(annotatedElement);
        this.beanName = getBeanName(annotatedElement);
        this.scopeType = getScopeType(annotatedElement);
        this.stereotypeTypes = getStereotypeTypes(annotatedElement);
        this.alternative = isAnnotationPresent(annotatedElement, Alternative.class);
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    @Override
    public Set<Type> getTypes() {
        return beanTypes;
    }

    @Override
    public Set<Annotation> getQualifiers() {
        return qualifiers;
    }

    @Override
    public Class<? extends Annotation> getScope() {
        return scopeType;
    }

    @Override
    public String getName() {
        return beanName;
    }

    @Override
    public Set<Class<? extends Annotation>> getStereotypes() {
        return stereotypeTypes;
    }

    @Override
    public boolean isAlternative() {
        return alternative;
    }

    public A getAnnotatedElement() {
        return annotatedElement;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AbstractBeanAttributes.class.getSimpleName() + "[", "]")
                .add("annotatedElement=" + getAnnotatedElement())
                .add("beanClass=" + getBeanClass())
                .add("types=" + getTypes())
                .add("qualifiers=" + getQualifiers())
                .add("beanName='" + getName() + "'")
                .add("scopeType=" + getScope())
                .add("stereotypeTypes=" + getStereotypes())
                .add("alternative=" + isAlternative())
                .toString();
    }

    // Abstract methods
    protected abstract String getBeanName(A annotatedElement);

    protected abstract void validateAnnotatedElement(A annotatedElement);

}
