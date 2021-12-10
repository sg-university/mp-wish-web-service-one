package com.wish.webserviceone.infrastructure.persistences.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;
import java.util.UUID;

public class Fund {
    private UUID ID;
    @JsonProperty("post_id")
    private UUID postID;
    @JsonProperty("sponsor_account_id")
    private UUID sponsorAccountID;
    private String content;
    private Double amount;
    @JsonProperty("created_at")
    private Timestamp createdAt;
    @JsonProperty("updated_at")
    private Timestamp updatedAt;

    public Fund() {
    }

    public Fund(UUID ID, UUID postID, UUID sponsorAccountID, String content, Double amount, Timestamp createdAt, Timestamp updatedAt) {
        this.ID = ID;
        this.postID = postID;
        this.sponsorAccountID = sponsorAccountID;
        this.content = content;
        this.amount = amount;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public UUID getPostID() {
        return postID;
    }

    public void setPostID(UUID postID) {
        this.postID = postID;
    }

    public UUID getSponsorAccountID() {
        return sponsorAccountID;
    }

    public void setSponsorAccountID(UUID sponsorAccountID) {
        this.sponsorAccountID = sponsorAccountID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
