#include <stdio.h>
#include <stdlib.h>

#include "lz78.h"

int main(int argc, char **argv)
{
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
