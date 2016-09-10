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
  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-doo "0.1.7"]]
  :figwheel {:css-dirs ["resources/public/css"]}
  :aliases {"cljstest" ["with-profiles" "-dev,+test" "doo" "once"]
            "cljstest-refresh" ["with-profiles" "-dev,+test" "doo" "auto"]}
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
                                                    :source-map true}}]}}
             :test {:test-paths ["test"]
                    :doo {:build "test"
                          :alias {:default [:node]}}
                    :cljsbuild {:builds [{:id "test"
                                          :source-paths ["src" "test"]
                                          :compiler {:output-to "resources/public/cljs/testable.js"
                                                     :output-dir "resources/public/cljs/testable_out"
                                                     :main city.test-runner
                                                     :target :nodejs
                                                     :optimizations :none}}]}}})
