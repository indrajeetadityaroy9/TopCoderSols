//https://archive.topcoder.com/ProblemStatement/pm/18235

public class NumReverseEasy {
    public long getsum(int A, int B){
        long ans = 0;
        for(int x=A; x<=B; x++){
            int rev = reverse(x);
            ans += Math.max(x,rev);
        }
        return ans;
    }
    private int reverse(int x){
        int rev = 0;
        while(x>0){
            rev = rev*10 + x%10;
            x /= 10;
        }
        return rev;
    }
    
    public static void main(String[] args) {
        NumReverseEasy nre = new NumReverseEasy();
        assert nre.getsum(21, 23) == 75 : "Test 1 failed";
        assert nre.getsum(12, 21) == 489 : "Test 2 failed";
        assert nre.getsum(97, 101) == 495 : "Test 3 failed";
        assert nre.getsum(123, 127) == 2605 : "Test 4 failed";
        assert nre.getsum(1, 100000) == 6226873030L : "Test 5 failed";
        assert nre.getsum(7, 99876) == 6214480635L : "Test 6 failed";
        assert nre.getsum(47, 87654) == 5065338047L : "Test 7 failed";
        assert nre.getsum(3565, 8766) == 36490662L : "Test 8 failed";
        assert nre.getsum(2, 99999) == 6226773029L : "Test 9 failed";
        assert nre.getsum(98654, 99890) == 122803424L : "Test 10 failed";
        assert nre.getsum(12345, 54432) == 2370027018L : "Test 11 failed";
        System.out.println("All tests passed!");
    }
}
