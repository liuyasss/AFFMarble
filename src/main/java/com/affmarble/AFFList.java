package com.affmarble;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AFFList {

    private AFFList() {
        throw new UnsupportedOperationException(AFFConstant.UNSUPPORTED_OPERATION_EXCEPTION_TIP);
    }

    public static <E> ArrayList<E> newArrayList(E... element) {
        ArrayList<E> list = new ArrayList<>();
        if (element == null || element.length == 0) {
            return list;
        }
        list.addAll(Arrays.asList(element));
        return list;
    }

    public static <E> LinkedList<E> newLinkedList(E... element){
        LinkedList<E> list = new LinkedList<>();
        if (element == null || element.length == 0) {
            return list;
        }
        list.addAll(Arrays.asList(element));
        return list;
    }

    public boolean isNullOrEmpty(List list) {
        return list == null || list.isEmpty();
    }

    public boolean isNotEmpty(List list) {
        return list != null && !list.isEmpty();
    }

}
