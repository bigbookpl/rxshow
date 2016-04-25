package pl.bigbook;

import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import rx.subjects.PublishSubject;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ArduinoReader implements SerialPortEventListener {
    SerialPort serialPort;
    // Na windowsie domyślnie posługujemy się portem COM3
    private static final String PORT_NAME = "/dev/cu.usbmodem1411";

    private BufferedReader input;

    /** Milliseconds to block while waiting for port open */
    private static final int TIME_OUT = 2000;
    /** Default bits per second for COM port. */
    private static final int DATA_RATE = 9600;
    private PublishSubject<String> subject;

    public ArduinoReader() {
        subject = PublishSubject.create();

    }

    public void initialize() {
        CommPortIdentifier portId;
        try {
            portId = CommPortIdentifier.getPortIdentifier(PORT_NAME);

            // otwieramy i konfigurujemy port
            serialPort = (SerialPort) portId.open(this.getClass().getName(),
                    TIME_OUT);
            serialPort.setSerialPortParams(DATA_RATE, SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            input = new BufferedReader(new InputStreamReader(
                    serialPort.getInputStream()));



            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);

        } catch (Exception e) {
            System.err.println(e.toString());
        }
    }

    public void close() {
        if (serialPort != null) {
            serialPort.removeEventListener();
            serialPort.close();
        }
    }

    /**
     * Metoda nasłuchuje na dane na wskazanym porcie i wyświetla je w konsoli
     */
    public void serialEvent(SerialPortEvent oEvent) {
        if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
            try {
                String inputLine = input.readLine();
                this.subject.onNext(inputLine);
            } catch (Exception e) {
                System.err.println(e.toString());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        ArduinoReader main = new ArduinoReader();
        main.initialize();
//        main.subject.subscribe(System.out::println);
        main.subject.subscribe(s -> {
            System.out.println("1|" + s.length());
        });
        main.subject.subscribe(s -> {
            System.out.println("->" + s);
        });
        Thread.sleep(1000000);
        main.close();
    }
}