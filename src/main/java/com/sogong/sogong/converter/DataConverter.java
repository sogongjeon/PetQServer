package com.sogong.sogong.converter;


import org.springframework.core.convert.ConversionException;

import java.io.IOException;

public interface DataConverter<Source, Target> {
    Target convert(Source source, Target target) throws ConversionException, IOException;
    Target convert(Source source) throws ConversionException, IOException;
}
