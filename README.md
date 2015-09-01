shadow-build-example
====================

Minimal shadow-build example.


```
git clone https://github.com/thheller/shadow-build-example
lein run -m build/dev
```

See: https://github.com/thheller/shadow-build-example/blob/master/src/dev/build.clj

All we do is call the build.dev function via lein, it is all just Clojure code.

Open the ```demo.html``` in a browser.

Now edit ```src/cljs/demo/app.cljs``` wait for the compilation to finish (should be a couple milliseconds) and reload the demo.html.

```
lein run -m build/live-reload
```

Will automate the reload for you.

Then stop the lein task and execute ```lein run -m build/production``` then reload demo.html. It will now load the advanced optimized Javascript which is substantially smaller than the dev version.

For more advanced configuration options refer directly to https://github.com/thheller/shadow-build.


Automated Testing
=====================

```
lein run -m build/autotest
```

This will compile CLJS as above and automatically execute tests affected by code changes. Tests will execute with "node" (v0.12+ required).