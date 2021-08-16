package com.sogong.sogong.repositories.animal;

import com.sogong.sogong.model.animal.AnimalKind;
import org.springframework.lang.Nullable;

import java.util.List;

public interface AnimalKindRepositoryCustom {
    List<AnimalKind> listByType(@Nullable String type);
}
