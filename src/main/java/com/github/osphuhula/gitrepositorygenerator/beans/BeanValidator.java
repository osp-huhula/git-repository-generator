package com.github.osphuhula.gitrepositorygenerator.beans;

public interface BeanValidator<T> {

    boolean validate(
        T o);
}
