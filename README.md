# Lettuce Cutter GUI
This is the code for the GUI that runs on the Raspberry PI with the touch
screen. It is written in Java using the Swing framework. The pi4j and WiringPi
libraries are also used for access to the GPIO pins on the Raspberry PI, as
well as access to the serial port on the Raspberry PI to allow bidirectional
communication over a USB cable with the Arduino.

## Compiling the Code
To compile the code, run the build.sh script. You may need to give it execute
permission before running it.
```sh
chmod +x build.sh
./build.sh
```

## Running the code
To run the code, run the run.sh script. You may need to give it execute
permission before running it.
```sh
chmod +x run.sh
./run.sh
```
