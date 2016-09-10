(defproject city "0.1.0-SNAPSHOT"
  :description ""
  :url ""
  :license  {:name "Eclipse Public License"
             :url "http://www.eclipse.org/legal/epl-v10.html"}
  :main city.core
  :clean-targets ^{:protect false} [:target-path "out" "resources/public/cljs" ]
  :repl-options {:init-ns dev.repl}
  :min-lein-version "2.5.3"
  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 [org.clojure/clojurescript "1.9.229"]
                 [cljsjs/d3 "4.2.2-0"]]
  :plugins [[lein-cljsbuild "1.1.1-SNAPSHOT"]]
  :figwheel {:css-dirs ["resources/public/css"]}
  :profiles {:dev {:dependencies [[com.cemerick/piggieback "0.2.1"]
                                  [figwheel-sidecar "0.5.7"]
                                  [binaryage/devtools "0.8.1"]]
                   :source-paths ["dev" "src"]
                   :cljsbuild {:builds [{:id "dev"
                                         :source-paths ["dev" "src"]
                                         :figwheel true
                                         :compiler {:main "city.core"
                                                    :preloads [devtools.preload]
                                                    :asset-path "cljs/out"
                                                    :output-to "resources/public/cljs/main.js"
                                                    :output-dir "resources/public/cljs/out"
                                                    :optimizations :none
                                                    :recompile-dependents true
                                                    :source-map true}}]}}})
