package example.arithmetic;

/**
 * 二分查找
 * Created by liaoxudong on 2017/5/4.
 */
public class BitSearchDemo {

    public static void main(String[] args) {
        int[] arrays = {1,2,5,9,11,12,23,24,53,64,89};
        System.out.println(binarySearch(arrays,9));
        System.out.println(bitSearch(arrays,9,0,arrays.length-1));
    }

    /**
     * 非递归实现
     * @param arrays 有序数组
     * @param val 待查找值
     * @return
     */
    public static int binarySearch(int[] arrays,int val){
        int min = 0;
        int max = arrays.length - 1;
        int mid;
        while(min <= max){
            mid = (min+max)/2;
            if(arrays[mid] == val){
                return mid + 1;
            }else if(arrays[mid]<val){
                min = mid +1;
            }else{
                max = mid-1;
            }
        }
        return -1;
    }

    /**
     * 递归实现
     * @param arrays 有序数组
     * @param val 待查找值
     * @param min 最小下标
     * @param max 最大下标
     * @return
     */
    public static int bitSearch(int[] arrays,int val,int min,int max){
        if(min <= max){
            int mid = (min+max)/2;
            if(val == arrays[mid]){
                return mid +1;
            }else if(val <arrays[mid]){
                return bitSearch(arrays,val,min,mid-1);
            }else{
                return bitSearch(arrays,val,mid+1,max);
            }
        }
        return -1;
    }
}
