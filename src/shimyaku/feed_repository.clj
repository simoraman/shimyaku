(ns shimyaku.feed-repository
  (:require [monger.core :as mg] [monger.collection :as mc])
  (:import [com.mongodb MongoOptions ServerAddress]))

(mg/connect-via-uri! "mongodb://127.0.0.1/local")

(defn find-all []
  (mc/find-maps "feeds"))
