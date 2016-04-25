package pl.bigbook;

import gnu.io.CommPortIdentifier;

import java.util.Enumeration;

/**
 * Created by kkonieczny on 24.04.2016.
 */
public class Detector {


        public static void main(String[] args) {
            System.out.println("Program started");
            CommPortIdentifier serialPortId;
            Enumeration enumComm;

            enumComm = CommPortIdentifier.getPortIdentifiers();
            while (enumComm.hasMoreElements()) {
                serialPortId = (CommPortIdentifier) enumComm.nextElement();
                if (serialPortId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                    System.out.println(serialPortId.getName());
                }
            }

            System.out.println("Finished successfully");
        }
}
