#!/bin/bash

PATH=~jgsavola/bin:$PATH

set -e

for prog in unlimited 100k_reset 100k_freeze
do
    dstdir="testdata.$prog"
    rm -rf "$dstdir"
    cp -a testdata $dstdir

    echo ========================
    echo "Pakkaus: $prog"
    echo ========================

    if [ "$prog" = unlimited ]
    then
	/usr/bin/time java -jar dist/tiraht.jar -v $dstdir/*
    elif [ "$prog" = 100k_reset ]
    then
	/usr/bin/time java -jar dist/tiraht.jar -v --dict-size 100000 --dict-fill-up-strategy reset $dstdir/*
    elif [ "$prog" = 100k_freeze ]
    then
	/usr/bin/time java -jar dist/tiraht.jar -v --dict-size 100000 --dict-fill-up-strategy freeze $dstdir/*
    fi
done

du -s testdata testdata.unlimited testdata.100k_reset testdata.100k_freeze

for prog in unlimited 100k_reset 100k_freeze
do
    dstdir="testdata.$prog"

    echo ========================
    echo "Purku: $prog"
    echo ========================

    /usr/bin/time java -jar dist/tiraht.jar -d $dstdir/*.lz78

    for f in $dstdir/*
    do
	cmp $f testdata/`basename $f`
    done

    echo "Purku OK!"
done

