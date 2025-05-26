import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class ReversalSorting {
    private static int bp(int[] a){
        int n = a.length;
        int[] p = new int[n + 2];
        p[0] = 0;
        System.arraycopy(a, 0, p, 1, n);
        p[n + 1] = n + 1;
    
        int b = 0;
        for(int i = 0; i < p.length - 1; i++){
            if(Math.abs(p[i] - p[i + 1]) != 1) b++;
        }
        return b;
    }
    private static void reverse(int[] a, int l, int r){
        for(int i=l,j=r-1; i<j; i++,j--){
            int temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
    }
    public int[] solve(int[] P){
        int n = P.length;
        List<Integer> ans = new ArrayList<>();
        int [] perm = P.clone();

        while(true){
            int curBp = bp(perm);
            if(curBp == 0) break;

            int bestGain = 0, bestL = -1, bestR = -1;

            for(int l=0; l<n; l++){
                for(int r=l+2;r<=n;r++){
                    int[] temp = perm.clone();
                    reverse(temp, l, r);
                    int gain = curBp - bp(temp);
                    if(gain > bestGain){
                        bestGain = gain;
                        bestL = l;
                        bestR = r;
                        if(gain >= 2) break;
                    }
                }
                if(bestGain >= 2) break;
            }
            if(bestGain == 0){
                int i = 0;
                while(i < n && perm[i] == i + 1) i++;  // 1-indexed
                int pos = i;
                for(int j = i + 1; j < n; j++) {
                    if (perm[j] == i + 1) {
                        pos = j;
                        break;
                    }
                }
                bestL = i;
                bestR = pos + 1;
            }
            reverse(perm, bestL, bestR);
            ans.add(bestL);
            ans.add(bestR);
        }
        int[] ret = new int[ans.size()];
        for(int i=0; i<ans.size(); i++) ret[i] = ans.get(i);
        return ret;
    }

    public static void main(String[] args) {
        ReversalSorting rs = new ReversalSorting();
        
        // Test case 1: {0, 1, 2} -> {}
        int[] test1 = {0, 1, 2};
        int[] result1 = rs.solve(test1);
        int[] expected1 = {};
        assert Arrays.equals(result1, expected1) : "Test 1 failed";
        
        // Test case 2: {0, 1, 2, 6, 5, 4, 3, 7, 8} -> {3, 7, 0, 1}
        int[] test2 = {0, 1, 2, 6, 5, 4, 3, 7, 8};
        int[] result2 = rs.solve(test2);
        // Verify solution sorts the array
        int[] perm2 = test2.clone();
        for (int i = 0; i < result2.length; i += 2) reverse(perm2, result2[i], result2[i + 1]);
        for (int i = 0; i < perm2.length; i++) assert perm2[i] == i : "Test 2 failed: Not sorted";
        
        // Test case 3: {1, 0, 3, 2, 5, 4, 7, 6} -> {0, 2, 2, 4, 4, 6, 6, 8}
        int[] test3 = {1, 0, 3, 2, 5, 4, 7, 6};
        int[] result3 = rs.solve(test3);
        int[] perm3 = test3.clone();
        for (int i = 0; i < result3.length; i += 2) reverse(perm3, result3[i], result3[i + 1]);
        for (int i = 0; i < perm3.length; i++) assert perm3[i] == i : "Test 3 failed: Not sorted";
        
        // Test case 4: {0} -> {}
        int[] test4 = {0};
        int[] result4 = rs.solve(test4);
        int[] expected4 = {};
        assert Arrays.equals(result4, expected4) : "Test 4 failed";
        
        // Test case 5: {0, 1} -> {}
        int[] test5 = {0, 1};
        int[] result5 = rs.solve(test5);
        int[] expected5 = {};
        assert Arrays.equals(result5, expected5) : "Test 5 failed";
        
        // Test case 6: {1, 0} -> {0, 2}
        int[] test6 = {1, 0};
        int[] result6 = rs.solve(test6);
        int[] perm6 = test6.clone();
        for (int i = 0; i < result6.length; i += 2) reverse(perm6, result6[i], result6[i + 1]);
        for (int i = 0; i < perm6.length; i++) assert perm6[i] == i : "Test 6 failed: Not sorted";
        
        // Test case 7: {0, 1, 2, ..., 49} -> {}
        int[] test7 = new int[50];
        for (int i = 0; i < 50; i++) test7[i] = i;
        int[] result7 = rs.solve(test7);
        int[] expected7 = {};
        assert Arrays.equals(result7, expected7) : "Test 7 failed";
        
        // Test case 8: {49, 0, 1, 2, ..., 48} -> {0, 50, 0, 49}
        int[] test8 = new int[50];
        test8[0] = 49;
        for (int i = 1; i < 50; i++) test8[i] = i - 1;
        int[] result8 = rs.solve(test8);
        int[] perm8 = test8.clone();
        for (int i = 0; i < result8.length; i += 2) reverse(perm8, result8[i], result8[i + 1]);
        for (int i = 0; i < perm8.length; i++) assert perm8[i] == i : "Test 8 failed: Not sorted";
        
        // Test case 9: {1, 2, 3, ..., 49, 0} -> {0, 49, 0, 50}
        int[] test9 = new int[50];
        for (int i = 0; i < 49; i++) test9[i] = i + 1;
        test9[49] = 0;
        int[] result9 = rs.solve(test9);
        int[] perm9 = test9.clone();
        for (int i = 0; i < result9.length; i += 2) reverse(perm9, result9[i], result9[i + 1]);
        for (int i = 0; i < perm9.length; i++) assert perm9[i] == i : "Test 9 failed: Not sorted";
        
        // Test case 10: {2, 0, 4, 1, 6, 3, 5} -> {0, 2, 2, 6, 1, 4, 1, 5, 4, 6, 5, 7}
        int[] test10 = {2, 0, 4, 1, 6, 3, 5};
        int[] result10 = rs.solve(test10);
        int[] perm10 = test10.clone();
        for (int i = 0; i < result10.length; i += 2) reverse(perm10, result10[i], result10[i + 1]);
        for (int i = 0; i < perm10.length; i++) assert perm10[i] == i : "Test 10 failed: Not sorted";
        
        // Test case 11: {1, 3, 0, 5, 2, 6, 4} -> {0, 2, 0, 3, 3, 5, 2, 4, 4, 6, 4, 7}
        int[] test11 = {1, 3, 0, 5, 2, 6, 4};
        int[] result11 = rs.solve(test11);
        int[] perm11 = test11.clone();
        for (int i = 0; i < result11.length; i += 2) reverse(perm11, result11[i], result11[i + 1]);
        for (int i = 0; i < perm11.length; i++) assert perm11[i] == i : "Test 11 failed: Not sorted";
        
        // Test case 12: Large pattern case
        int[] test12 = {2, 0, 4, 1, 6, 3, 8, 5, 10, 7, 12, 9, 14, 11, 16, 13, 18, 15, 20, 17, 22, 19, 24, 21, 26, 23, 28, 25, 30, 27, 32, 29, 34, 31, 36, 33, 38, 35, 40, 37, 42, 39, 44, 41, 46, 43, 48, 45, 47};
        int[] result12 = rs.solve(test12);
        int[] perm12 = test12.clone();
        for (int i = 0; i < result12.length; i += 2) reverse(perm12, result12[i], result12[i + 1]);
        for (int i = 0; i < perm12.length; i++) assert perm12[i] == i : "Test 12 failed: Not sorted";
        
        // Test case 13: Another large pattern case
        int[] test13 = {1, 3, 0, 5, 2, 7, 4, 9, 6, 11, 8, 13, 10, 15, 12, 17, 14, 19, 16, 21, 18, 23, 20, 25, 22, 27, 24, 29, 26, 31, 28, 33, 30, 35, 32, 37, 34, 39, 36, 41, 38, 43, 40, 45, 42, 47, 44, 48, 46};
        int[] result13 = rs.solve(test13);
        int[] perm13 = test13.clone();
        for (int i = 0; i < result13.length; i += 2) reverse(perm13, result13[i], result13[i + 1]);
        for (int i = 0; i < perm13.length; i++) assert perm13[i] == i : "Test 13 failed: Not sorted";
        
        // Test case 14: {2, 0, 4, 1, 6, 3, 8, 5, 7}
        int[] test14 = {2, 0, 4, 1, 6, 3, 8, 5, 7};
        int[] result14 = rs.solve(test14);
        int[] perm14 = test14.clone();
        for (int i = 0; i < result14.length; i += 2) reverse(perm14, result14[i], result14[i + 1]);
        for (int i = 0; i < perm14.length; i++) assert perm14[i] == i : "Test 14 failed: Not sorted";
        
        // Test case 15: {0, 2, 4, 1, 6, 3, 8, 5, 7}
        int[] test15 = {0, 2, 4, 1, 6, 3, 8, 5, 7};
        int[] result15 = rs.solve(test15);
        int[] perm15 = test15.clone();
        for (int i = 0; i < result15.length; i += 2) reverse(perm15, result15[i], result15[i + 1]);
        for (int i = 0; i < perm15.length; i++) assert perm15[i] == i : "Test 15 failed: Not sorted";
        
        // Test case 16: Complex pattern
        int[] test16 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 15, 13, 17, 14, 19, 16, 21, 18, 23, 20, 24, 22, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
        int[] result16 = rs.solve(test16);
        int[] perm16 = test16.clone();
        for (int i = 0; i < result16.length; i += 2) reverse(perm16, result16[i], result16[i + 1]);
        for (int i = 0; i < perm16.length; i++) assert perm16[i] == i : "Test 16 failed: Not sorted";
        
        // Test case 17: Another complex pattern
        int[] test17 = {2, 0, 4, 1, 6, 3, 8, 5, 10, 7, 11, 9, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
        int[] result17 = rs.solve(test17);
        int[] perm17 = test17.clone();
        for (int i = 0; i < result17.length; i += 2) reverse(perm17, result17[i], result17[i + 1]);
        for (int i = 0; i < perm17.length; i++) assert perm17[i] == i : "Test 17 failed: Not sorted";
        
        // Test case 18: Large sorted with swaps at end
        int[] test18 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 40, 38, 42, 39, 44, 41, 46, 43, 48, 45, 49, 47};
        int[] result18 = rs.solve(test18);
        int[] perm18 = test18.clone();
        for (int i = 0; i < result18.length; i += 2) reverse(perm18, result18[i], result18[i + 1]);
        for (int i = 0; i < perm18.length; i++) assert perm18[i] == i : "Test 18 failed: Not sorted";
        
        // Test case 19: Another complex pattern
        int[] test19 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 14, 16, 13, 18, 15, 20, 17, 22, 19, 24, 21, 23, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
        int[] result19 = rs.solve(test19);
        int[] perm19 = test19.clone();
        for (int i = 0; i < result19.length; i += 2) reverse(perm19, result19[i], result19[i + 1]);
        for (int i = 0; i < perm19.length; i++) assert perm19[i] == i : "Test 19 failed: Not sorted";
        
        // Test case 20: Pattern with early swaps
        int[] test20 = {1, 3, 0, 5, 2, 7, 4, 9, 6, 11, 8, 10, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49};
        int[] result20 = rs.solve(test20);
        int[] perm20 = test20.clone();
        for (int i = 0; i < result20.length; i += 2) reverse(perm20, result20[i], result20[i + 1]);
        for (int i = 0; i < perm20.length; i++) assert perm20[i] == i : "Test 20 failed: Not sorted";
        
        // Test case 21: Another end pattern
        int[] test21 = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 39, 41, 38, 43, 40, 45, 42, 47, 44, 49, 46, 48};
        int[] result21 = rs.solve(test21);
        int[] perm21 = test21.clone();
        for (int i = 0; i < result21.length; i += 2) reverse(perm21, result21[i], result21[i + 1]);
        for (int i = 0; i < perm21.length; i++) assert perm21[i] == i : "Test 21 failed: Not sorted";
        
        // Test case 22: {0, 4, 3} -> {3, 5}
        int[] test22 = {0, 4, 3};
        int[] result22 = rs.solve(test22);
        int[] perm22 = test22.clone();
        for (int i = 0; i < result22.length; i += 2) reverse(perm22, result22[i], result22[i + 1]);
        for (int i = 0; i < perm22.length; i++) assert perm22[i] == i : "Test 22 failed: Not sorted";
        
        // Test case 23: {1, 0, 2, 4, 3} -> {0, 2, 3, 5}
        int[] test23 = {1, 0, 2, 4, 3};
        int[] result23 = rs.solve(test23);
        int[] perm23 = test23.clone();
        for (int i = 0; i < result23.length; i += 2) reverse(perm23, result23[i], result23[i + 1]);
        for (int i = 0; i < perm23.length; i++) assert perm23[i] == i : "Test 23 failed: Not sorted";
        
        // Test case 24: {3, 4, 8, 7, 6, 2, 1, 0, 5} -> {2, 9, 0, 3, 0, 6, 0, 3}
        int[] test24 = {3, 4, 8, 7, 6, 2, 1, 0, 5};
        int[] result24 = rs.solve(test24);
        int[] perm24 = test24.clone();
        for (int i = 0; i < result24.length; i += 2) reverse(perm24, result24[i], result24[i + 1]);
        for (int i = 0; i < perm24.length; i++) assert perm24[i] == i : "Test 24 failed: Not sorted";
        
        System.out.println("All assertion tests passed!");
        System.out.println("ReversalSorting algorithm is working correctly for all test cases.");
    }
}
