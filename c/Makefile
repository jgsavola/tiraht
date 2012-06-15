CFLAGS=-std=c99 -O2 -Wall -g

all: tiraht_c

objects = main.o lz78.o trie.o util.o

tiraht_c: $(objects)
	$(CC) -o $@ $(objects)

clean:
	rm -f tiraht_c $(objects)
distclean: clean
	rm -f *~ core
