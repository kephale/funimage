(ns fun.imagej.img.cursor
  (:refer-clojure :exclude [inc dec])
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [fun.imagej.img.type :as tpe])
  (:import [net.imglib2.algorithm.neighborhood Neighborhood RectangleShape]
           [net.imglib2.util Intervals]
           [net.imglib2.img ImagePlusAdapter Img]
           [net.imglib2.img.display.imagej ImageJFunctions]
           [net.imglib2.type NativeType]
           [net.imglib2.type.numeric NumericType ARGBType]
           [net.imglib2.type.numeric.real FloatType]
           [net.imglib2.view Views IntervalView]
           [net.imglib2 Cursor RandomAccess RandomAccessibleInterval Interval]))      

(defn get-val
  "Get the value of a numeric cursor."
  [^Cursor cur]
  (tpe/get-type-val (.get cur)))

(defn set-val
  "Get the value of a numeric cursor."
  [^Cursor cur val]
  (tpe/set-type-val (.get cur) val))
(def set-val! set-val)

(defn set-byte-val
  "Get the value of a numeric cursor."
  [^Cursor cur ^long val]
  (.setInteger ^net.imglib2.type.numeric.integer.GenericByteType (.get cur) val))
(def set-byte-val! set-byte-val)

(defn inc
  "Increment the value at a cursor."
  [^Cursor cur]
  (.inc ^net.imglib2.type.numeric.RealType (.get cur)))
(def inc! inc)
(def inc-val! inc)

(defn dec
  "Decrement the value at a cursor."
  [^Cursor cur]
  (.dec ^net.imglib2.type.numeric.RealType (.get cur)))
(def dec! dec)
(def dec-val! dec)

(defn set-one
  "Set a cursor's value to one."
  [^Cursor cur]
  (.setOne ^net.imglib2.type.operators.SetOne (.get cur)))
(def set-one! set-one)

(defn set-zero
  "Set a cursor's value to zero."
  [^Cursor cur]
  (.setZero ^net.imglib2.type.operators.SetZero (.get cur)))
(def set-zero! set-zero)

(defn copy
  "Copy one cursor to another."  
  [^Cursor cur1 ^Cursor cur2]
  (.set ^net.imglib2.type.numeric.RealType (.get cur1) (.get cur2)))
(def copy! copy)

(defn copy-real
  "Copy one cursor to another."  
  [^Cursor cur1 ^Cursor cur2]
  (.setReal ^net.imglib2.type.numeric.ComplexType (.get cur1) 
    (.getRealDouble ^net.imglib2.type.numeric.ComplexType (.get cur2))))
(def copy-real! copy-real)

(defn add
  "Add 2 cursors together."
  [^Cursor cur1 ^Cursor cur2]
  (.add 
    ^net.imglib2.type.operators.Add (.get cur1)
    ^net.imglib2.type.operators.Add (.get cur2)))
(def add! add)

(defn mul
  "Multiply 2 cursors together."
  [^Cursor cur1 ^Cursor cur2]
  (.mul 
    ^net.imglib2.type.operators.Mul (.get cur1)
    ^net.imglib2.type.operators.Mul (.get cur2)))
(def mul! mul)
;; Not having this might be an issue, consider multimethod net.imglib2.type.operators.MulFloatingPoint

(defn sub
  "Subtract 2 cursors together."
  [^Cursor cur1 ^Cursor cur2]
  (.sub
    ^net.imglib2.type.operators.Sub (.get cur1)
    ^net.imglib2.type.operators.Sub (.get cur2)))
(def sub! sub)

(defn div
  "Divide one cursor by another."  
  [^Cursor cur1 ^Cursor cur2]
  (.div
    ^net.imglib2.type.operators.Div (.get cur1)
    ^net.imglib2.type.operators.Div (.get cur2)))
(def div! div)

(defn sum-neighborhood
  "Sum a neighborhood"
  [nbrhood]
  (loop [sum 0
         cur ^net.imglib2.Cursor (.cursor nbrhood)]
    (if (.hasNext cur)
      (do (.fwd cur)
        (recur (+ sum (.get ^net.imglib2.type.numeric.integer.UnsignedByteType (.get cur)))
              cur))
      sum)))


; Consider a macro that would convert from the usual math functions to the cursor math functions
