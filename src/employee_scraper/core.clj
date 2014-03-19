(ns employee-scraper.core)

(use 'clj-webdriver.taxi)

(set-driver! {:browser :firefox})

(to "INTRANET URL HERE")