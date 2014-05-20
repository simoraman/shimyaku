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

(describe "Feed items"
  (it "should not return items marked as read"
      (let [feeditems (get-feeditems)]
        (should= 3 (count feeditems))) ))
(run-specs)
