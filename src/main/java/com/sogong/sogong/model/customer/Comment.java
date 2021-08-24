package com.sogong.sogong.model.customer;

import com.sogong.sogong.model.generic.AuditableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="TBL_COMMENT")
public class Comment extends AuditableEntity {

    @Column
    private String type; //LOST_PET , ANIMAL_DATA

    @Column
    private Long dataId;

    @Column
    private Long customerId;

    @Column
    private String comment;

    @Column
    private Long commentTo;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getDataId() {
        return dataId;
    }

    public void setDataId(Long dataId) {
        this.dataId = dataId;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Long getCommentTo() {
        return commentTo;
    }

    public void setCommentTo(Long commentTo) {
        this.commentTo = commentTo;
    }

}
