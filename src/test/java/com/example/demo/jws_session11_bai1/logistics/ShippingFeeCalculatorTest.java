package com.example.demo.jws_session11_bai1.logistics;

import com.example.demo.jws_session11_bai1.logistics.ShippingFeeCalculator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ShippingFeeCalculatorTest {

    private final ShippingFeeCalculator calculator = new ShippingFeeCalculator();

    @Test
    void calculateFeeWithWeightAtMostOneKgAndDistanceUnderTenKm() {
        double fee = calculator.calculateFee(1.0, 9.0);

        assertThat(fee).isEqualTo(50_000);
    }

    @Test
    void calculateFeeWithIntegerWeightGreaterThanOneKgAndDistanceFromTenToUnderFiftyKm() {
        double fee = calculator.calculateFee(3.0, 49.0);

        assertThat(fee).isEqualTo(315_000);
    }

    @Test
    void calculateFeeWithFractionalWeightAndDistanceGreaterThanFiftyKm() {
        double feeForOnePointFiveKg = calculator.calculateFee(1.5, 60.0);
        double feeForTwoPointThreeKg = calculator.calculateFee(2.3, 60.0);

        assertThat(feeForOnePointFiveKg).isEqualTo(300_000);
        assertThat(feeForTwoPointThreeKg).isEqualTo(310_000);
    }

    @Test
    void calculateFeeWithDistanceExactlyTenKm() {
        double fee = calculator.calculateFee(1.0, 10.0);

        assertThat(fee).isEqualTo(100_000);
    }

    @Test
    void calculateFeeWithDistanceExactlyFiftyKm() {
        double fee = calculator.calculateFee(1.0, 50.0);

        assertThat(fee).isEqualTo(250_000);
    }

    @Test
    void calculateFeeRejectsNonPositiveWeightOrDistance() {
        assertThatThrownBy(() -> calculator.calculateFee(0, 10))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight and distance must be positive");

        assertThatThrownBy(() -> calculator.calculateFee(1, 0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Weight and distance must be positive");
    }
}
