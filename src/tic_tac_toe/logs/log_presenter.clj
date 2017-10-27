(ns tic-tac-toe.logs.log-presenter
  (:use [clojure.string :only [join]])
  (:require [clj-time.core :as t]))

(defn- format-single-log-prompt
  [{:keys [id name time-diff]} {:keys [empty-log save-log]}]
  (if (nil? name)
    (format empty-log id)
    (format save-log id name time-diff)))

(defn format-log-prompts
  [{:keys [select-log] :as game} logs]
  (->> (map #(format-single-log-prompt % game) logs)
       (join "\n")
       (format select-log)))
