(ns shimyaku.repository-spec
	(:use [speclj.core] [shimyaku.feed-repository :as feed-repo]))

(defn initialize-db []
  (feed-repo/insert-feed {:title "Feed 2"})
  (feed-repo/insert-feed {:title "Feed 1"}))

(defn finalize-db []
  (feed-repo/drop-feeds))

(describe "Feed reading"
  (before (initialize-db))
  (after (finalize-db))
  (it "should list titles of feeds"
    (should= "Feed 2" (:title (first (feed-repo/find-all))))
    (should= "Feed 1" (:title (second (feed-repo/find-all))))))

(describe "Create feed"
  (before (initialize-db))
  (after (finalize-db))
  (it "should create feed"
    (should= "newfeed" (:title (feed-repo/insert-feed {:title "newfeed"})))))

(describe "Update feed"
  (before (initialize-db))
  (after (finalize-db))
  (it "should update feed"
      (let [feed (feed-repo/insert-feed {:title "updatefeed" :items {:title "test" :read false}})
            updated-feed (assoc-in feed [:items] {:title "test" :read true})]
        (should= updated-feed (feed-repo/update-feed updated-feed)))
      ))

(run-specs)
