(ns fact.clara
  (:require #?(:clj clara.macros)
            [clara.rules :as clara]
            [clara.rules.engine :as eng]
            [clara.rules.listener :as l]
            #?(:clj [clara.rules.compiler :as clara.compiler])
            fact.namespaces)
  #?(:cljs (:require-macros fact.clara)))

; functions baked in to fact sessions by mk-fact-session
;(defn fact-type-fn
  ;[fact]
  ;(when fact
    ;(if (record? fact)
      ;(let [t (type fact)]
        ;(if (identical? fact.type.core.FactType t)
          ;(.-ftype-name ^fact.type.core.FactType fact)
          ;t))
      ;(type/ftype-name fact))))

(defn fact-ancestors-fn [_] #{:fact/ftype})

; Rule for triggering dispatch functions on insert/insert! for fact-sessions
(clara/defrule ftype-dispatch-rule
  [:ftype-dispatch-fn [{f :dispatch-fn}] (== ?f f)]
  [?ftype <- :fact/ftype]
  =>
  (?f ?ftype))

; Extend the clojure clara/mk-session function to accept sets as well as vectors of rules
#?(:clj
(extend-type clojure.lang.PersistentHashSet
  clara.compiler/IRuleSource
  (load-rules [rule-set]
    (seq rule-set))))

; Modified version of clara function for our version of defsession (mk-static-session)
#?(:clj
(defn get-production [source]
  (let [source-ns (symbol (namespace source))
        source-name (symbol (name source))]
    (-> (get-in @cljs.env/*compiler* [:clara.macros/productions source-ns source-name])
        (#(into {} (map eval %)))))))


; Modified version of clara's defsession that accepts sane arguments (not just namespaces)
#?(:clj
(defmacro mk-static-session
  [sources & options]
  (let [options (apply hash-map (drop-while #(not (keyword? %)) options))
        productions (vec (map get-production sources))
        beta-tree (clara.rules.compiler/to-beta-tree productions)
        beta-network (#'clara.macros/gen-beta-network beta-tree #{})
        alpha-tree (clara.rules.compiler/to-alpha-tree beta-tree)
        alpha-nodes (#'clara.macros/compile-alpha-nodes alpha-tree)
        ]

    `(let [beta-network# ~beta-network
           alpha-nodes# ~alpha-nodes
           productions# '~productions
           options# ~options]
       (clara/assemble-session beta-network#
                               alpha-nodes#
                               productions#
                               options#)))))

;; Support functions for flattening of recursive rule collections accepted by mk-fact-session
(defn coll-of-symbols? [x]
  (every? symbol? x))

(defn flatten-coll
  [coll]
  (filter (complement coll?)
          (rest (tree-seq coll? seq coll))))

(defn flatten-sources
  [sources]
  (if (coll-of-symbols? sources)
    (set sources)
    (set (flatten-coll sources))))

;; isomorphic version of clara/mk-session that accepts recursively nested collections
;; of rules and bakes in the fact-type-fn and ancestors-fn for fact use.
;; *NOTE*: sources is a collection of symbols referring to rule vars
;; (or a collection of collections of ..., or collection of some rule symbols,
;; some collections, etc)
;; See the tests in fact.clara-test for examples.
;; Symbols are necessary for isomorphism, all collection related logic is run in
;; *clojure only*, both cljs and clj mk-session functions get a collection of symbols
;; early eval of those symbols will cause problems. Don't do that.
#?(:clj
(defmacro mk-session
  [sources & options]
  (let [sources (flatten-sources (eval sources))]
    `(fact.namespaces/if-cljs
       (fact.clara/mk-static-session
         ~sources
         ~@options)
       (clara.rules/mk-session
         ~sources
         ~@options)))))
#?(:clj
(defmacro mk-fact-session
  [sources & options]
  `(mk-session ~sources
               :fact-type-fn :ftype-name
               :ancestors-fn fact-ancestors-fn
               ~@options)))
