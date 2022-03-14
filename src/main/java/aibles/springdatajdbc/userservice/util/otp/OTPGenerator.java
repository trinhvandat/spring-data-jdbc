package aibles.springdatajdbc.userservice.util.otp;

import org.springframework.stereotype.Component;

import java.util.Random;
@Component
public class OTPGenerator {

    public String execute() {
        StringBuilder otp = new StringBuilder();
        Random random = new Random();

        int i;
        final int otpLength = 6;

        for (i = 0; i < otpLength; i++) {
            int randomNumber = random.nextInt(9);
            otp.append(randomNumber);
        }

        return otp.toString();
    }


}
