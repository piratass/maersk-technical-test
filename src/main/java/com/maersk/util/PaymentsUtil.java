package com.maersk.util;

import com.maersk.constants.PaymentsConstants;
import com.maersk.errorhandler.PaymentsGenericClientException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Component
@Slf4j
public class PaymentsUtil {
    public static <T> T notNull(T object,String message){
        if(object == null){
            throw new NullPointerException(String.format(message));
        } else {
            return object;
        }
    }

    public static void validateCard(Integer cvv){
        Boolean stateValidate = Boolean.FALSE;
        List<Integer> listValidateCard = new ArrayList<>();
        listValidateCard.add(PaymentsConstants.VALUE_AMEX);
        listValidateCard.add(PaymentsConstants.VALUE_VISA_MASTERCARD);
        for (Integer codeCard : listValidateCard){
            if(cvv == codeCard) {
                stateValidate = Boolean.TRUE;
                break;
            }
        }
        if(!stateValidate)
            throw new PaymentsGenericClientException("invalid card code");
    }

    public static void validateEmail(String email){
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(email);
        if (mather.find() != true) {
            throw new PaymentsGenericClientException("invalid email");
        }
    }

    public static void validateLuhn(Long cardNum){
        String value = cardNum.toString();
        int sum = 0;
        boolean alternate = false;
        for (int i = value.length() - 1; i >= 0; i--)
        {
            int n = Integer.parseInt(value.substring(i, i + 1));
            if (alternate)
            {
                n *= 2;
                if (n > 9)
                {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        if(sum % 10 != 0){
            throw new PaymentsGenericClientException("invalid card number");
        }
    }

    public static void validateExpirationYear(Integer year){
        Calendar date = new GregorianCalendar();
        int valueYear = date.get(Calendar.YEAR);
        Boolean validateYear = Boolean.FALSE;
        if(valueYear <= year && (valueYear+5)>=year){
            validateYear = Boolean.TRUE;
        }
        if(!validateYear){
            throw new PaymentsGenericClientException("invalid expiration year");
        }
    }

    public static void validateMonth(Integer month){
        Boolean validateMoth = Boolean.FALSE;
        if(1 <= month && 12 >= month){
            validateMoth = Boolean.TRUE;
        }
        if(!validateMoth){
            throw new PaymentsGenericClientException("invalid expiration moth");
        }
    }

    public static void validateHeaderToken(String token){
        String[] values = token.split(" ");
        if(!values[1].substring(0,2).equals("pk")){
            throw new PaymentsGenericClientException("invalid partner");
        }
    }
}
