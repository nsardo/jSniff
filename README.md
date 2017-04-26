# jSniff
A Simple Java Port Sniffer

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
