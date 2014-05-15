(ns shimyaku.core-spec
  (:use [speclj.core] [shimyaku.core]))

(def parsed-xml (parse-xml-string (slurp "spec/shimyaku/test.rss")))
(describe "RSS parsing"
  (it "top tag should be RSS"
    (should= :rss (:tag parsed-xml)))
  (it "should list items"
  	(should= 4 (count (get-items parsed-xml))))

  (it "item has title"
  	(let [res (get-items parsed-xml)]
  	(should= "Star City" (:title (first res)) )))

  (it "empty title is labeled untitled"
  	(let [res (get-items parsed-xml)]
	(should= "untitled" (:title (second res)) )))
)

(run-specs)
