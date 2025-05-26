//https://archive.topcoder.com/ProblemStatement/pm/17816

import java.util.*;

public class FlippingCoinSum {
    public int[] flip(int[] faceUp, int[] faceDown, int goal){
        int nUp = faceUp.length;
        int nDn = faceDown.length;
        int n = nUp+nDn;

        int[] val = new int[n];
        int idx = 0;
        for(int v: faceDown) val[idx++] = v;
        for(int v: faceUp) val[idx++] = -v;
        int startSum = 0;
        for(int v: faceUp) startSum += v;

        int diff = goal-startSum;
        int tot = 0;
        for(int v: val) tot += Math.abs(v);
        if(Math.abs(diff) > tot){
            return new int[]{-123456789};
        }
        int shift = tot;
        int size = 2*tot+1;
        final int INF = 1 << 20;
        int[] best = new int[size];
        int[] prev = new int[size];
        int[] who = new int[size];
        Arrays.fill(best,INF);
        Arrays.fill(prev,-1);
        Arrays.fill(who,-1);

        best[shift] = 0;
        for(int i=0; i<n; i++){
            int w = val[i];
            int[] nextBest = best.clone();
            int[] nextPrev = prev.clone();
            int[] nextWho = who.clone();
            for(int s=0; s<size; s++){
                if(best[s] == INF) continue;
                int ns = s +w;
                if(ns < 0 || ns>= size) continue;
                if(best[s]+1 < nextBest[ns]){
                    nextBest[ns] = best[s]+1;
                    nextPrev[ns] =s;
                    nextWho[ns] =i;
                }
            }
            best = nextBest;
            prev = nextPrev;
            who = nextWho;
        }
        int target = diff + shift;
        if(best[target] == INF){
            return new int[]{-123456789};
        }
        ArrayList<Integer> answer = new ArrayList<>();
        int cur = target;
        while(cur != shift){
            int coinIdx = who[cur];
            answer.add(val[coinIdx]);
            cur = prev[cur];
        }
        int[] res = new int[answer.size()];
        for(int i=0; i<answer.size(); i++) res[i] = answer.get(i);
        return res;
     }

    public static void main(String[] args) {
        FlippingCoinSum obj = new FlippingCoinSum();
        // Example 1
        int[] res1 = obj.flip(new int[]{2, 2, 5}, new int[]{1, 10}, 9);
        assert Arrays.equals(res1, new int[]{});
        // Example 2
        int[] res2 = obj.flip(new int[]{2, 2, 5}, new int[]{1, 10}, 14);
        assert Arrays.equals(res2, new int[]{-5, 10});
        // Example 3
        int[] res3 = obj.flip(new int[]{2, 2, 5}, new int[]{2, 10}, 3);
        assert Arrays.equals(res3, new int[]{-123456789});
        // Example 4
        int[] res4 = obj.flip(new int[]{2, 2, 5}, new int[]{100, 10}, 5);
        assert Arrays.equals(res4, new int[]{-2, -2});
        // Example 5
        int[] res5 = obj.flip(new int[]{1, 1, 1, 1, 1, 1, 1}, new int[]{1, 1, 1, 1, 1, 1, 1}, 10);
        assert Arrays.equals(res5, new int[]{1, 1, 1});
        // Example 6
        int[] res6 = obj.flip(new int[]{1, 1, 2, 2, 2, 3, 3, 3, 3}, new int[]{1, 2, 3, 4, 5, 6, 7}, 0);
        assert Arrays.equals(res6, new int[]{-1, -1, -2, -2, -2, -3, -3, -3, -3});
        // Example 7
        int[] res7 = obj.flip(new int[]{5, 5, 5, 5, 47, 100}, new int[]{42, 80, 174}, 147);
        assert Arrays.equals(res7, new int[]{-100, 80});
        // Example 8
        int[] faceUp8 = new int[50];
        int[] faceDown8 = new int[50];
        Arrays.fill(faceUp8, 1000);
        Arrays.fill(faceDown8, 1000);
        int[] res8 = obj.flip(faceUp8, faceDown8, 100000);
        int[] expected8 = new int[50];
        Arrays.fill(expected8, 1000);
        assert Arrays.equals(res8, expected8);
        // Example 9
        int[] faceUp9 = new int[50];
        int[] faceDown9 = new int[50];
        for (int i = 0; i < 50; i++) {
            faceUp9[i] = 951 + i;
            faceDown9[i] = 951 + i;
        }
        int[] res9 = obj.flip(faceUp9, faceDown9, 97550);
        int[] expected9 = new int[50];
        for (int i = 0; i < 50; i++) expected9[i] = 951 + i;
        assert Arrays.equals(res9, expected9);
        System.out.println("All tests passed.");

        // Test 10
        int[] t10 = new int[50];
        int[] t10b = new int[50];
        for (int i = 0; i < 50; i++) t10[i] = t10b[i] = 951 + i;
        int[] r10 = obj.flip(t10, t10b, 0);
        int[] e10 = new int[50];
        for (int i = 0; i < 50; i++) e10[i] = -(951 + i);
        assert Arrays.equals(r10, e10);
        // Test 11
        int[] t11 = new int[50];
        int[] t11b = new int[50];
        for (int i = 0; i < 50; i++) t11[i] = t11b[i] = 951 + i;
        int[] r11 = obj.flip(t11, t11b, 12345);
        assert Arrays.equals(r11, new int[]{-123456789});
        // Test 12
        int[] t12 = {8, 975, 982, 970, 997, 976, 987, 996, 990, 965, 969, 978, 971, 1, 1000, 980, 999, 995, 991, 973, 988, 993, 963, 256, 964, 968, 974, 961, 981, 992, 998, 4, 64, 983, 989, 967, 2, 962, 32, 979, 977, 512, 16, 984, 966, 986, 994, 128, 985, 972};
        int[] t12b = t12.clone();
        int[] r12 = obj.flip(t12, t12b, 40243);
        assert Arrays.equals(r12, new int[]{});
        // Test 13
        int[] r13 = obj.flip(t12, t12b, 80486);
        assert Arrays.equals(r13, t12);
        // Test 14
        int[] r14 = obj.flip(t12, t12b, 0);
        int[] e14 = new int[t12.length];
        for (int i = 0; i < t12.length; i++) e14[i] = -t12[i];
        assert Arrays.equals(r14, e14);
    }
}
