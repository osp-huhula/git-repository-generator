package com.github.osphuhula.gitrepositorygenerator.beans;

import java.io.Serializable;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.github.osphuhula.gitrepositorygenerator.util.ToStringUtils;

public final class CustomProperty<V extends Serializable>
    implements
    Serializable, Comparable<CustomProperty<V>> {

    private static final long serialVersionUID = -6031131102090555223L;
    private final String name;
    private final V value;

    public CustomProperty(
        final String name,
        final V value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(
        final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof CustomProperty) {
            EqualsBuilder builder = new EqualsBuilder();
            CustomProperty<?> o = (CustomProperty<?>) obj;
            builder.append(name, o.name);
            builder.append(value, o.value);
            return builder.isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        HashCodeBuilder builder = new HashCodeBuilder();
        builder.append(name);
        builder.append(value);
        return builder.hashCode();
    }

    @Override
    public String toString() {
        return ToStringUtils.toString(this);
    }

    @Override
    public int compareTo(
        final CustomProperty<V> o) {
        return new CompareToBuilder().append(name, o.name).append(value, o.value).build();
    }
}
