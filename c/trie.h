#ifndef __TRIE_H__
#define __TRIE_H__

#define SIZE_CHILDREN(node) (1 << (node)->size_children_bits)

struct trie_node {
	unsigned char key;

	char node_is_valid;
	int value;

        /* children-taulukon koko on 2^size_children_bits.
	 * Käytä SIZE_CHILDREN-makroa koon laskemiseen.
	 */
	unsigned char size_children_bits;
	struct trie_node **children;
};

struct trie_node *trie_node_new(unsigned char key);
void trie_node_insert(struct trie_node *node, unsigned char key, int value);
struct trie_node *trie_node_retrieve(struct trie_node *node, unsigned char key);

#endif /* __TRIE_H__ */
