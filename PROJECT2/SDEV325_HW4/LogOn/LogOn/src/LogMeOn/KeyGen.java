package LogMeOn;

import java.util.Random;

public class KeyGen {

    static char[] OTP(int length) {
        System.out.println( "Generating OTP" );
        String numbers = "0123456789";
        Random random = new Random();
        char[] otp = new char[length];
        for (int i = 0; i < length; i++) {
            otp[i] = numbers.charAt( random.nextInt( numbers.length() ) );
        }

        return otp;


    }



}
