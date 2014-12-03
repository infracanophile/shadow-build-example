shadow-build-example
====================

Minimal shadow-build example.

```
git clone https://github.com/thheller/shadow-build-example
lein cljs-dev
```

In another shell
```
open demo.html
```

Now edit ```src/cljs/demo/app.cljs``` wait for the compilation to finish (should be a couple milliseconds) and reload the demo.html.

Then stop the ```lein cljs-dev``` task and execute ```lein cljs-prod``` then reload demo.html. It will now load the advanced optimized Javascript which is substantially less than the dev version.

For more advanced configuration options refer directly to https://github.com/thheller/shadow-build.
