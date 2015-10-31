shadow-build-example
====================

Minimal shadow-build example.


```
git clone https://github.com/thheller/shadow-build-example
```

## Things to try

REPL with code reloading (like lein-figwheel)
```
lein run -m build/dev-repl
```
Open the ```demo.html``` in a browser. The running process acts like a REPL and will execute the compiled input in the browser if one is connected.

Compile code once
```
lein run -m build/dev
```

Compile and re-compile when files change without reloading on the client
```
lein run -m build/dev-reload
```

`:advanced` compiled release build
```
lein run -m build/production
```

Run all cljs.test tests via node
```
lein run -m build/test-all
```

Run tests affected by a given namespace (eg. all tests that use the namespace directly)
```
lein run -m buid/test-affected demo.app
```

Test everything once, the on code change test only those directly affected by the changes (eg. only files that directly import the changes namespaces)
```
lein run -m build/autotest
```

See: https://github.com/thheller/shadow-build-example/blob/master/src/dev/build.clj
