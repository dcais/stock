#!/bin/bash
sqEcho(){
   echo -e $@
}

## blue to echo
function blue(){
    sqEcho "\033[34m $@\033[0m"
}

## green to echo
function green(){
    sqEcho "\033[32m $@\033[0m"
}

## Error to warning with blink
function bred(){
    sqEcho "\033[31m\033[01m\033[05m $@\033[0m"
}

## Error to warning with blink
function byellow(){
    sqEcho "\033[33m\033[01m\033[05m $@\033[0m"
}


## Error
function red(){
    sqEcho "\033[31m\033[01m $@\033[0m"
}

## warning
function yellow(){
    sqEcho "\033[33m\033[01m $@\033[0m"
}
