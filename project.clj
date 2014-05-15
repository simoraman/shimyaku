(defproject shimyaku "1.0.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [
          [org.clojure/clojure "1.6.0"]
          [compojure "1.1.5"]
          [org.clojure/data.zip "0.1.1"]
          [ring/ring-json "0.2.0"]
          [com.novemberain/monger "1.8.0"]]
  :plugins [[lein-ring "0.8.2"]
            [speclj "2.5.0"]
            [lein-cljsbuild "1.0.3"]]
  :dev-dependencies [[speclj "2.5.0"]]
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]
                        [speclj "2.5.0"]]
         }
  }
  :test-path "spec/"
  :ring {:handler shimyaku.handler/app}

)
