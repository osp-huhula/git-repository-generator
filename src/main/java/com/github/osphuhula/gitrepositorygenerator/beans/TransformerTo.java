package com.github.osphuhula.gitrepositorygenerator.beans;

public interface TransformerTo<I, O> {

    O transform(
        I in);
}
