(ns shimyaku.core
  (:require [clojure.zip :as zip] [clojure.xml :as xml])
  (:use [clojure.data.zip.xml] [shimyaku.feed-repository :as repo])
  (:import [java.io ByteArrayInputStream]))

(defn string-to-stream [string]
  (ByteArrayInputStream. (.getBytes (.trim string))))

(defn parse-xml-string[xml]
  (xml/parse (string-to-stream xml)))

(defn- gather-items[parsed-xml]
  (map (juxt
        #(xml1-> % :title text)
        #(xml1-> % :description text))
       (xml-> (zip/xml-zip parsed-xml) :channel :item)))

(defn- create-record [items-vector]
  (for[i items-vector]
          {:title (or (first i) "untitled") :description (second i)}))

(defn get-items [parsed-xml]
  (create-record (gather-items parsed-xml)))

(defn get-feeds []
  (repo/find-all))

(defn get-feeditems []
  (let [feeds (repo/find-all)]
    (filter (fn [x] (not= (:read x) true)) (mapcat :items feeds))))
