shadow-build-example
====================

Minimal shadow-build example.

```
git clone https://github.com/thheller/shadow-build-example
lein run -m build/dev
```

Open the ```demo.html``` in a browser.

Now edit ```src/cljs/demo/app.cljs``` wait for the compilation to finish (should be a couple milliseconds) and reload the demo.html.

Then stop the lein task and execute ```lein run -m build/production``` then reload demo.html. It will now load the advanced optimized Javascript which is substantially smaller than the dev version.

For more advanced configuration options refer directly to https://github.com/thheller/shadow-build.


Automated Testing
=====================

```
lein run -m build/autotest
```

This will compile CLJS as above and automatically execute tests affected by code changes. Tests will execute with "node" (v0.12+ required).