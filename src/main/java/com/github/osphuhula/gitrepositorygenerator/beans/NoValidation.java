package com.github.osphuhula.gitrepositorygenerator.beans;

public final class NoValidation<T>
    implements
    BeanValidator<T> {

    @Override
    public boolean validate(
        final T o) {
        return true;
    }
}
