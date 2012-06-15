#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include "trie.h"

void lz78_compress_string(char *string)
{
	int symbols_read = 0;
	int tokens_written = 0;

	int max_prefix_index = 0;

	struct trie_node *trie = trie_node_new(0);
	
	unsigned char symbol;
	for (int i = 0; i < strlen(string); i++) {
		symbols_read++;
		symbol = string[i];

		struct trie_node *node;
		struct trie_node *last_node = trie;
		int last_prefix_index = 0;
		while ((node = trie_node_retrieve(last_node, symbol))) {
			if (i == strlen(string) - 1)
				break;

			last_node = node;
			last_prefix_index = node->value;
			i++;
			symbol = string[i];
			symbols_read++;
		}

		trie_node_insert(last_node, symbol, ++max_prefix_index);
		tokens_written++;
	}

}

void lz78_compress_from_file(FILE *f)
{
	int symbols_read = 0;
	int tokens_written = 0;

	int max_prefix_index = 0;

	struct trie_node *trie = trie_node_new(0);
	
	unsigned int symbol;
	while ((symbol = fgetc(f)) != -1) {
		symbols_read++;

		struct trie_node *node;
		struct trie_node *last_node = trie;
		int last_prefix_index = 0;
		while ((node = trie_node_retrieve(last_node, (unsigned char)symbol))) {
			int next_symbol = fgetc(f);
			if (next_symbol == -1)
				break;

			last_node = node;
			last_prefix_index = node->value;
			symbol = next_symbol;
			symbols_read++;
		}

		trie_node_insert(last_node, (unsigned char)symbol, ++max_prefix_index);

		// fprintf(stdout, "(%d, %c)\n", last_prefix_index, (unsigned char)symbol);
		tokens_written++;
	}

	fprintf(stderr, "sisään %d tavua; ulos %d koodia\n", symbols_read, max_prefix_index);
}
