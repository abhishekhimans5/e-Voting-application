package com.example.e_voting_samadhan;



public class model {
  String election_name,organizer,winner;

    public model()
    {
    }

    public model(String election_name, String organizer, String winner) {
        this.election_name = election_name;
        this.organizer = organizer;
        this.winner = winner;

    }

    public String getElection_name() {
        return election_name;
    }

    public void setElection_name(String election_name) {
        this.election_name = election_name;
    }

    public String getOrganizer() {
        return organizer;
    }

    public void setOrganizer(String organizer) {
        this.organizer = organizer;
    }

    public String getWinner() {
        return winner;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }


}
