
(ns alphabet-cipher.coder)


;(int \b)
;(char (- (reduce + (map int "ah")) 96))

;(char (+ 97 (mod (reduce + -194 (map int "ba")) 26)))

;letter to integer
(defn letter-to-int [t]
   (- (int t) 97))

(def l2i (memoize letter-to-int))

;(l2i \z)

;integer to letter
(defn int-to-letter [i]
  (char (+ i 97)))

(def i2l (memoize int-to-letter))
;(i2l 25)

;(defn add-or-sub [a b op]
;  (op a b))

;(add-or-sub 4 3 +)
;(add-or-sub 4 3 -)

;(map l2i "hello")

;(encode-pair "ms")
;(decode-pair "es")
;(first "qf")

(defn codec-pair*  [s op]
    (i2l 
     (mod
       (reduce op
         (map l2i s))
       26)))

(def codec-pair (memoize codec-pair*))

;(defn encode-pair [s]
;  (codec-pair s +))

;(defn decode-pair [s]
;  (codec-pair s -))

;(codec-pair "es" -)

;(interleave "hello" "codeword")

;(interleave "hello" (cycle "code"))

;(partition 2 (interleave "hello" (cycle "code")))

;(map encode-pair (partition 2 (interleave "hello" (cycle "code"))))


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

(def cyclic "vigilancevigilancevigilancevi")

;(= true true false false)

;(reduce = true [true false])


;(partition 2 
;  (interleave 
;  "abcdabcd" 
;  (take 8 (cycle "abc")))

;(map (fn [[a b]] (= a b)) 
;    (partition 2 
;    (interleave 
;    "abcdabcd" 
;    (take 8 (cycle "abc"))))

;(reduce #(and %1 %2) 
;     (map (fn [[a b]] (= a b)) 
;      (partition 2 
;      (interleave 
;      "abcdabcd" 
;      (take 8 (cycle "abc"))))

;(defn compare-cyclic [simple repetitive]
;  (reduce #(and %1 %2) 
;    (map (fn [[a b]] (= a b)) 
;      (partition 2 
;        (interleave 
;          repetitive 
;          (take 
;            (count repetitive)
;            (cycle simple)))))))

;(compare-cyclic "abc" "abcabca")
;(compare-cyclic "abc" "abcac")
;(compare-cyclic "vig" cyclic)
;(compare-cyclic (take 3 cyclic) cyclic)
;(compare-cyclic (subs cyclic 0 3) cyclic)
;(subs cyclic 0 3)


;(defn uncycle-old [cyclic]
;  (loop [i 1]
;   (if (compare-cyclic  
;         (take i cyclic)
;         cyclic)
;     (subs cyclic 0 i)
;     (recur (inc i)))))
  

;(defn decipher-old [cipher message]
;  (uncycle (decode message cipher)))


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
    
           
    


















