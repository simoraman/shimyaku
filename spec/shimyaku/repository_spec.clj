(ns shimyaku.repository-spec
	(:use [speclj.core] [shimyaku.feed-repository :as feed-repo]))

(describe "Feed reading"
  (it "should list titles of feeds"
    (should= "Feed 2" (:title (first (feed-repo/find-all))))
    (should= "Feed 1" (:title (second (feed-repo/find-all))))))

(run-specs)
