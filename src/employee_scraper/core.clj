(ns employee-scraper.core
  (:require [clj-webdriver.core :as core]))

(use 'clj-webdriver.taxi)

(set-driver! {:browser :firefox})

(defn get-next-btn []
  (find-element {:id "PageLinkNext"}))

(defn person-finder []
  (core/find-elements *driver* {:id "NameFieldLink"}))

(defn get-match-str [srcstr]
  (re-find #"SYSTEMATICGROUP%5C\S+" srcstr))
  
(defn get-initials-2 [initialstr]
  (let [initialstr2 (get-match-str initialstr)]
  (if (> (count initialstr2) 18)
      (clojure.string/replace 
	      (subs initialstr2 18)
	      #"%2D" "-")
	  "XXXXXXXXXXXX")))
  
(defn get-initials [elem]
  (let [srcstr (attribute elem :href)]
    (if (nil? srcstr)
	    "XXXXXXXXXXX"
	    (get-initials-2 srcstr))))
  
(defn get-name [elem]
  (let [name-elem (attribute elem :title)]
    (if (nil? name-elem)
	    "XXXXXXXXXXX"
        (clojure.string/trim name-elem))))
  
(defn print-names []
  (doseq [pelem (person-finder)]
     (spit "initials-and-names-from-sharepoint.txt" 
	       (str (get-initials pelem) "," (get-name pelem) "\n") 
		   :append true)))

(defn process-page []
  (let [nextbtn (get-next-btn)]
    (if (not (nil? nextbtn))
      (do
	    (print-names)
		(click nextbtn)
		(Thread/sleep 4000)
		(recur))
      ("done"))))
      "done")))
	 
(to "INTRANET URL HERE")

(process-page)

;(click (find-element {:id "PageLinkNext"}))

(quit)