#include <stdlib.h>
#include <stdio.h>

#include "trie.h"
#include "util.h"

static struct trie_node *trie_node_find_child(struct trie_node *node, unsigned char key);
static void trie_node_add_child(struct trie_node *node, struct trie_node *child);

struct trie_node *trie_node_new(unsigned char key)
{
	struct trie_node *node = xmalloc(sizeof(*node));
	node->key = key;
	node->node_is_valid = 0;
	node->size_children_bits = 0;
	node->children = xcalloc(SIZE_CHILDREN(node), sizeof(struct trie_node *));

	return node;
}

void trie_node_insert(struct trie_node *node, unsigned char key, int value)
{
	struct trie_node *child = trie_node_find_child(node, key);
	if (child == NULL) {
		child = trie_node_new(key);
		trie_node_add_child(node, child);
	} else {
		child->key = key;
	}

	child->node_is_valid = 1;
	child->value = value;
}

struct trie_node *trie_node_retrieve(struct trie_node *node, unsigned char key)
{
	return trie_node_find_child(node, key);
}

static int binary_search(struct trie_node *node, unsigned char key)
{
	int left = 0;
	int right = SIZE_CHILDREN(node) - 1;

	while (right >= left) {
		int i = (left + right) / 2;

		if (node->children[i] == NULL || key < node->children[i]->key) {
			right = i - 1;
		} else if (key > node->children[i]->key) {
			left = i + 1;
		} else {
			return i;
		}
        }

        return -1;
    }


static struct trie_node *trie_node_find_child(struct trie_node *node, unsigned char key)
{
	int i = binary_search(node, key);
	if (i == -1)
		return NULL;

	return node->children[i];
}

static void trie_node_add_child(struct trie_node *node, struct trie_node *child)
{
	int first_free_index = 0;
	for (int i = SIZE_CHILDREN(node) - 1; i >= 0; i--) {
		if (node->children[i]) {
			first_free_index = i + 1;
			break;
		}
	}
	
	if (first_free_index == SIZE_CHILDREN(node)) {
		node->size_children_bits++;
		size_t new_size = SIZE_CHILDREN(node);
		node->children = xrealloc(node->children, new_size*sizeof(struct trie_node *));
		for (int i = first_free_index + 1; i < SIZE_CHILDREN(node); i++)
			node->children[i] = NULL;
	}
	node->children[first_free_index] = child;
	for (int i = first_free_index; i > 0
		     && node->children[i]->key < node->children[i - 1]->key; i--) {
		struct trie_node *tmp = node->children[i];
		node->children[i] = node->children[i - 1];
		node->children[i - 1] = tmp;
	}
}
