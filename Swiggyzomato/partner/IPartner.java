package com.lld.swiggyzomato.partner;

import com.lld.swiggyzomato.common.Rating;

public class IPartner {
    protected String name;
    protected Rating rating;
    // kyc details

    public IPartner(String pName) {
        this.name = pName;
        this.rating = Rating.UNASSIGNED;
    }

    public String getName() {
        return name;
    }

    // void performKyc() = 0;
}
