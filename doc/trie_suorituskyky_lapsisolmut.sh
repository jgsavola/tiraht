#!/bin/sh

gnuplot <<EOF >doc/trie_suorituskyky_lapsisolmut.png
set terminal pngcairo
set xlabel "lapsisolmujen määrä"
set ylabel "sekuntia / 10 milj. hakua (fraasin pituus 6 tavua)"
plot 'doc/trie_suorituskyky_lapsisolmut.csv' with lines
EOF
