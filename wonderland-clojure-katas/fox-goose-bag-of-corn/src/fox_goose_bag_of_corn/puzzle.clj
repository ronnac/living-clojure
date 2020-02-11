(ns fox-goose-bag-of-corn.puzzle)

;;debugging parts of expressions
(defmacro dbg[x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])

(defn shore-safe [shore-vector]
  (let [v (set shore-vector)]
   (and (not (some v [:boat]))
      (or (some v [:you])
          (not (or
                   (every? v
                     [:fox :goose])
                   (every? v
                     [:goose :corn])))))))

;(shore-safe [:corn])"
;(shore-safe [:corn :goose])
;(shore-safe [:fox :goose :you])
;(shore-safe [:fox])
;(shore-safe [:corn :fox :goose])
;(shore-safe [:fox :you :boat])
;(shore-safe [:goose])

(defn boat-check [boat-vector]
  (let [v (set boat-vector)]
    (and (some v [:boat])
         (> 4 (count v))
         (or (= v #{:boat})
             (some v [:you])))))
         
;(boat-check [:boat :you :goose])
;(boat-check [:corn :goose])
;(boat-check [:you :goose :corn :boat])
;(boat-check [:you :corn :boat])

(defn same-site? [a b]
   (= (set a)(set b)))

;(same-site? 
;  [:boat :you :corn]
;  [:you :corn :boat])

(defn same-state? [[a b c][d e f]]
  (and (same-site? a d)
       (same-site?  b e)
       (same-site? c f)))

;(same-state? [[:fox][:boat :goose :you][:corn]]
;             [[:fox][:goose :boat :you][:corn]])


(defn deja-vu [state states]
  (some #(same-state? state %) states))

;(def some-states
;  [[[:fox][:boat :goose :you][:corn]]
;   [[:fox][:boat :corn :you][:goose]]])

;(deja-vu [[:fox][:you :boat :goose][:corn]]
;  some-states)
  
;(deja-vu [[:fox][:you :goose][:boat :corn]]
;  some-states)
  
(defn state-allowed? [state states]
  (and 
       (shore-safe (state 0))
       (shore-safe (state 2))
       (boat-check (state 1))
       (not (deja-vu state states))))
  
;(state-allowed?
;  [[:goose]
;  [:you :boat]
;   [:fox :corn]]
;  some-states)
  
;(state-allowed?
;  [[:fox]
;   [:you :boat] 
;   [:goose :corn]]
;  some-states)

;(state-allowed?
;  [[:fox]
;   [:you :goose :corn]
;   [:boat]]
;  [[:fox][:boat :you :goose][:corn]
;   [[:fox :you][:boat :corn :goose][]]])

;(state-allowed? [[:fox :you :boat] [:corn] [:goose]] some-states)

(defn won? [state]
  (= (set (state 2))
     #{:you :fox :goose :corn}))
     

;(won? [[:corn]
;       [:you]
;       [:fox :goose]])
;(won? [[]p
;       [:you :corn]
;       [:fox :goose]])

(defn other-site [source target]
  (first 
    (remove #{source target}[0 1 2])))
  
;(other-site 0 2)

(defn move [who state source target]
  (let [os (other-site source target)]
    (if 
      (some #{who} (state source))
      (->{source (vec (remove #{who} (state source)))
          target (conj (state target) who)
          os (state os)} 
        sort vals vec)
      state)))

;(move :fox [[:fox :corn][:you][:goose]] 0 1)
;(move :goose [[:fox :corn][:you][:goose]] 2 1)

(defn site [sitecounter]
  (nth (cycle [0 1 2 1]) sitecounter))

;(site 0)
;(site 2)
;(site 3)
;(site 20)
;(site 21)

(defn make-state 
  [states sitecounter passenger]
  (reduce 
   #(move %2  %1
       (site sitecounter)
       (site (inc sitecounter)))
   (last states) 
   [:you passenger]))

    
;(def states [[[:fox ][:you :goose][:corn]]])
;(make-state states 3 :goose)
;(make-state states 3 :corn)
;(make-state states 4 :fox)

(defn init-passengers [state sitecounter]
  (state (site sitecounter)))
    
;(init-passengers (some-states 0) 5)

(defn make-plan 
  ([start-pos] 
   (make-plan 0 start-pos ((start-pos 0) 0)))
  ([sitecounter states passengers]
   (let [passenger (first passengers)]
     (when passenger
       (let [new-state 
             (make-state states sitecounter passenger)]         
         (if (won? new-state) 
           (concat states [new-state])
           (if (state-allowed? new-state states) 
               (let [plan (make-plan  
                            (inc sitecounter)
                            (conj states new-state)
                            (init-passengers new-state (inc sitecounter)))]
                 (if plan
                   plan
                   (make-plan sitecounter states (next passengers))))
               (make-plan sitecounter states (next passengers)))))))))
 
(make-plan start-pos)

(defn river-crossing-plan []
  (make-plan start-pos))














