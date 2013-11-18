{
var tweak = 0.8;
var tone = MoogLadder.ar(LeakDC.ar(Pulse.ar(4*tweak, width: 0.33)), SinOsc.ar(2*tweak).range(100,10000), SinOsc.ar(2*tweak).range(0.2,0.3)).dup * SinOsc.ar(3*tweak).range(0,1);
tone = HPF.ar(tone, 1000);
}.play;
