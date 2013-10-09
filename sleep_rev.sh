#!/usr/bin/env bash
# sleep reverse: spwan a process for each letter
# that sleeps N-i seconds before being displayed
# inspired by sleep sort
f() {
    sleep $1
    echo -n "$2"
}
l=${#1}
for i in `seq 0 $l`; do
    f $((l-i)) "${1:i:1}" &
done
sleep $l
