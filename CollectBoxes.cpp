//https://archive.topcoder.com/ProblemStatement/pm/18172

#include <iostream>
#include <vector>
#include <algorithm>
#include <climits>
#include <cassert>
using namespace std;

class CollectBoxes {
public:
    static inline long long plusVal(long long x) {
        return (x >= 0) ? 2LL * x : 0LL;
    }
    static inline long long minusVal(long long x) {
        return (x >= 0) ? 0LL : -2LL * x;
    }

    long long collect(int N, vector<int> Bprefix, int seed, int blo, int bhi) {
        vector<long long> B(N);
        int pref = (int)Bprefix.size();
        for (int i = 0; i < pref; ++i)
            B[i] = Bprefix[i];

        const long long MOD    = (1LL << 31) - 1;
        long long       state  = seed;
        long long       spread = 1LL * bhi - blo + 1;
        for (int i = pref; i < N; ++i) {
            state    = (state * 1103515245LL + 12345) & MOD;
            B[i]     = blo + (state % spread);
        }

        sort(B.begin(), B.end());

        vector<long long> prefSum(N);
        prefSum[0] = B[0];
        for (int i = 1; i < N; ++i)
            prefSum[i] = prefSum[i - 1] + B[i];
        long long totalSum = prefSum.back();

        vector<long long> prefMin(N);
        vector<long long> suffMin(N);

        prefMin[0] = plusVal(B[0]);
        for (int i = 1; i < N; ++i)
            prefMin[i] = std::min(prefMin[i - 1], plusVal(B[i]));

        suffMin[N - 1] = minusVal(B[N - 1]);
        for (int i = N - 2; i >= 0; --i)
            suffMin[i] = std::min(suffMin[i + 1], minusVal(B[i]));

        long long answer = LLONG_MAX;
        for (int i = 0; i < N; ++i) {
            long long F         = B[i];
            long long cntLeft   = i + 1;
            long long sumLeft   = prefSum[i];
            long long cntRight  = N - cntLeft;
            long long sumRight  = totalSum - sumLeft;

            long long S = F * cntLeft - sumLeft + sumRight - F * cntRight;

            if (S == 0) {
                return 0LL;
            }

            long long A  = suffMin[i];
            long long Bv = prefMin[i];

            long long mF = std::min(F + A, Bv - F);

            long long T = 2LL * S + mF;
            if (T < answer) answer = T;
        }
        return answer;
    }
};

int main() {
    CollectBoxes cb;
    
    vector<int> v1;
    v1.push_back(11); v1.push_back(10);
    assert(cb.collect(2, v1, 0, 0, 0) == 11);
    
    vector<int> v2;
    v2.push_back(10); v2.push_back(10); v2.push_back(11); v2.push_back(10); v2.push_back(10);
    assert(cb.collect(5, v2, 0, 0, 0) == 12);
    
    vector<int> v3;
    v3.push_back(-10); v3.push_back(-11);
    assert(cb.collect(2, v3, 0, 0, 0) == 11);
    
    vector<int> v4;
    v4.push_back(-47); v4.push_back(47);
    assert(cb.collect(2, v4, 0, 0, 0) == 141);
    
    vector<int> v5;
    v5.push_back(10); v5.push_back(20); v5.push_back(30);
    assert(cb.collect(9, v5, 47, -20, 100) == 322);
    
    vector<int> v6;
    v6.push_back(42);
    assert(cb.collect(1, v6, 0, 0, 0) == 0);
    
    vector<int> v7;
    v7.push_back(47); v7.push_back(47);
    assert(cb.collect(2, v7, 3, 3, 3) == 0);
    
    vector<int> v8;
    v8.push_back(-5); v8.push_back(-5); v8.push_back(-5);
    assert(cb.collect(3, v8, 56, 66, 77) == 0);
    
    vector<int> v9;
    v9.push_back(1000000000); v9.push_back(-1000000000); v9.push_back(1000000000);
    assert(cb.collect(3, v9, 47, 47, 47) == 3000000000LL);
    
    vector<int> empty;
    assert(cb.collect(500000, empty, 47, -1000000000, 1000000000) == 526657236972880LL);
    assert(cb.collect(499999, empty, 43, -1000000000, 1000000000) == 526120454326314LL);
    
    vector<int> v10;
    v10.push_back(-244415920);
    assert(cb.collect(1, v10, 268543586, -76182073, 188933013) == 0);
    
    vector<int> v11;
    v11.push_back(225763432); v11.push_back(-262404342);
    assert(cb.collect(2, v11, 1105644294, -169875016, 100465896) == 713931206LL);
    
    vector<int> v12;
    v12.push_back(100); v12.push_back(100);
    assert(cb.collect(2, v12, 0, 0, 0) == 0);
    
    vector<int> v13;
    v13.push_back(1); v13.push_back(1);
    assert(cb.collect(2, v13, 1, 1, 1) == 0);
    
    vector<int> v14;
    v14.push_back(1);
    assert(cb.collect(50000, v14, 11465, 0, 10000) == 250263956LL);
    
    vector<int> v15;
    v15.push_back(4); v15.push_back(4);
    assert(cb.collect(2, v15, 1, 10, 20) == 0);
    
    vector<int> v16;
    v16.push_back(-5); v16.push_back(10);
    assert(cb.collect(1000, v16, 47, -20, -10) == 5453LL);
    
    cout << "All tests passed!" << endl;
    return 0;
} 