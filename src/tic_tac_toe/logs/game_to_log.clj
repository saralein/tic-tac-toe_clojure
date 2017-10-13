(ns tic-tac-toe.logs.game-to-log
  (require [tic-tac-toe.ui.user-interface :as ui]
           [tic-tac-toe.validation.log-validator :as log-validator]))

(defn both-unnamed
  [game log]
  (and (nil? (:name game)) (nil? (:name log))))

(defn only-one-named
  [first second]
  (-> (:name second)
      (nil?)
      (and (not= (:name first) (:name second)))))

(defn named-same
  [game log]
  (-> (:name log)
      (nil?)
      (not)
      (and (= (:name log) (:name game)))))

(defn associate-name
  [name game]
  (->> name
       (hash-map :name)
       (merge game)))

(defn disassociate-name
  [game]
  (-> game
      (dissoc :name)))

(defn associate-id
  [{:keys [id]} game]
  (->> id
       (hash-map :id)
       (merge game)))

(defn- get-name-selection
  ([game prompt] (get-name-selection game prompt ""))

  ([{:keys [ui invalid-selection] :as game} prompt error]
    (let [input (->> prompt
                     (str error)
                     (ui/prompt-selection ui))]
      (if (log-validator/valid-name? input)
        input
        (recur game prompt invalid-selection)))))

(defn name-game
  [{:keys [ui name-save] :as game}]
  (-> game
      (get-name-selection name-save)
      (associate-name game)))
