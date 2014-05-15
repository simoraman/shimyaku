(ns shimyaku.handler
  (:use [compojure.core] [shimyaku.core])
  (:require [compojure.handler :as handler]
            [compojure.route :as route]
  			[ring.middleware.json :as middleware]
        [ring.util.response :as resp]
        [monger.json]
        ))

(def parsed-xml (parse-xml-string (slurp "spec/shimyaku/test.rss")))

(defroutes app-routes
  (GET "/" [] (resp/file-response "index.html" {:root "public"}))
  (GET "/feeds" []  (get-feeds))
  (route/resources "/")
  (route/files "/" {:root (str (System/getProperty "user.dir") "/public")})
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
        (middleware/wrap-json-body)
        (middleware/wrap-json-response)))
