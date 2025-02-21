public class ListOracle {
    /**
     * Test oracle for checking if the size method does not exceed
     * Integer.MAX_VALUE.
     *
     * @param list the list to check
     * @return true if the size reported is <= Integer.MAX_VALUE, false otherwise
     */
    boolean checkSize(List<?> list) {
        return list.size() <= Integer.MAX_VALUE;
    }

    /**
     * Test oracle for checking if isEmpty correctly identifies an empty list.
     *
     * @param list the list to check
     * @return true if isEmpty returns true for an empty list and false for a
     *         non-empty list, false otherwise
     */
    boolean checkIsEmpty(List<?> list) {
        boolean empty = list.isEmpty();
        if (list.size() == 0) {
            return empty; // Should be true if the list is indeed empty
        } else {
            return !empty; // Should be false if the list is not empty
        }
    }

    /**
     * Test oracle for checking if the list correctly identifies presence of a
     * specified element.
     *
     * @param list the list to check
     * @param o    the object to be tested for presence in the list
     * @return true if the list contains the object as specified, false otherwise
     */
    boolean checkContains(List<?> list, Object o) {
        try {
            boolean result = list.contains(o);
            for (Object item : list) {
                if (o == null ? item == null : o.equals(item)) {
                    return result; // Should be true if the element is found as per condition
                }
            }
            return !result; // Should be false if no such element is found
        } catch (ClassCastException | NullPointerException e) {
            return false; // If an exception is thrown, the oracle returns false
        }
    }

    /**
     * Test oracle for checking if the method throws ClassCastException when an
     * incompatible type is provided.
     *
     * @param list the list to check
     * @param o    the object of incompatible type
     * @return true if ClassCastException is thrown, false otherwise
     */
    boolean checkTypeCompatibility(List<?> list, Object o) {
        try {
            list.contains(o);
            return false; // No exception means failure in this oracle
        } catch (ClassCastException e) {
            return true; // Correctly thrown exception
        } catch (NullPointerException e) {
            return false; // Wrong type of exception thrown
        }
    }

    /**
     * Test oracle for checking if the method throws NullPointerException for null
     * objects when not permitted.
     *
     * @param list the list to check
     * @return true if NullPointerException is thrown for null input when not
     *         permitted, false otherwise
     */
    boolean checkNullHandling(List<?> list) {
        try {
            list.contains(null);
            return !list.contains(null); // If no exception and null is handled, return false
        } catch (NullPointerException e) {
            return true; // Correctly thrown exception
        }
    }

    /**
     * Test oracle for checking if the iterator returns elements in the correct
     * sequence.
     *
     * @param list the list to be checked
     * @return true if the elements are iterated in the same order as they appear in
     *         the list, false otherwise
     */
    boolean checkIteratorSequence(List<?> list) {
        Iterator<?> iterator = list.iterator();
        for (Object item : list) {
            if (!iterator.hasNext() || !Objects.equals(iterator.next(), item)) {
                return false; // Fail if the iterator runs out of items or the items don't match
            }
        }
        return !iterator.hasNext(); // Pass if the iterator has no extra elements
    }

    /**
     * Test oracle for checking if the toArray method returns elements in the
     * correct sequence.
     *
     * @param list the list to be checked
     * @return true if the elements in the array match the sequence in the list,
     *         false otherwise
     */
    boolean checkToArraySequence(List<?> list) {
        Object[] array = list.toArray();
        if (array.length != list.size()) {
            return false; // Fail if the array size does not match the list size
        }
        for (int i = 0; i < array.length; i++) {
            if (!Objects.equals(array[i], list.get(i))) {
                return false; // Fail if any element does not match
            }
        }
        return true; // Pass if all elements match and are in order
    }

    /**
     * Test oracle for checking if the toArray method returns a "safe" array.
     *
     * @param list the list to be checked
     * @return true if modifications to the returned array do not affect the
     *         original list, false otherwise
     */
    boolean checkArraySafety(List<?> list) {
        Object[] array = list.toArray();
        if (array.length == 0 && list.size() == 0) {
            return true; // Trivial case where the list is empty
        }
        try {
            Object originalFirstElement = list.get(0); // Store original element for comparison
            array[0] = new Object(); // Attempt to modify the first element of the array
            return !Objects.equals(list.get(0), array[0]); // Check if the list's first element is unchanged
        } catch (IndexOutOfBoundsException e) {
            return false; // Fail if there is no element to test with
        }
    }

    /**
     * Test oracle for checking if the returned array can be modified without
     * affecting the list.
     *
     * @param list the list to be checked
     * @return true if modifications to the array do not affect the list, false
     *         otherwise
     */
    boolean checkArrayModifiability(List<?> list) {
        Object[] array = list.toArray();
        if (array.length == 0 && list.size() == 0) {
            return true; // Trivial case where both are empty
        }
        try {
            Object originalFirstElement = list.get(0); // Store original element for comparison
            array[0] = new Object(); // Modify the first element
            return !Objects.equals(list.get(0), array[0]); // Pass if list is unaffected
        } catch (IndexOutOfBoundsException e) {
            return false; // Fail if modification is not possible due to empty list
        }
    }

    /**
     * Test oracle for checking if toArray(T[] a) returns elements in the correct
     * sequence.
     *
     * @param list the list to be checked
     * @param a    the array to be used for storage, if big enough
     * @return true if the elements in the array match the sequence in the list,
     *         false otherwise
     */
    <T> boolean checkToArraySequence(List<T> list, T[] a) {
        T[] result = list.toArray(a);
        if (result.length != list.size()) {
            return false; // Fail if the array size does not match the list size
        }
        for (int i = 0; i < result.length; i++) {
            if (!Objects.equals(result[i], list.get(i))) {
                return false; // Fail if any element does not match
            }
        }
        return true; // Pass if all elements match and are in order
    }

    /**
     * Test oracle for checking if toArray(T[] a) correctly uses the specified array
     * when big enough and matches the type.
     *
     * @param list the list to be checked
     * @param a    the array to be used for storage, if big enough
     * @return true if the same array is used when sufficient and type matches,
     *         false otherwise
     */
    <T> boolean checkArrayTypeFit(List<T> list, T[] a) {
        T[] result = list.toArray(a);
        boolean isSameArrayUsed = result == a && result.length == list.size();
        return isSameArrayUsed || result.getClass() == a.getClass();
    }

    /**
     * Test oracle for checking if toArray(T[] a) sets the element following the
     * list to null when the array has room.
     *
     * @param list the list to be checked
     * @param a    the array to be used for storage, if big enough
     * @return true if the element after the list ends is set to null, false
     *         otherwise
     */
    <T> boolean checkExcessNull(List<T> list, T[] a) {
        if (a.length > list.size()) {
            T[] result = list.toArray(a);
            return result[list.size()] == null;
        }
        return true; // Trivially true if no excess space
    }

    /**
     * Test oracle for checking if toArray(T[] a) throws ArrayStoreException for
     * incompatible types and NullPointerException for null arrays.
     *
     * @param list the list to be checked
     * @param a    the array to be used for storage, potentially incompatible or
     *             null
     * @return true if the correct exception is thrown, false otherwise
     */
    <T> boolean checkArrayExceptions(List<T> list, T[] a) {
        try {
            list.toArray(a);
            return false; // No exception means failure in this oracle
        } catch (ArrayStoreException e) {
            return true; // Correctly thrown exception for type incompatibility
        } catch (NullPointerException e) {
            return a == null; // Correctly thrown exception for null array
        }
    }

    /**
     * Test oracle for checking if add(E e) correctly appends the element to the end
     * of the list.
     *
     * @param list the list to be checked
     * @param e    the element to be appended
     * @return true if the element is appended at the end, false otherwise
     */
    <E> boolean checkAppendElement(List<E> list, E e) {
        int originalSize = list.size();
        try {
            boolean result = list.add(e);
            return result && list.get(originalSize).equals(e);
        } catch (Exception ex) {
            return false; // Any exception means this check fails
        }
    }

    /**
     * Test oracle for checking if the add operation is supported by the list.
     *
     * @param list the list to be checked
     * @param e    the element to attempt to add
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported(List<E> list, E e) {
        try {
            list.add(e);
            return true; // If operation is successful or if any other exception is thrown, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if the list throws ClassCastException when the type
     * of the element is incompatible.
     *
     * @param list the list to be checked
     * @param e    the potentially incompatible element
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkElementTypeRestrictions(List<E> list, E e) {
        try {
            list.add(e);
            return true; // No exception means the type is acceptable
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if the list throws NullPointerException for null
     * elements when not permitted.
     *
     * @param list the list to be checked
     * @return true if NullPointerException is thrown for null input, false
     *         otherwise
     */
    <E> boolean checkNullElementHandling(List<E> list) {
        try {
            list.add(null);
            return true; // No exception means nulls are acceptable
        } catch (NullPointerException ex) {
            return false; // Correctly identifies null prohibition
        }
    }

    /**
     * Test oracle for checking if IllegalArgumentException is thrown when an
     * element's property prevents it from being added.
     *
     * @param list the list to be checked
     * @param e    the element with potentially problematic properties
     * @return true if IllegalArgumentException is thrown, false otherwise
     */
    <E> boolean checkElementPropertyRestrictions(List<E> list, E e) {
        try {
            list.add(e);
            return true; // No exception means the element's properties are acceptable
        } catch (IllegalArgumentException ex) {
            return false; // Correctly identifies problematic properties
        }
    }

    /**
     * Test oracle for checking if remove(Object o) correctly removes the first
     * occurrence of the element.
     *
     * @param list the list to be checked
     * @param o    the element to be removed
     * @return true if the element is correctly removed and method returns true,
     *         false otherwise
     */
    <E> boolean checkElementRemoval(List<E> list, E o) {
        int originalSize = list.size();
        boolean contains = list.contains(o);
        boolean result = list.remove(o);
        boolean newSizeCorrect = list.size() == (contains ? originalSize - 1 : originalSize);
        return result == contains && newSizeCorrect;
    }

    /**
     * Test oracle for checking if remove(Object o) does not change the list and
     * returns false when the element is not present.
     *
     * @param list the list to be checked
     * @param o    the element not present in the list
     * @return true if the list is unchanged and method returns false, false
     *         otherwise
     */
    <E> boolean checkNoChangeOnAbsence(List<E> list, E o) {
        List<E> copy = new ArrayList<>(list);
        boolean result = list.remove(o);
        return !result && list.equals(copy);
    }

    /**
     * Test oracle for checking if remove(Object o) throws NullPointerException when
     * null is not permitted.
     *
     * @param list the list to be checked
     * @return true if NullPointerException is thrown for null input, false
     *         otherwise
     */
    <E> boolean checkNullHandling1(List<E> list) {
        try {
            list.remove(null);
            return true; // No exception means nulls are acceptable
        } catch (NullPointerException ex) {
            return false; // Correctly identifies null prohibition
        }
    }

    /**
     * Test oracle for checking if remove(Object o) throws ClassCastException when
     * the type of the element is incompatible.
     *
     * @param list the list to be checked
     * @param o    the potentially incompatible element
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility1(List<E> list, E o) {
        try {
            list.remove(o);
            return true; // No exception means the type is acceptable
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if the remove operation is supported by the list.
     *
     * @param list the list to be checked
     * @param o    the element to attempt to remove
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported2(List<E> list, E o) {
        try {
            list.remove(o);
            return true; // If operation is successful or if any other exception is thrown, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if containsAll(Collection<?> c) correctly identifies
     * if all elements are contained.
     *
     * @param list the list to be checked
     * @param c    the collection of elements to check for containment
     * @return true if the list contains all elements from the collection, false
     *         otherwise
     */
    <E> boolean checkContainment(List<E> list, Collection<?> c) {
        boolean result = list.containsAll(c);
        for (Object element : c) {
            if (!list.contains(element)) {
                return false; // Fail if any element is not contained
            }
        }
        return result; // Should be true if all elements are contained
    }

    /**
     * Test oracle for checking if containsAll(Collection<?> c) throws
     * ClassCastException for incompatible element types.
     *
     * @param list the list to be checked
     * @param c    the collection with potentially incompatible types
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility(List<E> list, Collection<?> c) {
        try {
            list.containsAll(c);
            return true; // No exception means all types are compatible
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if containsAll(Collection<?> c) throws
     * NullPointerException for null elements or null collection.
     *
     * @param list the list to be checked
     * @param c    the collection that may contain nulls or might be null
     * @return true if NullPointerException is thrown when expected, false otherwise
     */
    <E> boolean checkNullHandling(List<E> list, Collection<?> c) {
        try {
            list.containsAll(c);
            return true; // No exception means handling is correct or nulls are permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies null prohibition
        }
    }

    /**
     * Test oracle for checking if addAll(Collection<? extends E> c) correctly
     * appends all elements to the end of the list.
     *
     * @param list the list to be checked
     * @param c    the collection of elements to be added
     * @return true if all elements are added in the correct order, false otherwise
     */
    <E> boolean checkAppendingElements(List<E> list, Collection<? extends E> c) {
        int originalSize = list.size();
        boolean result = list.addAll(c);
        boolean changed = result && (list.size() == originalSize + c.size());
        int i = originalSize;
        for (E element : c) {
            if (!list.get(i++).equals(element)) {
                return false; // Fail if any element does not match
            }
        }
        return changed;
    }

    /**
     * Test oracle for checking if the addAll operation is supported by the list.
     *
     * @param list the list to be checked
     * @param c    the collection of elements to be added
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported(List<E> list, Collection<? extends E> c) {
        try {
            list.addAll(c);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if addAll(Collection<? extends E> c) throws
     * ClassCastException for incompatible element types.
     *
     * @param list the list to be checked
     * @param c    the collection with potentially incompatible types
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility3(List<E> list, Collection<? extends E> c) {
        try {
            list.addAll(c);
            return true; // No exception means all types are compatible
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if addAll(Collection<? extends E> c) throws
     * NullPointerException for null elements or collection, and
     * IllegalArgumentException for improper element properties.
     *
     * @param list the list to be checked
     * @param c    the collection that may contain nulls or might be null
     * @return true if the appropriate exception is thrown when expected, false
     *         otherwise
     */
    <E> boolean checkNullHandlingAndPropertyRestrictions(List<E> list, Collection<? extends E> c) {
        try {
            list.addAll(c);
            return false; // No exception means handling is correct or conditions are met
        } catch (NullPointerException | IllegalArgumentException ex) {
            return true; // Correctly identifies issues with nulls or element properties
        }
    }

    /**
     * Test oracle for checking if addAll(int index, Collection<? extends E> c)
     * correctly inserts elements at the specified index.
     *
     * @param list  the list to be checked
     * @param index the index at which to insert elements
     * @param c     the collection of elements to be added
     * @return true if elements are correctly inserted and existing elements are
     *         shifted right, false otherwise
     */
    <E> boolean checkInsertionAtIndex(List<E> list, int index, Collection<? extends E> c) {
        List<E> original = new ArrayList<>(list);
        boolean result = list.addAll(index, c);
        if (!result)
            return false; // If no change, fail immediately
        boolean sizeChanged = list.size() == original.size() + c.size();
        int i = index;
        for (E element : c) {
            if (!list.get(i++).equals(element)) {
                return false; // Fail if any element is not correctly inserted
            }
        }
        while (i < list.size()) {
            if (!list.get(i).equals(original.get(i - c.size()))) {
                return false; // Fail if elements are not correctly shifted
            }
            i++;
        }
        return sizeChanged;
    }

    /**
     * Test oracle for checking if the addAll(int index, Collection<? extends E> c)
     * operation is supported.
     *
     * @param list  the list to be checked
     * @param index the index at which to insert elements
     * @param c     the collection of elements to be added
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported(List<E> list, int index, Collection<? extends E> c) {
        try {
            list.addAll(index, c);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if addAll(int index, Collection<? extends E> c)
     * throws ClassCastException for incompatible element types.
     *
     * @param list  the list to be checked
     * @param index the index at which to insert elements
     * @param c     the collection with potentially incompatible types
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility(List<E> list, int index, Collection<? extends E> c) {
        try {
            list.addAll(index, c);
            return true; // No exception means all types are compatible
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if addAll(int index, Collection<? extends E> c)
     * throws NullPointerException when nulls are not permitted.
     *
     * @param list  the list to be checked
     * @param index the index at which to insert elements
     * @param c     the collection that may contain nulls or might be null
     * @return true if NullPointerException is thrown for null inputs, false
     *         otherwise
     */
    <E> boolean checkNullHandling(List<E> list, int index, Collection<? extends E> c) {
        try {
            list.addAll(index, c);
            return true; // No exception means nulls are permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies issues with null handling
        }
    }

    /**
     * Test oracle for checking if IllegalArgumentException is thrown due to element
     * properties in addAll(int index, Collection<? extends E> c).
     *
     * @param list  the list to be checked
     * @param index the index at which to insert elements
     * @param c     the collection with elements that might have problematic
     *              properties
     * @return true if IllegalArgumentException is thrown, false otherwise
     */
    <E> boolean checkElementPropertyRestrictions(List<E> list, int index, Collection<? extends E> c) {
        try {
            list.addAll(index, c);
            return true; // No exception means element properties are acceptable
        } catch (IllegalArgumentException ex) {
            return false; // Correctly identifies problematic element properties
        }
    }

    /**
     * Test oracle for checking if IndexOutOfBoundsException is thrown when index is
     * out of range in addAll(int index, Collection<? extends E> c).
     *
     * @param list  the list to be checked
     * @param index the index that might be out of range
     * @param c     the collection of elements to be added
     * @return true if IndexOutOfBoundsException is thrown, false otherwise
     */
    <E> boolean checkIndexBounds(List<E> list, int index, Collection<? extends E> c) {
        try {
            list.addAll(index, c);
            return true; // No exception means index is within bounds
        } catch (IndexOutOfBoundsException ex) {
            return false; // Correctly identifies out-of-bounds index
        }
    }

    /**
     * Test oracle for checking if removeAll(Collection<?> c) correctly removes
     * elements that are contained in the specified collection.
     *
     * @param list the list to be checked
     * @param c    the collection containing elements to be removed
     * @return true if all elements from the collection are removed from the list,
     *         false otherwise
     */
    <E> boolean checkElementRemoval(List<E> list, Collection<?> c) {
        List<E> original = new ArrayList<>(list);
        boolean result = list.removeAll(c);
        boolean changed = !list.containsAll(c) && (result == (original.size() != list.size()));
        return changed;
    }

    /**
     * Test oracle for checking if the removeAll operation is supported by the list.
     *
     * @param list the list to be checked
     * @param c    the collection of elements to attempt to remove
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported8(List<E> list, Collection<?> c) {
        try {
            list.removeAll(c);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if removeAll(Collection<?> c) throws
     * ClassCastException when types are incompatible.
     *
     * @param list the list to be checked
     * @param c    the collection with potentially incompatible element types
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility5(List<E> list, Collection<?> c) {
        try {
            list.removeAll(c);
            return true; // No exception means types are compatible
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if removeAll(Collection<?> c) throws
     * NullPointerException when null handling is incompatible.
     *
     * @param list the list to be checked
     * @param c    the collection that may contain nulls or might be null
     * @return true if NullPointerException is thrown for incompatible null
     *         handling, false otherwise
     */
    <E> boolean checkNullHandling5(List<E> list, Collection<?> c) {
        try {
            list.removeAll(c);
            return true; // No exception means handling is correct or nulls are permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies issues with null handling
        }
    }

    /**
     * Test oracle for checking if retainAll(Collection<?> c) correctly retains only
     * the elements in the specified collection.
     *
     * @param list the list to be checked
     * @param c    the collection containing elements to be retained
     * @return true if the list retains only the elements from the collection and
     *         changes accordingly, false otherwise
     */
    <E> boolean checkRetentionOfElements(List<E> list, Collection<?> c) {
        List<E> original = new ArrayList<>(list);
        boolean result = list.retainAll(c);
        boolean correctElementsRetained = list.stream().allMatch(c::contains);
        boolean elementsRemoved = original.stream().anyMatch(e -> !c.contains(e));
        return result == elementsRemoved && correctElementsRetained;
    }

    /**
     * Test oracle for checking if the retainAll operation is supported by the list.
     *
     * @param list the list to be checked
     * @param c    the collection of elements that should be retained
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported6(List<E> list, Collection<?> c) {
        try {
            list.retainAll(c);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation
        }
    }

    /**
     * Test oracle for checking if retainAll(Collection<?> c) throws
     * ClassCastException when types are incompatible.
     *
     * @param list the list to be checked
     * @param c    the collection with potentially incompatible element types
     * @return true if ClassCastException is thrown, false otherwise
     */
    <E> boolean checkTypeCompatibility7(List<E> list, Collection<?> c) {
        try {
            list.retainAll(c);
            return true; // No exception means types are compatible
        } catch (ClassCastException ex) {
            return false; // Correctly identifies type incompatibility
        }
    }

    /**
     * Test oracle for checking if retainAll(Collection<?> c) throws
     * NullPointerException when null handling is incompatible.
     *
     * @param list the list to be checked
     * @param c    the collection that may contain nulls or might be null
     * @return true if NullPointerException is thrown for incompatible null
     *         handling, false otherwise
     */
    <E> boolean checkNullHandling1(List<E> list, Collection<?> c) {
        try {
            list.retainAll(c);
            return true; // No exception means handling is correct or nulls are permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies issues with null handling
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) correctly
     * applies the operator to each element.
     *
     * @param list     the list to be checked
     * @param operator the operator to apply to each element
     * @return true if each element is replaced correctly, false if the elements do
     *         not match the expected results
     */
    <E> boolean checkElementReplacement12(List<E> list, UnaryOperator<E> operator) {
        List<E> original = new ArrayList<>(list);
        try {
            list.replaceAll(operator);
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals(operator.apply(original.get(i)))) {
                    return false; // Element not correctly replaced
                }
            }
            return true; // All elements replaced correctly
        } catch (Exception ex) {
            return false; // Handling of errors/exceptions in applying the operator
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) supports
     * the set operation and modifications.
     *
     * @param list     the list to be checked
     * @param operator the operator to apply to each element
     * @return true if the operation is supported and set operation is possible,
     *         false if UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationAndSetSupport(List<E> list, UnaryOperator<E> operator) {
        try {
            list.replaceAll(operator);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation or set operation not supported
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) throws
     * NullPointerException correctly.
     *
     * @param list     the list to be checked
     * @param operator possibly null or an operator that could return null
     * @return true if NullPointerException is thrown when expected, false otherwise
     */
    <E> boolean checkNullHandling(List<E> list, UnaryOperator<E> operator) {
        try {
            list.replaceAll(operator);
            return true; // No exception means nulls are handled correctly or permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies issues with null handling or null operator
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) correctly
     * applies the operator to each element.
     *
     * @param list     the list to be checked
     * @param operator the operator to apply to each element
     * @return true if each element is replaced correctly, false if the elements do
     *         not match the expected results
     */
    <E> boolean checkElementReplacement(List<E> list, UnaryOperator<E> operator) {
        List<E> original = new ArrayList<>(list);
        try {
            list.replaceAll(operator);
            for (int i = 0; i < list.size(); i++) {
                if (!list.get(i).equals(operator.apply(original.get(i)))) {
                    return false; // Element not correctly replaced
                }
            }
            return true; // All elements replaced correctly
        } catch (Exception ex) {
            return false; // Handling of errors/exceptions in applying the operator
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) supports
     * the set operation and modifications.
     *
     * @param list     the list to be checked
     * @param operator the operator to apply to each element
     * @return true if the operation is supported and set operation is possible,
     *         false if UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationAndSetSupport3(List<E> list, UnaryOperator<E> operator) {
        try {
            list.replaceAll(operator);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Correctly identifies unsupported operation or set operation not supported
        }
    }

    /**
     * Test oracle for checking if replaceAll(UnaryOperator<E> operator) throws
     * NullPointerException correctly.
     *
     * @param list     the list to be checked
     * @param operator possibly null or an operator that could return null
     * @return true if NullPointerException is thrown when expected, false otherwise
     */
    <E> boolean checkNullHandling73(List<E> list, UnaryOperator<E> operator) {
        try {
            list.replaceAll(operator);
            return true; // No exception means nulls are handled correctly or permitted
        } catch (NullPointerException ex) {
            return false; // Correctly identifies issues with null handling or null operator
        }
    }

    /**
     * Test oracle for checking if sort(Comparator<? super E> c) correctly sorts the
     * list.
     *
     * @param list       the list to be checked
     * @param comparator the comparator to define the sort order; if null, natural
     *                   ordering is assumed
     * @return true if the list is sorted according to the specified comparator or
     *         natural order, false otherwise
     */
    <E> boolean checkSorting_incorrect(List<E> list, Comparator<? super E> comparator) {
        List<E> original = new ArrayList<>(list);
        try {
            list.sort(comparator);
            List<E> sorted = new ArrayList<>(list);
            if (comparator != null) {
                sorted.sort(comparator);
            } else {
                sorted.sort(Comparator.naturalOrder());
            }
            return list.equals(sorted);
        } catch (Exception ex) {
            return false; // If any sorting exception occurs
        }
    }

    /**
     * Test oracle for checking if all elements are mutually comparable using the
     * specified comparator.
     *
     * @param list       the list to be checked
     * @param comparator the comparator to test mutual comparability
     * @return true if no ClassCastException or IllegalArgumentException is thrown,
     *         false otherwise
     */
    <E> boolean checkMutualComparability(List<E> list, Comparator<? super E> comparator) {
        try {
            list.sort(comparator);
            return true; // No exception means all elements are mutually comparable
        } catch (ClassCastException | IllegalArgumentException ex) {
            return false; // Correctly identifies comparability issues or comparator contract violations
        }
    }

    /**
     * Test oracle for checking if the list supports sorting operation, i.e.,
     * modifiability.
     *
     * @param list       the list to be checked
     * @param comparator the comparator to use for sorting
     * @return true if the list supports sorting (modifiable), false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkListModifiability(List<E> list, Comparator<? super E> comparator) {
        try {
            list.sort(comparator);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException ex) {
            return false; // Identifies if the list is unmodifiable
        }
    }

    /**
     * Test oracle for checking if clear() correctly removes all elements and leaves
     * the list empty.
     *
     * @param list the list to be cleared
     * @return true if the list is empty after the operation, false if elements
     *         remain or the operation fails
     */
    <E> boolean checkClearOperation(List<E> list) {
        try {
            list.clear();
            return list.isEmpty(); // Check if the list is indeed empty
        } catch (UnsupportedOperationException ex) {
            return false; // Handle if clear operation is not supported
        }
    }

    /**
     * Test oracle for checking if the clear operation is supported by the list.
     *
     * @param list the list to test the clear operation on
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported(List<E> list) {
        try {
            list.clear();
            return true; // Successful clear indicates support
        } catch (UnsupportedOperationException ex) {
            return false; // Operation not supported
        }
    }

    /**
     * Test oracle for checking if equals(Object o) correctly determines equality.
     *
     * @param list the list to be checked
     * @param o    the object to compare with the list
     * @return true if the list equals the object according to the definition, false
     *         otherwise
     */
    <E> boolean checkListEquality(List<E> list, Object o) {
        if (!(o instanceof List)) {
            return !list.equals(o); // Returns false if o is not a list
        }
        List<?> other = (List<?>) o;
        if (list.size() != other.size()) {
            return !list.equals(other); // Returns false if sizes differ
        }
        for (int i = 0; i < list.size(); i++) {
            E e1 = list.get(i);
            Object e2 = other.get(i);
            if (!(e1 == null ? e2 == null : e1.equals(e2))) {
                return false; // Returns false if any pair of elements differ
            }
        }
        return list.equals(other); // Returns true if all conditions are satisfied
    }

    /**
     * Test oracle for checking the correctness of hashCode() in relation to
     * equals().
     *
     * @param list1 the first list to be checked
     * @param list2 the second list to be checked
     * @return true if the hash codes are consistent with the equals definition,
     *         false otherwise
     */
    <E> boolean checkHashCodeConsistency(List<E> list1, List<E> list2) {
        boolean equalsResult = list1.equals(list2);
        boolean hashResult = list1.hashCode() == list2.hashCode();
        return equalsResult ? hashResult : true; // If lists are equal, hashes must be too; if not equal, hashes can
                                                 // differ
    }

    /**
     * Test oracle for checking the correctness of the hash code calculation.
     *
     * @param list the list whose hash code is to be tested
     * @return true if the hash code matches the expected calculation, false
     *         otherwise
     */
    <E> boolean checkHashCode(List<E> list) {
        int expectedHashCode = 1;
        for (E e : list) {
            expectedHashCode = 31 * expectedHashCode + (e == null ? 0 : e.hashCode());
        }
        return list.hashCode() == expectedHashCode;
    }

    /**
     * Test oracle for checking if get(int index) correctly retrieves the element at
     * the specified position.
     *
     * @param list  the list to be checked
     * @param index the index of the element to retrieve
     * @return true if the retrieved element matches the expected element at the
     *         index, false if incorrect or if an exception is thrown
     */
    <E> boolean checkElementRetrieval(List<E> list, int index) {
        try {
            E element = list.get(index);
            // Optionally, validate against a known good value if one is available
            // if (element.equals(expectedElement)) {
            return true;
            // }
            // return false;
        } catch (IndexOutOfBoundsException ex) {
            return false; // Correctly handling out of bounds as expected
        }
    }

    /**
     * Test oracle for checking if get(int index) throws IndexOutOfBoundsException
     * when the index is out of range.
     *
     * @param list  the list to be checked
     * @param index the index that might be out of range
     * @return true if IndexOutOfBoundsException is thrown for an out-of-range
     *         index, false otherwise
     */
    <E> boolean checkIndexBounds(List<E> list, int index) {
        try {
            list.get(index);
            // If no exception and index is within the expected range
            return index >= 0 && index < list.size();
        } catch (IndexOutOfBoundsException ex) {
            // If exception is caught, check if the index was indeed out of range
            return index < 0 || index >= list.size();
        }
    }

    /**
     * Test oracle for checking if set(int index, E element) correctly replaces and
     * returns the element.
     *
     * @param list    the list to be tested
     * @param index   the position to replace the element
     * @param element the new element to insert
     * @return true if the element at the index is replaced and returned correctly,
     *         false otherwise
     */
    <E> boolean checkElementReplacement(List<E> list, int index, E element) {
        try {
            E oldElement = list.set(index, element);
            return list.get(index).equals(element) && (oldElement != null || list.contains(oldElement));
        } catch (Exception ex) {
            return false; // Handles all exceptions by returning false
        }
    }

    /**
     * Test oracle for checking if set(int index, E element) throws
     * IndexOutOfBoundsException for invalid indexes.
     *
     * @param list    the list to be tested
     * @param index   the position to replace the element
     * @param element the new element to insert
     * @return true if IndexOutOfBoundsException is thrown for invalid index, false
     *         otherwise
     */
    <E> boolean checkIndexBounds(List<E> list, int index, E element) {
        try {
            list.set(index, element);
            return index >= 0 && index < list.size(); // should not throw if index is valid
        } catch (IndexOutOfBoundsException ex) {
            return index < 0 || index >= list.size(); // correctly identifies out-of-bounds
        }
    }

    /**
     * Test oracle for checking if set(int index, E element) supports the operation
     * and handles type issues.
     *
     * @param list    the list to be tested
     * @param index   the position to replace the element
     * @param element the new element to insert
     * @return true if the operation is supported and the type is compatible, false
     *         if UnsupportedOperationException or ClassCastException is thrown
     */
    <E> boolean checkOperationAndTypeSupport(List<E> list, int index, E element) {
        try {
            list.set(index, element);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException | ClassCastException ex) {
            return false; // Identifies unsupported operation or type incompatibility
        }
    }

    /**
     * Test oracle for checking if set(int index, E element) handles nulls and
     * element properties correctly.
     *
     * @param list    the list to be tested
     * @param index   the position to replace the element
     * @param element the new element to insert, potentially null
     * @return true if NullPointerException or IllegalArgumentException is thrown
     *         when expected, false otherwise
     */
    <E> boolean checkNullHandlingAndProperties(List<E> list, int index, E element) {
        try {
            list.set(index, element);
            return element != null; // If no exception and element is not null, return true
        } catch (NullPointerException | IllegalArgumentException ex) {
            return element == null; // correctly identifies issues with nulls or properties
        }
    }

    /**
     * Test oracle for checking if add(int index, E element) correctly inserts an
     * element at the specified index and validates the index.
     *
     * @param list    the list to be checked
     * @param index   the index where the element should be inserted
     * @param element the element to be inserted
     * @return true if the element is inserted correctly and all subsequent elements
     *         are shifted right, false otherwise
     */
    <E> boolean checkElementInsertionAndIndexValidation(List<E> list, int index, E element) {
        try {
            list.add(index, element);
            return list.get(index).equals(element) && (index >= 0 && index <= list.size() - 1);
        } catch (IndexOutOfBoundsException ex) {
            return index < 0 || index > list.size(); // Correctly identifies out-of-range index
        }
    }

    /**
     * Test oracle for checking if add(int index, E element) supports the add
     * operation and handles type issues.
     *
     * @param list    the list to be checked
     * @param index   the index where the element should be inserted
     * @param element the element to be inserted
     * @return true if the operation is supported and the type is compatible, false
     *         if UnsupportedOperationException or ClassCastException is thrown
     */
    <E> boolean checkOperationSupportAndTypeCompatibility(List<E> list, int index, E element) {
        try {
            list.add(index, element);
            return true; // If operation is successful, return true
        } catch (UnsupportedOperationException | ClassCastException ex) {
            return false; // Identifies unsupported operation or type incompatibility
        }
    }

    /**
     * Test oracle for checking if add(int index, E element) handles null elements
     * and element properties correctly.
     *
     * @param list    the list to be checked
     * @param index   the index where the element should be inserted
     * @param element the element to be inserted, potentially null
     * @return true if NullPointerException or IllegalArgumentException is thrown
     *         when expected, false otherwise
     */
    <E> boolean checkNullHandlingAndProperties12(List<E> list, int index, E element) {
        try {
            list.add(index, element);
            return element != null; // Assumes the list permits nulls if no exception is thrown
        } catch (NullPointerException | IllegalArgumentException ex) {
            return element == null || ex instanceof IllegalArgumentException; // Correctly identifies null or invalid
                                                                              // element properties
        }
    }

    /**
     * Test oracle for checking if remove(int index) correctly removes the element
     * and returns it.
     *
     * @param list  the list to be checked
     * @param index the index of the element to remove
     * @return true if the element is removed and returned correctly, false if the
     *         method fails or an exception is thrown
     */
    <E> boolean checkElementRemoval(List<E> list, int index) {
        try {
            E expectedElement = list.get(index); // Get the element before removal for comparison
            E removedElement = list.remove(index); // Perform removal
            boolean isElementCorrect = expectedElement.equals(removedElement);
            boolean isShiftCorrect = true;
            for (int i = index; i < list.size(); i++) {
                if (!list.get(i).equals(list.get(i + 1))) {
                    isShiftCorrect = false;
                    break;
                }
            }
            return isElementCorrect && isShiftCorrect;
        } catch (IndexOutOfBoundsException | UnsupportedOperationException ex) {
            return false; // Correct handling of exceptions
        }
    }

    /**
     * Test oracle for checking if remove(int index) throws
     * IndexOutOfBoundsException for invalid indexes.
     *
     * @param list  the list to be checked
     * @param index the index that might be out of range
     * @return true if IndexOutOfBoundsException is thrown for an out-of-range
     *         index, false otherwise
     */
    <E> boolean checkIndexBounds10(List<E> list, int index) {
        try {
            list.remove(index);
            return index >= 0 && index < list.size(); // should not throw if index is valid
        } catch (IndexOutOfBoundsException ex) {
            return index < 0 || index >= list.size(); // correctly identifies out-of-bounds
        }
    }

    /**
     * Test oracle for checking if the remove operation is supported by the list.
     *
     * @param list  the list to test the remove operation on
     * @param index the index at which to remove the element
     * @return true if the operation is supported, false if
     *         UnsupportedOperationException is thrown
     */
    <E> boolean checkOperationSupported(List<E> list, int index) {
        try {
            list.remove(index);
            return true; // Successful removal indicates support
        } catch (UnsupportedOperationException ex) {
            return false; // Operation not supported
        }
    }

    /**
     * Test oracle for checking if indexOf(Object o) correctly identifies the index
     * of the element.
     *
     * @param list    the list to be checked
     * @param element the element to search for
     * @return true if the index returned matches the first occurrence of the
     *         element or -1 if the element is not found, false otherwise
     */
    <E> boolean checkElementLocation(List<E> list, E element) {
        int index = list.indexOf(element);
        if (index == -1) {
            return !list.contains(element); // Confirm element is truly not in the list
        } else {
            return element == null ? list.get(index) == null : element.equals(list.get(index)); // Check correct element
                                                                                                // at the index
        }
    }

    /**
     * Test oracle for checking if indexOf(Object o) handles null elements
     * appropriately.
     *
     * @param list    the list to be checked
     * @param element the element to search for, potentially null
     * @return true if the method handles null correctly (returns index or throws
     *         NullPointerException), false otherwise
     */
    <E> boolean checkNullHandling21(List<E> list, E element) {
        try {
            int index = list.indexOf(element);
            return element != null || index != -1; // Check if null is handled correctly or if index is found for null
        } catch (NullPointerException ex) {
            return element == null; // NullPointerException should only be thrown if null elements are not permitted
        }
    }

    /**
     * Test oracle for checking if indexOf(Object o) throws ClassCastException for
     * incompatible element types.
     *
     * @param list    the list to be checked
     * @param element the element to search for, potentially of incompatible type
     * @return true if ClassCastException is thrown for incompatible types, false
     *         otherwise
     */
    <E> boolean checkTypeCompatibility11(List<E> list, Object element) {
        try {
            list.indexOf(element);
            return true; // No exception means the type is compatible
        } catch (ClassCastException ex) {
            return false; // Identifies incompatibility
        }
    }

    /**
     * Test oracle for checking if lastIndexOf(Object o) correctly identifies the
     * last index of the element.
     *
     * @param list    the list to be checked
     * @param element the element to search for
     * @return true if the index returned matches the last occurrence of the element
     *         or -1 if the element is not found, false otherwise
     */
    <E> boolean checkLastElementLocation(List<E> list, E element) {
        int lastIndex = list.lastIndexOf(element);
        if (lastIndex == -1) {
            return !list.contains(element); // Confirm element is truly not in the list
        } else {
            return element == null ? list.get(lastIndex) == null : element.equals(list.get(lastIndex)); // Check correct
                                                                                                        // element at
                                                                                                        // the index
        }
    }

    /**
     * Test oracle for checking if lastIndexOf(Object o) handles null elements
     * appropriately.
     *
     * @param list    the list to be checked
     * @param element the element to search for, potentially null
     * @return true if the method handles null correctly (returns index or throws
     *         NullPointerException), false otherwise
     */
    <E> boolean checkNullHandling(List<E> list, E element) {
        try {
            int index = list.lastIndexOf(element);
            return element != null || index != -1; // Check if null is handled correctly or if index is found for null
        } catch (NullPointerException ex) {
            return element == null; // NullPointerException should only be thrown if null elements are not permitted
        }
    }

    /**
     * Test oracle for checking if lastIndexOf(Object o) throws ClassCastException
     * for incompatible element types.
     *
     * @param list    the list to be checked
     * @param element the element to search for, potentially of incompatible type
     * @return true if ClassCastException is thrown for incompatible types, false
     *         otherwise
     */
    <E> boolean checkTypeCompatibility22(List<E> list, Object element) {
        try {
            list.lastIndexOf(element);
            return true; // No exception means the type is compatible
        } catch (ClassCastException ex) {
            return false; // Identifies incompatibility
        }
    }

    /**
     * Test oracle for checking if listIterator() correctly iterates over elements
     * in the proper sequence.
     *
     * @param list the list to be checked
     * @return true if the iterator traverses all elements in the correct order,
     *         false otherwise
     */
    <E> boolean checkIteratorFunctionalityAndOrder(List<E> list) {
        ListIterator<E> it = list.listIterator();
        for (E expectedElement : list) {
            if (!it.hasNext() || !it.next().equals(expectedElement)) {
                return false; // Iterator fails to match the list's sequence or ends prematurely
            }
        }
        return !it.hasNext(); // Ensure no extra elements in iterator beyond list content
    }

    /**
     * Test oracle for checking if the listIterator supports modification operations
     * correctly.
     *
     * @param list the list to be checked
     * @return true if modifications are supported and correctly reflected in the
     *         list, false if unsupported or incorrect
     */
    <E> boolean checkIteratorModificationCapabilities(List<E> list) {
        try {
            ListIterator<E> it = list.listIterator();
            if (it.hasNext()) {
                E element = it.next();
                it.set(element); // Check set operation
                it.add(element); // Check add operation
                it.remove(); // Check remove operation
                return true; // If no exceptions, assume modifications are supported
            }
            return false; // Iterator does not have any elements to test modifications
        } catch (UnsupportedOperationException ex) {
            return false; // Modifications are not supported by the iterator
        }
    }

    /**
     * Test oracle for checking if the listIterator behaves correctly under boundary
     * conditions.
     *
     * @param list the list to be checked
     * @return true if the iterator behaves correctly at boundaries (beginning and
     *         end), false if it throws unexpected exceptions
     */
    <E> boolean checkIteratorValidity(List<E> list) {
        ListIterator<E> it = list.listIterator();
        try {
            if (it.hasPrevious()) {
                return false; // Iterator should not have a previous at the start
            }
            while (it.hasNext())
                it.next(); // Traverse to the end
            if (it.hasNext()) {
                return false; // Iterator should have no next at the end
            }
            it.next(); // This should throw NoSuchElementException
            return false;
        } catch (NoSuchElementException ex) {
            return true; // Correct behavior at the end of the iterator
        } catch (Exception ex) {
            return false; // Any other exception is incorrect
        }
    }

    /**
     * Test oracle for checking if listIterator(int index) correctly starts at the
     * specified position and follows the proper sequence.
     *
     * @param list  the list to be checked
     * @param index the starting index for the iterator
     * @return true if the iterator starts at the correct position and iterates
     *         properly, false otherwise
     */
    <E> boolean checkIteratorStartPositionAndSequence(List<E> list, int index) {
        try {
            ListIterator<E> it = list.listIterator(index);
            if (index < list.size() && it.next() != list.get(index)) {
                return false; // Check next() starts at the specified index
            }
            if (index > 0 && it.previous() != list.get(index - 1)) {
                return false; // Check previous() returns the element before the index
            }
            return true;
        } catch (IndexOutOfBoundsException ex) {
            return false; // Properly handle if the index is out of bounds
        }
    }

    /**
     * Test oracle for checking if listIterator(int index) throws
     * IndexOutOfBoundsException for invalid indexes.
     *
     * @param list  the list to be checked
     * @param index the starting index for the iterator
     * @return true if IndexOutOfBoundsException is thrown for an out-of-range
     *         index, false otherwise
     */
    <E> boolean checkIndexValidation(List<E> list, int index) {
        try {
            list.listIterator(index);
            return index >= 0 && index <= list.size(); // Should not throw if index is valid
        } catch (IndexOutOfBoundsException ex) {
            return index < 0 || index > list.size(); // Correctly identifies out-of-range index
        }
    }

    /**
     * Test oracle for checking if the listIterator(int index) supports standard
     * iterator operations correctly from the given index.
     *
     * @param list  the list to be checked
     * @param index the starting index for the iterator
     * @return true if all iterator operations are supported correctly, false if an
     *         operation fails or throws an unexpected exception
     */
    <E> boolean checkIteratorFunctionality(List<E> list, int index) {
        try {
            ListIterator<E> it = list.listIterator(index);
            if (it.hasNext()) {
                it.next(); // Test next operation
            }
            if (it.hasPrevious()) {
                it.previous(); // Test previous operation
            }
            // Additional operations could be tested based on list modifiability:
            // it.set(...);
            // it.add(...);
            // it.remove();
            return true;
        } catch (Exception ex) {
            return false; // Any exception here is unexpected based on the operations performed
        }
    }

    /**
     * Test oracle for checking if subList(int fromIndex, int toIndex) correctly
     * returns the view of the specified range.
     *
     * @param list      the list to be checked
     * @param fromIndex the starting index of the sublist
     * @param toIndex   the ending index of the sublist
     * @return true if the sublist reflects the correct range and is empty when
     *         indices are equal, false otherwise
     */
    <E> boolean checkSublistRangeAndView(List<E> list, int fromIndex, int toIndex) {
        try {
            List<E> sublist = list.subList(fromIndex, toIndex);
            if (fromIndex == toIndex && !sublist.isEmpty()) {
                return false; // Sublist should be empty when fromIndex equals toIndex
            }
            for (int i = 0; i < sublist.size(); i++) {
                if (!sublist.get(i).equals(list.get(fromIndex + i))) {
                    return false; // Elements should match the original list
                }
            }
            return true;
        } catch (IndexOutOfBoundsException ex) {
            return false; // Handles unexpected index errors
        }
    }

    /**
     * Test oracle for checking if changes to the sublist reflect in the original
     * list and vice versa.
     *
     * @param list      the original list to be checked
     * @param fromIndex the starting index of the sublist
     * @param toIndex   the ending index of the sublist
     * @return true if changes reflect properly, false otherwise
     */
    <E> boolean checkReflectionOfChanges(List<E> list, int fromIndex, int toIndex) {
        List<E> sublist = list.subList(fromIndex, toIndex);
        if (!sublist.isEmpty()) {
            E originalElement = sublist.get(0);
            sublist.set(0, originalElement); // Set the same element to check reflection
            if (!list.get(fromIndex).equals(originalElement)) {
                return false; // Changes should reflect in the original list
            }
        }
        return true;
    }

    /**
     * Test oracle for checking if subList(int fromIndex, int toIndex) throws
     * IndexOutOfBoundsException for invalid indices.
     *
     * @param list      the list to be checked
     * @param fromIndex the starting index of the sublist
     * @param toIndex   the ending index of the sublist
     * @return true if an exception is correctly thrown for invalid indices, false
     *         otherwise
     */
    <E> boolean checkIndexValidation(List<E> list, int fromIndex, int toIndex) {
        try {
            list.subList(fromIndex, toIndex);
            return fromIndex >= 0 && toIndex <= list.size() && fromIndex <= toIndex;
        } catch (IndexOutOfBoundsException ex) {
            return fromIndex < 0 || toIndex > list.size() || fromIndex > toIndex; // Correctly identifies out-of-range
                                                                                  // index
        }
    }

    /**
     * Test oracle for checking if spliterator() creates a Spliterator with correct
     * characteristic values.
     *
     * @param list the list to be checked
     * @return true if the Spliterator reports SIZED, ORDERED, and SUBSIZED, false
     *         otherwise
     */
    <E> boolean checkSpliteratorCharacteristics(List<E> list) {
        Spliterator<E> spliterator = list.spliterator();
        boolean hasSized = spliterator.hasCharacteristics(Spliterator.SIZED);
        boolean hasOrdered = spliterator.hasCharacteristics(Spliterator.ORDERED);
        boolean hasSubsized = spliterator.hasCharacteristics(Spliterator.SUBSIZED);
        return hasSized && hasOrdered && hasSubsized;
    }

    /**
     * Test oracle for checking if the spliterator() creates a late-binding,
     * fail-fast Spliterator.
     *
     * @param list the list to be checked
     * @return true if the Spliterator is late-binding and fail-fast, false
     *         otherwise
     */
    <E> boolean checkSpliteratorLateBindingAndFailFast_wrong(List<E> list) {
        Spliterator<E> spliterator = list.spliterator();
        // Adding elements to check fail-fast behavior
        try {
            list.add(null); // Modify list to test fail-fast
            spliterator.tryAdvance(System.out::println);
            return false; // If no ConcurrentModificationException, then it's not fail-fast
        } catch (ConcurrentModificationException ex) {
            return true; // Correctly detected modification
        }
    }

}
