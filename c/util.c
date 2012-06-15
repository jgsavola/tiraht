#include <stdio.h>
#include <stdlib.h>

#include "util.h"

void *xmalloc(size_t size)
{
	void *p = malloc(size);
	if (!p) {
		fprintf(stderr, "malloc(%zd) failed\n", size);
		exit(1);
	}

	return p;
}

void *xcalloc(size_t nmemb, size_t size)
{
	void *p = calloc(nmemb, size);
	if (!p) {
		fprintf(stderr, "calloc(%zd, %zd) failed\n", nmemb, size);
		exit(1);
	}

	return p;
}

void *xrealloc(void *ptr, size_t size)
{
	void *newptr = realloc(ptr, size);
	if (!newptr) {
		fprintf(stderr, "realloc(%zd) failed\n", size);
		exit(1);
	}

	return newptr;
}
	
