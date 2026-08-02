package com.lld.uber.common;

public class Util {

    private Util() {
        // utility class, no instances
    }

    public static String ratingToString(Rating rating) {
        switch (rating) {
            case ONE_STAR:
                return "one star";
            case TWO_STARS:
                return "two stars";
            case THREE_STARS:
                return "three stars";
            case FOUR_STARS:
                return "four stars";
            case FIVE_STARS:
                return "five stars";
            default:
                return "invalid rating";
        }
    }

    public static boolean isHighRating(Rating rating) {
        return rating == Rating.FOUR_STARS || rating == Rating.FIVE_STARS;
    }
}
