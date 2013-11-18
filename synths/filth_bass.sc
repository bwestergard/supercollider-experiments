{
var points,s,sel,freq;
freq = (47+12+7).midicps;
points = LFNoise2.ar(0.8!40);
sel = VarSaw.ar(freq * [0.999,2,0.499,0.5,2,1.01] * 0.5, width: LFNoise2.ar(1/2).range(0.2,0.5)).range(0, points.size-1);
s = (SelectX.ar(sel, points) * 10).tanh;
s = LeakDC.ar(Splay.ar(s, 1)) * 0.3;
s = s + Pulse.ar(freq/2, width: 0.3).dup * 0.6;
RLPF.ar(s, VarSaw.ar(1.5, iphase: pi/2, width: 0.3).exprange(20,2000), 0.8);
}.play;

{
var points,s,sel,freq;
freq = (47).midicps;
points = LFNoise2.ar(0.8!40);
sel = VarSaw.ar(freq * [0.999,2,0.499,0.5,2,1.01] * 0.5, width: LFNoise2.ar(1/2).range(0.2,0.5)).range(0, points.size-1);
s = SelectX.ar(sel, points);
s = LeakDC.ar(Splay.ar(s, 1)) * 0.3;
s = s + SinOsc.ar(freq/2, width: 0.3).dup * 0.6;
RLPF.ar(s, VarSaw.ar(MouseButton.kr(3,8), width: 0.2).exprange(10,2000), 0.8);
}.play;
