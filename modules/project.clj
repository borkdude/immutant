 (defproject org.immutant/immutant-modules-parent "1.0.3-SNAPSHOT"
  :description "Parent for all modules"
  :parent [org.immutant/immutant-parent _ :relative-path "../pom.xml"]
  :plugins [[lein-modules "0.1.0-SNAPSHOT"]]
  :packaging "pom"
  :modules  {:inherited {:dependencies [[org.immutant/immutant-clojure-test-support _ :scope "test"]
                                        [org.immutant/immutant-as-test-support _ :scope "test"]]}})