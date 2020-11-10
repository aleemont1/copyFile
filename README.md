	#copyFile
Copying a file using the producer-consumer model to copy any file with multiple threads.
The program uses an ArrayBlockingQueue, which is filled from fbyte with bytes arrays (1kbyte
per time until the last time when the length of the array may be shorter) and it is emptied
from bwrite which puts into the output file bytes arrays taken from the head of the Queue.

The main constructor requires 2 parameters (the input and the output file) which are stored in
args[0] and args[1].
