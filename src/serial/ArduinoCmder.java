package serial;

import com.pi4j.wiringpi.Serial;

public class ArduinoCmder {
    private static final ArduinoCmder instance;
    public static ArduinoCmder getInstance() {
        return instance;
    }

    static {
        instance = new ArduinoCmder();
    }

    private ArduinoCmder() {
        // initialize the serial connection to the arduino
        deviceFileDescriptor = Serial.serialOpen(DEVICE_NAME, BAUD_RATE);

        // check for error opening serial connection
        if (deviceFileDescriptor == -1) {
            System.err.println("ERROR: could not open serial connection");
        }
    }

    // device name of the arduino
    private static final String DEVICE_NAME = "/dev/ttyACM0";

    // file descriptor of the arduino
    // this is given when we open the serial connection
    private int deviceFileDescriptor;

    // baud rate
    // must be the same as on the arduino
    private static final int BAUD_RATE = 9600;

    // commands
    public static final byte CMD_00 = 0x00;
    public static final byte CMD_01 = 0x01;
    public static final byte CMD_02 = 0x02;

    public static final int CMD_DATA_LEN = 32;
    public static final int CMD_BUF_LEN = CMD_DATA_LEN + 1;

    // command buffer
    // used for holding the command id and data to send the command
    private static final byte[] cmdBuf;
    static {
        cmdBuf = new byte[CMD_BUF_LEN];
    }

    public void sendCmd(byte cmdId, byte[] data) {
        // make sure that the length of the data array is
        // correct
        if (data.length != CMD_DATA_LEN) {
            System.err.println("ERROR: wrong data len: " + data.length);
            return;
        }

        // write the command id into the command buffer
        cmdBuf[0] = cmdId;

        // write the command data into the command buffer
        for (int i = 0; i < CMD_DATA_LEN; i++) {
            cmdBuf[1 + i] = data[i];
        }

        // send the command over the serial connection
        Serial.serialPutBytes(deviceFileDescriptor, cmdBuf, CMD_BUF_LEN);
    }

    public void recvData() { /* TODO */ }
}
