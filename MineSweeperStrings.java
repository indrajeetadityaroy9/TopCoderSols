//https://archive.topcoder.com/ProblemStatement/pm/18236

public class MineSweeperStrings {
    private static final int DOT  = 0;    
    private static final int STAR = 1;    
    private static final int ZERO = 2;   
    private static final int ONE  = 3;    
    private static final int TWO  = 4;    

    private static final long LIMIT = 1_000_000_000_000_000_005L;

    private int len;             
    private long[][][] dp;      

    private long ways(int pos, int prev, int prev2) {
        if (pos == len) { 
            if (prev >= ZERO) {
                int required = prev - ZERO;    
                int left     = (prev2 == STAR) ? 1 : 0;
                return (required == left) ? 1 : 0;
            }
            return 1;
        }

        if (dp[pos][prev][prev2] != -1) return dp[pos][prev][prev2];

        long res = 0;
        for (int c = STAR; c <= TWO; ++c) { 
            if (prev >= ZERO) {
                int required = prev - ZERO;
                int left     = (prev2 == STAR) ? 1 : 0;
                int right    = (c     == STAR) ? 1 : 0;
                if (required != left + right) continue;
            }
            res += ways(pos + 1, c, prev);
            if (res > LIMIT) res = LIMIT;
        }
        return dp[pos][prev][prev2] = res;
    }

    public String generate(int N, long X) {
        len = N;
        dp  = new long[N + 1][5][5];
        for (int i = 0; i <= N; ++i)
            for (int j = 0; j < 5; ++j)
                java.util.Arrays.fill(dp[i][j], -1);

        long total = ways(0, DOT, DOT);
        if (X >= total) return "";

        StringBuilder sb  = new StringBuilder();
        int prev2 = DOT, prev = DOT; 

        for (int pos = 0; pos < N; ++pos) {
            boolean chosen = false;
            for (int c = STAR; c <= TWO; ++c) { 
                if (prev >= ZERO) {
                    int required = prev - ZERO;
                    int left     = (prev2 == STAR) ? 1 : 0;
                    int right    = (c     == STAR) ? 1 : 0;
                    if (required != left + right) continue;
                }

                long cnt = ways(pos + 1, c, prev);
                if (X < cnt) {
                    sb.append(codeToChar(c));
                    prev2  = prev;
                    prev   = c;
                    chosen = true;
                    break;
                }
                X -= cnt;
            }
            if (!chosen) return ""; 
        }
        return (X == 0) ? sb.toString() : "";
    }

    private static char codeToChar(int code) {
        switch (code) {
            case STAR: return '*';
            case ZERO: return '0';
            case ONE : return '1';
            case TWO : return '2';
        }
        throw new AssertionError();
    }
    
    public static void main(String[] args) {
        MineSweeperStrings mss = new MineSweeperStrings();
        assert mss.generate(1, 0).equals("*") : "Test 1 failed";
        assert mss.generate(1, 1).equals("0") : "Test 2 failed";
        assert mss.generate(1, 47).equals("") : "Test 3 failed";
        assert mss.generate(7, 71).equals("0001*2*") : "Test 4 failed";
        assert mss.generate(14, 9876543210987654L).equals("") : "Test 5 failed";
        assert mss.generate(47, 47).equals("*****************************************11*2*1") : "Test 6 failed";
        assert mss.generate(3, 0).equals("***") : "Test 7 failed";
        assert mss.generate(3, 1).equals("**1") : "Test 8 failed";
        assert mss.generate(3, 2).equals("*10") : "Test 9 failed";
        assert mss.generate(3, 3).equals("*2*") : "Test 10 failed";
        assert mss.generate(3, 4).equals("000") : "Test 11 failed";
        assert mss.generate(3, 5).equals("01*") : "Test 12 failed";
        assert mss.generate(3, 6).equals("1**") : "Test 13 failed";
        assert mss.generate(3, 7).equals("1*1") : "Test 14 failed";
        assert mss.generate(3, 8).equals("") : "Test 15 failed";
        assert mss.generate(3, 124125).equals("") : "Test 16 failed";
        assert mss.generate(3, 1000000000000000000L).equals("") : "Test 17 failed";
        assert mss.generate(60, 1000000000000000000L).equals("1**2*2******11*11*11**2***2*11***2*11*101*******************") : "Test 18 failed";
        assert mss.generate(60, 100000000000000000L).equals("***11*1001*11****11**2*2*****11*2**2****11******************") : "Test 19 failed";
        assert mss.generate(60, 10000000000000000L).equals("******1001*2*****2**2*2***101*11*2*2*100001*****************") : "Test 20 failed";
        assert mss.generate(60, 100000000000000L).equals("*************11*11**2*2***2****100001*2*11***100000000000000") : "Test 21 failed";
        assert mss.generate(60, 100000000000L).equals("***********************11*2**101*****2*11*11*2**100000000000") : "Test 22 failed";
        assert mss.generate(60, 100000000L).equals("*********************************11*2*2**11*2*10001*********") : "Test 23 failed";
        assert mss.generate(60, 100000L).equals("*******************************************2*****2**11******") : "Test 24 failed";
        assert mss.generate(60, 100L).equals("*****************************************************2***100") : "Test 25 failed";
        assert mss.generate(60, 0).equals("************************************************************") : "Test 26 failed";
        assert mss.generate(47, 3295230580238502L).equals("") : "Test 27 failed";
        assert mss.generate(47, 3205230582085855L).equals("") : "Test 28 failed";
        assert mss.generate(47, 2345).equals("***********************************101***11***1") : "Test 29 failed";
        assert mss.generate(47, 240359305305380658L).equals("") : "Test 30 failed";
        assert mss.generate(1, 3).equals("") : "Test 31 failed";
        assert mss.generate(59, 1000000000L).equals("*****************************2*11*2***2**11*101**1000000000") : "Test 32 failed";
        System.out.println("All tests passed!");
    }
}
