(ns clojure.core-test.max
  (:require [clojure.test :as t :refer [deftest testing is are]]
            [clojure.core-test.portability :as p]))

(p/when-var-exists clojure.core/max
 (deftest test-max
   (are [expected x y] (= expected (max x y) (max y x))
     2     1      2
     2N    1N     2N
     2     1N     2
     2N    1      2N
     2.0   1.0    2.0
     2.0   1      2.0
     2     1.0    2
     1     1/2    1
     ##Inf 1      ##Inf
     1     1      ##-Inf
     ##Inf ##-Inf ##Inf)

   ;; Single arg just returns argument
   (is (= 1 (max 1)))
   (is (= 2 (max 2)))
   (is (= "x" (max "x")))        ; doesn't check single arg for Number

   ;; Multi-arg
   (is (= 5 (max 1 2 3 4 5)))
   (is (= 5 (max 5 4 3 2 1)))
   (is (= 5 (max 1 2 3 4 5 ##-Inf)))
   (is (= ##Inf (max 1 2 3 4 5 ##Inf)))

   (is (NaN? (max ##NaN 1)))
   (is (NaN? (max 1 ##NaN)))
   (is (NaN? (max 1 2 3 4 ##NaN)))
   (is (NaN? (max ##-Inf ##NaN ##Inf)))
   (is (NaN? (max ##NaN)))

   (is (thrown? Exception (max "x" "y")))
   (is (thrown? Exception (max nil 1)))
   (is (thrown? Exception (max 1 nil)))))
