package com.github.osphuhula.gitrepositorygenerator.dp;

import java.util.Objects;

public abstract class TemplateDPBuilder<T>
    implements
    BeanDPBuilder<T> {

    private final T bean;

    public TemplateDPBuilder(
        final BeanDPBuilder<T> builder) {
        this(builder.build());
    }

    protected TemplateDPBuilder(
        final T bean) {
        super();
        Objects.requireNonNull(bean);
        this.bean = bean;
    }

    @Override
    public final T getBean() {
        return bean;
    }
}
