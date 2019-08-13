## noric-bot

noric-bot is a port of noricmud's ruby bot.rb.

Run it with `bin/noric-bot <# of bots to run>`

`bin/noric-bot` is a symlink that requires graalvm native-image compilation. On my macbook pro use alias `graalvm-enable` and then run `sbt b`

See Config.scala for environment variables.

### how to run a noricmud stress test

0. compile noricmud with sanity checks disabled
1. run noricmud locally outside of sbt:

```
run packaged MUD (without sbt) on OSX without using docker
    build in sbt with
        universal:packageBin
    run with
        cd target/universal && unzip noricmud-*zip && cd noricmud-*/
        JAVA_OPTS="-Xmx512m -Xms512m -Dgame.storage.init=init -Dcom.sun.management.jmxremote.port=9090 -Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false" bin/noricmud
```

2. run `bin/noric-bot <# of bots to run>`

3. or use parallel: `seq 100 | parallel --ungroup -j 100 -n0 bin/noric-bot 1 > /dev/null`

### gotchas

problem: hit limit of max open file handles

```
parallel: Warning: Only enough file handles to run 60 jobs in parallel.
parallel: Warning: Running 'parallel -j0 -N 60 --pipe parallel -j0' or
parallel: Warning: raising 'ulimit -n' or 'nofile' in /etc/security/limits.conf
parallel: Warning: or /proc/sys/fs/file-max may help.
```

solution: https://gist.github.com/tombigel/d503800a282fcadbee14b537735d202c
