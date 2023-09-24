package com.duracel.bridge.model;

import lombok.Value;

@Value
public class Hand {
    String hand;

    @Override
    public String toString() {
        return hand;
    }
}
