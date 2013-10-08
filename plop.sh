#!/bin/bash
TMPPWD=$PWD;for letter in $(echo -e "$1" | sed 's/./&\n/g') ; do mkdir $letter; cd $letter; done; while [ "$PWD" != "$TMPPWD" ]; do echo $(basename $PWD); cd ..; done;

