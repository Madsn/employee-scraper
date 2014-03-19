(ns employee-scraper.core
  (:require [clj-webdriver.core :as core]))

(use 'clj-webdriver.taxi)

(set-driver! {:browser :firefox})

(defn get-next-btn []
  (find-element {:id "PageLinkNext"}))

(defn person-finder []
  (core/find-elements *driver* {:class "ms-peopleux-userImg"}))

(defn get-initials [elem]
  (subs (re-find #"Pictures/\S+(?=_)" (attribute elem :src)) 9))
  
(defn get-name [elem]
  (clojure.string/trim (attribute elem :alt)))
  
(defn print-names []
  (doseq [pelem (person-finder)]
     (println (str (get-initials pelem) " - " (get-name pelem)))))

(defn process-page []
  (let [nextbtn (get-next-btn)]
    (if (not (nil? nextbtn))
      (do
	    (print-names)
		(click nextbtn)
		(Thread/sleep 4000)
		(recur))
      ("done"))))
	 
(to "INTRANET URL HERE")

(process-page)

;(click (find-element {:id "PageLinkNext"}))

(quit)