#!/bin/sh

gnuplot <<EOF >doc/trie_suorituskyky_fraasin_pituus.png
set terminal pngcairo
set xlabel "fraasin pituus (tavua)"
set ylabel "sekuntia / 1 milj. hakua (haarautumisaste 256)"
plot 'doc/trie_suorituskyky_fraasin_pituus.csv' with lines
EOF
