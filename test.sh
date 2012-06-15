#!/bin/bash

PATH=~jgsavola/bin:$PATH

set -e

for prog in lz78 compress gzip
do
    dstdir="testdata.$prog"
    rm -rf "$dstdir"
    cp -a testdata $dstdir

    echo ========================
    echo "Pakkaus: $prog"
    echo ========================

    if [ "$prog" = lz78 ]
    then
	/usr/bin/time java -jar dist/tiraht.jar -v $dstdir/*
    elif [ "$prog" = compress ]
    then
	/usr/bin/time compress -f -v $dstdir/* 
    elif [ "$prog" = gzip ]
    then
	/usr/bin/time gzip -f -v $dstdir/* && true
    fi
done

du -s testdata testdata.lz78 testdata.compress testdata.gzip

for prog in lz78 compress gzip
do
    dstdir="testdata.$prog"

    echo ========================
    echo "Purku: $prog"
    echo ========================

    if [ "$prog" = lz78 ]
    then
	/usr/bin/time java -jar dist/tiraht.jar -d $dstdir/*.lz78
    elif [ "$prog" = compress ]
    then
	/usr/bin/time compress -d $dstdir/*.Z
    elif [ "$prog" = gzip ]
    then
	/usr/bin/time gzip -d $dstdir/*.gz
    fi

    for f in $dstdir/*
    do
	cmp $f testdata/`basename $f`
    done

    echo "Purku OK!"
done

