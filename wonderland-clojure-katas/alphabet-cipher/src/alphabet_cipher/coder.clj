
(ns alphabet-cipher.coder)

;letter to integer
(defn letter-to-int [t]
   (- (int t) 97))

(def l2i (memoize letter-to-int))

;integer to letter
(defn int-to-letter [i]
  (char (+ i 97)))

(def i2l (memoize int-to-letter))
;(i2l 25)

(defn codec-pair*  [s op]
    (i2l 
     (mod
       (reduce op
         (map l2i s))
       26)))

(def codec-pair (memoize codec-pair*))

(defn codec [keyword message op]
  (apply str 
    (map #(codec-pair % op) 
      (partition 2 
        (interleave 
          message 
          (cycle keyword))))))

(defn encode [keyword message]
  (codec keyword message +))

;(encode "scones" "meetmebythetree")

(defn decode [keyword message]
  (codec keyword message -))

;(decode "scones" "egsgqwtahuiljgs")

(decode "vigilance" "hmkbxebpxpmyllyrxiiqtoltfgzzv")


;un-cycle this: vigilancevigilancevigilancevi
;(def cyclic "vigilancevigilancevigilancevi")


(defn uncycle [cyclic cipher message]
  (loop [i 1]
    (if (=
           (encode 
             (subs cyclic 0 i)
             message)
           cipher)
      (subs cyclic 0 i)
      (recur (inc i)))))

(defn decipher [cipher message]
  (uncycle (decode message cipher) 
    cipher
    message))

(decipher
  "hmkbxebpxpmyllyrxiiqtoltfgzzv"
  "meetmeontuesdayeveningatseven")
    
           
    


















