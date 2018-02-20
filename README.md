# jSniff
A Simple Java Port Sniffer

[![GitHub version](https://badge.fury.io/gh/nsardo%2FjSniff.svg)](https://badge.fury.io/gh/nsardo%2FjSniff)

## Command Signature: ##

```
tcp <url-or-ip> <begin-port-range> <end-port-range>
```

## To Use the .Jar in **Dist/** ##

cd into dist/

then (as an example)

```
java -jar jSniff.jar tcp google.com 79 81
```

It will return:

port 79 is not open.

port 81 is not open.

There is a TCP port open at: 80

And the total time it took for the scan.

## Clone, download the zip, or just grab the jar file from the release:
[RELEASE](https://github.com/nsardo/jSniff/releases)
