//https://archive.topcoder.com/ProblemStatement/pm/18025

#include <algorithm>
#include <iostream>
#include <cassert>
#include <vector>
using namespace std;
void dfs(int idx, const vector<int> &a, long long sInv, long long d, vector<pair<long long, long long> >&out){
    if(idx == (int)a.size()){
        out.emplace_back(d,sInv);
        return;
    }
    dfs(idx+1,a,sInv,d,out);
    dfs(idx+1,a,sInv+a[idx],d+a[idx],out);
    dfs(idx+1,a,sInv+a[idx],d-a[idx],out);
}
long long split(vector<int>skill, int K){
    const int n = skill.size();
    int mid = n/2;
    vector<int>left(skill.begin(), skill.begin()+mid);
    vector<int>right(skill.begin()+mid,skill.end());
    vector<pair<long long, long long> > L,R;
    dfs(0,left,0,0,L);
    dfs(0,right,0,0,R);
    sort(R.begin(),R.end());
    vector<pair<long long,long long> >Runiq;
    for(auto &p: R){
        if(Runiq.empty() || Runiq.back().first != p.first){
            Runiq.push_back(p);
        }else{
            Runiq.back().second = max(Runiq.back().second,p.second);
        }
    }
    const int m = (int) Runiq.size();
    vector<long long> arrD(m), arrS(m);
    for(int i=0; i<m; i++){
        arrD[i] = Runiq[i].first;
        arrS[i] = Runiq[i].second;
    }
    vector<long long> sufPlus(m),preMinus(m);
    for(int i=m-1; i>=0;i--){
        long long val = arrS[i] - (long long)K * arrD[i];
        sufPlus[i] = (i==m-1) ? val : max(val,sufPlus[i+1]);
    }
    for(int i=0; i<m; i++){
        long long val = arrS[i] + (long long)K * arrD[i];
        preMinus[i] = (i==0) ? val : max(val,preMinus[i-1]);
    }
    long long bestV = LLONG_MIN/4;
    for(auto &p: L){
        long long d1 = p.first;
        long long s1 = p.second;
        long long t = -d1;
        int idx = lower_bound(arrD.begin(), arrD.end(),t) - arrD.begin();
        if(idx < m){
            long long candidate = s1+ (sufPlus[idx]-(long long)K * d1);
            if(candidate > bestV) bestV = candidate;
        }
        if(idx > 0){
            long long candidate = s1+ (preMinus[idx-1]+(long long)K*d1);
            if(candidate > bestV) bestV = candidate;
        }
    }
    long long tot = 0;
    for(int v: skill) tot +=v;
    return (long long)(tot - bestV);
}
int main() {
    std::vector<int> v1 = {100, 200, 300, 400, 1000};
    assert(split(v1, 47) == 0);
    std::vector<int> v2 = {100};
    assert(split(v2, 1) == 100);
    std::vector<int> v3 = {100, 200, 300, 1000, 4000, 4010, 400};
    assert(split(v3, 2) == 20);
    assert(split(v3, 1000) == 8010);
    std::vector<int> v4 = {47224006, 8391988, 57738621, 74308314, 60890673, 76610215, 46099729, 34551385, 68779119, 51882812, 52669104, 5490676, 56174496, 3175573, 13751392, 84089, 35407528, 64632920, 73375192, 1226181};
    assert(split(v4, 1899) == 175241);
    std::vector<int> v5 = {30876380, 31909170, 70232057, 68981569, 48195186, 59353898, 32487244, 4464634, 75288849, 50219876, 47439602, 45890889, 74865366, 77189027, 26304436, 32026926, 4735058, 16844270, 44819067, 13146234, 48699377};
    assert(split(v5, 463249) == 39435979);
    std::vector<int> v6 = {68223646, 72494800, 33934876, 13032362, 30461760, 44944006, 29333810, 63220607, 14783506, 55923194, 47385437, 38428994, 32075750, 74526546, 35109974, 47908113, 71495689, 75884515, 5383447, 64954138, 41903667, 75529437};
    assert(split(v6, 6227) == 1120860);
    std::vector<int> v7 = {69712563, 29653665, 15292187, 27794651, 50984922, 65149124, 33975703, 74899271, 20025591, 59577420, 2038796, 22856692, 73416108, 11109993, 10013282, 35668352, 68616780, 73398917, 63956112, 58466977, 49067131, 4453565, 35569502};
    assert(split(v7, 127377) == 6624368);
    std::vector<int> v8 = {42277653, 2984115, 77433000, 32617533, 28368894, 46434550, 57933396, 24698211, 23713244, 63054543, 26665903, 17163265, 13209404, 18342177, 76453270, 46178285, 70786584, 52661755, 14506944, 35818038, 59582024, 60653, 16425003, 55575541};
    assert(split(v8, 6) == 282);
    std::vector<int> v9 = {2781208, 35466738, 5597579, 18849326, 24317464, 68673581, 65898658, 26997685, 66396816, 70903855, 60879960, 75645856, 76146683, 1340479, 9911364, 23504514, 20208789, 72939858, 45411577, 6266053, 31474227, 8218842, 50832166, 16528644, 2981515};
    assert(split(v9, 6) == 18);
    std::vector<int> v10 = {7233265, 73531947, 41294168, 32093870, 71791362, 20294364, 73810122, 77813849, 36662996, 76616138, 10831796, 75559089, 65934993, 45998527, 74695084, 7003813, 15380714, 55496059, 56055471, 47478537, 40642778, 75741865, 65666713, 18743785, 8527695, 68165870};
    assert(split(v10, 15) == 150);
    std::vector<int> v11 = {79999968, 46445568, 78951424, 79999872, 79999984, 79999992, 63222784, 77902848, 79999998, 71611392, 79475712, 79999936, 79967232, 79999999, 79999488, 75805696, 79995904, 79934464, 79999744, 79998976, 79737856, 79983616, 79868928, 79991808, 79999996, 79997952};
    assert(split(v11, 1) == 8191);
    std::vector<int> v12 = {79999999, 79999744, 79999984, 75805696, 63222784, 79967232, 79998976, 78951424, 79999968, 79475712, 79999872, 79737856, 79999998, 79999992, 71611392, 79868928, 79983616, 79934464, 46445568, 79999996, 79999488, 79999936, 79991808, 79995904, 79997952, 77902848};
    assert(split(v12, 999999) == 987956417);
    std::vector<int> v13 = {79999992, 79999998, 79999968, 79999984, 79999996, 79999999};
    assert(split(v13, 42) == 294);
    std::vector<int> v14 = {79737856, 79999968, 79998976, 79999872, 79999999, 79997952, 79999984, 79999996, 79999936, 79868928, 79991808, 79999998, 79995904, 79934464, 79475712, 79999744, 79999992, 79999488, 79983616, 79967232};
    assert(split(v14, 309202) == 316313646);
    cout << "All test cases passed!" << endl;
    return 0;
} 