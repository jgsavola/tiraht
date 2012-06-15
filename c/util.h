#ifndef __UTIL_H__
#define __UTIL_H__

#include <stdlib.h>

void *xmalloc(size_t size);
void *xcalloc(size_t nmemb, size_t size);
void *xrealloc(void *ptr, size_t size);

#endif /* __UTIL_H__ */
