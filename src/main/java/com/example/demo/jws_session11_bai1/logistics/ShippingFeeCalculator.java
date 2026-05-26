package com.example.demo.jws_session11_bai1.logistics;

public class ShippingFeeCalculator {

    private static final double BASE_WEIGHT_FEE = 50_000;
    private static final double EXTRA_WEIGHT_FEE_PER_KG = 10_000;
    private static final double SHORT_DISTANCE_LIMIT_KM = 10;
    private static final double LONG_DISTANCE_LIMIT_KM = 50;
    private static final double MEDIUM_DISTANCE_FEE_PER_KM = 5_000;
    private static final double LONG_DISTANCE_FEE_PER_KM = 4_000;

    public double calculateFee(double weightKg, double distanceKm) {
        if (weightKg <= 0 || distanceKm <= 0) {
            throw new IllegalArgumentException("Weight and distance must be positive");
        }

        double weightFee = BASE_WEIGHT_FEE;
        if (weightKg > 1) {
            weightFee += Math.ceil(weightKg - 1) * EXTRA_WEIGHT_FEE_PER_KG;
        }

        double distanceFee = 0;
        if (distanceKm >= SHORT_DISTANCE_LIMIT_KM && distanceKm < LONG_DISTANCE_LIMIT_KM) {
            distanceFee = distanceKm * MEDIUM_DISTANCE_FEE_PER_KM;
        } else if (distanceKm >= LONG_DISTANCE_LIMIT_KM) {
            distanceFee = distanceKm * LONG_DISTANCE_FEE_PER_KM;
        }

        return weightFee + distanceFee;
    }
}
