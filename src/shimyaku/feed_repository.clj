(ns shimyaku.feed-repository
  (:require [monger.core :as mg]
            [monger.collection :as mc]
            [carica.core :refer [config]])
  (:import [com.mongodb MongoOptions ServerAddress]))

(def db-uri (config :db))
(def uri (mg/connect-via-uri db-uri))
(def feeds-collection (config :collection))

(defn find-all []
  (let [db (:db uri)]
    (mc/find-maps db feeds-collection)))

(defn insert-feed [feed]
  (let [db (:db uri)]
    (mc/insert-and-return db feeds-collection feed)))

(defn update-feed [feed]
  (let [db (:db uri)]
    (mc/save-and-return db feeds-collection feed)))

(defn drop-feeds []
  (let [db (:db uri)]
    (mc/drop db feeds-collection))  )
