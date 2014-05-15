(ns shimyaku.repository-spec
	(:use [speclj.core] [shimyaku.feed-repository :as feed-repo]))

(describe "Feed reading"
  (it "should list titles of feeds"
    (should= "test" (:title (first (feed-repo/find-all))))
    (should= "test2" (:title (second (feed-repo/find-all))))))

(run-specs)
