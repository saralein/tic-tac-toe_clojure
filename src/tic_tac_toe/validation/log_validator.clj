(ns tic-tac-toe.validation.log-validator
  (:use [clojure.string :only [trim]])
  (:require [tic-tac-toe.read-write.reader :as reader]))

(defn valid-name?
  [name]
  (-> name
      trim
      count
      zero?
      not))

(defn get-log-ids
  [logs]
  (->> logs
       (map #(:id %))
       (map str)))

(defn valid-log?
  [logs input]
  (->> logs
       get-log-ids
       (some #(= input %))
       (not= nil)))

(defn check-selection
  [func logs {:keys [invalid-selection] :as game} input]
  (if (valid-log? logs input)
    (Integer/parseInt input)
    (func logs game invalid-selection)))
