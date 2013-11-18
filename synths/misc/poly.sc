{
var pulse,out,root,div_a,div_b;
var a,b,c;

div_a = Dseq([
4,4,4,4,
4,4,4,4,
4,4,4,4,

2,2,2,2,
2,2,2,2,
2,2,2,2,
], inf);

div_b = Dseq([
6,6,6,6,
6,6,6,6,
6,6,6,6,

3,3,3,3,
3,3,3,3,
3,3,3,3,
], inf);

pulse = TDuty.ar(Dseq([
1,1,1,1,
1,1,1,1,
1,1,1,1,

2,2,2,2,
2,2,2,2,
2,2,2,2,
],inf) / 8);
root = 60;

a = Ringz.ar(PulseDivider.ar(pulse,Demand.ar(pulse,0,div_a),100), (root+7).midicps, 0.5) * 0.2;
a = LPF.ar(a,(root + 7 + 24).midicps);

b = Ringz.ar(PulseDivider.ar(pulse,Demand.ar(pulse,0,div_b),100), (root+0).midicps, 0.5) * 0.2;
b = LPF.ar(b,(root + 0 + 24).midicps);

c = Ringz.ar(pulse, (root+12).midicps, 0.5) * 0.2;
c = LPF.ar(c,(root + 12 + 24).midicps);

out = Mix.ar([a,b]);
[out,out];
}.play;
