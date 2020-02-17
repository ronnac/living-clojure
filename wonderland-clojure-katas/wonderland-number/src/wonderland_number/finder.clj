(ns wonderland-number.finder)

(defn wonderland-number []
  ;; calculate me
  142857)
  
(defn sameDigits? [n1 n2]
  (let [s1 (set (str n1))
        s2 (set (str n2))]
    (= s1 s2)))
    
(defn
 test-number [wondernum]
      (and (= 6 (count (str wondernum)))
           (sameDigits? wondernum (* 2 wondernum))
           (sameDigits? wondernum (* 3 wondernum))
           (sameDigits? wondernum (* 4 wondernum))
           (sameDigits? wondernum (* 5 wondernum))
           (sameDigits? wondernum (* 6 wondernum))))


(test-number 43)


(loop [i 100000]
  (if (test-number i)
      i
      (recur (inc i))))
  


