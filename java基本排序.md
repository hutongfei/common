基本排序

```java
package com.my.sort;

/**
 * @author hutf
 * @createTime 2020年11月06日 22:44:00
 */
public class SortDemo {

    public static void main(String[] args) {
        int[] array = new int[]{9, 6, 3, 1, 2, 4, 7, 8, 5, 10};

//        doSelectionSort(array);

//        doBubblingSort(array);

//        doSort(array, 0, array.length - 1);


        insertSort(array);


        doPrint(array);
    }

    /**
     * array[i] 和 I 之前的元素进行比较 ，如果找到比当前值大的数值 J ，则插入
     * @param array
     */
    private static void insertSort(int[] array) {
        for (int i = 1; i < array.length; i++) {
            int current = array[i];
            int j = i;
            while (j >= 0){
                if (array[i] <= array[j]) {
                    j--;
                } else if (array[i] > array[j]) {
                    break;
                }
            }

            if (i != j) {
                for (int k = i; k > j+1 ; k--) {
                    array[k] = array[k-1];
                }
                array[j+1] = current;
             }
        }
    }

    /**
     * 归并排序
     * @param array
     * @param start
     * @param end
     */
    private static void doMerger(int[] array, int start, int end) {
        if (end == start) {
            return;
        }
        int middle = (end + start) / 2;
        doMerger(array, start, middle);

        doMerger(array, middle + 1, end);

        doMergerAll(array, start, middle, end);
    }

    private static void doMergerAll(int[] array, int start, int middle, int end) {
        System.out.println("左边，" + start + "中间，" + middle + "右边 " + end);
        int[] left = new int[middle - start + 1];
        int[] right = new int[end - (middle + 1) + 1];

        int leftIndex = 0;
        for (int i = start; i <= middle; i++) {
            left[leftIndex] = array[i];
            leftIndex++;
        }

//        System.out.println("左边填满");

        int rightIndex = 0;
        for (int i = middle + 1; i <= end; i++) {
            right[rightIndex] = array[i];
            rightIndex++;
        }

        int i = 0, j = 0;// right

        int index = start;

        while (i < left.length && j < right.length) {
            if (left[i] < right[j]) {
                array[index] = left[i];
                i++;
                index++;
            } else {
                array[index] = right[j];
                j++;
                index++;
            }
        }

        while (i < left.length) {
            array[index] = left[i];
            i++;
            index++;
        }

        while (j < right.length) {
            array[index] = right[j];
            j++;
            index++;
        }
    }

    /**
     * 冒泡排序
     * @param array
     */
    private static void doBubblingSort(int[] array) {
        for (int i = 0; i < array.length; i++) {

            for (int j = 0; j < array.length - 1 - i; j++) {
                if (array[j] > array[j + 1]) {
                    int temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }

    }

    private static void doPrint(int[] array) {
        System.out.println(" ");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + "  ");
        }
        System.out.println(" ");
    }

    /**
     * 选择排序
     * @param array
     */
    private static void doSelectionSort(int[] array) {

        for (int i = 0; i < array.length - 1; i++) {// 趟数
            int min = array[i];
            int index = i;
            for (int j = i; j < array.length - 1; j++) {
                if (min > array[j]) {
                    min = array[j];
                    index = j;
                }
            }
            int temp = array[index];
            array[index] = array[i];
            array[i] = temp;

            System.out.print(" 最小值 " + array[i] + "  ");
        }

    }

}
	
```

