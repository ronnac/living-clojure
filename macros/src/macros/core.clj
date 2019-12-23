(ns macros.core
  (:gen-class))


(defn hi-queen [phrase]
  (str phrase ", so please your Majesty."))

(defn alice-hi-queen []
  (hi-queen "My name is Alice"))

(alice-hi-queen)
;; -> "My name is Alice, so please your Majesty."

(defn march-hare-hi-queen []
  (hi-queen "I'm the March Hare"))

(march-hare-hi-queen)
;; -> "I'm the March Hare, so please your Majesty."

(defn white-rabbit-hi-queen []
  (hi-queen "I'm the White Rabbit"))

(white-rabbit-hi-queen)

;; -> "I'm the White Rabbit, so please your Majesty."

(defn mad-hatter-hi-queen []
  (hi-queen "I'm the Mad Hatter"))
(mad-hatter-hi-queen)
;; -> "I'm the Mad Hatter, so please your Majesty."

(defmacro def-hi-queen 
  [name phrase]
  (list 'defn 
    (symbol name)
    []
    (list 'hi-queen phrase)))

(def-hi-queen "hi-queen-niveauverleih" "My name is niveauverleih")

(hi-queen-niveauverleih)

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
