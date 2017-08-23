package com.huhtamaki.marhuh.simplenotifications;

/**
 * Created by marhuh on 23.8.2017.
 */

public class ValidateIP {

    public static boolean isValidIp(String ipStr) {

        int port = -1; // -1 Port not specified.
        String[] ipAndPort = ipStr.split("-");

        try {
            port = Integer.parseInt(ipAndPort[1]);
        } catch(ArrayIndexOutOfBoundsException e) {
            port = -1;
            return false;
        } catch(NumberFormatException e) {
            // A port was given but faulty.
            e.printStackTrace();
            return false; // Not numeric port.
        }

        if(port != -1 && (port < 0 || port > 65535)) {

            (new Exception("Port out of range: " + port)).printStackTrace();
            return false; // A port was specified but not between 0-65535.

        }

        String[] ip = ipAndPort[0].split("\\."); //
        int[] ipDigits = new int[4];

        if(ip.length != 4) {

            (new Exception("Invalid number of parts in ip address: " + ip.length)).printStackTrace();
            return false; // Must have four sections (ipv4)
        }

        for(int i = 0; i < ipDigits.length; i++) {

            try {
                ipDigits[i] = Integer.parseInt(ip[i]);
            } catch(NumberFormatException e) {
                e.printStackTrace();
                return false; // Not numeric part of an ip-address.
            }

            if(ipDigits[i] < 0 || ipDigits[i] > 255) {

                (new Exception("Ip digit #" + i + " is out of range 0-255: " + ipDigits[i])).printStackTrace();
                return false; // Allowed range 0–255
            }

        }

        return true;

    }
}
