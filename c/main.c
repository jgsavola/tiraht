#include <stdio.h>
#include <stdlib.h>

#include "lz78.h"

int main(int argc, char **argv)
{
//	lz78_compress_string("a date at a date");

//	lz78_compress_string("Alice was beginning to get very tired of sitting by her sister on the bank, and of having nothing to do:  once or twice she had peeped into the book her sister was reading, but it had nopictures or conversations in it, `and what is the use of a book,'thought Alice `without pictures or conversation?'");

	for (int i = 1; i < argc; i++) {
		FILE *f = fopen(argv[i], "rb");
		if (!f) {
			fprintf(stderr, "Cannot open %s\n", argv[i]);
			exit(1);
		}
		lz78_compress_from_file(f);
	}

	exit(0);
}

/**

a 1
  2
d 3

**/
