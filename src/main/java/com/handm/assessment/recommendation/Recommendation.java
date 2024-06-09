package com.handm.assessment.recommendation;

import com.handm.assessment.product.Tag;

import java.util.List;

public class Recommendation {

    private Event event;
    private int budget;
    private List<Tag> preference;

    public Recommendation() {
    }

    public Recommendation(Event event, int budget, List<Tag> preference) {
        this.event = event;
        this.budget = budget;
        this.preference = preference;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public List<Tag> getPreference() {
        return preference;
    }

    public void setPreference(List<Tag> preference) {
        this.preference = preference;
    }
}
