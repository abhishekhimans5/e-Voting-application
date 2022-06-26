package com.example.e_voting_samadhan;

public class name_vote
{
    String name,vote_count;

    public name_vote() {
    }

    public name_vote(String name, String vote_count) {
        this.name = name;
        this.vote_count = vote_count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVote_count() {
        return vote_count;
    }

    public void setVote_count(String vote_count) {
        this.vote_count = vote_count;
    }
}
