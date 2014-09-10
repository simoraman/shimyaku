(ns shimyaku.handler
  (:use [compojure.core] [shimyaku.core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
            [ring.middleware.json :as middleware]
            [ring.util.response :refer [response]]
            [ring.middleware.format :refer [wrap-restful-format]]
            [monger.json]
        ))

(def parsed-xml (parse-xml-string (slurp "spec/shimyaku/test.rss")))

(defroutes app-routes
  (GET "/" [] (ring.util.response/file-response "index.html" {:root "public"}))
  (GET "/feeds" []  (get-feeds))
  (POST "/feeds" {body :params} (response (insert-or-update body)))
  (GET "/feeditems" [] (get-feeditems))
  (route/resources "/")
  (route/files "/" {:root (str (System/getProperty "user.dir") "/public")})
  (route/not-found "Not Found"))

(def app
  (-> app-routes
      (wrap-restful-format)
      (middleware/wrap-json-response)
      (handler/api)))
