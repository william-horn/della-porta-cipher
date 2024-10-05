
import serial
import time

# Documentation: https://edu.irobot.com/learning-library/getting-started-with-create-2
ser = serial.Serial("COM3", 115200)

dir1 = b'\x91\x00\x00\x00\x32'

ser.write(b'\x80') # 128 Start
time.sleep(1)
ser.write(b'\x07') # 7 Reset
time.sleep(10)
ser.write(b'\x80') # 128 Start
time.sleep(1)
ser.write(b'\x83') # 131 Enter Safe Mode
time.sleep(1)

ser.write(dir1)

time.sleep(2)

# ser.write(b'\x91\x00\x00\x00\x32') # 145 Drive Direct RV = 0 LV = 50
# time.sleep(5) # Drive one-wheeled for 5 seconds

# ser.write(b'\x91\x01\x2C\xFE\xD4') # 145 Drive Direct RV = 300 LV = -300
# time.sleep(5) # Spin for 5 seconds

ser.write(b'\x91\x00\x00\x00\x00') # 145 Drive Direct RV = 0LV = 0 STOP
ser.close()
