package com.restaurant.springboot.domain.dto;

public class ReviewDto {

    private Long userId;
    private Long detailsId;
    private String comment;
    private Double rate;

    public ReviewDto() {
    }

    public ReviewDto(Long userId, Long detailsId, String comment, Double rate) {
        this.userId = userId;
        this.detailsId = detailsId;
        this.comment = comment;
        this.rate = rate;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDetailsId() {
        return detailsId;
    }

    public void setDetailsId(Long detailsId) {
        this.detailsId = detailsId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
