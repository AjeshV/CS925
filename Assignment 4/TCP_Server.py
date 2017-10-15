#server.py
#!/usr/bin/env python
import socket
from threading import Thread
from SocketServer import ThreadingMixIn

#Server IP address
TCP_IP = socket.gethostbyaddr("10.10.0.1")[0]
TCP_PORT = 50001
BUFFER_SIZE = 1024

print 'TCP_IP=',TCP_IP
print 'TCP_PORT=',TCP_PORT

class ClientThread(Thread):
#Looping the transactions
for i in range(10): {
#Initiating time
clock_start = time.clock()
time_start = time.time()
    def __init__(self,ip,port,sock):
        Thread.__init__(self)
        self.ip = ip
        self.port = port
        self.sock = sock
        print "+ip+":"+str(port)

    def run(self):
        filename='File1.avi'
        f = open(filename,'rb')
        while True:
            l = f.read(BUFFER_SIZE)
            while (l):
                self.sock.send(l)
                 l = f.read(BUFFER_SIZE)
            if not l:
                f.close()
                self.sock.close()
                break

tcpsock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
tcpsock.setsockopt(socket.SOL_SOCKET, socket.SO_REUSEADDR, 1)
tcpsock.bind((TCP_IP, TCP_PORT))
threads = []

clock_end = time.clock()
time_end = time.time()

while True:
    tcpsock.listen(5)
    print "Awaiting connections..."
    (conn, (ip,port)) = tcpsock.accept()
    print 'Received connection from ', (ip,port)
    newthread = ClientThread(ip,port,conn)
    newthread.start()
    threads.append(newthread)

#Calculating time taken to send the file
duration_clock = clock_end - clock_start
print 'clock:  start = ',clock_start, ' end = ',clock_end
print 'clock:  duration clock = ', duration_clock

duration_time = time_end - time_start
print 'time:  start = ',time_start, ' end = ',time_end
print 'time:  duration time = ', duration_time
}	
for t in threads:
    t.join()
