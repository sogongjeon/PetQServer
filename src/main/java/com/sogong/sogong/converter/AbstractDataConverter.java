package com.sogong.sogong.converter;


import org.springframework.core.convert.ConversionException;

import java.io.IOException;

public abstract class AbstractDataConverter<Source, Target> implements DataConverter<Source, Target> {
    @Override
    public Target convert(Source source) throws IOException {
        return convert(source, createTarget());
    }

    protected abstract Target createTarget();
}
