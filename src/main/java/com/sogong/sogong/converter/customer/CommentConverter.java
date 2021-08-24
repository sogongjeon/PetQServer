package com.sogong.sogong.converter.customer;

import com.sogong.sogong.converter.AbstractDataConverter;
import com.sogong.sogong.entity.animal.AnimalDetailEntity;
import com.sogong.sogong.entity.customer.CommentEntity;
import com.sogong.sogong.model.animal.AnimalData;
import com.sogong.sogong.model.customer.Comment;
import com.sogong.sogong.model.customer.Customer;
import com.sogong.sogong.services.customer.CustomerService;
import org.springframework.core.convert.ConversionException;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

public class CommentConverter extends AbstractDataConverter<Comment, CommentEntity> {
    private final CustomerService customerService;
    private final String profilePath;

    public CommentConverter(String profilePath, CustomerService customerService) {
        this.profilePath = profilePath;
        this.customerService = customerService;
    }

    @Override
    protected CommentEntity createTarget() {
        return new CommentEntity();
    }


    @Override
    public CommentEntity convert(Comment source, CommentEntity target) throws ConversionException, IOException {
        Customer customer = customerService.findByIdAndEnabledTrue(source.getCustomerId());
        target.setId(source.getId());
        target.setNickname(customer.getNickname());
        target.setCreatedAt(source.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        target.setComment(source.getComment());
        target.setProfile(profilePath+customer.getId());

        return target;
    }
}
