## noric-bot

A bot to stress test [text-based MMORPGs](https://en.wikipedia.org/wiki/MUD) written in [Scala 3](https://dotty.epfl.ch).

### OSX Quickstart

* git clone
* run the precompiled OSX binary that was added to git repo
  * `$ HOST=yourmud.com PORT=4000 bin/osx-noric-bot 17`
  * 👉 17 bots will connect to yourmud.com:4000 and spam one cmd every 450ms
* this program scales gracefully to 1000+ bots

### How to support your MUD

* PRs welcome!
* MUD-specific support is provided by each MUD implementing `LoginCmds` and `NextCmds` for each MUD.
* See [NoricMud.scala](https://github.com/ryanberckmans/noric-bot/blob/master/src/main/scala/NoricMud.scala), the cmds for the author's closed-source MUD.

### Technology

noric-bot is written in [Scala 3](https://dotty.epfl.ch) and compiled to native using graalvm. noric-bot doesn't yet use many of the new features in Scala 3.

(noric-bot uses some Java APIs in lieu of Scala libraries because the latter aren't yet available for Scala 3. I think it's possible to use libraries compiled with Scala 2 within a Scala 3 codebase and would be interested to learn how.)

Are you a Scala expert? I'd greatly appreciate PRs that improve the "Scala-ness" of this project.

Excellent IDE support for this toolchain is provided by [atom-ide-scala](https://github.com/laughedelic/atom-ide-scala).

Scala is my favorite language and I'm excited for Scala 3. I think Scala is the best mainstream language with a sophisticated type system. Sophisticated type systems are money in the bank. Going from TypeScript to Scala is like going from JavaScript to TypeScript.

Scala 3 launches in 2020 after a new compiler was written over a five year period. The new compiler is named "Dotty".

See [A Tour of Scala 3](https://www.youtube.com/watch?v=_Rnrx2lo9cw).

### Roadmap

I'd like to improve the level of documentation/comments in the code.

Native compilation is cool. The binary starts up instantly, unlike the jvm. The current roadmap is to expand [Config.scala](https://github.com/ryanberckmans/noric-bot/blob/master/src/main/scala/Config.scala) to allow greater custom behavior without recompilation.

### Compile & Run

* install Scala `brew install scala sbt lampepfl/brew/dotty`
* install [graalvm](https://www.graalvm.org) and ensure `native-image` is on your `PATH`
  * 👉you can skip this step and run non-natively with `sbt run`
* compile `sbt compile`
* compile to native `sbt show graalvm-native-image:packageBin` or the alias `sbt b`
* run with `bin/noric-bot` which is a symlink to the binary produced by the build

### Personal notes from the author to himself

noric-bot is a port of noricmud's ruby bot.rb.

On my macbook pro use alias `graalvm-enable` and then run `sbt b`.

#### how to run a noricmud stress test

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
